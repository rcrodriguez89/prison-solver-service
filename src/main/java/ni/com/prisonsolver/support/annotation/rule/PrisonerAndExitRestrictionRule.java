package ni.com.prisonsolver.support.annotation.rule;

import ni.com.prisonsolver.support.annotation.BeanValidationMessagesKeys;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ni.com.prisonsolver.support.annotation.BeanValidationMessagesKeys.*;
import static ni.com.prisonsolver.support.helper.StringHelper.countLetter;
import static ni.com.prisonsolver.support.util.Checks.validateNumber;

@Component
public class PrisonerAndExitRestrictionRule implements RulePrisonFormat {

  @Override
  public List<BeanValidationMessagesKeys> verify(final List<String> prison) {
    List<BeanValidationMessagesKeys> violations = new ArrayList<>();
    String prisonListToString = prison.stream().collect(Collectors.joining());
    validateNumber(countLetter(prisonListToString, 'P'),
      p -> p == 0, PRISONER_IS_ABSENT, violations);
    validateNumber(countLetter(prisonListToString, 'P'),
      p -> p > 1, PRISONER_IS_MANY_TIMES, violations);
    validateNumber(countLetter(prisonListToString, 'S'),
      s -> s == 0, EXIT_IS_ABSENT, violations);
    validateNumber(countLetter(prisonListToString, 'S'),
      s -> s > 1, EXIT_IS_MANY_TIMES, violations);
    return violations;
  }
}
