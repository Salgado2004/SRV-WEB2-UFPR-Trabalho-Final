package br.ufpr.webII.trabalhoFinal.infra.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ExceptionResponseHandler {

    @ExceptionHandler(RegisteringException.class)
    public ResponseEntity<HttpRequestError> handleRegisterExceptions(RegisteringException e){
        if(e.getCause() != null){
            return ResponseEntity.internalServerError().body(new HttpRequestError(e.getMessage()));
        }
        return ResponseEntity.badRequest().body(new HttpRequestError(e.getMessage()));
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<HttpRequestError> handleLoginExceptions(LoginException e){
        if (e.getReason() == 1) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new HttpRequestError("Credenciais Inválidas."));
        }
        return ResponseEntity.internalServerError().body(new HttpRequestError("Erro ao processar login."));
    }

    @ExceptionHandler(RequestException.class)
    public ResponseEntity<String> handleRequestExceptions(RequestException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<HttpRequestError>> handleValidationExceptions(MethodArgumentNotValidException e){
        List<FieldError> errors = e.getFieldErrors();
        return ResponseEntity.badRequest().body(errors.stream().map(HttpRequestError::new).toList());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<HttpRequestError> handleIllegalArgument(IllegalArgumentException e){
        return ResponseEntity.badRequest().body(new HttpRequestError(e.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<HttpRequestError> handleResourceNotFound(ResourceNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HttpRequestError(e.getMessage()));
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<HttpRequestError> handleTokenExceptions(TokenException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new HttpRequestError(e.getMessage()));
    }

    public record HttpRequestError(String cause) {
        public HttpRequestError(FieldError error) {
            this(error.getField() + " "+ error.getDefaultMessage());
        }
    }

}
