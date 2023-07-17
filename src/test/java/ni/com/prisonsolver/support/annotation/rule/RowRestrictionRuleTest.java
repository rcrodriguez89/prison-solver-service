package ni.com.prisonsolver.support.annotation.rule;

import ni.com.prisonsolver.support.annotation.BeanValidationMessagesKeys;
import ni.com.prisonsolver.support.util.AppConstants;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static ni.com.prisonsolver.support.annotation.BeanValidationMessagesKeys.PRISON_MAX_ROWS_ALLOWED;
import static ni.com.prisonsolver.support.annotation.BeanValidationMessagesKeys.PRISON_MIN_ROWS_ALLOWED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RowRestrictionRuleTest {

  private RowRestrictionRule rule = new RowRestrictionRule();

  @ParameterizedTest
  @CsvSource({
    "|||||||||;| ||   P|;||    | |;|v| | < |;|   <   |;| ^     |;||||S||||"
  })
  void returnEmptyListDoNotExistsViolations(String prisonString) {
    List<String> prison = List.of(prisonString.split(AppConstants.ROW_SEPARATOR));
    List<BeanValidationMessagesKeys> violations = this.rule.verify(prison);
    assertTrue(violations.isEmpty());
  }

  @ParameterizedTest
  @CsvSource({
    "|||||||||;| ||   P|;||    | |;|v| | < |;|   <   |"
  })
  void returnOneViolationWhenMinumumRowsIsNotValid(String prisonString) {
    List<String> prison = List.of(prisonString.split(AppConstants.ROW_SEPARATOR));
    List<BeanValidationMessagesKeys> violations = this.rule.verify(prison);
    assertEquals(1, violations.size());
    assertTrue(violations.contains(PRISON_MIN_ROWS_ALLOWED));
  }

  @ParameterizedTest
  @CsvSource({
    "||||||||;||||||||;||||||||;||||||||;||||||||;||||||||;||||||||;||||||||;||||||||;" +
    "||||||||;||||||||;||||||||;||||||||;||||||||;||||||||;||||||||;||||||||;||||||||;" +
    "||||||||;||||||||;||||||||;||||||||;||||||||;||||||||;||||||||;||||||||;||||||||;" +
    "||||||||;||||||||;||||||||;||||||||;"
  })
  void returnOneViolationWhenMaxRowsIsNotValid(String prisonString) {
    List<String> prison = List.of(prisonString.split(AppConstants.ROW_SEPARATOR));
    List<BeanValidationMessagesKeys> violations = this.rule.verify(prison);
    assertEquals(1, violations.size());
    assertTrue(violations.contains(PRISON_MAX_ROWS_ALLOWED));
  }
}
