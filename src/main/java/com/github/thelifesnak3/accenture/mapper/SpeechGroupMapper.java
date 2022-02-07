package com.github.thelifesnak3.accenture.mapper;

import com.github.thelifesnak3.accenture.dto.CreateSpeechGroupDTO;
import com.github.thelifesnak3.accenture.dto.UpdateSpeechGroupDTO;
import com.github.thelifesnak3.accenture.entity.SpeechGroup;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public interface SpeechGroupMapper {
    SpeechGroup toSpeechGroup(CreateSpeechGroupDTO dto);
    void toSpeechGroup(UpdateSpeechGroupDTO dto, @MappingTarget SpeechGroup speechGroup);
}