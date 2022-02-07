package com.github.thelifesnak3.accenture;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title="API Accenture Movies",
                version = "0.0.1",
                description = "Services collections from Accenture Movies API."
        )
)
public class MoviesApplication {
}
