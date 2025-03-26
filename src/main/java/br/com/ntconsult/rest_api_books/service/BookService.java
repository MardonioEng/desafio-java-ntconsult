package br.com.ntconsult.rest_api_books.service;

import br.com.ntconsult.rest_api_books.dto.BookDTO;
import br.com.ntconsult.rest_api_books.model.Book;
import br.com.ntconsult.rest_api_books.repository.BookRepository;
import br.com.ntconsult.rest_api_books.repository.specs.BookSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Page<BookDTO> findAll(String title, String author, Integer publishYear, Integer pageNumber, Integer pageSize) {
        List<Book> books = bookRepository.findAll();

        Specification<Book> specs = Specification
                .where((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());

        if(title != null) {
            specs = specs.and(BookSpecification.titleLike(title));
        }

        if(author != null) {
            specs = specs.and(BookSpecification.authorLike(author));
        }

        if(publishYear != null) {
            specs = specs.and(BookSpecification.publishYearEqual(publishYear));
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Book> page = bookRepository.findAll(specs, pageable);

        return page.map(BookDTO::new);
    }

}
