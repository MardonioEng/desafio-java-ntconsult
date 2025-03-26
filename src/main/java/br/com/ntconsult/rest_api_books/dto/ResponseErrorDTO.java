package br.com.ntconsult.rest_api_books.dto;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ResponseErrorDTO {

    private int status;
    private String message;
    private List<FieldErrorDetailDTO> fieldErrors;

    public ResponseErrorDTO(int status, String message, List<FieldErrorDetailDTO> fieldErrors) {
        this.status = status;
        this.message = message;
        this.fieldErrors = fieldErrors;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<FieldErrorDetailDTO> getFieldErrors() {
        return fieldErrors;
    }

    public static ResponseErrorDTO defaultError(String message) {
        return new ResponseErrorDTO(HttpStatus.BAD_REQUEST.value(), message, List.of());
    }

}
