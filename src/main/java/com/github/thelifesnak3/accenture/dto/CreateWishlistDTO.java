package com.github.thelifesnak3.accenture.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class CreateWishlistDTO {
    @NotEmpty(message = "The name field is mandatory")
    public String name;
    public String description;
    @NotEmpty(message = "The imdbIds field is mandatory")
    public List<@NotBlank(message = "Unable to enter empty imdbId") String> imdbIds;
    @NotNull(message = "The flPrivate field is mandatory")
    public Boolean flPrivate;

    @Override
    public String toString() {
        return "CreateWishlistDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imdbIds=" + imdbIds +
                ", flPrivate=" + flPrivate +
                '}';
    }
}
