package com.github.thelifesnak3.accenture.repository;

import com.github.thelifesnak3.accenture.entity.SpeechGroup;
import io.quarkus.mongodb.panache.PanacheMongoRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class SpeechGroupRepository implements PanacheMongoRepository<SpeechGroup> {

    public List<SpeechGroup> getAllByUser(String idUser) {
        return find("idUser", idUser).list();
    }

    public Optional<SpeechGroup> getByIdUser(Map<String, Object> params) {
        return find("_id = :id and idUser = :idUser", params).firstResultOptional();
    }
}
