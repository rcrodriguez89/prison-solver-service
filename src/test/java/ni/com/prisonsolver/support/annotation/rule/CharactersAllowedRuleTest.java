package ni.com.prisonsolver.support.annotation.rule;

import ni.com.prisonsolver.support.annotation.BeanValidationMessagesKeys;
import ni.com.prisonsolver.support.util.AppConstants;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static ni.com.prisonsolver.support.annotation.BeanValidationMessagesKeys.PRISON_INVALID_CHARACTERS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CharactersAllowedRuleTest {

  private CharactersAllowedRule rule = new CharactersAllowedRule();

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
    "|||||||||;| ||   p|;||    | |;|V| | < |;|   <   |;| ^ @   |;|||s|||||"
  })
  void returnOneViolationWhenExistsAtLeastNotValidChar(String prisonString) {
    List<String> prison = List.of(prisonString.split(AppConstants.ROW_SEPARATOR));
    List<BeanValidationMessagesKeys> violations = this.rule.verify(prison);
    assertEquals(1, violations.size());
    assertTrue(violations.contains(PRISON_INVALID_CHARACTERS));
  }
}
