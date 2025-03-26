package br.com.ntconsult.rest_api_books.controller;

import br.com.ntconsult.rest_api_books.dto.BookDTO;
import br.com.ntconsult.rest_api_books.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(final BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<Page<BookDTO>> getAllBooks(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "publish-year", required = false) Integer publishYear,
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "page-size", defaultValue = "10") Integer pageSize
    ) {
        Page<BookDTO> page = bookService.findAll(title, author, publishYear, pageNumber, pageSize);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable(value = "id", required = true) Long id) {
        BookDTO bookDTO = bookService.getById(id);
        if (bookDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(bookDTO);
    }

    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
        BookDTO dto = bookService.save(bookDTO);
        URI uriLocation = this.generateHeaderLocation(dto.getId());
        return ResponseEntity.created(uriLocation).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable(value = "id", required = true) Long id, @RequestBody BookDTO bookDTO) {
        try {
            BookDTO dto = bookService.update(id, bookDTO);
            return ResponseEntity.ok().body(dto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

    private URI generateHeaderLocation(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

}
