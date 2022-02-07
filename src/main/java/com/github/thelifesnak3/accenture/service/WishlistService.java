package com.github.thelifesnak3.accenture.service;

import com.github.thelifesnak3.accenture.dto.CreateWishlistDTO;
import com.github.thelifesnak3.accenture.dto.MovieDTO;
import com.github.thelifesnak3.accenture.dto.WishlistDTO;
import com.github.thelifesnak3.accenture.entity.Wishlist;
import com.github.thelifesnak3.accenture.exception.validate.ValidateException;
import com.github.thelifesnak3.accenture.helper.ValidationHelper;
import com.github.thelifesnak3.accenture.mapper.MovieMapper;
import com.github.thelifesnak3.accenture.mapper.WishlistMapper;
import com.github.thelifesnak3.accenture.repository.WishlistRepository;
import com.github.thelifesnak3.accenture.service.omdb.OmdbService;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class WishlistService {

    @Inject WishlistRepository wishlistRepository;

    @Inject UsuarioService usuarioService;

    @Inject @RestClient OmdbService omdbService;

    @Inject WishlistMapper wishlistMapper;

    @Inject MovieMapper movieMapper;

    @Inject
    ValidationHelper validationHelper;

    public final String idInvalidoException = "The given id is invalid.";

    public List<Wishlist> getAll(String gender, String actor, String director) {
        List<Wishlist> list = wishlistRepository.getAllByUser(usuarioService.getIdUser());

        if(gender != null) {
            list = list.stream().filter(wishlist ->
                wishlist.movies.stream().anyMatch(movieFormattedDTO ->
                    movieFormattedDTO.genre.contains(gender))
            ).collect(Collectors.toList());
        }
        if(actor != null) {
            list = list.stream().filter(wishlist ->
                wishlist.movies.stream().anyMatch(movieFormattedDTO ->
                    movieFormattedDTO.actor.contains(actor))
            ).collect(Collectors.toList());
        }
        if(director != null) {
            list = list.stream().filter(wishlist ->
                wishlist.movies.stream().anyMatch(movieFormattedDTO ->
                    movieFormattedDTO.director.contains(director))
            ).collect(Collectors.toList());
        }

        return list;
    }

    public WishlistDTO getById(String id) {
        if(!ObjectId.isValid(id)) {
            throw new BadRequestException(idInvalidoException);
        }

        Optional<Wishlist> wishlistOp = wishlistRepository.findByIdOptional(new ObjectId(id));
        if(wishlistOp.isEmpty()) {
            throw new NotFoundException();
        }

        return wishlistMapper.toWishlistDTO(wishlistOp.get());
    }

    public void create(CreateWishlistDTO createWishlistDTO) throws ValidateException {
        validationHelper.validarDto(createWishlistDTO);

        Wishlist wishlist = wishlistMapper.toWishlist(createWishlistDTO);

        wishlist.movies = createWishlistDTO.imdbIds.stream().map(s -> {
            MovieDTO movieDTO = omdbService.getMovieById(s);
            if(movieDTO.imdbID == null) {
                throw new BadRequestException("Could not find any movie with the given id: "+s);
            }
            return movieMapper.toMovieFormattedDTO(movieDTO);
        }).collect(Collectors.toList());
        wishlist.idUser = usuarioService.getIdUser();

        wishlistRepository.persist(wishlist);
    }
}
