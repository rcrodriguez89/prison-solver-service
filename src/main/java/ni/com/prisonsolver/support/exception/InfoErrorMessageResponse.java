package ni.com.prisonsolver.support.exception;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Map;

public record InfoErrorMessageResponse(Exception exception) {

  private static final String NULL_OBJECT_MESSAGE = "Null Object";

  @JsonValue
  public Map<String, Object> asMap() {
    String message = exception == null ? NULL_OBJECT_MESSAGE : exception.getMessage();
    return Map.of("error", message);
  }
}
