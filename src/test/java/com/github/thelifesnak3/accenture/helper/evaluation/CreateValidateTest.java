package com.github.thelifesnak3.accenture.helper.evaluation;

import com.github.thelifesnak3.accenture.dto.CreateEvaluationDTO;
import com.github.thelifesnak3.accenture.dto.MovieDTO;
import com.github.thelifesnak3.accenture.entity.Evaluation;
import com.github.thelifesnak3.accenture.exception.validate.ValidateException;
import com.github.thelifesnak3.accenture.helper.EvaluationHelper;
import com.github.thelifesnak3.accenture.helper.ValidationHelper;
import com.github.thelifesnak3.accenture.repository.EvaluationRepository;
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
import java.util.Optional;

@QuarkusTest
public class CreateValidateTest {

    @Inject EvaluationHelper evaluationHelper;
    @InjectMock EvaluationRepository mockEvaluationRepository;
    @InjectMock @RestClient OmdbService mockOmdbService;
    @InjectMock
    ValidationHelper mockValidationHelper;

    @BeforeEach
    public void setup() throws ValidateException {
        Mockito.doNothing().when(mockValidationHelper).validarDto(Mockito.any());
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    @DisplayName("Testing createValidate validate everything without throw")
    public void testCreateValidateSuccess() {
        Mockito.when(mockOmdbService.getMovieById(Mockito.anyString())).thenReturn(createMovieDTO());
        Mockito.when(mockEvaluationRepository.getByMovie(Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertDoesNotThrow(() -> evaluationHelper.createValidate(createEvaluationDTO(), "123"));
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    @DisplayName("Testing createValidate throw BadRequestException for empty return from omdbService.getMovieById")
    public void testCreateValidateThrowBadRequestExceptionOmdb() {
        Mockito.when(mockOmdbService.getMovieById(Mockito.anyString())).thenReturn(new MovieDTO());
        Mockito.when(mockEvaluationRepository.getByMovie(Mockito.any())).thenReturn(Optional.empty());

        BadRequestException badRequestException = Assertions.assertThrows(BadRequestException.class, () ->
            evaluationHelper.createValidate(createEvaluationDTO(), "123")
        );
        Assertions.assertEquals(
                "Could not find any movie with the given id: 123",
                badRequestException.getMessage()
        );
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    @DisplayName("Testing createValidate throw BadRequestException for duplicated evaluation for the informed movie")
    public void testCreateValidateThrowBadRequestExceptionDuplicated() {
        Mockito.when(mockOmdbService.getMovieById(Mockito.anyString())).thenReturn(createMovieDTO());
        Mockito.when(mockEvaluationRepository.getByMovie(Mockito.any())).thenReturn(Optional.of(new Evaluation()));

        BadRequestException badRequestException = Assertions.assertThrows(BadRequestException.class, () ->
            evaluationHelper.createValidate(createEvaluationDTO(), "123")
        );
        Assertions.assertEquals(
            "There is already an evaluation for the informed movie.",
            badRequestException.getMessage()
        );
    }

    private CreateEvaluationDTO createEvaluationDTO() {
        CreateEvaluationDTO createEvaluationDTO = new CreateEvaluationDTO();
        createEvaluationDTO.idMovie = "123";
        return createEvaluationDTO;
    }

    private MovieDTO createMovieDTO() {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.imdbID = "123123";
        return movieDTO;
    }
}
