package ni.com.prisonsolver.domain.guard;

import ni.com.prisonsolver.domain.Cell;
import ni.com.prisonsolver.domain.Symbol;

public interface Guard extends Cell {

  default boolean isLookingUp() {
    return this.symbol() == Symbol.GUARD_LOOKING_UP;
  }

  default boolean isLookingRight() {
    return this.symbol() == Symbol.GUARD_LOOKING_RIGHT;
  }

  default boolean isLookingDown() {
    return this.symbol() == Symbol.GUARD_LOOKING_DOWN;
  }

  default boolean isLookingLeft() {
    return this.symbol() == Symbol.GUARD_LOOKING_LEFT;
  }
}
