package ni.com.prisonsolver.factory.guardviewrange;

import ni.com.prisonsolver.domain.Challenge;
import ni.com.prisonsolver.domain.Location;
import ni.com.prisonsolver.support.util.InstanceBuilderUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GuardViewRangeStrategyBaseTest {

  protected void executeAssert(GuardViewRangeStrategy strategy, int rowIndex, int columnIndex, String prisonString, String expectedValue) {
    Challenge challenge = InstanceBuilderUtil.buildChallengeInstance(prisonString);
    strategy.calculateGuardViewRange(challenge, new Location(rowIndex, columnIndex));
    assertEquals(expectedValue, challenge.drawSolutionMap());
  }
}
