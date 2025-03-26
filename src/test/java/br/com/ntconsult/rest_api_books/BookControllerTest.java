package br.com.ntconsult.rest_api_books;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import br.com.ntconsult.rest_api_books.controller.BookController;
import br.com.ntconsult.rest_api_books.dto.BookDTO;
import br.com.ntconsult.rest_api_books.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    private ObjectMapper objectMapper;
    private BookDTO bookDTO;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        bookDTO = new BookDTO();
        bookDTO.setId(1L);
        bookDTO.setTitle("Clean Code");
        bookDTO.setAuthor("Robert C. Martin");
        bookDTO.setPublishYear(2008);
    }

    @Test
    void getAllBooks_ReturnsBookPage() throws Exception {
        List<BookDTO> books = Arrays.asList(bookDTO);
        Page<BookDTO> page = new PageImpl<>(books);

        when(bookService.findAll(anyString(), anyString(), any(Integer.class), anyInt(), anyInt()))
                .thenReturn(page);

        mockMvc.perform(get("/api/books")
                        .param("title", "Clean")
                        .param("author", "Martin")
                        .param("publish-year", "2008")
                        .param("page", "0")
                        .param("page-size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].title").value("Clean Code"))
                .andExpect(jsonPath("$.content[0].author").value("Robert C. Martin"))
                .andExpect(jsonPath("$.content[0].publishYear").value(2008));
    }

    @Test
    void getBookById_ExistingId_ReturnsBook() throws Exception {
        when(bookService.getById(1L)).thenReturn(bookDTO);

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Clean Code"))
                .andExpect(jsonPath("$.author").value("Robert C. Martin"))
                .andExpect(jsonPath("$.publishYear").value(2008));
    }

    @Test
    void getBookById_NonExistingId_ReturnsNotFound() throws Exception {
        when(bookService.getById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/books/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createBook_ValidBook_ReturnsCreated() throws Exception {
        when(bookService.save(any(BookDTO.class))).thenReturn(bookDTO);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Clean Code"))
                .andExpect(jsonPath("$.author").value("Robert C. Martin"))
                .andExpect(jsonPath("$.publishYear").value(2008));
    }

    @Test
    void updateBook_ExistingId_ReturnsUpdatedBook() throws Exception {
        BookDTO updatedBook = new BookDTO();
        updatedBook.setId(1L);
        updatedBook.setTitle("Clean Code: Updated");
        updatedBook.setAuthor("Robert C. Martin");
        updatedBook.setPublishYear(2008);

        when(bookService.update(eq(1L), any(BookDTO.class))).thenReturn(updatedBook);

        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Clean Code: Updated"))
                .andExpect(jsonPath("$.author").value("Robert C. Martin"))
                .andExpect(jsonPath("$.publishYear").value(2008));
    }

    @Test
    void updateBook_NonExistingId_ReturnsNotFound() throws Exception {
        when(bookService.update(eq(99L), any(BookDTO.class))).thenThrow(new RuntimeException("Book not found"));

        mockMvc.perform(put("/api/books/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteBook_ExistingId_ReturnsNoContent() throws Exception {
        doNothing().when(bookService).delete(1L);

        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteBook_NonExistingId_ReturnsNotFound() throws Exception {
        doThrow(new RuntimeException("Book not found")).when(bookService).delete(99L);

        mockMvc.perform(delete("/api/books/99"))
                .andExpect(status().isNotFound());
    }
}