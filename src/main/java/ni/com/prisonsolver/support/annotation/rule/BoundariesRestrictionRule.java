package ni.com.prisonsolver.support.annotation.rule;

import ni.com.prisonsolver.support.annotation.BeanValidationMessagesKeys;
import ni.com.prisonsolver.support.util.Checks;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static ni.com.prisonsolver.support.annotation.BeanValidationMessagesKeys.PRISON_INVALID_EXIT;
import static ni.com.prisonsolver.support.annotation.BeanValidationMessagesKeys.PRISON_INVALID_WALLS;
import static ni.com.prisonsolver.support.helper.StringHelper.countLetter;
import static ni.com.prisonsolver.support.helper.StringHelper.getCharsOfPrisonsBoundaries;

@Component
public class BoundariesRestrictionRule implements RulePrisonFormat {

  @Override
  public List<BeanValidationMessagesKeys> verify(final List<String> prison) {
    List<BeanValidationMessagesKeys> violations = new ArrayList<>();
    String boundaries = getCharsOfPrisonsBoundaries(prison);
    if (!countLetter(boundaries, 'S', 1)) {
      violations.add(PRISON_INVALID_EXIT);
    }
    Checks.validateNumber(countLetter(boundaries, '|'),
      s -> s != boundaries.length() - 1, PRISON_INVALID_WALLS, violations);
    return violations;
  }
}
