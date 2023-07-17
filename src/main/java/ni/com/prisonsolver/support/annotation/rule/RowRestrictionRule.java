package ni.com.prisonsolver.support.annotation.rule;

import ni.com.prisonsolver.support.annotation.BeanValidationMessagesKeys;
import ni.com.prisonsolver.support.util.Checks;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static ni.com.prisonsolver.support.annotation.BeanValidationMessagesKeys.PRISON_MAX_ROWS_ALLOWED;
import static ni.com.prisonsolver.support.annotation.BeanValidationMessagesKeys.PRISON_MIN_ROWS_ALLOWED;
import static ni.com.prisonsolver.support.util.AppConstants.MAX_ROWS_ALLOWED;
import static ni.com.prisonsolver.support.util.AppConstants.MIN_ROWS_ALLOWED;

@Component
public class RowRestrictionRule implements RulePrisonFormat {

  @Override
  public List<BeanValidationMessagesKeys> verify(final List<String> prison) {
    List<BeanValidationMessagesKeys> violations = new ArrayList<>();
    Checks.validateNumber(prison.size(), rows -> rows < MIN_ROWS_ALLOWED, PRISON_MIN_ROWS_ALLOWED, violations);
    Checks.validateNumber(prison.size(), rows -> rows > MAX_ROWS_ALLOWED, PRISON_MAX_ROWS_ALLOWED, violations);
    return violations;
  }
}
