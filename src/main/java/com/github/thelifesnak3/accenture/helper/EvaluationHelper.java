package com.github.thelifesnak3.accenture.helper;

import com.github.thelifesnak3.accenture.dto.CreateEvaluationDTO;
import com.github.thelifesnak3.accenture.dto.MovieDTO;
import com.github.thelifesnak3.accenture.dto.UpdateEvaluationDTO;
import com.github.thelifesnak3.accenture.entity.Evaluation;
import com.github.thelifesnak3.accenture.exception.validate.ValidateException;
import com.github.thelifesnak3.accenture.repository.EvaluationRepository;
import com.github.thelifesnak3.accenture.service.omdb.OmdbService;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class EvaluationHelper {

    @Inject EvaluationRepository evaluationRepository;

    @Inject @RestClient OmdbService omdbService;

    @Inject
    ValidationHelper validationHelper;

    public final String idInvalidoException = "The given id is invalid.";

    public void createValidate(CreateEvaluationDTO createEvaluationDTO, String idUser) throws ValidateException {
        validationHelper.validarDto(createEvaluationDTO);

        MovieDTO movieDTO = omdbService.getMovieById(createEvaluationDTO.idMovie);
        if(movieDTO.imdbID == null) {
            throw new BadRequestException("Could not find any movie with the given id: "+createEvaluationDTO.idMovie);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("idMovie", createEvaluationDTO.idMovie);
        params.put("idUser", idUser);

        Optional<Evaluation> evaluationOp = evaluationRepository.getByMovie(params);

        if(evaluationOp.isPresent()) {
            throw new BadRequestException("There is already an evaluation for the informed movie.");
        }
    }

    public void updateValidate(UpdateEvaluationDTO updateEvaluationDTO, String id) throws ValidateException {
        validationHelper.validarDto(updateEvaluationDTO);

        MovieDTO movieDTO = omdbService.getMovieById(updateEvaluationDTO.idMovie);
        if(movieDTO.imdbID == null) {
            throw new BadRequestException("Could not find any movie with the given id: "+updateEvaluationDTO.idMovie);
        }

        if(!ObjectId.isValid(id)) {
            throw new BadRequestException(idInvalidoException);
        }
    }

    public void deleteValidate(String id) {
        if(!ObjectId.isValid(id)) {
            throw new BadRequestException(idInvalidoException);
        }
    }
}
