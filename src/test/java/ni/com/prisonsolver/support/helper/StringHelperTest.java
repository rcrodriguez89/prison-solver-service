package ni.com.prisonsolver.support.helper;

import ni.com.prisonsolver.support.util.AppConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StringHelperTest {

  @ParameterizedTest
  @CsvSource({
    "first|;1     2;3     4;last||,first|1234last||",
    "first|;1     2;3     4;;last||,first|1234last||",
    "first;1  2;;   ;last,first12last",
    "|||||||||;| ||   P|;||    | |;|v| | < |;|   < < |;|   <   |;| ^     |;||||S||||,|||||||||||||||||||||||||S||||"
  })
  void getBoundaries(String prisonString, String expectedValue) {
    List<String> prison = List.of(prisonString.split(AppConstants.ROW_SEPARATOR));
    assertEquals(expectedValue, StringHelper.getCharsOfPrisonsBoundaries(prison));
  }

  @Test
  void getBoundariesWhenOneElementIsNull() {
    List<String> prison = new ArrayList<>();
    prison.add("first");
    prison.add(null);
    prison.add("1     2");
    prison.add("last");
    assertEquals("first12last", StringHelper.getCharsOfPrisonsBoundaries(prison));
  }

  @Test
  void failGetBoundariesWhenPrisonIsNull() {
    NullPointerException e = assertThrows(NullPointerException.class,
      () -> StringHelper.getCharsOfPrisonsBoundaries(null));
    assertEquals("Prison is required", e.getMessage());
  }

  @ParameterizedTest
  @CsvSource({
    "|||||||||;| ||   P|;||    | |;|v| | < |;|   < < |;|   <   |;| ^     |;||||S||||,S,1",
    "|||||||||;| ||   P|;||    | |;|v| | < |;|   < < |;|   <   |;| ^     |;|||||||||,S,0",
    "|||||||||;| ||   P|;||    | |;|v| | < |;|   < < |;|   <   |;| ^     |;|||||||||,<,4"
  })
  void countLetter(String prisonString, char letterToCount, int expectedValue) {
    assertEquals(expectedValue, StringHelper.countLetter(prisonString, letterToCount));
  }

  @Test
  void countEmptySpaces() {
    assertEquals(3, StringHelper.countLetter("   ", ' '));
  }

  @Test
  void failCountWithNullRowString() {
    NullPointerException e = assertThrows(NullPointerException.class,
      () -> StringHelper.countLetter(null, 'S'));
    assertEquals("Row is required", e.getMessage());
  }

  @ParameterizedTest
  @CsvSource({
    "ABCDEF Z,Z,1",
    "ABCDEF ZZ,Z,2",
    "ABCDEF ZZZ,Z,3"
  })
  void countLetterWithExpectedMinNumberOfOccurrences(String row, char charToSearch, int expectedMinNumberOfOccurrences) {
    assertTrue(StringHelper.countLetter(row, charToSearch, expectedMinNumberOfOccurrences));
  }

  @ParameterizedTest
  @CsvSource({
    "ABCDEF,Z,1",
    "ABCDEFZZ,F,4",
    "ABCDEF,M,1",
    "ABCDEF,f,1"
  })
  void countWhenNumberLettersIsLessTheExpectedMinimum(String row, char charToSearch, int expectedMinNumberOfOccurrences) {
    assertFalse(StringHelper.countLetter(row, charToSearch, expectedMinNumberOfOccurrences));
  }

  @Test
  void countExpectedMinimumNumberEmptySpaces() {
    assertTrue(StringHelper.countLetter("   ", ' ', 3));
    assertTrue(StringHelper.countLetter("   ", ' ', 1));
  }

  @ParameterizedTest
  @ValueSource(ints = {-1, 0})
  void failCountExpectedMinimumNumberOfOccurrences(int expectedMinNumberOfOccurrences) {
    IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
      () -> StringHelper.countLetter("", 'S', expectedMinNumberOfOccurrences));
    assertEquals("The expected minimum of occurrences must be greater than zero", e.getMessage());
  }
}
