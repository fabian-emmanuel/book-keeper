package com.findar.bookkeeper.repositories;

import com.findar.bookkeeper.models.Book;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface BookRepository extends ReactiveCrudRepository<Book, Long>, BookRepositoryCustom {
    Mono<Book> findBookById(Long bookId);
}
