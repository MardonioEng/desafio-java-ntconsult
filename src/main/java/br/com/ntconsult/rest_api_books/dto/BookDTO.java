package br.com.ntconsult.rest_api_books.dto;

import br.com.ntconsult.rest_api_books.model.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class BookDTO {

    private Long id;

    @NotBlank(message = "Field required")
    @Size(min = 2, max = 150, message = "field outside the standard size")
    private String title;

    @NotBlank(message = "Field required")
    @Size(min = 2, max = 100, message = "field outside the standard size")
    private String author;

    @NotNull(message = "Field required")
    @PastOrPresent(message = "It cannot be a future date")
    private LocalDate publishDate;

    public BookDTO() {
    }

    public BookDTO(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.publishDate = book.getPublishDate();
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

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }
}
