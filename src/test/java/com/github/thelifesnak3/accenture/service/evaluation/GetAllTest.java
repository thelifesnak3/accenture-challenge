package com.github.thelifesnak3.accenture.service.evaluation;

import com.github.thelifesnak3.accenture.repository.EvaluationRepository;
import com.github.thelifesnak3.accenture.service.EvaluationService;
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

    @Inject EvaluationService evaluationService;
    @InjectMock EvaluationRepository mockEvaluationRepository;
    @InjectMock UsuarioService mockUsuarioService;

    @BeforeEach
    public void setup() {
        Mockito.when(mockUsuarioService.getIdUser()).thenReturn("");
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    @DisplayName("Testing getAll service return an list without throw")
    public void testGetAllSuccess() {
        Mockito.when(mockEvaluationRepository.getAllByUser(Mockito.anyString())).thenReturn(new ArrayList<>());
        Assertions.assertDoesNotThrow(() -> evaluationService.getAll());
    }
}
