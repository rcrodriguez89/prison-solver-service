package ni.com.prisonsolver.persistence;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record ChallengeStats(
  @JsonProperty("count_successful_scape") Integer countSuccessfulEscape,
  @JsonProperty("count_unsuccessful_scape") Integer countUnsuccessfulEscape) {

  @JsonProperty("ratio")
  public BigDecimal getRatio() {
    if (countUnsuccessfulEscape > 0) {
      return BigDecimal.valueOf(countSuccessfulEscape)
        .divide(BigDecimal.valueOf(countUnsuccessfulEscape), 2, RoundingMode.HALF_UP);
    }
    return BigDecimal.ZERO;
  }
}
