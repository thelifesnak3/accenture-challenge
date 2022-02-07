package com.github.thelifesnak3.accenture.entity;

import com.github.thelifesnak3.accenture.dto.MovieFormattedDTO;
import io.quarkus.mongodb.panache.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import java.util.List;

@MongoEntity(collection="wishlist")
public class Wishlist {
    @BsonId
    public ObjectId id;
    public String name;
    public String description;
    public String idUser;
    public List<MovieFormattedDTO> movies;
    public Boolean flPrivate;
}
