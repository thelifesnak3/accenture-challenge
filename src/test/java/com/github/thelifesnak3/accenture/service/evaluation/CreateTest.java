package com.github.thelifesnak3.accenture.service.evaluation;

import com.github.thelifesnak3.accenture.dto.CreateEvaluationDTO;
import com.github.thelifesnak3.accenture.entity.Evaluation;
import com.github.thelifesnak3.accenture.exception.validate.ValidateException;
import com.github.thelifesnak3.accenture.helper.EvaluationHelper;
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

@QuarkusTest
public class CreateTest {

    @Inject EvaluationService evaluationService;
    @InjectMock EvaluationRepository mockEvaluationRepository;
    @InjectMock UsuarioService mockUsuarioService;
    @InjectMock EvaluationHelper mockEvaluationHelper;

    @BeforeEach
    public void setup() throws ValidateException {
        Mockito.when(mockUsuarioService.getIdUser()).thenReturn("");
        Mockito.doNothing().when(mockEvaluationHelper).createValidate(Mockito.any(CreateEvaluationDTO.class), Mockito.anyString());
        Mockito.doNothing().when(mockEvaluationRepository).persist(Mockito.any(Evaluation.class));
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    @DisplayName("Testing create service register the document without throw")
    public void testCreateSuccess() {
        Assertions.assertDoesNotThrow(() -> evaluationService.create(new CreateEvaluationDTO()));
    }
}
