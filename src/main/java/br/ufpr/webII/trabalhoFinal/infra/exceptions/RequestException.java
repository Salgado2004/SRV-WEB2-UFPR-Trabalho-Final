package br.ufpr.webII.trabalhoFinal.infra.exceptions;

public class RequestException extends RuntimeException{
    public RequestException(String message) {
        super(message);
    }
}
