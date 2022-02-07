package com.github.thelifesnak3.accenture.service.speechgroup;

import com.github.thelifesnak3.accenture.entity.SpeechGroup;
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
public class GetByIdTest {

    @Inject SpeechGroupService speechGroupService;
    @InjectMock SpeechGroupRepository mockSpeechGroupRepository;
    @InjectMock UsuarioService mockUsuarioService;
    @InjectMock SpeechGroupHelper mockSpeechGroupHelper;

    final String validId = "60aff0b4e443fc2b67c753e0";

    @BeforeEach
    public void setup() {
        Mockito.when(mockUsuarioService.getIdUser()).thenReturn("");
        Mockito.doNothing().when(mockSpeechGroupHelper).getByIdValidate(Mockito.anyString());
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    @DisplayName("Testing getById service return an speech group without throw")
    public void testGetByIdSuccess() {
        Mockito.when(mockSpeechGroupRepository.getByIdUser(Mockito.any()))
                .thenReturn(Optional.of(createSpeechGroup()));

        SpeechGroup speechGroup = Assertions.assertDoesNotThrow(() -> speechGroupService.getById(validId));
        Assertions.assertEquals("Teste", speechGroup.title);
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    @DisplayName("Testing getById service throw NotFoundException")
    public void testGetByIdThrowNotFoundException() {
        Mockito.when(mockSpeechGroupRepository.getByIdUser(Mockito.any()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> speechGroupService.getById(validId));
    }

    private SpeechGroup createSpeechGroup() {
        SpeechGroup speechGroup = new SpeechGroup();
        speechGroup.title = "Teste";
        return speechGroup;
    }
}
