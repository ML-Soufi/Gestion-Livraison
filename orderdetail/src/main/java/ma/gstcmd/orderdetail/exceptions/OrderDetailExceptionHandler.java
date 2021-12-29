package ma.gstcmd.orderdetail.exceptions;

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
public class OrderDetailExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public OrderDetailExceptionTemplate handler(NullPointerException ex){
        return new OrderDetailExceptionTemplate(HttpStatus.BAD_REQUEST.value(),"Null Pointeur.", new Date());
    }

    @ExceptionHandler(OrderDetailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public OrderDetailExceptionTemplate handler(OrderDetailException ex){
        return new OrderDetailExceptionTemplate(HttpStatus.BAD_REQUEST.value(),ex.getMessage(), new Date());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public OrderDetailExceptionTemplate handler(Exception ex){
        return new OrderDetailExceptionTemplate(HttpStatus.BAD_REQUEST.value(),ex.getMessage(), new Date());
    }

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
        OrderDetailExceptionTemplate exceptionTemplate = new OrderDetailExceptionTemplate(HttpStatus.BAD_REQUEST.value(), errorList, new Date());
        return new ResponseEntity<>(exceptionTemplate, HttpStatus.BAD_REQUEST);
    }
}
