package ni.com.prisonsolver.factory;

import ni.com.prisonsolver.domain.*;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static ni.com.prisonsolver.domain.Symbol.*;

@Component
public class CellFactory {

  private final GuardFactory guardFactory;

  public CellFactory(GuardFactory guardFactory) {
    this.guardFactory = guardFactory;
  }

  public Optional<Cell> build(char type) {
    if (Symbol.isGuard(type)) {
      return this.guardFactory.build(type);
    }

    if (PRISONER == type) {
      return Optional.of(new Prisoner());
    }

    if (WALL == type) {
      return Optional.of(new Wall());
    }

    if (EXIT == type) {
      return Optional.of(new Exit());
    }

    if (Character.isWhitespace(type)) {
      return Optional.of(new Road());
    }

    return Optional.empty();
  }
}
