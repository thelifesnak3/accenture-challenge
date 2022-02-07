package com.github.thelifesnak3.accenture.helper;

import com.github.thelifesnak3.accenture.dto.CreateSpeechGroupDTO;
import com.github.thelifesnak3.accenture.dto.UpdateSpeechGroupDTO;
import com.github.thelifesnak3.accenture.exception.validate.ValidateException;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;

@ApplicationScoped
public class SpeechGroupHelper {

    @Inject
    ValidationHelper validationHelper;

    public final String idInvalidoException = "The given id is invalid.";

    public void getByIdValidate(String id) {
        if(!ObjectId.isValid(id)) {
            throw new BadRequestException(idInvalidoException);
        }
    }

    public void createValidate(CreateSpeechGroupDTO createSpeechGroupDTO) throws ValidateException {
        validationHelper.validarDto(createSpeechGroupDTO);

        if(createSpeechGroupDTO.flPrivate && (createSpeechGroupDTO.users == null || createSpeechGroupDTO.users.isEmpty())) {
            throw new BadRequestException("Its mandatory to inform users in case of private Speech Group");
        }
    }

    public void updateValidate(UpdateSpeechGroupDTO updateSpeechGroupDTO, String id) throws ValidateException {
        validationHelper.validarDto(updateSpeechGroupDTO);

        if(!ObjectId.isValid(id)) {
            throw new BadRequestException(idInvalidoException);
        }

        if(updateSpeechGroupDTO.flPrivate && (updateSpeechGroupDTO.users == null || updateSpeechGroupDTO.users.isEmpty())) {
            throw new BadRequestException("Its mandatory to inform users in case of private Speech Group");
        }
    }

    public void deleteValidate(String id) {
        if(!ObjectId.isValid(id)) {
            throw new BadRequestException(idInvalidoException);
        }
    }
}
