package ni.com.prisonsolver.factory.guardviewrange;

import ni.com.prisonsolver.domain.Challenge;
import ni.com.prisonsolver.domain.Location;
import ni.com.prisonsolver.domain.Symbol;
import ni.com.prisonsolver.domain.guard.GuardLookingLeft;

public class ViewRangeLookingLeft implements GuardViewRangeStrategy {

  @Override
  public void calculateGuardViewRange(Challenge challenge, Location initialLocation) {
    int rowIndex = initialLocation.rowIndex();
    int sliderColumnLeft = initialLocation.columnIndex();

    if (!this.isRowIndexValid(rowIndex, challenge.getRows())) {
      return;
    }

    if (!this.isColumnIndexValid(sliderColumnLeft, challenge.getColumns())) {
      return;
    }

    if (!this.isCellInstanceValid(challenge.getSolutionMap()[rowIndex][sliderColumnLeft],
      GuardLookingLeft.class)) {
      return;
    }

    sliderColumnLeft--;

    while (sliderColumnLeft >= 0 &&
      challenge.getSolutionMap()[rowIndex][sliderColumnLeft].symbol() == Symbol.ROAD) {
      challenge.getSolutionMap()[rowIndex][sliderColumnLeft] = new GuardLookingLeft();
      sliderColumnLeft--;
    }
  }
}
