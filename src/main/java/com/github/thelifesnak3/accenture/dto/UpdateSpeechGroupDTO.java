package com.github.thelifesnak3.accenture.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class UpdateSpeechGroupDTO {
    @NotEmpty(message = "The title field is mandatory")
    public String title;
    public String description;
    public List<String> users;
    @NotNull(message = "The flPrivate field is mandatory")
    public Boolean flPrivate;

    @Override
    public String toString() {
        return "UpdateSpeechGroupDTO{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", flPrivate=" + flPrivate +
                '}';
    }
}
