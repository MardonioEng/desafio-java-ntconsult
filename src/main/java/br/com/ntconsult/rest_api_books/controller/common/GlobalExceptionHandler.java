package br.com.ntconsult.rest_api_books.controller.common;

import br.com.ntconsult.rest_api_books.dto.FieldErrorDetailDTO;
import br.com.ntconsult.rest_api_books.dto.ResponseErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseErrorDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<FieldError> fieldError = e.getFieldErrors();
        List<FieldErrorDetailDTO> errorsList = fieldError
                .stream()
                .map(fe -> new FieldErrorDetailDTO(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ResponseErrorDTO(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation Failed", errorsList);
    }

}
