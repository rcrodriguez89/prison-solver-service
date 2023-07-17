package ni.com.prisonsolver.domain;

public class Road implements Cell, Visitable {
  private boolean visited;

  @Override
  public char symbol() {
    return Symbol.ROAD;
  }

  @Override
  public boolean isVisited() {
    return this.visited;
  }

  @Override
  public void setVisited(boolean visited) {
    this.visited = visited;
  }
}
