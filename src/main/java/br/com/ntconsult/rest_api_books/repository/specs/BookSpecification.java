package br.com.ntconsult.rest_api_books.repository.specs;

import br.com.ntconsult.rest_api_books.model.Book;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {

    public static Specification<Book> titleLike(String title) {
        return (root, query, cb) ->
                cb.like(cb.upper(root.get("title")), "%" + title.toUpperCase() + "%");
    }

    public static Specification<Book> authorLike(String author) {
        return (root, query, cb) ->
                cb.like(cb.upper(root.get("author")), "%" + author.toUpperCase() + "%");
    }

    public static Specification<Book> publishYearEqual(Integer publishYear) {
        return (root, query, cb) ->
                cb.equal(root.get("publishYear"), publishYear);
    }
}
