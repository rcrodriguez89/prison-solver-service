package ni.com.prisonsolver.support.annotation.rule;

import ni.com.prisonsolver.support.annotation.BeanValidationMessagesKeys;
import ni.com.prisonsolver.support.util.AppConstants;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static ni.com.prisonsolver.support.annotation.BeanValidationMessagesKeys.PRISON_INVALID_EXIT;
import static ni.com.prisonsolver.support.annotation.BeanValidationMessagesKeys.PRISON_INVALID_WALLS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoundariesRestrictionRuleTest {

  private BoundariesRestrictionRule rule = new BoundariesRestrictionRule();

  @ParameterizedTest
  @CsvSource({
    "|||||||||;| ||   P|;||    | |;|v| | < |;|   <   |;| ^     |;||||S||||"
  })
  void returnEmptyListWhenDontExistViolations(String prisonString) {
    List<String> prison = List.of(prisonString.split(AppConstants.ROW_SEPARATOR));
    List<BeanValidationMessagesKeys> violations = this.rule.verify(prison);
    assertTrue(violations.isEmpty());
  }

  @ParameterizedTest
  @CsvSource({
    "|||||||||;| ||   P|;||    | |;|v| | < |;|   <   |;| ^     |;|||<|||||"
  })
  void returnOneViolationWhenExitIsAbsentInTheBoundaries(String prisonString) {
    List<String> prison = List.of(prisonString.split(AppConstants.ROW_SEPARATOR));
    List<BeanValidationMessagesKeys> violations = this.rule.verify(prison);
    assertEquals(1, violations.size());
    assertTrue(violations.contains(PRISON_INVALID_EXIT));
  }

  @ParameterizedTest
  @CsvSource({
    "|||||||||;| ||   P|;||    | |;|v| | < |;|   <   |;| ^     |;|||<S||||"
  })
  void returnOneViolationWhenExistsAtLeastOneWallCharIsAbsent(String prisonString) {
    List<String> prison = List.of(prisonString.split(AppConstants.ROW_SEPARATOR));
    List<BeanValidationMessagesKeys> violations = this.rule.verify(prison);
    assertEquals(1, violations.size());
    assertTrue(violations.contains(PRISON_INVALID_WALLS));
  }

  @ParameterizedTest
  @CsvSource({
    "|||||||||;| ||   P|;||    | |;|v| | < |;|   <   |;| ^     |;|||<<||||"
  })
  void returnTwoViolationsWhenExitIsAbsentAndExistsAtLeastOneWallCharIsAbsent(String prisonString) {
    List<String> prison = List.of(prisonString.split(AppConstants.ROW_SEPARATOR));
    List<BeanValidationMessagesKeys> violations = this.rule.verify(prison);
    assertEquals(2, violations.size());
    assertTrue(violations.contains(PRISON_INVALID_EXIT));
    assertTrue(violations.contains(PRISON_INVALID_WALLS));
  }
}
