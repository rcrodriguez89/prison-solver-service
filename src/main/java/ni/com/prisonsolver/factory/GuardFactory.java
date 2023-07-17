package ni.com.prisonsolver.factory;

import ni.com.prisonsolver.domain.Cell;
import ni.com.prisonsolver.domain.guard.GuardLookingDown;
import ni.com.prisonsolver.domain.guard.GuardLookingLeft;
import ni.com.prisonsolver.domain.guard.GuardLookingRight;
import ni.com.prisonsolver.domain.guard.GuardLookingUp;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static ni.com.prisonsolver.domain.Symbol.*;

@Component
public class GuardFactory {

  public Optional<Cell> build(char guardViewDirection) {
    if (GUARD_LOOKING_UP == guardViewDirection) {
      return Optional.of(new GuardLookingUp());
    }

    if (GUARD_LOOKING_RIGHT == guardViewDirection) {
      return Optional.of(new GuardLookingRight());
    }

    if (GUARD_LOOKING_DOWN == guardViewDirection) {
      return Optional.of(new GuardLookingDown());
    }

    if (GUARD_LOOKING_LEFT == guardViewDirection) {
      return Optional.of(new GuardLookingLeft());
    }

    return Optional.empty();
  }
}
