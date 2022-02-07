package com.github.thelifesnak3.accenture.service.speechgroup;

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
import java.util.ArrayList;

@QuarkusTest
public class GetAllTest {

    @Inject SpeechGroupService speechGroupService;
    @InjectMock SpeechGroupRepository mockSpeechGroupRepository;
    @InjectMock UsuarioService mockUsuarioService;

    @BeforeEach
    public void setup() {
        Mockito.when(mockUsuarioService.getIdUser()).thenReturn("");
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    @DisplayName("Testing getAll service return an list without throw")
    public void testGetAllSuccess() {
        Mockito.when(mockSpeechGroupRepository.getAllByUser(Mockito.anyString())).thenReturn(new ArrayList<>());
        Assertions.assertDoesNotThrow(() -> speechGroupService.getAll());
    }
}
