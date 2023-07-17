package ni.com.prisonsolver.support.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static ni.com.prisonsolver.support.util.AppConstants.*;

@Documented
@Constraint(validatedBy = PrisonFormatValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PrisonFormatConstraint {

  String message() default "Prison invalid format";

  int minRowsAllowed() default MIN_ROWS_ALLOWED;

  int maxRowsAllowed() default MAX_ROWS_ALLOWED;

  int minColumnsAllowed() default MIN_COLUMNS_ALLOWED;

  int maxColumnsAllowed() default MAX_COLUMNS_ALLOWED;

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
