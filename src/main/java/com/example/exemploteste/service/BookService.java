package com.example.exemploteste.service;

import org.springframework.stereotype.Service;
import com.example.exemploteste.model.Book;
import com.example.exemploteste.model.RentalHistory;
import com.example.exemploteste.repository.BookRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Optional<Book> updateBook(Long id, Book updatedBook) {
        return bookRepository.findById(id).map(book -> {
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            book.setPublisher(updatedBook.getPublisher());
            book.setPublicationYear(updatedBook.getPublicationYear());
            book.setGenre(updatedBook.getGenre());
            book.setAvailableCopies(updatedBook.getAvailableCopies());
            book.setDescription(updatedBook.getDescription());
            return bookRepository.save(book);
        });
    }

    public boolean deleteBook(Long id) {
        return bookRepository.findById(id).map(book -> {
            if (book.isRented() || book.getAvailableCopies() < 1) {
                return false;
            }
            bookRepository.delete(book);
            return true;
        }).orElse(false);
    }

    public boolean updateRentalStatus(Long id, String status, String clientName, String adminName) {
        return bookRepository.findById(id).map(book -> {
            if ("Alugado".equalsIgnoreCase(status) && book.getAvailableCopies() > 0) {
                book.setAvailableCopies(book.getAvailableCopies() - 1);
                book.setRented(true);
            } else if ("Disponível".equalsIgnoreCase(status)) {
                book.setAvailableCopies(book.getAvailableCopies() + 1);
                book.setRented(false);
            } else {
                return false;
            }
            RentalHistory history = new RentalHistory();
            history.setBook(book);
            history.setStatus(status);
            history.setClientName(clientName);
            history.setAdminName(adminName);
            history.setChangeDate(LocalDateTime.now());
            book.getRentalHistory().add(history);
            bookRepository.save(book);
            return true;
        }).orElse(false);
    }

    public List<RentalHistory> getRentalHistory(Long id) {
        return bookRepository.findById(id)
            .map(Book::getRentalHistory)
            .orElse(List.of());
    }
}
