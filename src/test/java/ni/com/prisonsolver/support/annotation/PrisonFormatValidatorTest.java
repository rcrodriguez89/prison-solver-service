package ni.com.prisonsolver.support.annotation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import ni.com.prisonsolver.dto.PrisonDtoRequest;
import ni.com.prisonsolver.support.util.AppConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static ni.com.prisonsolver.support.annotation.BeanValidationMessagesKeys.*;
import static org.mockito.Mockito.*;

public class PrisonFormatValidatorTest {

  private Validator validator;

  @BeforeEach
  public void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @ParameterizedTest
  @CsvSource({
    "|||||||||;| ||   P|;||    | |;|v| | < |;|   <   |;| ^     |;||||S||||"
  })
  void returnEmptyListDoNotExistsViolations(String prisonString) {
    PrisonDtoRequest dto = new PrisonDtoRequest();
    dto.getPrison().addAll(List.of(prisonString.split(AppConstants.ROW_SEPARATOR)));
    Set<ConstraintViolation<PrisonDtoRequest>> violations = validator.validate(dto);
    assertTrue(violations.isEmpty());
  }

  @Test
  void returnOneViolationWhenPrisonListIsEmpty() {
    Set<ConstraintViolation<PrisonDtoRequest>> violations = validator.validate(new PrisonDtoRequest());
    assertFalse(violations.isEmpty());
    assertEquals(1, violations.stream()
      .filter(c -> c.getMessage().equals(PRISON_PARAM_IS_NULL.toString()))
      .count());
  }

  @Test
  void returnOneViolationWhenPrisonListIsNull() {
    PrisonDtoRequest dto = mock(PrisonDtoRequest.class);
    when(dto.getPrison()).thenReturn(null);
    Set<ConstraintViolation<PrisonDtoRequest>> violations = validator.validate(dto);
    assertFalse(violations.isEmpty());
    assertEquals(1, violations.stream()
      .filter(c -> c.getMessage().equals(PRISON_PARAM_IS_NULL.toString()))
      .count());
  }

  @ParameterizedTest
  @CsvSource({
    "|||||||||;| ||   P|;||    | |;|v| | < |;|   <   |;| ^     |;||||S||||"
  })
  void returnOneViolationWhenExistsOneNullRow(String prisonString) {
    List<String> prison = new ArrayList<>(List.of(prisonString.split(AppConstants.ROW_SEPARATOR)));
    prison.add(null);
    PrisonDtoRequest dto = new PrisonDtoRequest();
    dto.getPrison().addAll(prison);
    Set<ConstraintViolation<PrisonDtoRequest>> violations = validator.validate(dto);
    assertFalse(violations.isEmpty());
    assertEquals(1, violations.stream()
      .filter(c -> c.getMessage().equals(PRISON_EXISTS_EMPTY_ROW.toString()))
      .count());
  }
}
