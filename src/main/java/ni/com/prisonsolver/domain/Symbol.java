package ni.com.prisonsolver.domain;

import java.util.List;

public interface Symbol {

  char PRISONER = 'P';

  char GUARD_LOOKING_UP = '^';

  char GUARD_LOOKING_RIGHT = '>';

  char GUARD_LOOKING_DOWN = 'v';

  char GUARD_LOOKING_LEFT = '<';

  char WALL = '|';

  char EXIT = 'S';

  char ROAD = ' ';

  char ROUTE = '*';

  static boolean isGuard(char type) {
    return List.of(GUARD_LOOKING_UP, GUARD_LOOKING_RIGHT,
      GUARD_LOOKING_DOWN, GUARD_LOOKING_LEFT).contains(type);
  }

  static boolean isValidCharacter(char type) {
    return List.of(GUARD_LOOKING_UP, GUARD_LOOKING_RIGHT,
      GUARD_LOOKING_DOWN, GUARD_LOOKING_LEFT, PRISONER,
      WALL, EXIT, ROAD).contains(type);
  }
}
