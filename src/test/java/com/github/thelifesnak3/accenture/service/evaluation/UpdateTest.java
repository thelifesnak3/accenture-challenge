package com.github.thelifesnak3.accenture.service.evaluation;

import com.github.thelifesnak3.accenture.dto.UpdateEvaluationDTO;
import com.github.thelifesnak3.accenture.entity.Evaluation;
import com.github.thelifesnak3.accenture.exception.validate.ValidateException;
import com.github.thelifesnak3.accenture.helper.EvaluationHelper;
import com.github.thelifesnak3.accenture.repository.EvaluationRepository;
import com.github.thelifesnak3.accenture.service.EvaluationService;
import com.github.thelifesnak3.accenture.service.UsuarioService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.test.security.TestSecurity;
import org.bson.types.ObjectId;
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

    @Inject EvaluationService evaluationService;
    @InjectMock EvaluationRepository mockEvaluationRepository;
    @InjectMock UsuarioService mockUsuarioService;
    @InjectMock EvaluationHelper mockEvaluationHelper;

    final String validId = "60aff0b4e443fc2b67c753e0";

    @BeforeEach
    public void setup() throws ValidateException {
        Mockito.when(mockUsuarioService.getIdUser()).thenReturn("");
        Mockito.doNothing().when(mockEvaluationHelper).updateValidate(Mockito.any(UpdateEvaluationDTO.class), Mockito.anyString());
        Mockito.doNothing().when(mockEvaluationRepository).update(Mockito.any(Evaluation.class));
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    @DisplayName("Testing update service register the document without throw")
    public void testUpdateSuccess() {
        Mockito.when(mockEvaluationRepository.findByIdOptional(Mockito.any(ObjectId.class)))
            .thenReturn(Optional.of(new Evaluation()));

        Assertions.assertDoesNotThrow(() -> evaluationService.update(new UpdateEvaluationDTO(), validId));
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    @DisplayName("Testing update service throw NotFoundException")
    public void testUpdateThrowNotFoundException() {
        Mockito.when(mockEvaluationRepository.findByIdOptional(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> evaluationService.update(new UpdateEvaluationDTO(), validId));
    }
}
