package ni.com.prisonsolver.domain;

public interface Cell {

  char symbol();

  default String draw() {
    return String.format("%s", symbol());
  }

  default String prettyFormat() {
    return String.format(" %s ", symbol());
  }
}
