package ni.com.prisonsolver.factory;

import ni.com.prisonsolver.domain.Cell;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CellFactoryTest {

  private CellFactory cellFactory = new CellFactory(new GuardFactory());

  @ParameterizedTest
  @CsvSource({
    "^,GuardLookingUp",
    ">,GuardLookingRight",
    "v,GuardLookingDown",
    "<,GuardLookingLeft",
    "P,Prisoner",
    "|,Wall",
    "S,Exit"
  })
  void buildCellWhenTypeIsValid(char type, String expectedClassName) {
    Optional<Cell> cell = this.cellFactory.build(type);
    assertTrue(cell.isPresent());
    assertEquals(expectedClassName, cell.get().getClass().getSimpleName());
  }

  @Test
  void getRoadCellWhenGuardViewDirectionCharIsEmpty() {
    Optional<Cell> cell = this.cellFactory.build(' ');
    assertTrue(cell.isPresent());
    assertEquals("Road", cell.get().getClass().getSimpleName());
  }

  @Test
  void getEmptyOptionalWhenGuardViewDirectionCharIsNotValid() {
    Optional<Cell> cell = this.cellFactory.build('a');
    assertFalse(cell.isPresent());
  }
}
