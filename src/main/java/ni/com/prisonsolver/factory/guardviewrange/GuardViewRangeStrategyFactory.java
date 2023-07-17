package ni.com.prisonsolver.factory.guardviewrange;

import ni.com.prisonsolver.domain.guard.Guard;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GuardViewRangeStrategyFactory {

  public Optional<GuardViewRangeStrategy> getStrategy(Guard guard) {
    if (guard.isLookingUp()) {
      return Optional.of(new ViewRangeLookingUp());
    }

    if (guard.isLookingRight()) {
      return Optional.of(new ViewRangeLookingRight());
    }

    if (guard.isLookingDown()) {
      return Optional.of(new ViewRangeLookingDown());
    }

    if (guard.isLookingLeft()) {
      return Optional.of(new ViewRangeLookingLeft());
    }
    return Optional.empty();
  }
}
