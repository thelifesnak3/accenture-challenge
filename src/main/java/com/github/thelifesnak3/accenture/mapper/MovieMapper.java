package com.github.thelifesnak3.accenture.mapper;

import com.github.thelifesnak3.accenture.dto.MovieDTO;
import com.github.thelifesnak3.accenture.dto.MovieFormattedDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface MovieMapper {
    @Mapping(source = "Title", target = "title")
    @Mapping(source = "Year", target = "year")
    @Mapping(source = "Genre", target = "genre")
    @Mapping(source = "Director", target = "director")
    @Mapping(source = "Actors", target = "actor")
    MovieFormattedDTO toMovieFormattedDTO(MovieDTO dto);
}
