package com.github.thelifesnak3.accenture.service;

import com.github.thelifesnak3.accenture.dto.CreateSpeechGroupDTO;
import com.github.thelifesnak3.accenture.dto.UpdateSpeechGroupDTO;
import com.github.thelifesnak3.accenture.entity.Evaluation;
import com.github.thelifesnak3.accenture.entity.SpeechGroup;
import com.github.thelifesnak3.accenture.exception.validate.ValidateException;
import com.github.thelifesnak3.accenture.helper.SpeechGroupHelper;
import com.github.thelifesnak3.accenture.mapper.SpeechGroupMapper;
import com.github.thelifesnak3.accenture.repository.SpeechGroupRepository;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class SpeechGroupService {

    @Inject SpeechGroupRepository speechGroupRepository;

    @Inject UsuarioService usuarioService;

    @Inject SpeechGroupMapper speechGroupMapper;

    @Inject SpeechGroupHelper speechGroupHelper;

    public List<SpeechGroup> getAll() {
        return speechGroupRepository.getAllByUser(usuarioService.getIdUser());
    }

    public SpeechGroup getById(String id) {
        speechGroupHelper.getByIdValidate(id);

        Map<String, Object> params = new HashMap<>();
        params.put("id", new ObjectId(id));
        params.put("idUser", usuarioService.getIdUser());
        Optional<SpeechGroup> speechGroupOp = speechGroupRepository.getByIdUser(params);

        if(speechGroupOp.isEmpty()) {
            throw new NotFoundException();
        }

        return speechGroupOp.get();
    }

    public void create(CreateSpeechGroupDTO createSpeechGroupDTO) throws ValidateException {
        speechGroupHelper.createValidate(createSpeechGroupDTO);

        SpeechGroup speechGroup = speechGroupMapper.toSpeechGroup(createSpeechGroupDTO);
        speechGroup.idUser = usuarioService.getIdUser();

        speechGroupRepository.persist(speechGroup);
    }

    public void update(UpdateSpeechGroupDTO updateSpeechGroupDTO, String id) throws ValidateException {
        speechGroupHelper.updateValidate(updateSpeechGroupDTO, id);

        Map<String, Object> params = new HashMap<>();
        params.put("id", new ObjectId(id));
        params.put("idUser", usuarioService.getIdUser());
        Optional<SpeechGroup> speechGroupOp = speechGroupRepository.getByIdUser(params);

        speechGroupOp.ifPresentOrElse(speechGroup -> {
            speechGroupMapper.toSpeechGroup(updateSpeechGroupDTO, speechGroup);
            speechGroupRepository.update(speechGroup);
        }, () -> {
            throw new NotFoundException();
        });
    }

    public void delete(String id) {
        speechGroupHelper.deleteValidate(id);

        Optional<SpeechGroup> speechGroupOp = speechGroupRepository.findByIdOptional(new ObjectId(id));

        speechGroupOp.ifPresentOrElse(speechGroup -> speechGroupRepository.delete(speechGroup), () -> {
            throw new NotFoundException();
        });
    }
}
