package ni.com.prisonsolver.support.annotation.rule;

import ni.com.prisonsolver.support.annotation.BeanValidationMessagesKeys;
import ni.com.prisonsolver.support.util.Checks;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ni.com.prisonsolver.support.annotation.BeanValidationMessagesKeys.*;
import static ni.com.prisonsolver.support.util.AppConstants.MAX_COLUMNS_ALLOWED;
import static ni.com.prisonsolver.support.util.AppConstants.MIN_COLUMNS_ALLOWED;

@Component
public class ColumnRestrictionRule implements RulePrisonFormat {

  @Override
  public List<BeanValidationMessagesKeys> verify(final List<String> prison) {
    List<BeanValidationMessagesKeys> violations = new ArrayList<>();
    var columnsSize = prison
      .stream()
      .map(row -> {
        if (row == null || row.trim().length() == 0) {
          return 0;
        }
        return row.length();
      })
      .distinct()
      .collect(Collectors.toList());

    if (columnsSize.stream()
      .filter(s -> s == 0)
      .findAny().isPresent()) {
      violations.add(PRISON_EXISTS_EMPTY_ROW);
    }

    if (columnsSize.stream()
      .filter(cs -> cs > 0 && cs < MIN_COLUMNS_ALLOWED)
      .findAny().isPresent()) {
      violations.add(PRISON_MIN_COLUMNS_ALLOWED);
    }

    if (columnsSize.stream()
      .filter(cs -> cs > MAX_COLUMNS_ALLOWED)
      .findAny().isPresent()) {
      violations.add(PRISON_MAX_COLUMNS_ALLOWED);
    }
    Checks.validateNumber(columnsSize.size() - (violations.contains(PRISON_EXISTS_EMPTY_ROW) ? 1 : 0),
      cs -> cs > 1, PRISON_DIFFERENT_COLUMNS_SIZE, violations);
    return violations;
  }
}
