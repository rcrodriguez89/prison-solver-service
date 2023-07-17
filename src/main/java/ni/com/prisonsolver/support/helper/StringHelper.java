package ni.com.prisonsolver.support.helper;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static ni.com.prisonsolver.support.util.Checks.verify;

public final class StringHelper {

  private StringHelper() {}

  public static String getCharsOfPrisonsBoundaries(List<String> prison) {
    requireNonNull(prison, "Prison is required");
    StringBuffer boundaries = new StringBuffer();
    for (int rowIndex = 0; rowIndex < prison.size(); rowIndex++) {
      String currentRow = prison.get(rowIndex);
      if (rowIndex == 0 || rowIndex == prison.size() - 1) {
        boundaries.append(currentRow);
      } else if (currentRow != null && currentRow.trim().length() > 0) {
        boundaries.append(currentRow.charAt(0))
          .append(currentRow.charAt(currentRow.length() - 1));
      }
    }
    return boundaries.toString();
  }

  public static int countLetter(String row, char charToSearch) {
    requireNonNull(row, "Row is required");
    int numberOfOccurrences = 0;
    for (char c : row.toCharArray()) {
      if (c == charToSearch) {
        numberOfOccurrences++;
      }
    }
    return numberOfOccurrences;
  }

  public static boolean countLetter(String row, char charToSearch, int expectedMinNumberOfOccurrences) {
    requireNonNull(row, "Row is required");
    verify(expectedMinNumberOfOccurrences > 0,
      "The expected minimum of occurrences must be greater than zero");
    int numberOfOccurrences = 0;
    for (char c : row.toCharArray()) {
      if (c == charToSearch) {
        numberOfOccurrences++;
      }
      if (numberOfOccurrences == expectedMinNumberOfOccurrences) {
        return true;
      }
    }
    return false;
  }
}