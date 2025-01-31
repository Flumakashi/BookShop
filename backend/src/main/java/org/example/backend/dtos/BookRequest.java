package org.example.backend.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backend.model.Book;
import org.example.backend.model.Genre;
import org.example.backend.model.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {

    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters")
    private String title;

    @NotBlank(message = "Author is required")
    @Size(min = 1, max = 100, message = "Author must be between 1 and 100 characters")
    private String author;

    @NotBlank(message = "ISBN is required")
    private String isbn;

    @NotNull(message = "Genre is required")
    private Genre genre;

    @Min(value = 0, message = "Price must be positive")
    private double price;

    @Size(max = 1000, message = "Description can't be longer than 1000 characters")
    private String description;

    @Size(max = 255, message = "Photo path can't be longer than 255 characters")
    private String photoPath;

    public Book toBook(User user) {
        Book book = new Book();
        book.setTitle(this.title);
        book.setAuthor(this.author);
        book.setIsbn(this.isbn);
        book.setGenre(this.genre);
        book.setPrice(this.price);
        book.setDescription(this.description);
        book.setPhotoPath(this.photoPath);
        book.setUser(user);
        return book;
    }
}
