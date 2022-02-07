package com.github.thelifesnak3.accenture.entity;

import io.quarkus.mongodb.panache.MongoEntity;
import org.bson.types.ObjectId;

import java.util.List;

@MongoEntity(collection="speechGroup")
public class SpeechGroup {
    public ObjectId id;
    public String title;
    public String description;
    public String idUser;
    public List<String> users;
    public Boolean flPrivate;
}
