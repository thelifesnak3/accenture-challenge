package com.github.thelifesnak3.accenture.helper;

import com.github.thelifesnak3.accenture.exception.validate.ValidateException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class ValidationHelper {
    @Inject
    Validator validator;

    public <T> void validarDto(T t)
            throws ValidateException {
        Set<ConstraintViolation<T>> violations = validator.validate(t);
        List<String> listViolations =
                violations.stream().map(ConstraintViolation::getMessageTemplate).collect(Collectors.toList());

        if(!violations.isEmpty()) {
            throw new ValidateException(
                    "There was an error in validating mandatory fields.",
                    listViolations
            );
        }
    }
}
