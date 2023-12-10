package com.ar.cac.IntegradorFinalGrupo5.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class InsufficientFoundsException extends ResponseStatusException {

    public InsufficientFoundsException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
