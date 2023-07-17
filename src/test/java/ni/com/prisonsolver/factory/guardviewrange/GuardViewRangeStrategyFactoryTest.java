package ni.com.prisonsolver.factory.guardviewrange;

import ni.com.prisonsolver.domain.guard.Guard;
import ni.com.prisonsolver.factory.GuardFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GuardViewRangeStrategyFactoryTest {

  private GuardFactory guardFactory = new GuardFactory();

  private GuardViewRangeStrategyFactory factory = new GuardViewRangeStrategyFactory();

  @Mock
  Guard guardMock;

  @ParameterizedTest
  @CsvSource({
    "^,ViewRangeLookingUp",
    ">,ViewRangeLookingRight",
    "v,ViewRangeLookingDown",
    "<,ViewRangeLookingLeft"
  })
  void getStrategy(char guardViewDirection, String className) {
    Guard guard = (Guard) this.guardFactory.build(guardViewDirection).get();
    Optional<GuardViewRangeStrategy> strategy = this.factory.getStrategy(guard);
    assertTrue(strategy.isPresent());
    assertEquals(strategy.get().getClass().getSimpleName(), className);
  }

  @Test
  void getEmptyOptionalWhenGuardViewDirectionCharIsNotValidChar() {
    when(guardMock.isLookingUp())
      .thenReturn(false);
    when(guardMock.isLookingRight())
      .thenReturn(false);
    when(guardMock.isLookingLeft())
      .thenReturn(false);
    when(guardMock.isLookingDown())
      .thenReturn(false);
    Optional<GuardViewRangeStrategy> strategy = this.factory.getStrategy(guardMock);
    verify(this.guardMock, times(1))
      .isLookingUp();
    verify(this.guardMock, times(1))
      .isLookingRight();
    verify(this.guardMock, times(1))
      .isLookingLeft();
    verify(this.guardMock, times(1))
      .isLookingDown();
    assertFalse(strategy.isPresent());
  }
}
