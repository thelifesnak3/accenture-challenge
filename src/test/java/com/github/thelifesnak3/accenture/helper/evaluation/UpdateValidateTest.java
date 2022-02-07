package com.github.thelifesnak3.accenture.helper.evaluation;

import com.github.thelifesnak3.accenture.dto.MovieDTO;
import com.github.thelifesnak3.accenture.dto.UpdateEvaluationDTO;
import com.github.thelifesnak3.accenture.exception.validate.ValidateException;
import com.github.thelifesnak3.accenture.helper.EvaluationHelper;
import com.github.thelifesnak3.accenture.helper.ValidationHelper;
import com.github.thelifesnak3.accenture.service.omdb.OmdbService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.test.security.TestSecurity;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;

@QuarkusTest
public class UpdateValidateTest {

    @Inject EvaluationHelper evaluationHelper;
    @InjectMock @RestClient OmdbService mockOmdbService;
    @InjectMock
    ValidationHelper mockValidationHelper;

    final String idInvalidoException = "The given id is invalid.";
    final String validId = "60aff0b4e443fc2b67c753e0";

    @BeforeEach
    public void setup() throws ValidateException {
        Mockito.doNothing().when(mockValidationHelper).validarDto(Mockito.any());
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    @DisplayName("Testing updateValidate validate everything without throw")
    public void testUpdateValidateSuccess() {
        Mockito.when(mockOmdbService.getMovieById(Mockito.anyString())).thenReturn(createMovieDTO());

        Assertions.assertDoesNotThrow(() -> evaluationHelper.updateValidate(updateEvaluationDTO(), validId));
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    @DisplayName("Testing updateValidate throw BadRequestException for empty return from omdbService.getMovieById")
    public void testUpdateValidateThrowBadRequestExceptionOmdb() {
        Mockito.when(mockOmdbService.getMovieById(Mockito.anyString())).thenReturn(new MovieDTO());

        BadRequestException badRequestException = Assertions.assertThrows(BadRequestException.class, () ->
            evaluationHelper.updateValidate(updateEvaluationDTO(), validId)
        );
        Assertions.assertEquals(
            "Could not find any movie with the given id: 123",
            badRequestException.getMessage()
        );
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    @DisplayName("Testing updateValidate throw BadRequestException for invalid id")
    public void testUpdateValidateThrowBadRequestExceptionIdInvalid() {
        Mockito.when(mockOmdbService.getMovieById(Mockito.anyString())).thenReturn(createMovieDTO());

        BadRequestException badRequestException = Assertions.assertThrows(BadRequestException.class, () ->
            evaluationHelper.updateValidate(updateEvaluationDTO(), "123")
        );
        Assertions.assertEquals(
            idInvalidoException,
            badRequestException.getMessage()
        );
    }

    private UpdateEvaluationDTO updateEvaluationDTO() {
        UpdateEvaluationDTO updateEvaluationDTO = new UpdateEvaluationDTO();
        updateEvaluationDTO.idMovie = "123";
        return updateEvaluationDTO;
    }

    private MovieDTO createMovieDTO() {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.imdbID = "123123";
        return movieDTO;
    }
}
