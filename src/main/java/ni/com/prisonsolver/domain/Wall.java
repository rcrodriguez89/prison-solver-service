package ni.com.prisonsolver.domain;

public class Wall implements Cell {

  @Override
  public char symbol() {
    return Symbol.WALL;
  }
}
