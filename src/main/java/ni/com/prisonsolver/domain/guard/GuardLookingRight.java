package ni.com.prisonsolver.domain.guard;

import ni.com.prisonsolver.domain.Symbol;

public class GuardLookingRight implements Guard {

  @Override
  public char symbol() {
    return Symbol.GUARD_LOOKING_RIGHT;
  }
}
