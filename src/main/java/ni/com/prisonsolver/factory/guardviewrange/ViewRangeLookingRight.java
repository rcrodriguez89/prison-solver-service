package ni.com.prisonsolver.factory.guardviewrange;

import ni.com.prisonsolver.domain.Challenge;
import ni.com.prisonsolver.domain.Location;
import ni.com.prisonsolver.domain.Symbol;
import ni.com.prisonsolver.domain.guard.GuardLookingRight;

public class ViewRangeLookingRight implements GuardViewRangeStrategy {

  @Override
  public void calculateGuardViewRange(Challenge challenge, Location initialLocation) {
    int rowIndex = initialLocation.rowIndex();
    int sliderColumnRight = initialLocation.columnIndex();

    if (!this.isRowIndexValid(rowIndex, challenge.getRows())) {
      return;
    }

    if (!this.isColumnIndexValid(sliderColumnRight, challenge.getColumns())) {
      return;
    }

    if (!this.isCellInstanceValid(challenge.getSolutionMap()[rowIndex][sliderColumnRight],
      GuardLookingRight.class)) {
      return;
    }

    sliderColumnRight++;

    while (sliderColumnRight < challenge.getColumns() &&
      challenge.getSolutionMap()[rowIndex][sliderColumnRight].symbol() == Symbol.ROAD) {
      challenge.getSolutionMap()[rowIndex][sliderColumnRight] = new GuardLookingRight();
      sliderColumnRight++;
    }
  }
}
