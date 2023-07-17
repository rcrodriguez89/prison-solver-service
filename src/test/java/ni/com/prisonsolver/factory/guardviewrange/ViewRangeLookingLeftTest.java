package ni.com.prisonsolver.factory.guardviewrange;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ViewRangeLookingLeftTest extends GuardViewRangeStrategyBaseTest {

  private ViewRangeLookingLeft viewRangeLookingLeft = new ViewRangeLookingLeft();

  @ParameterizedTest
  @CsvSource({
    "1,7,|||||||||;|      <|;|      P|;|       |;|       |;||||S||||,|||||||||;|<<<<<<<|;|      P|;|       |;|       |;||||S||||",
    "1,1,||||||||; <     |;||||||||,||||||||;<<     |;||||||||"
  })
  void resolveChallenge(int rowIndex, int columnIndex, String prisonString, String expectedValue) {
    executeAssert(viewRangeLookingLeft, rowIndex, columnIndex, prisonString, expectedValue);
  }

  @ParameterizedTest
  @CsvSource({
    "-1,1,|||||||||;|      <|;|      P|;|       |;|       |;||||S||||,|||||||||;|      <|;|      P|;|       |;|       |;||||S||||",
    "10,1,|||||||||;|      <|;|      P|;|       |;|       |;||||S||||,|||||||||;|      <|;|      P|;|       |;|       |;||||S||||"
  })
  void notApplyChangesWhenRowIndexIsNotValid(int rowIndex, int columnIndex, String prisonString, String expectedValue) {
    executeAssert(viewRangeLookingLeft, rowIndex, columnIndex, prisonString, expectedValue);
  }

  @ParameterizedTest
  @CsvSource({
    "1,-1,|||||||||;|      <|;|      P|;|       |;|       |;||||S||||,|||||||||;|      <|;|      P|;|       |;|       |;||||S||||",
    "1,20,|||||||||;|      <|;|      P|;|       |;|       |;||||S||||,|||||||||;|      <|;|      P|;|       |;|       |;||||S||||"
  })
  void notApplyChangesWhenColumnIndexIsNotValid(int rowIndex, int columnIndex, String prisonString, String expectedValue) {
    executeAssert(viewRangeLookingLeft, rowIndex, columnIndex, prisonString, expectedValue);
  }

  @ParameterizedTest
  @CsvSource({
    "0,0,|||||||||;|      <|;|      P|;|       |;|       |;||||S||||,|||||||||;|      <|;|      P|;|       |;|       |;||||S||||",
    "1,0,|||||||||;|      <|;|      P|;|       |;|       |;||||S||||,|||||||||;|      <|;|      P|;|       |;|       |;||||S||||"
  })
  void notApplyChangesWhenLocationDoesNotBelongToGuardLookingLeftInstances(int rowIndex, int columnIndex, String prisonString, String expectedValue) {
    executeAssert(viewRangeLookingLeft, rowIndex, columnIndex, prisonString, expectedValue);
  }
}
