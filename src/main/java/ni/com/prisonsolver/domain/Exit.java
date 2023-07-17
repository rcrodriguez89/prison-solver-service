package ni.com.prisonsolver.domain;

public class Exit implements Cell, Visitable {
  private boolean visited;

  @Override
  public char symbol() {
    return Symbol.EXIT;
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
