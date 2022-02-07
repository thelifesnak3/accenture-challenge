package com.github.thelifesnak3.accenture.service.speechgroup;

import com.github.thelifesnak3.accenture.dto.UpdateSpeechGroupDTO;
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
import javax.ws.rs.NotFoundException;
import java.util.Optional;

@QuarkusTest
public class UpdateTest {

    @Inject SpeechGroupService speechGroupService;
    @InjectMock SpeechGroupRepository mockSpeechGroupRepository;
    @InjectMock UsuarioService mockUsuarioService;
    @InjectMock SpeechGroupHelper mockSpeechGroupHelper;

    final String validId = "60aff0b4e443fc2b67c753e0";

    @BeforeEach
    public void setup() throws ValidateException {
        Mockito.when(mockUsuarioService.getIdUser()).thenReturn("");
        Mockito.doNothing().when(mockSpeechGroupHelper).updateValidate(Mockito.any(UpdateSpeechGroupDTO.class), Mockito.anyString());
        Mockito.doNothing().when(mockSpeechGroupRepository).update(Mockito.any(SpeechGroup.class));
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    @DisplayName("Testing update service register the document without throw")
    public void testUpdateSuccess() {
        Mockito.when(mockSpeechGroupRepository.getByIdUser(Mockito.any()))
                .thenReturn(Optional.of(new SpeechGroup()));

        Assertions.assertDoesNotThrow(() -> speechGroupService.update(new UpdateSpeechGroupDTO(), validId));
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    @DisplayName("Testing update service throw NotFoundException")
    public void testUpdateThrowNotFoundException() {
        Mockito.when(mockSpeechGroupRepository.getByIdUser(Mockito.any()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> speechGroupService.update(new UpdateSpeechGroupDTO(), validId));
    }
}
