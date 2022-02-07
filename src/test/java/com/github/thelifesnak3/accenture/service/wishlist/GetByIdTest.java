package com.github.thelifesnak3.accenture.service.wishlist;

import com.github.thelifesnak3.accenture.entity.Wishlist;
import com.github.thelifesnak3.accenture.repository.WishlistRepository;
import com.github.thelifesnak3.accenture.service.UsuarioService;
import com.github.thelifesnak3.accenture.service.WishlistService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.test.security.TestSecurity;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.Optional;

@QuarkusTest
public class GetByIdTest {

    @Inject WishlistService wishlistService;
    @InjectMock WishlistRepository mockWishlistRepository;
    @InjectMock UsuarioService mockUsuarioService;

    final String validId = "60aff0b4e443fc2b67c753e0";
    final String invalidId = "asd123";
    public final String idInvalidoException = "The given id is invalid.";

    @BeforeEach
    public void setup() {
        Mockito.when(mockUsuarioService.getIdUser()).thenReturn("");
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    @DisplayName("Testing getById service return an wishlist without throw")
    public void testGetByIdSuccess() {
        Mockito.when(mockWishlistRepository.findByIdOptional(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.of(new Wishlist()));

        Assertions.assertDoesNotThrow(() -> wishlistService.getById(validId));

    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    @DisplayName("Testing getById service return an BadRequestException for invalid id")
    public void testGetByIdThrowBadRequestIdInvalid() {
        BadRequestException badRequestException = Assertions.assertThrows(
            BadRequestException.class, () -> wishlistService.getById(invalidId)
        );

        Assertions.assertEquals(idInvalidoException, badRequestException.getMessage());
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    @DisplayName("Testing getById service return an BadRequestException for invalid id")
    public void testGetByIdThrowNotFound() {
        Mockito.when(mockWishlistRepository.findByIdOptional(Mockito.any(ObjectId.class)))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> wishlistService.getById(validId));
    }
}
