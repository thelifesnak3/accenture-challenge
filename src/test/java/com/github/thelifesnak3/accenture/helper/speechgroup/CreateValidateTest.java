package com.github.thelifesnak3.accenture.helper.speechgroup;

import com.github.thelifesnak3.accenture.dto.CreateSpeechGroupDTO;
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
public class CreateValidateTest {

    @Inject SpeechGroupHelper speechGroupHelper;
    @InjectMock
    ValidationHelper mockValidationHelper;

    @BeforeEach
    public void setup() throws ValidateException {
        Mockito.doNothing().when(mockValidationHelper).validarDto(Mockito.any());
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    @DisplayName("Testing createValidate doesnt throw")
    public void testCreateValidateSuccess() {
        Assertions.assertDoesNotThrow(() ->
            speechGroupHelper.createValidate(validCreateSpeechGroupDTO())
        );
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    @DisplayName("Testing createValidate throw BadRequestException for flag private without users")
    public void testCreateValidateThrowBadRequestException() {
        BadRequestException badRequestException = Assertions.assertThrows(BadRequestException.class, () ->
            speechGroupHelper.createValidate(invalidCreateSpeechGroupDTO())
        );
        Assertions.assertEquals(
            "Its mandatory to inform users in case of private Speech Group",
            badRequestException.getMessage()
        );
    }

    private CreateSpeechGroupDTO validCreateSpeechGroupDTO() {
        CreateSpeechGroupDTO createSpeechGroupDTO = new CreateSpeechGroupDTO();
        createSpeechGroupDTO.flPrivate = true;
        createSpeechGroupDTO.users = new ArrayList<>();
        createSpeechGroupDTO.users.add("teste123");
        return createSpeechGroupDTO;
    }

    private CreateSpeechGroupDTO invalidCreateSpeechGroupDTO() {
        CreateSpeechGroupDTO createSpeechGroupDTO = new CreateSpeechGroupDTO();
        createSpeechGroupDTO.flPrivate = true;
        createSpeechGroupDTO.users = new ArrayList<>();
        return createSpeechGroupDTO;
    }
}
