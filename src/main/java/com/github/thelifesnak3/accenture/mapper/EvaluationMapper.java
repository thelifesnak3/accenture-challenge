package com.github.thelifesnak3.accenture.mapper;

import com.github.thelifesnak3.accenture.dto.CreateEvaluationDTO;
import com.github.thelifesnak3.accenture.dto.UpdateEvaluationDTO;
import com.github.thelifesnak3.accenture.entity.Evaluation;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public interface EvaluationMapper {
    Evaluation toEvaluation(CreateEvaluationDTO dto);
    void toEvaluation(UpdateEvaluationDTO dto, @MappingTarget Evaluation evaluation);
}
