package ni.com.prisonsolver.support.annotation.rule;

import ni.com.prisonsolver.support.annotation.BeanValidationMessagesKeys;
import ni.com.prisonsolver.support.util.AppConstants;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static ni.com.prisonsolver.support.annotation.BeanValidationMessagesKeys.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PrisonerAndExitRestrictionRuleTest {

  private PrisonerAndExitRestrictionRule rule = new PrisonerAndExitRestrictionRule();

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
    "|||||||||;| ||    |;||    | |;|v| | < |;|   <   |;| ^     |;||||S||||"
  })
  void returnOneViolationWhenPrisonerIsAbsent(String prisonString) {
    List<String> prison = List.of(prisonString.split(AppConstants.ROW_SEPARATOR));
    List<BeanValidationMessagesKeys> violations = this.rule.verify(prison);
    assertEquals(1, violations.size());
    assertTrue(violations.contains(PRISONER_IS_ABSENT));
  }

  @ParameterizedTest
  @CsvSource({
    "|||||||||;| ||    |;||  PP| |;|v| | < |;|   <   |;| ^     |;||||S||||"
  })
  void returnOneViolationWhenPrisonerIsManyTimes(String prisonString) {
    List<String> prison = List.of(prisonString.split(AppConstants.ROW_SEPARATOR));
    List<BeanValidationMessagesKeys> violations = this.rule.verify(prison);
    assertEquals(1, violations.size());
    assertTrue(violations.contains(PRISONER_IS_MANY_TIMES));
  }

  @ParameterizedTest
  @CsvSource({
    "|||||||||;| ||   P|;||    | |;|v| | < |;|   <   |;| ^     |;|||||||||"
  })
  void returnOneViolationWhenExitIsAbsent(String prisonString) {
    List<String> prison = List.of(prisonString.split(AppConstants.ROW_SEPARATOR));
    List<BeanValidationMessagesKeys> violations = this.rule.verify(prison);
    assertEquals(1, violations.size());
    assertTrue(violations.contains(EXIT_IS_ABSENT));
  }

  @ParameterizedTest
  @CsvSource({
    "|||||||||;| ||   P|;||    | |;|v| | < |;|   <   |;| ^     |;|||SS||||"
  })
  void returnOneViolationWhenExitIsManyTimes(String prisonString) {
    List<String> prison = List.of(prisonString.split(AppConstants.ROW_SEPARATOR));
    List<BeanValidationMessagesKeys> violations = this.rule.verify(prison);
    assertEquals(1, violations.size());
    assertTrue(violations.contains(EXIT_IS_MANY_TIMES));
  }
}
