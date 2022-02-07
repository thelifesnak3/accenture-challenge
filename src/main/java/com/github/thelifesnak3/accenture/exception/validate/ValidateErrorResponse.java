package com.github.thelifesnak3.accenture.exception.validate;

import java.util.List;

public class ValidateErrorResponse {
    public String message;
    public List<String> errors;

    public ValidateErrorResponse(String message, List<String> errors) {
        this.message = message;
        this.errors = errors;
    }
}
