package ni.com.prisonsolver.support.annotation.rule;

import ni.com.prisonsolver.support.annotation.BeanValidationMessagesKeys;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static ni.com.prisonsolver.support.annotation.BeanValidationMessagesKeys.PRISON_INVALID_CHARACTERS;

@Component
public class CharactersAllowedRule implements RulePrisonFormat {

  private static final String ALLOWED_CHARACTERS = "[|^<>vPS\\s+]+";

  private static final Pattern VALIDATOR_PATTERN = Pattern.compile(ALLOWED_CHARACTERS);

  @Override
  public List<BeanValidationMessagesKeys> verify(final List<String> prison) {
    List<BeanValidationMessagesKeys> violations = new ArrayList<>();
    if (!prison.stream()
      .filter(r -> r != null)
      .allMatch(row -> VALIDATOR_PATTERN.matcher(row).matches())) {
      violations.add(PRISON_INVALID_CHARACTERS);
    }
    return violations;
  }
}
