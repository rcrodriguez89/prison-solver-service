package ni.com.prisonsolver.factory;

import ni.com.prisonsolver.domain.Cell;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class GuardFactoryTest {

  private GuardFactory guardFactory = new GuardFactory();

  @ParameterizedTest
  @CsvSource({
    "^,GuardLookingUp",
    ">,GuardLookingRight",
    "v,GuardLookingDown",
    "<,GuardLookingLeft"
  })
  void buildGuardWhenGuardViewDirectionCharIsValid(char guardViewDirection, String expectedClassName) {
    Optional<Cell> guard = this.guardFactory.build(guardViewDirection);
    assertTrue(guard.isPresent());
    assertEquals(expectedClassName, guard.get().getClass().getSimpleName());
  }

  @Test
  void getEmptyOptionalWhenGuardViewDirectionCharIsEmpty() {
    Optional<Cell> guard = this.guardFactory.build(' ');
    assertFalse(guard.isPresent());
  }
}
