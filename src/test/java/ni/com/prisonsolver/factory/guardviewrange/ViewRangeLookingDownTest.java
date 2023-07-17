package ni.com.prisonsolver.factory.guardviewrange;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ViewRangeLookingDownTest extends GuardViewRangeStrategyBaseTest {

  private ViewRangeLookingDown viewRangeLookingDown = new ViewRangeLookingDown();

  @ParameterizedTest
  @CsvSource({
    "1,1,|||||||||;|v     P|;|       |;|       |;|       |;||||S||||,|||||||||;|v     P|;|v      |;|v      |;|v      |;||||S||||",
    "0,1,|v||||||;| ||||||,|v||||||;|v||||||"
  })
  void resolveChallenge(int rowIndex, int columnIndex, String prisonString, String expectedValue) {
    executeAssert(viewRangeLookingDown, rowIndex, columnIndex, prisonString, expectedValue);
  }

  @ParameterizedTest
  @CsvSource({
    "-1,1,|||||||||;|v     P|;|       |;|       |;|       |;||||S||||,|||||||||;|v     P|;|       |;|       |;|       |;||||S||||",
    "10,1,|||||||||;|v     P|;|       |;|       |;|       |;||||S||||,|||||||||;|v     P|;|       |;|       |;|       |;||||S||||"
  })
  void notApplyChangesWhenRowIndexIsNotValid(int rowIndex, int columnIndex, String prisonString, String expectedValue) {
    executeAssert(viewRangeLookingDown, rowIndex, columnIndex, prisonString, expectedValue);
  }

  @ParameterizedTest
  @CsvSource({
    "1,-1,|||||||||;|v     P|;|       |;|       |;|       |;||||S||||,|||||||||;|v     P|;|       |;|       |;|       |;||||S||||",
    "1,20,|||||||||;|v     P|;|       |;|       |;|       |;||||S||||,|||||||||;|v     P|;|       |;|       |;|       |;||||S||||"
  })
  void notApplyChangesWhenColumnIndexIsNotValid(int rowIndex, int columnIndex, String prisonString, String expectedValue) {
    executeAssert(viewRangeLookingDown, rowIndex, columnIndex, prisonString, expectedValue);
  }

  @ParameterizedTest
  @CsvSource({
    "0,0,|||||||||;|v     P|;|       |;|       |;|       |;||||S||||,|||||||||;|v     P|;|       |;|       |;|       |;||||S||||",
    "1,1,|||||||||;|      P|;||      |;|       |;|       |;|v||S||||,|||||||||;|      P|;||      |;|       |;|       |;|v||S||||"
  })
  void notApplyChangesWhenLocationDoesNotBelongToGuardLookingDownInstances(int rowIndex, int columnIndex, String prisonString, String expectedValue) {
    executeAssert(viewRangeLookingDown, rowIndex, columnIndex, prisonString, expectedValue);
  }
}
