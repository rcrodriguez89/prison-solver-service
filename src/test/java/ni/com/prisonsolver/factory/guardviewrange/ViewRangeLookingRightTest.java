package ni.com.prisonsolver.factory.guardviewrange;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ViewRangeLookingRightTest extends GuardViewRangeStrategyBaseTest {

  private ViewRangeLookingRight viewRangeLookingRight = new ViewRangeLookingRight();

  @ParameterizedTest
  @CsvSource({
    "1,1,|||||||||;|>      |;|      P|;|       |;|       |;||||S||||,|||||||||;|>>>>>>>|;|      P|;|       |;|       |;||||S||||",
    "1,6,||||||||;|     > ;||||||||,||||||||;|     >>;||||||||"
  })
  void resolveChallenge(int rowIndex, int columnIndex, String prisonString, String expectedValue) {
    executeAssert(viewRangeLookingRight, rowIndex, columnIndex, prisonString, expectedValue);
  }

  @ParameterizedTest
  @CsvSource({
    "-1,1,|||||||||;|>      |;|      P|;|       |;|       |;||||S||||,|||||||||;|>      |;|      P|;|       |;|       |;||||S||||",
    "10,1,|||||||||;|>      |;|      P|;|       |;|       |;||||S||||,|||||||||;|>      |;|      P|;|       |;|       |;||||S||||"
  })
  void notApplyChangesWhenRowIndexIsNotValid(int rowIndex, int columnIndex, String prisonString, String expectedValue) {
    executeAssert(viewRangeLookingRight, rowIndex, columnIndex, prisonString, expectedValue);
  }

  @ParameterizedTest
  @CsvSource({
    "1,-1,|||||||||;|>      |;|      P|;|       |;|       |;||||S||||,|||||||||;|>      |;|      P|;|       |;|       |;||||S||||",
    "1,20,|||||||||;|>      |;|      P|;|       |;|       |;||||S||||,|||||||||;|>      |;|      P|;|       |;|       |;||||S||||"
  })
  void notApplyChangesWhenColumnIndexIsNotValid(int rowIndex, int columnIndex, String prisonString, String expectedValue) {
    executeAssert(viewRangeLookingRight, rowIndex, columnIndex, prisonString, expectedValue);
  }

  @ParameterizedTest
  @CsvSource({
    "0,0,|||||||||;|>      |;|      P|;|       |;|       |;||||S||||,|||||||||;|>      |;|      P|;|       |;|       |;||||S||||",
    "1,0,|||||||||;|>      |;|      P|;|       |;|       |;||||S||||,|||||||||;|>      |;|      P|;|       |;|       |;||||S||||"
  })
  void notApplyChangesWhenLocationDoesNotBelongToGuardLookingRightInstances(int rowIndex, int columnIndex, String prisonString, String expectedValue) {
    executeAssert(viewRangeLookingRight, rowIndex, columnIndex, prisonString, expectedValue);
  }
}
