package com.github.thelifesnak3.accenture.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UpdateEvaluationDTO {
    @NotEmpty(message = "The idMovie field is mandatory")
    public String idMovie;
    @NotNull(message = "The rate field is mandatory")
    @Max(value = 10)
    @Min(value = 5)
    public Integer rate;
    @NotNull(message = "The star field is mandatory")
    @Max(value = 5)
    @Min(value = 1)
    public Integer star;
    @NotEmpty(message = "The comment field is mandatory")
    public String comment;
    @NotNull(message = "The flPrivate field is mandatory")
    public Boolean flPrivate;

    @Override
    public String toString() {
        return "CreateEvaluationDTO{" +
                "idMovie='" + idMovie + '\'' +
                ", rate=" + rate +
                ", star=" + star +
                ", comment='" + comment + '\'' +
                ", flPrivate=" + flPrivate +
                '}';
    }
}
