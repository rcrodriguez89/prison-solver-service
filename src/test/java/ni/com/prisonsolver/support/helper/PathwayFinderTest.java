package ni.com.prisonsolver.support.helper;

import ni.com.prisonsolver.domain.Challenge;
import ni.com.prisonsolver.domain.Location;
import ni.com.prisonsolver.factory.guardviewrange.GuardViewRangeStrategyFactory;
import ni.com.prisonsolver.support.util.InstanceBuilderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PathwayFinderTest {

  private PathwayFinder pathwayFinder;

  @BeforeEach
  void setup() {
    GuardViewRangeStrategyFactory guardViewRangeStrategyFactory =
      new GuardViewRangeStrategyFactory();
    this.pathwayFinder = new PathwayFinder(guardViewRangeStrategyFactory);
  }

  @ParameterizedTest
  @CsvSource({
    "|||||||||;| ||   P|;||    | |;|v| | < |;|   <   |;|   <   |;| ^     |;||||S||||,true",
    "|||||||||;| ||   P|;||    | |;|v|v| <v|;|   <   |;|   <   |;| ^     |;||||S||||,false",
    "|||||||||;| ||  |P|;||    | |;|v|v| <v|;|   <   |;|   <   |;| ^     |;||||S||||,false"
  })
  void resolveChallenge(String prisonString, boolean expectedValue) {
    Challenge challenge = InstanceBuilderUtil.buildChallengeInstance(prisonString);
    assertEquals(expectedValue, this.pathwayFinder.calculatePathWay(challenge));
  }

  @ParameterizedTest
  @CsvSource({
    "|||||||||;| ||   P|;||    | |;|v| | < |;|   <   |;|   <   |;| ^     |;||||S||||,-1,-1",
    "|||||||||;| ||   P|;||    | |;|v| | < |;|   <   |;|   <   |;| ^     |;||||S||||,100,100",
    "|||||||||;| ||   P|;||    | |;|v| | < |;|   <   |;|   <   |;| ^     |;||||S||||,0,100",
    "|||||||||;| ||   P|;||    | |;|v| | < |;|   <   |;|   <   |;| ^     |;||||S||||,0,-1"
  })
  void resolveChallengeWithInvalidLocationForPrisoner(String prisonString, int rowIndex, int columnIndex) {
    Challenge challenge = InstanceBuilderUtil.buildChallengeInstance(prisonString);
    challenge.setPrisonerLocation(new Location(rowIndex, columnIndex));
    assertEquals(false, this.pathwayFinder.calculatePathWay(challenge));
  }

  @Test
  void failWhenChallengeInstanceIsNull() {
    NullPointerException e = assertThrows(NullPointerException.class,
      () -> this.pathwayFinder.calculatePathWay(null));
    assertEquals("Challenge instance must not be null", e.getMessage());
  }

  @Test
  void failWhenSolutionMapInstanceIsNull() {
    NullPointerException e = assertThrows(NullPointerException.class,
      () -> this.pathwayFinder.calculatePathWay(new Challenge()));
    assertEquals("Solution Map instance must not be null", e.getMessage());
  }
}
