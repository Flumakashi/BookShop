package org.example.backend.services;

import org.example.backend.dtos.BookRequest;
import org.example.backend.dtos.BookDTO;
import org.example.backend.model.Book;
import org.example.backend.model.User;
import org.example.backend.repository.BookRepository;
import org.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;
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

    public Optional<BookDTO> createBook(BookRequest bookRequest, String username){
        return userRepository.findByUsername(username).map(user -> {
            Book book = new Book();
            book.setTitle(bookRequest.getTitle());
            book.setAuthor(bookRequest.getAuthor());
            book.setIsbn(bookRequest.getIsbn());
            book.setGenre(bookRequest.getGenre());
            book.setPrice(bookRequest.getPrice());
            book.setDescription(bookRequest.getDescription());
            book.setPhotoPath(bookRequest.getPhotoPath());
            book.setUser(user);

            return convertToDTO(bookRepository.save(book));
        });
    }

    public Optional<BookDTO> updateBook (Long id, BookRequest bookRequest, String username, boolean isAdmin){
        return bookRepository.findById(id).map(book -> {
            if (!isAdmin && !book.getUser().getUsername().equals(username)){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only edit your own books!");
            }
            book.setTitle(bookRequest.getTitle());
            book.setAuthor(bookRequest.getAuthor());
            book.setIsbn(bookRequest.getIsbn());
            book.setGenre(bookRequest.getGenre());
            book.setPrice(bookRequest.getPrice());
            book.setDescription(bookRequest.getDescription());
            book.setPhotoPath(bookRequest.getPhotoPath());

            return convertToDTO(bookRepository.save(book));
        });
    }

    public boolean deleteBook(Long id, String username, boolean isAdmin){
        return bookRepository.findById(id).map(book -> {
            if(!isAdmin && !book.getUser().getUsername().equals(username)){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only delete your own books!");
            }
            bookRepository.delete(book);
            return true;
        }).orElse(false);
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
