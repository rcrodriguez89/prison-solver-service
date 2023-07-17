package ni.com.prisonsolver.factory.guardviewrange;

import ni.com.prisonsolver.domain.Cell;
import ni.com.prisonsolver.domain.Challenge;
import ni.com.prisonsolver.domain.Location;

public interface GuardViewRangeStrategy {

  void calculateGuardViewRange(Challenge challenge, Location initialLocaltion);

  default boolean isRowIndexValid(int rowIndex, int totalRows) {
    return rowIndex >= 0 && rowIndex < totalRows;
  }

  default boolean isColumnIndexValid(int columnIndex, int totalColumns) {
    return columnIndex >= 0 && columnIndex < totalColumns;
  }

  default boolean isCellInstanceValid(Cell cell, Class guardClass) {
    return cell.getClass().getSimpleName().equals(guardClass.getSimpleName());
  }
}
