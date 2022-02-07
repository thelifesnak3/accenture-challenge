package com.github.thelifesnak3.accenture.service.speechgroup;

import com.github.thelifesnak3.accenture.dto.CreateSpeechGroupDTO;
import com.github.thelifesnak3.accenture.entity.SpeechGroup;
import com.github.thelifesnak3.accenture.exception.validate.ValidateException;
import com.github.thelifesnak3.accenture.helper.SpeechGroupHelper;
import com.github.thelifesnak3.accenture.repository.SpeechGroupRepository;
import com.github.thelifesnak3.accenture.service.SpeechGroupService;
import com.github.thelifesnak3.accenture.service.UsuarioService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;

@QuarkusTest
public class CreateTest {

    @Inject SpeechGroupService speechGroupService;
    @InjectMock SpeechGroupRepository mockSpeechGroupRepository;
    @InjectMock UsuarioService mockUsuarioService;
    @InjectMock SpeechGroupHelper mockSpeechGroupHelper;

    @BeforeEach
    public void setup() throws ValidateException {
        Mockito.when(mockUsuarioService.getIdUser()).thenReturn("");
        Mockito.doNothing().when(mockSpeechGroupHelper).createValidate(Mockito.any(CreateSpeechGroupDTO.class));
        Mockito.doNothing().when(mockSpeechGroupRepository).persist(Mockito.any(SpeechGroup.class));
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    @DisplayName("Testing create service register the document without throw")
    public void testCreateSuccess() {
        Assertions.assertDoesNotThrow(() -> speechGroupService.create(new CreateSpeechGroupDTO()));
    }
}
