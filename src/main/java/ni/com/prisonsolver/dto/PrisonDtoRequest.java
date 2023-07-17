package ni.com.prisonsolver.dto;

import ni.com.prisonsolver.support.annotation.PrisonFormatConstraint;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PrisonDtoRequest {

  @PrisonFormatConstraint
  private List<String> prison = new ArrayList<>();

  public List<String> getPrison() {
    return prison;
  }

  @Override
  public String toString() {
    return prison.isEmpty() ? "" :
      prison.stream()
        .map(s -> s.concat("\n"))
        .collect(Collectors.joining());
  }
}
