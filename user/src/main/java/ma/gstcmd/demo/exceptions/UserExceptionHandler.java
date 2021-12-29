package ma.gstcmd.demo.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class UserExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public UserExceptionTemplate handler(NullPointerException ex){
        return new UserExceptionTemplate(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Pointeur Null.", new Date());
    }

    // to handle ProductException errors
    @ExceptionHandler(UserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public UserExceptionTemplate handler(UserException ex){
        return new UserExceptionTemplate(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), new Date());
    }

    // to handle any Exception errors
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public UserExceptionTemplate handler(Exception ex){
        return new UserExceptionTemplate(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), new Date());
    }

    // to handle bean validation errors
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errorList = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(
                objectError -> {
                    String fieldName =((FieldError) objectError).getField();
                    String errorMessage = objectError.getDefaultMessage();
                    errorList.put(fieldName, errorMessage);
                }
        );
        UserExceptionTemplate exceptionTemplate = new UserExceptionTemplate(HttpStatus.BAD_REQUEST.value(), errorList, new Date());
        return new ResponseEntity<>(exceptionTemplate, HttpStatus.BAD_REQUEST);
    }
}
