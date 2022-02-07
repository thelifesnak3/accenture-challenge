package com.github.thelifesnak3.accenture.mapper;

import com.github.thelifesnak3.accenture.dto.CreateWishlistDTO;
import com.github.thelifesnak3.accenture.dto.WishlistDTO;
import com.github.thelifesnak3.accenture.entity.Wishlist;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface WishlistMapper {
    Wishlist toWishlist(CreateWishlistDTO dto);
    WishlistDTO toWishlistDTO(Wishlist wishlist);
}
