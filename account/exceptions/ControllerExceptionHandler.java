package account.exceptions;

import org.apache.coyote.Response;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String error = "Bad Request";
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", error);
        body.put("path", request.getDescription(false));

        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<CustomErrorMessage> handleUserExists(UserExistException e, WebRequest request) {
        String error = "Bad Request";
        String message = "User exist!";
        String path = "/api/auth/signup";
        CustomErrorMessage body = new CustomErrorMessage(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                error,
                message,
                path
        );

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedUser.class)
    public ResponseEntity<CustomErrorMessage> handleUnauthorized() {
        String error = "Unauthorized";
        String message = "";
        String path = "/api/empl/payment";
        CustomErrorMessage body = new CustomErrorMessage(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                error,
                message
                ,path
        );

        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);

    }
}
