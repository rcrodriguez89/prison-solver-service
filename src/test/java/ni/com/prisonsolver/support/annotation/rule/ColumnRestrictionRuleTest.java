package ni.com.prisonsolver.support.annotation.rule;

import ni.com.prisonsolver.support.annotation.BeanValidationMessagesKeys;
import ni.com.prisonsolver.support.util.AppConstants;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static ni.com.prisonsolver.support.annotation.BeanValidationMessagesKeys.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ColumnRestrictionRuleTest {

  private ColumnRestrictionRule rule = new ColumnRestrictionRule();

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
    "|||||||||;| ||   P|;||    | |;|v| | < |;|   <   |;| ^     |;||||S||||"
  })
  void returnOneViolationWhenExistsOneNullRowAndOneEmptyRow(String prisonString) {
    List<String> prison = new ArrayList<>();
    prison.addAll(List.of(prisonString.split(AppConstants.ROW_SEPARATOR)));
    prison.add(null);
    prison.add(" ".repeat(9));
    List<BeanValidationMessagesKeys> violations = this.rule.verify(prison);
    assertEquals(1, violations.size());
    assertTrue(violations.contains(PRISON_EXISTS_EMPTY_ROW));
  }

  @ParameterizedTest
  @CsvSource({
    "|||||;<<<<<;>>>>>;^^^^^;vvvvv;SSSSS"
  })
  void returnOneViolationWhenExistsOneRowWithMinColsInvalid(String prisonString) {
    List<String> prison = List.of(prisonString.split(AppConstants.ROW_SEPARATOR));
    List<BeanValidationMessagesKeys> violations = this.rule.verify(prison);
    assertEquals(1, violations.size());
    assertTrue(violations.contains(PRISON_MIN_COLUMNS_ALLOWED));
  }

  @ParameterizedTest
  @CsvSource({
    "||||||||||||||||||||||||||||||||;|                              |;|                              |;" +
      "|                              |;|                              |;||||||||||||||||||||||||||||||||"
  })
  void returnOneViolationWhenExistsOneRowWithMaxColsInvalid(String prisonString) {
    List<String> prison = List.of(prisonString.split(AppConstants.ROW_SEPARATOR));
    List<BeanValidationMessagesKeys> violations = this.rule.verify(prison);
    assertEquals(1, violations.size());
    assertTrue(violations.contains(PRISON_MAX_COLUMNS_ALLOWED));
  }

  @ParameterizedTest
  @CsvSource({
    "||||||;| ||   P|;||    ;|v| | < |;|   <   |;| ^     |;||||S|||"
  })
  void returnOneViolationWhenExistsOneRowWithDifferentColumnsSize(String prisonString) {
    List<String> prison = List.of(prisonString.split(AppConstants.ROW_SEPARATOR));
    List<BeanValidationMessagesKeys> violations = this.rule.verify(prison);
    assertEquals(1, violations.size());
    assertTrue(violations.contains(PRISON_DIFFERENT_COLUMNS_SIZE));
  }
}
