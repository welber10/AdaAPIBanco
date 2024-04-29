package tech.ada.banco.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = IdadeValidator.class)
public @interface IdadeValidation {
    public String message() default "Dever ter mais de 18 anos.";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
