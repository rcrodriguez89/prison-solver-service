package ni.com.prisonsolver.support.exception;

import ni.com.prisonsolver.persistence.PrisonChallenge;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RestExceptionManagerTest {

  private RestExceptionManager exceptionManager = new RestExceptionManager();

  @Test
  void notFoundExceptionIsThrown() {
    final String id = UUID.randomUUID().toString();
    NotFoundException nfe = new NotFoundException(PrisonChallenge.class, id);
    InfoErrorMessageResponse infoError = this.exceptionManager.handleNotFoundException(nfe);
    assertEquals(1, infoError.asMap().size());
    assertEquals(String.format("PrisonChallenge with Id [%s] not found", id), infoError.asMap().get("error"));
  }

  @Test
  void illegalArgumentExceptionIsThrown() {
    IllegalArgumentException e = new IllegalArgumentException("Param is required");
    InfoErrorMessageResponse infoError = this.exceptionManager.handleIllegalArgumentException(e);
    assertEquals(1, infoError.asMap().size());
    assertEquals("Param is required", infoError.asMap().get("error"));
  }

  @Test
  void methodArgumentNotValidExceptionIsThrown() {
    String errorMsg1 = "The firstName param should not be empty";
    FieldError firstNameFieldError = new FieldError("person", "firstName", errorMsg1);
    String errorMsg2 = "The lastName param should not be empty";
    FieldError lastNameFieldError = new FieldError("person", "lastName", errorMsg2);

    MethodArgumentNotValidException methodArgumentNotValidException = mock(MethodArgumentNotValidException.class);
    BindingResult bindingResult = mock(BindingResult.class);
    when(bindingResult.getFieldErrors())
      .thenReturn(List.of(firstNameFieldError, lastNameFieldError));
    when(methodArgumentNotValidException.getBindingResult())
      .thenReturn(bindingResult);
    Map<String, List<String>> errors = this.exceptionManager.handleValidationErrors(methodArgumentNotValidException);
    assertEquals(1, errors.size());
    assertNotNull(errors.get("errors"));
    assertEquals(2, errors.get("errors").size());
    assertTrue(errors.get("errors").contains(errorMsg1));
    assertTrue(errors.get("errors").contains(errorMsg2));
  }
}
