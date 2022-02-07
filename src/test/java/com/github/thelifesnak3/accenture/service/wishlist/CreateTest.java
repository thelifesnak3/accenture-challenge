package com.github.thelifesnak3.accenture.service.wishlist;

import com.github.thelifesnak3.accenture.dto.CreateWishlistDTO;
import com.github.thelifesnak3.accenture.dto.MovieDTO;
import com.github.thelifesnak3.accenture.entity.Wishlist;
import com.github.thelifesnak3.accenture.exception.validate.ValidateException;
import com.github.thelifesnak3.accenture.helper.ValidationHelper;
import com.github.thelifesnak3.accenture.repository.WishlistRepository;
import com.github.thelifesnak3.accenture.service.UsuarioService;
import com.github.thelifesnak3.accenture.service.WishlistService;
import com.github.thelifesnak3.accenture.service.omdb.OmdbService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.test.security.TestSecurity;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import java.util.ArrayList;

@QuarkusTest
public class CreateTest {

    @InjectMock WishlistRepository mockWishlistRepository;
    @InjectMock @RestClient OmdbService mockOmdbService;
    @InjectMock UsuarioService mockUsuarioService;
    @InjectMock
    ValidationHelper validationHelper;
    @Inject WishlistService wishlistService;

    final String movieNotFound = "Could not find any movie with the given id: 123";

    @BeforeEach
    public void setup() throws ValidateException {
        Mockito.when(mockUsuarioService.getIdUser()).thenReturn("");
        Mockito.doNothing().when(validationHelper).validarDto(Mockito.any());
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    @DisplayName("Testing create service register the document without throw")
    public void testCreateSuccess() {
        Mockito.when(mockOmdbService.getMovieById(Mockito.anyString())).thenReturn(createValidMovieDTO());
        Mockito.doNothing().when(mockWishlistRepository).persist(Mockito.any(Wishlist.class));

        Assertions.assertDoesNotThrow(() -> wishlistService.create(createBody()));

    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    @DisplayName("Testing create service throw bad request")
    public void testCreateThrowBadRequest() {
        Mockito.doNothing().when(mockWishlistRepository).persist(Mockito.any(Wishlist.class));
        Mockito.when(mockOmdbService.getMovieById(Mockito.anyString())).thenReturn(createInvalidMovieDTO());
        BadRequestException badRequestException = Assertions.assertThrows(
            BadRequestException.class, () -> wishlistService.create(createBody())
        );

        Assertions.assertEquals(movieNotFound, badRequestException.getMessage());
    }

    public CreateWishlistDTO createBody() {
        CreateWishlistDTO createWishlistDTO = new CreateWishlistDTO();
        createWishlistDTO.imdbIds = new ArrayList<>();
        createWishlistDTO.imdbIds.add("123");
        return createWishlistDTO;
    }

    public MovieDTO createValidMovieDTO() {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.imdbID = "123";
        return movieDTO;
    }

    public MovieDTO createInvalidMovieDTO() {
        return new MovieDTO();
    }
}
