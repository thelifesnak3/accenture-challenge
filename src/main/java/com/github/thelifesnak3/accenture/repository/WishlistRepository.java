package com.github.thelifesnak3.accenture.repository;

import com.github.thelifesnak3.accenture.entity.Wishlist;
import io.quarkus.mongodb.panache.PanacheMongoRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class WishlistRepository implements PanacheMongoRepository<Wishlist> {

    public List<Wishlist> getAllByUser(String idUser) {
        return find("idUser", idUser).list();
    }
}
