package ni.com.prisonsolver.factory.guardviewrange;

import ni.com.prisonsolver.domain.Challenge;
import ni.com.prisonsolver.domain.Location;
import ni.com.prisonsolver.domain.Symbol;
import ni.com.prisonsolver.domain.guard.GuardLookingUp;

public class ViewRangeLookingUp implements GuardViewRangeStrategy {

  @Override
  public void calculateGuardViewRange(Challenge challenge, Location initialLocation) {
    int sliderRowUp = initialLocation.rowIndex();
    int columnIndex = initialLocation.columnIndex();

    if (!this.isRowIndexValid(sliderRowUp, challenge.getRows())) {
      return;
    }

    if (!this.isColumnIndexValid(columnIndex, challenge.getColumns())) {
      return;
    }

    if (!this.isCellInstanceValid(challenge.getSolutionMap()[sliderRowUp][columnIndex],
      GuardLookingUp.class)) {
      return;
    }

    sliderRowUp--;

    while (sliderRowUp >= 0 &&
      challenge.getSolutionMap()[sliderRowUp][columnIndex].symbol() == Symbol.ROAD) {
      challenge.getSolutionMap()[sliderRowUp][columnIndex] = new GuardLookingUp();
      sliderRowUp--;
    }
  }
}
