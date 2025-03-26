package br.com.ntconsult.rest_api_books.dto;

public class FieldErrorDetailDTO {

    private String field;
    private String error;

    public FieldErrorDetailDTO(String field, String error) {
        this.field = field;
        this.error = error;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
