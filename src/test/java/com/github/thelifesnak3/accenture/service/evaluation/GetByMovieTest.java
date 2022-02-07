package com.github.thelifesnak3.accenture.service.evaluation;

import com.github.thelifesnak3.accenture.entity.Evaluation;
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
import javax.ws.rs.NotFoundException;
import java.util.Optional;

@QuarkusTest
public class GetByMovieTest {

    @Inject EvaluationService evaluationService;
    @InjectMock EvaluationRepository mockEvaluationRepository;
    @InjectMock UsuarioService mockUsuarioService;

    @BeforeEach
    public void setup() {
        Mockito.when(mockUsuarioService.getIdUser()).thenReturn("");
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    @DisplayName("Testing getByMovie service return an evaluation without throw")
    public void testGetByMovieSuccess() {
        Mockito.when(mockEvaluationRepository.getByMovie(Mockito.any()))
            .thenReturn(Optional.of(createEvaluation()));

        Evaluation evaluation = Assertions.assertDoesNotThrow(() -> evaluationService.getByMovie(""));
        Assertions.assertEquals("Teste", evaluation.comment);
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    @DisplayName("Testing getByMovie service throw NotFoundException")
    public void testGetByMovieThrowNotFoundException() {
        Mockito.when(mockEvaluationRepository.getByMovie(Mockito.any()))
            .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> evaluationService.getByMovie(""));
    }

    private Evaluation createEvaluation() {
        Evaluation evaluation = new Evaluation();
        evaluation.comment = "Teste";
        return evaluation;
    }
}
