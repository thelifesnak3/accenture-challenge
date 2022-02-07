package com.github.thelifesnak3.accenture.service;

import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class UsuarioService {

    @Inject
    JsonWebToken jwt;

    public String getIdUser() {
        return jwt.getClaim("preferred_username");
    }
}
