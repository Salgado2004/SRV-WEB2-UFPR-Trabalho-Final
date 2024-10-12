package br.ufpr.webII.trabalhoFinal.infra.exceptions;

public class LoginException extends RuntimeException{
    private final int reason;

    public LoginException(String message){
        super(message);
        this.reason = 0;
    }

    public LoginException(String message, int reason){
        super(message);
        this.reason = reason;
    }

    public int getReason() {
        return reason;
    }
}
