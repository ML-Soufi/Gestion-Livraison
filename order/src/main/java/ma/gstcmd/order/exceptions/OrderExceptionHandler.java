package ma.gstcmd.order.exceptions;

import feign.FeignException;
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

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestControllerAdvice
public class OrderExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public OrderExceptionTemplate handler(NullPointerException ex){
        return new OrderExceptionTemplate(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Pointeur null.", new Date());
    }

    @ExceptionHandler(OrderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public OrderExceptionTemplate handler(OrderException ex){
        return new OrderExceptionTemplate(HttpStatus.BAD_REQUEST.value(),ex.getMessage(), new Date());
    }

    @ExceptionHandler(FeignException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public OrderExceptionTemplate handler(FeignException e, HttpServletResponse response){
        response.setStatus(e.status());
//        List<String> errorContent = Arrays.asList(e.contentUTF8().split(","));
//        List<String> errorMessageContent = Arrays.asList(errorContent.get(1).split(":"));
        return new OrderExceptionTemplate(HttpStatus.BAD_REQUEST.value(), e.getMessage(), new Date());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public OrderExceptionTemplate handler(Exception ex){
        return new OrderExceptionTemplate(HttpStatus.BAD_REQUEST.value(), "yes i am", new Date());
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
        OrderExceptionTemplate orderExceptionTemplate = new OrderExceptionTemplate(HttpStatus.BAD_REQUEST.value(),errorList, new Date());
        return new ResponseEntity<>(orderExceptionTemplate, HttpStatus.BAD_REQUEST);
    }
}
