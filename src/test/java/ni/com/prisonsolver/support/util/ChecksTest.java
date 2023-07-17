package ni.com.prisonsolver.support.util;

import ni.com.prisonsolver.support.annotation.BeanValidationMessagesKeys;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static ni.com.prisonsolver.support.util.Checks.validateNumber;
import static ni.com.prisonsolver.support.util.Checks.verify;
import static org.junit.jupiter.api.Assertions.*;

public class ChecksTest {

  @Test
  void verifyWhenConditionIsFalseThenExceptionIsNotThrown() {
    String errorMessage = "error-message";
    IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
      () -> verify(false, errorMessage));
    assertEquals(e.getMessage(), errorMessage);
  }

  @Test
  void returnsNullPointerExceptionWhenMessageIsNull() {
    NullPointerException e = assertThrows(NullPointerException.class,
      () -> verify(false, null));
    assertEquals(e.getMessage(), "Message must not be null");
  }

  @Test
  void validateNumberWhenConditionIsTrue() {
    List<BeanValidationMessagesKeys> violations = new ArrayList<>();
    var violation = BeanValidationMessagesKeys.EXIT_IS_ABSENT;
    int size = 2;
    validateNumber(size, s -> s > 0, violation, violations);
    assertFalse(violations.isEmpty());
    assertTrue(violations.contains(violation));
  }

  @Test
  void validateNumberWhenConditionIsFalse() {
    List<BeanValidationMessagesKeys> violations = new ArrayList<>();
    var violation = BeanValidationMessagesKeys.EXIT_IS_ABSENT;
    int size = 0;
    validateNumber(size, s -> s > 0, violation, violations);
    assertTrue(violations.isEmpty());
    assertFalse(violations.contains(violation));
  }

  @Test
  void returnsNullPointerExceptionWhenViolationKeyIsNull() {
    int size = 0;
    NullPointerException e = assertThrows(NullPointerException.class,
      () -> validateNumber(size, s -> s > 0, null, new ArrayList<>()));
    assertEquals(e.getMessage(), "Violation key must not be null");
  }

  @Test
  void returnsNullPointerExceptionWhenViolationListIsNull() {
    int size = 0;
    var violation = BeanValidationMessagesKeys.EXIT_IS_ABSENT;
    NullPointerException e = assertThrows(NullPointerException.class,
      () -> validateNumber(size, s -> s > 0, violation, null));
    assertEquals(e.getMessage(), "Violation list must not be null");
  }
}
