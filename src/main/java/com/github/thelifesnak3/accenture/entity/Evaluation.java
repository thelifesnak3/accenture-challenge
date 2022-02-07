package com.github.thelifesnak3.accenture.entity;

import io.quarkus.mongodb.panache.MongoEntity;
import org.bson.types.ObjectId;

@MongoEntity(collection="evaluation")
public class Evaluation {
    public ObjectId id;
    public String idMovie;
    public String idUser;
    public Integer rate;
    public Integer star;
    public String comment;
    public Boolean flPrivate;
}
