package tech.ada.banco.annotations;

import java.time.LocalDate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IdadeValidator implements ConstraintValidator<IdadeValidation, LocalDate> {

    @Override
    public boolean isValid(LocalDate dataNascimento, ConstraintValidatorContext context) {
        return dataNascimento.isBefore(LocalDate.now().minusYears(18));
    }

}
