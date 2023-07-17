package ni.com.prisonsolver.support.helper;

import ni.com.prisonsolver.domain.*;
import ni.com.prisonsolver.factory.guardviewrange.GuardViewRangeStrategyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Component
public class PathwayFinder {

  private static final Logger LOG = LoggerFactory.getLogger(PathwayFinder.class);

  private final GuardViewRangeStrategyFactory guardViewRangeStrategyFactory;

  public PathwayFinder(GuardViewRangeStrategyFactory guardViewRangeStrategyFactory) {
    this.guardViewRangeStrategyFactory = guardViewRangeStrategyFactory;
  }

  public boolean calculatePathWay(Challenge challenge) {
    requireNonNull(challenge, "Challenge instance must not be null");
    requireNonNull(challenge.getSolutionMap(), "Solution Map instance must not be null");

    LOG.info("PRISON MAP -->\n{}", challenge.drawPrisonMap());
    computeGuardsViewRange(challenge);
    LOG.info("GUARD VIEW RANGE MAP -->\n{}", challenge.drawSolutionMap());

    List<Location> pathway = new ArrayList<>();
    boolean canEscape = this.canEscape(challenge.getSolutionMap(), challenge.getPrisonerLocation(),
      challenge.getRows(), challenge.getColumns(), pathway);
    drawPathwayOnSolutionMap(challenge, pathway);
    LOG.info("SOLUTION MAP -->\n{}", challenge.drawSolutionMap());
    return canEscape;
  }

  private void drawPathwayOnSolutionMap(Challenge challenge, List<Location> pathway) {
    pathway.forEach(location -> {
      Cell cell = challenge.getSolutionMap()[location.rowIndex()][location.columnIndex()];
      if (cell instanceof Road) {
        challenge.getSolutionMap()[location.rowIndex()][location.columnIndex()] = new EscapeRoute();
      }
    });
  }

  private void computeGuardsViewRange(Challenge challenge) {
    challenge.getGuards().forEach((location, guard) -> {
      guardViewRangeStrategyFactory
        .getStrategy(guard)
        .ifPresent(f -> f.calculateGuardViewRange(challenge, location));
    });
  }

  private boolean canEscape(Cell[][] maze, Location currentLocation, int rows, int columns, List<Location> pathway) {
    if (this.isValidCurrentLocation(currentLocation, rows, columns)) {
      return false;
    }

    var currentCell = maze[currentLocation.rowIndex()][currentLocation.columnIndex()];

    if (!(currentCell instanceof Visitable)
      || ((Visitable) currentCell).isVisited()) {
      return false;
    }

    if (currentCell instanceof Exit) {
      pathway.add(currentLocation);
      return true;
    }

    ((Visitable) currentCell).setVisited(true);

    for (Direction direction : CardinalDirections.toList()) {
      var nextLocation = this.computeNextLocation(currentLocation, direction);

      if (this.canEscape(maze, nextLocation, rows, columns, pathway)) {
        pathway.add(currentLocation);
        return true;
      }
    }
    return false;
  }

  private boolean isValidCurrentLocation(Location location, int rows, int columns) {
    return location.rowIndex() < 0 || location.rowIndex() > rows
      || location.columnIndex() < 0 || location.columnIndex() >= columns;
  }

  private Location computeNextLocation(Location currentLocation, Direction direction) {
    int nextRowIndex = currentLocation.rowIndex() + direction.x();
    int nextColumnIndex = currentLocation.columnIndex() + direction.y();
    return new Location(nextRowIndex, nextColumnIndex);
  }
}
