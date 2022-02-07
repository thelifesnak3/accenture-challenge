package com.github.thelifesnak3.accenture.helper.speechgroup;

import com.github.thelifesnak3.accenture.helper.SpeechGroupHelper;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;

@QuarkusTest
public class DeleteValidateTest {

    @Inject
    SpeechGroupHelper speechGroupHelper;

    final String idInvalidoException = "The given id is invalid.";
    final String validId = "60aff0b4e443fc2b67c753e0";

    @Test
    @TestSecurity(authorizationEnabled = false)
    @DisplayName("Testing deleteValidate doesnt throw")
    public void testDeleteValidateSuccess() {
        Assertions.assertDoesNotThrow(() ->
            speechGroupHelper.deleteValidate(validId)
        );
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    @DisplayName("Testing deleteValidate throw BadRequestException for invalid id")
    public void testDeleteValidateThrowBadRequestExceptionIdInvalid() {
        BadRequestException badRequestException = Assertions.assertThrows(BadRequestException.class, () ->
            speechGroupHelper.deleteValidate("123")
        );
        Assertions.assertEquals(
            idInvalidoException,
            badRequestException.getMessage()
        );
    }
}
