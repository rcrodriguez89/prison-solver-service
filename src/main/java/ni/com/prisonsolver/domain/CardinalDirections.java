package ni.com.prisonsolver.domain;

import java.util.List;

public interface CardinalDirections {

  Direction WEST = new Direction(-1, 0);

  Direction EAST = new Direction(1, 0);

  Direction SOUTH = new Direction(0, -1);

  Direction NORTH = new Direction(0, 1);

  static List<Direction> toList() {
    return List.of(WEST, EAST, SOUTH, NORTH);
  }
}
