package ni.com.prisonsolver.factory.guardviewrange;

import ni.com.prisonsolver.domain.Challenge;
import ni.com.prisonsolver.domain.Location;
import ni.com.prisonsolver.domain.Symbol;
import ni.com.prisonsolver.domain.guard.GuardLookingDown;

public class ViewRangeLookingDown implements GuardViewRangeStrategy {

  @Override
  public void calculateGuardViewRange(Challenge challenge, Location initialLocation) {
    int sliderRowDown = initialLocation.rowIndex();
    int columnIndex = initialLocation.columnIndex();

    if (!this.isRowIndexValid(sliderRowDown, challenge.getRows())) {
      return;
    }

    if (!this.isColumnIndexValid(columnIndex, challenge.getColumns())) {
      return;
    }

    if (!this.isCellInstanceValid(challenge.getSolutionMap()[sliderRowDown][columnIndex],
      GuardLookingDown.class)) {
      return;
    }

    sliderRowDown++;

    while (sliderRowDown < challenge.getRows() &&
      challenge.getSolutionMap()[sliderRowDown][columnIndex].symbol() == Symbol.ROAD) {
      challenge.getSolutionMap()[sliderRowDown][columnIndex] = new GuardLookingDown();
      sliderRowDown++;
    }
  }
}
