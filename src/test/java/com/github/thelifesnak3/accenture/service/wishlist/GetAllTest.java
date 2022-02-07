package com.github.thelifesnak3.accenture.service.wishlist;

import com.github.thelifesnak3.accenture.dto.MovieFormattedDTO;
import com.github.thelifesnak3.accenture.entity.Wishlist;
import com.github.thelifesnak3.accenture.repository.WishlistRepository;
import com.github.thelifesnak3.accenture.service.UsuarioService;
import com.github.thelifesnak3.accenture.service.WishlistService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@QuarkusTest
public class GetAllTest {

    @Inject WishlistService wishlistService;
    @InjectMock WishlistRepository mockWishlistRepository;
    @InjectMock UsuarioService mockUsuarioService;

    @BeforeEach
    public void setup() {
        Mockito.when(mockUsuarioService.getIdUser()).thenReturn("");
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    @DisplayName("Testing getAll service return an list without throw")
    public void testGetAllSuccess() {
        Mockito.when(mockWishlistRepository.getAllByUser(Mockito.anyString())).thenReturn(new ArrayList<>());
        Assertions.assertDoesNotThrow(() -> wishlistService.getAll(null, null, null));

    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    @DisplayName("Testing getAll service return an list without throw filtering by gender")
    public void testGetAllFilteredByGender() {
        Mockito.when(mockWishlistRepository.getAllByUser(Mockito.anyString())).thenReturn(createWishlistGenderTest());
        Assertions.assertDoesNotThrow(() -> {
            List<Wishlist> list = wishlistService.getAll("Music", null, null);
            Assertions.assertEquals(1, list.size());
        });
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    @DisplayName("Testing getAll service return an list without throw filtering by actor")
    public void testGetAllFilteredByActor() {
        Mockito.when(mockWishlistRepository.getAllByUser(Mockito.anyString())).thenReturn(createWishlistActorTest());
        Assertions.assertDoesNotThrow(() -> {
            List<Wishlist> list = wishlistService.getAll(null, "John", null);
            Assertions.assertEquals(1, list.size());
        });
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    @DisplayName("Testing getAll service return an list without throw filtering by gender")
    public void testGetDirector() {
        Mockito.when(mockWishlistRepository.getAllByUser(Mockito.anyString())).thenReturn(createWishlistDirectorTest());
        Assertions.assertDoesNotThrow(() -> {
            List<Wishlist> list = wishlistService.getAll(null, null, "Becca");
            Assertions.assertEquals(1, list.size());
        });
    }

    private List<Wishlist> createWishlistGenderTest() {
        List<Wishlist> list = new ArrayList<>();
        Wishlist wishlist = new Wishlist();
        wishlist.movies = new ArrayList<>();
        MovieFormattedDTO movieFormattedDTO1 = new MovieFormattedDTO();
        movieFormattedDTO1.genre = "Music";
        MovieFormattedDTO movieFormattedDTO2 = new MovieFormattedDTO();
        movieFormattedDTO2.genre = "Documentary";
        wishlist.movies.add(movieFormattedDTO1);
        wishlist.movies.add(movieFormattedDTO2);
        list.add(wishlist);
        return list;
    }

    private List<Wishlist> createWishlistActorTest() {
        List<Wishlist> list = new ArrayList<>();
        Wishlist wishlist = new Wishlist();
        wishlist.movies = new ArrayList<>();
        MovieFormattedDTO movieFormattedDTO1 = new MovieFormattedDTO();
        movieFormattedDTO1.actor = "John";
        MovieFormattedDTO movieFormattedDTO2 = new MovieFormattedDTO();
        movieFormattedDTO2.actor = "Jonas";
        wishlist.movies.add(movieFormattedDTO1);
        wishlist.movies.add(movieFormattedDTO2);
        list.add(wishlist);
        return list;
    }

    private List<Wishlist> createWishlistDirectorTest() {
        List<Wishlist> list = new ArrayList<>();
        Wishlist wishlist = new Wishlist();
        wishlist.movies = new ArrayList<>();
        MovieFormattedDTO movieFormattedDTO1 = new MovieFormattedDTO();
        movieFormattedDTO1.director = "Jenifer";
        MovieFormattedDTO movieFormattedDTO2 = new MovieFormattedDTO();
        movieFormattedDTO2.director = "Becca";
        wishlist.movies.add(movieFormattedDTO1);
        wishlist.movies.add(movieFormattedDTO2);
        list.add(wishlist);
        return list;
    }
}
