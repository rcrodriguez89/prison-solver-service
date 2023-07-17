package ni.com.prisonsolver.support.helper;

import ni.com.prisonsolver.domain.Challenge;
import ni.com.prisonsolver.dto.PrisonDtoRequest;
import ni.com.prisonsolver.factory.CellFactory;
import ni.com.prisonsolver.factory.GuardFactory;
import ni.com.prisonsolver.support.util.AppConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PrisonMapperTest {

  private PrisonMapper prisonMapper;

  @BeforeEach
  void setup() {
    GuardFactory guardFactory = new GuardFactory();
    CellFactory cellFactory = new CellFactory(guardFactory);
    this.prisonMapper = new PrisonMapper(cellFactory);
  }

  @ParameterizedTest
  @CsvSource({
    "|||||||||;| ||   P|;||    | |;|v| | < |;|   <   |;|   <   |;| ^     |;||||S||||"
  })
  void transformPrisonStringToCellArray(String prisonString) {
    List<String> prison = List.of(prisonString.split(AppConstants.ROW_SEPARATOR));
    PrisonDtoRequest prisonDtoRequest = new PrisonDtoRequest();
    prisonDtoRequest.getPrison().addAll(prison);
    Challenge challenge = this.prisonMapper.transform(prisonDtoRequest);
    assertEquals(prisonString, challenge.drawSolutionMap());
  }

  @ParameterizedTest
  @CsvSource({
    "|||||||||;| ||   p|;||    | |;|V| | < |;|   <   |;|   <   |;| ^     |;||||S||||"
  })
  void failForInvalidCharacter(String prisonString) {
    List<String> prison = List.of(prisonString.split(AppConstants.ROW_SEPARATOR));
    PrisonDtoRequest prisonDtoRequest = new PrisonDtoRequest();
    prisonDtoRequest.getPrison().addAll(prison);
    IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
      () -> this.prisonMapper.transform(prisonDtoRequest));
    assertEquals("The only characters allowed are:|, ^, <, >, v, P, S and white spaces", e.getMessage());
  }

  @Test
  void throwNullPointExceptionWhenPrisonDtoIsNull() {
    NullPointerException e = assertThrows(NullPointerException.class,
      () -> prisonMapper.transform(null));
    assertEquals("Prison DTO is required", e.getMessage());
  }

  @Test
  void throwIllegalArgumentExceptionWhenPrisonListIsEmpty() {
    IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
      () -> prisonMapper.transform(new PrisonDtoRequest()));
    assertEquals("Prison List must not be empty", e.getMessage());
  }

  @Test
  void throwIllegalArgumentExceptionWhenOneElementIsNull() {
    PrisonDtoRequest prisonDtoRequest = new PrisonDtoRequest();
    prisonDtoRequest.getPrison().add("first");
    prisonDtoRequest.getPrison().add(null);
    IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
      () -> prisonMapper.transform(prisonDtoRequest));
    assertEquals("At least one row is null or empty", e.getMessage());
  }

  @Test
  void throwIllegalArgumentExceptionWhenOneElementIsEmpty() {
    PrisonDtoRequest prisonDtoRequest = new PrisonDtoRequest();
    prisonDtoRequest.getPrison().add("first");
    prisonDtoRequest.getPrison().add("    ");
    prisonDtoRequest.getPrison().add("");
    IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
      () -> prisonMapper.transform(prisonDtoRequest));
    assertEquals("At least one row is null or empty", e.getMessage());
  }
}
