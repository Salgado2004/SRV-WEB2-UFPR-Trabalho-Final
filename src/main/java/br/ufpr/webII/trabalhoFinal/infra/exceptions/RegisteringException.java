package br.ufpr.webII.trabalhoFinal.infra.exceptions;

public class RegisteringException extends RuntimeException{

    public RegisteringException(String message){
        super(message);
    }

    public RegisteringException(String message, Throwable cause){
        super(message, cause);
    }
}
