package com.github.thelifesnak3.accenture.service;

import com.github.thelifesnak3.accenture.dto.CreateEvaluationDTO;
import com.github.thelifesnak3.accenture.dto.UpdateEvaluationDTO;
import com.github.thelifesnak3.accenture.entity.Evaluation;
import com.github.thelifesnak3.accenture.exception.validate.ValidateException;
import com.github.thelifesnak3.accenture.helper.EvaluationHelper;
import com.github.thelifesnak3.accenture.mapper.EvaluationMapper;
import com.github.thelifesnak3.accenture.repository.EvaluationRepository;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class EvaluationService {

    @Inject EvaluationRepository evaluationRepository;

    @Inject UsuarioService usuarioService;

    @Inject EvaluationMapper evaluationMapper;

    @Inject EvaluationHelper evaluationHelper;

    public List<Evaluation> getAll() {
        return evaluationRepository.getAllByUser(usuarioService.getIdUser());
    }

    public Evaluation getByMovie(String idMovie) {
        Map<String, Object> params = new HashMap<>();
        params.put("idMovie", idMovie);
        params.put("idUser", usuarioService.getIdUser());

        Optional<Evaluation> evaluationOp = evaluationRepository.getByMovie(params);

        if(evaluationOp.isEmpty()) {
            throw new NotFoundException();
        }

        return evaluationOp.get();
    }

    public void create(CreateEvaluationDTO createEvaluationDTO) throws ValidateException {
        evaluationHelper.createValidate(createEvaluationDTO, usuarioService.getIdUser());

        Evaluation evaluation = evaluationMapper.toEvaluation(createEvaluationDTO);
        evaluation.idUser = usuarioService.getIdUser();

        evaluationRepository.persist(evaluation);
    }

    public void update(UpdateEvaluationDTO updateEvaluationDTO, String id) throws ValidateException {
        evaluationHelper.updateValidate(updateEvaluationDTO, id);

        Optional<Evaluation> evaluationOp = evaluationRepository.findByIdOptional(new ObjectId(id));

        evaluationOp.ifPresentOrElse(evaluation -> {
            evaluationMapper.toEvaluation(updateEvaluationDTO, evaluation);
            evaluationRepository.update(evaluation);
        }, () -> {
            throw new NotFoundException();
        });
    }

    public void delete(String id) {
        evaluationHelper.deleteValidate(id);

        Optional<Evaluation> evaluationOp = evaluationRepository.findByIdOptional(new ObjectId(id));

        evaluationOp.ifPresentOrElse(evaluation -> evaluationRepository.delete(evaluation), () -> {
            throw new NotFoundException();
        });
    }
}
