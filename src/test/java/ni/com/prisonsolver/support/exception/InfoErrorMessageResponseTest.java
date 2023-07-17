package ni.com.prisonsolver.support.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InfoErrorMessageResponseTest {

  @Test
  void throwExceptionWithMessage() {
    String errorMessage = "Dummy error message";
    InfoErrorMessageResponse e = new InfoErrorMessageResponse(new Exception(errorMessage));
    assertEquals(errorMessage, e.asMap().get("error"));
    assertEquals(1, e.asMap().size());
  }

  @Test
  void getMapWhenExceptionIsNull() {
    NullPointerException npe = null;
    InfoErrorMessageResponse e = new InfoErrorMessageResponse(npe);
    assertEquals("Null Object", e.asMap().get("error"));
    assertEquals(1, e.asMap().size());
  }
}
