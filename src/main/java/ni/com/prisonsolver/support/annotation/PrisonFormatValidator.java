package ni.com.prisonsolver.support.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ni.com.prisonsolver.support.annotation.rule.*;

import java.util.List;
import java.util.stream.Collectors;

import static ni.com.prisonsolver.support.annotation.BeanValidationMessagesKeys.PRISON_PARAM_IS_NULL;

public class PrisonFormatValidator implements ConstraintValidator<PrisonFormatConstraint, List<String>> {

  private List<RulePrisonFormat> rules;

  private final ColumnRestrictionRule columnRestrictionRule = new ColumnRestrictionRule();

  private final CharactersAllowedRule charactersAllowedRule = new CharactersAllowedRule();

  private final PrisonerAndExitRestrictionRule prisonerAndExitRestrictionRule = new PrisonerAndExitRestrictionRule();

  private final RowRestrictionRule rowRestrictionRule = new RowRestrictionRule();

  private final BoundariesRestrictionRule boundariesRestrictionRule = new BoundariesRestrictionRule();


  @Override
  public void initialize(PrisonFormatConstraint constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
    rules = List.of(columnRestrictionRule, charactersAllowedRule,
      prisonerAndExitRestrictionRule, rowRestrictionRule, boundariesRestrictionRule);
  }

  @Override
  public boolean isValid(List<String> prison, ConstraintValidatorContext context) {
    context.disableDefaultConstraintViolation();
    var violations = this.validateField(prison);
    violations.forEach(v -> context.buildConstraintViolationWithTemplate(v.toString()).addConstraintViolation());
    return violations.isEmpty();
  }

  private List<BeanValidationMessagesKeys> validateField(final List<String> prison) {
    if (prison == null || prison.isEmpty()) {
      return List.of(PRISON_PARAM_IS_NULL);
    }
    return rules.stream()
      .map(r -> r.verify(prison))
      .flatMap(List::stream)
      .collect(Collectors.toList());
  }
}
