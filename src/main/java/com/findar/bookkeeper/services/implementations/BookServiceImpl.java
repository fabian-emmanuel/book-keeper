package com.findar.bookkeeper.services.implementations;

import com.findar.bookkeeper.constants.Message;
import com.findar.bookkeeper.dtos.BookRequestDTO;
import com.findar.bookkeeper.dtos.BookResponseDTO;
import com.findar.bookkeeper.dtos.BookUpdateRequestDTO;
import com.findar.bookkeeper.dtos.FilterParam;
import com.findar.bookkeeper.exceptions.ResourceNotFoundException;
import com.findar.bookkeeper.models.Book;
import com.findar.bookkeeper.repositories.BookRepository;
import com.findar.bookkeeper.services.BookService;
import com.findar.bookkeeper.mappers.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public Mono<BookResponseDTO> registerBook(BookRequestDTO bookRequestDTO) {
        return bookRepository.save(BookMapper.toBook(bookRequestDTO))
                .map(BookMapper::toBookResponseDTO);
    }

    @Override
    public Mono<BookResponseDTO> retrieveBookById(Long bookId) {
        return findBookById(bookId)
                .map(BookMapper::toBookResponseDTO);
    }

    @Override
    public Mono<BookResponseDTO> updateBook(BookUpdateRequestDTO bookUpdateRequestDTO) {
        return findBookById(bookUpdateRequestDTO.bookId())
                .flatMap(book -> {
                    BookMapper.updateBook(book, bookUpdateRequestDTO);
                    return bookRepository.save(book);
                })
                .map(BookMapper::toBookResponseDTO);
    }

    @Override
    public Mono<Map<String, Object>> retrieveAllBooks(FilterParam filterParam) {
        return bookRepository.retrieveAllBooks(filterParam);
    }

    @Override
    public Mono<Void> deleteBook(Long bookId) {
        return findBookById(bookId)
                .flatMap(bookRepository::delete);
    }

    private Mono<Book> findBookById(Long bookId) {
        return bookRepository.findBookById(bookId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException(String.format(Message.BOOK_WITH_ID_NOT_FOUND, bookId))));
    }
}
