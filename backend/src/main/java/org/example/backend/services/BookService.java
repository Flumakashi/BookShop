package org.example.backend.services;

import jakarta.transaction.Transactional;
import org.example.backend.dtos.BookRequest;
import org.example.backend.dtos.BookDTO;
import org.example.backend.model.Book;
import org.example.backend.model.Genre;
import org.example.backend.repository.BookRepository;
import org.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Autowired
    public BookService (BookRepository bookRepository, UserRepository userRepository){
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<BookDTO> getBookById (Long id){
        return bookRepository.findById(id).map(this::convertToDTO);
    }

    @Transactional
    public Optional<BookDTO> createBook(BookRequest bookRequest, String username){
        return userRepository.findByUsername(username).map(user -> {
            Book book = bookRequest.toBook(user);  // Используем метод toBook из BookRequest
            return convertToDTO(bookRepository.save(book));
        });
    }

    @Transactional
    public Optional<BookDTO> updateBook(Long id, BookRequest bookRequest, String username, boolean isAdmin){
        return bookRepository.findById(id).map(book -> {
            if (!isAdmin && !book.getUser().getUsername().equals(username)){
                throw new AccessDeniedException("You can only edit your own books!");
            }
            book = bookRequest.toBook(book.getUser());
            book.setId(id);
            bookRepository.save(book);
            return convertToDTO(book);
        });
    }

    @Transactional
    public boolean deleteBook(Long id, String username, boolean isAdmin){
        return bookRepository.findById(id).map(book -> {
            if (!isAdmin && !book.getUser().getUsername().equals(username)) {
                throw new AccessDeniedException("You can only delete your own books!");
            }
            bookRepository.delete(book);
            return true;
        }).isPresent();
    }

    public List<BookDTO> searchBooks(String title, String author, Genre genre){
        List<Book> books;
        if (title != null){
            books = bookRepository.findByTitleContainingIgnoreCase(title);
        } else if (author != null) {
            books = bookRepository.findByAuthorContainingIgnoreCase(author);
        } else if (genre != null) {
            books = bookRepository.findByGenre(genre);
        } else {
            books = bookRepository.findAll();
        }
        return books.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private BookDTO convertToDTO(Book book) {
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getGenre(),
                book.getPrice(),
                book.getDescription(),
                book.getPhotoPath(),
                book.getUser().getUsername()
        );
    }
}
