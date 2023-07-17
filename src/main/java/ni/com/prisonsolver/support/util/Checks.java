package ni.com.prisonsolver.support.util;

import ni.com.prisonsolver.support.annotation.BeanValidationMessagesKeys;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public final class Checks {

  private Checks() { }

  public static void verify(boolean condition, String message) {
    Objects.requireNonNull(message, "Message must not be null");
    Optional.of(condition)
      .filter(c -> c)
      .orElseThrow(() -> new IllegalArgumentException(message));
  }

  public static void validateNumber(int numberToValidate, Predicate<Integer> condition,
    BeanValidationMessagesKeys violation, List<BeanValidationMessagesKeys> violations) {
    Objects.requireNonNull(violation, "Violation key must not be null");
    Objects.requireNonNull(violations, "Violation list must not be null");
    if (condition.test(numberToValidate)) {
      violations.add(violation);
    }
  }
}
