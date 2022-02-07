package com.github.thelifesnak3.accenture.helper.speechgroup;

import com.github.thelifesnak3.accenture.dto.UpdateSpeechGroupDTO;
import com.github.thelifesnak3.accenture.exception.validate.ValidateException;
import com.github.thelifesnak3.accenture.helper.SpeechGroupHelper;
import com.github.thelifesnak3.accenture.helper.ValidationHelper;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import java.util.ArrayList;

@QuarkusTest
public class UpdateValidateTest {

    @Inject SpeechGroupHelper speechGroupHelper;
    @InjectMock
    ValidationHelper mockValidationHelper;

    final String validId = "60aff0b4e443fc2b67c753e0";
    final String idInvalidoException = "The given id is invalid.";

    @BeforeEach
    public void setup() throws ValidateException {
        Mockito.doNothing().when(mockValidationHelper).validarDto(Mockito.any());
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    @DisplayName("Testing updateValidate doesnt throw")
    public void testUpdateValidateSuccess() {
        Assertions.assertDoesNotThrow(() ->
            speechGroupHelper.updateValidate(validUpdateSpeechGroupDTO(), validId)
        );
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    @DisplayName("Testing updateValidate throw BadRequestException for invalid id")
    public void testUpdateValidateThrowBadRequestException() {
        BadRequestException badRequestException = Assertions.assertThrows(BadRequestException.class, () ->
            speechGroupHelper.updateValidate(validUpdateSpeechGroupDTO(), "123")
        );
        Assertions.assertEquals(
            idInvalidoException,
            badRequestException.getMessage()
        );
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    @DisplayName("Testing updateValidate throw BadRequestException for flag private without users")
    public void testUpdateValidateThrowBadRequestExceptionFlPrivate() {
        BadRequestException badRequestException = Assertions.assertThrows(BadRequestException.class, () ->
            speechGroupHelper.updateValidate(invalidUpdateSpeechGroupDTO(), validId)
        );
        Assertions.assertEquals(
            "Its mandatory to inform users in case of private Speech Group",
            badRequestException.getMessage()
        );
    }

    private UpdateSpeechGroupDTO validUpdateSpeechGroupDTO() {
        UpdateSpeechGroupDTO updateSpeechGroupDTO = new UpdateSpeechGroupDTO();
        updateSpeechGroupDTO.flPrivate = true;
        updateSpeechGroupDTO.users = new ArrayList<>();
        updateSpeechGroupDTO.users.add("teste123");
        return updateSpeechGroupDTO;
    }

    private UpdateSpeechGroupDTO invalidUpdateSpeechGroupDTO() {
        UpdateSpeechGroupDTO updateSpeechGroupDTO = new UpdateSpeechGroupDTO();
        updateSpeechGroupDTO.flPrivate = true;
        updateSpeechGroupDTO.users = new ArrayList<>();
        return updateSpeechGroupDTO;
    }
}
