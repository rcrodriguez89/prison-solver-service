package ni.com.prisonsolver.support.helper;

import ni.com.prisonsolver.domain.Cell;
import ni.com.prisonsolver.domain.Challenge;
import ni.com.prisonsolver.domain.Location;
import ni.com.prisonsolver.domain.Prisoner;
import ni.com.prisonsolver.domain.guard.Guard;
import ni.com.prisonsolver.dto.PrisonDtoRequest;
import ni.com.prisonsolver.factory.CellFactory;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;
import static ni.com.prisonsolver.support.util.Checks.verify;

@Component
public class PrisonMapper {

  private final CellFactory cellFactory;

  public PrisonMapper(CellFactory cellFactory) {
    this.cellFactory = cellFactory;
  }

  public Challenge transform(PrisonDtoRequest prisonDtoRequest) {
    requireNonNull(prisonDtoRequest, "Prison DTO is required");
    verify(!prisonDtoRequest.getPrison().isEmpty(), "Prison List must not be empty");
    verify(prisonDtoRequest.getPrison().stream()
      .filter(r -> r == null || r.trim().isEmpty())
      .count() == 0, "At least one row is null or empty");

    var rows = prisonDtoRequest.getPrison().size();
    var columns = prisonDtoRequest.getPrison().get(0).toCharArray().length;
    var challenge = new Challenge();
    challenge.setRows(rows);
    challenge.setColumns(columns);
    var solutionMap = new Cell[rows][columns];
    var prisonMap = new Cell[rows][columns];

    for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
      char[] currentCharArray = prisonDtoRequest.getPrison().get(rowIndex).toCharArray();

      for (int columnIndex = 0; columnIndex < currentCharArray.length; columnIndex++) {
        int finalRowIndex = rowIndex;
        int finalColumnIndex = columnIndex;
        this.cellFactory.build(currentCharArray[finalColumnIndex])
          .ifPresentOrElse(object -> {
            solutionMap[finalRowIndex][finalColumnIndex] = object;
            prisonMap[finalRowIndex][finalColumnIndex] = object;

            if (object instanceof Guard) {
              challenge.getGuards().put(new Location(finalRowIndex, finalColumnIndex), (Guard) object);
            }
            if (object instanceof Prisoner) {
              challenge.setPrisonerLocation(new Location(finalRowIndex, finalColumnIndex));
            }
            prisonMap[finalRowIndex][finalColumnIndex] = this.cellFactory.build(currentCharArray[finalColumnIndex]).get();
          }, () -> {
            throw new IllegalArgumentException("The only characters allowed are:|, ^, <, >, v, P, S and white spaces");
          });
      }
      challenge.setSolutionMap(solutionMap);
      challenge.setPrisonMap(prisonMap);
    }
    return challenge;
  }
}
