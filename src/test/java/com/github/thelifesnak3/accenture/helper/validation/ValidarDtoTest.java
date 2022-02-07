package com.github.thelifesnak3.accenture.helper.validation;

import com.github.thelifesnak3.accenture.dto.CreateEvaluationDTO;
import com.github.thelifesnak3.accenture.exception.validate.ValidateException;
import com.github.thelifesnak3.accenture.helper.ValidationHelper;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@QuarkusTest
public class ValidarDtoTest {

    @Inject ValidationHelper validationHelper;

    @Test
    @TestSecurity(authorizationEnabled = false)
    @DisplayName("Testing validarDto validate everything without throw")
    public void testValidarDtoSuccess() {
        Assertions.assertDoesNotThrow(() -> validationHelper.validarDto(validCreateEvaluationDTO()));
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    @DisplayName("Testing validarDto throw ValidateException")
    public void testValidarDtoThrowValidateException() {
        ValidateException validateException = Assertions.assertThrows(ValidateException.class, () ->
            validationHelper.validarDto(new CreateEvaluationDTO())
        );
        Assertions.assertEquals(
            "There was an error in validating mandatory fields.",
            validateException.message
        );
        Assertions.assertEquals(
            5,
            validateException.errors.size()
        );
    }

    private CreateEvaluationDTO validCreateEvaluationDTO() {
        CreateEvaluationDTO createEvaluationDTO = new CreateEvaluationDTO();
        createEvaluationDTO.idMovie = "123";
        createEvaluationDTO.rate = 7;
        createEvaluationDTO.star = 3;
        createEvaluationDTO.comment = "teste";
        createEvaluationDTO.flPrivate = false;
        return  createEvaluationDTO;
    }
}
