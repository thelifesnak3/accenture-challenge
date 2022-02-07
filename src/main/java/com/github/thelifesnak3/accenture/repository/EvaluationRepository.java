package com.github.thelifesnak3.accenture.repository;

import com.github.thelifesnak3.accenture.entity.Evaluation;
import io.quarkus.mongodb.panache.PanacheMongoRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class EvaluationRepository implements PanacheMongoRepository<Evaluation> {

    public List<Evaluation> getAllByUser(String idUser) {
        return find("idUser", idUser).list();
    }

    public Optional<Evaluation> getByMovie(Map<String, Object> params) {
        return find("idMovie = :idMovie and idUser = :idUser", params).firstResultOptional();
    }
}
