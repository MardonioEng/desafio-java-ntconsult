package br.com.ntconsult.rest_api_books.dto;

import br.com.ntconsult.rest_api_books.model.Book;
import jakarta.validation.constraints.*;


public class BookDTO {

    private Long id;

    @NotBlank(message = "Field required")
    @Size(min = 2, max = 150, message = "field outside the standard size")
    private String title;

    @NotBlank(message = "Field required")
    @Size(min = 2, max = 100, message = "field outside the standard size")
    private String author;

    @NotNull(message = "The field cannot be null.")
    @Min(value = 1600, message = "The field cannot have a value lower than 1600.")
    @Max(value = 2025, message = "The field cannot have a value higher than 2025.")
    private Integer publishYear;

    public BookDTO() {
    }

    public BookDTO(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.publishYear = book.getPublishYear();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(Integer publishYear) {
        this.publishYear = publishYear;
    }
}
