package service;

import entity.Book;

import com.example.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    // Method to register the book and save its image
    public String registerBook(Book book, MultipartFile image) {
        try {
            // Convert the uploaded image to a byte array
            byte[] imageBytes = image.getBytes();

            // Set the image as a byte array
            book.setImage(imageBytes);

            // Save the book to the database
            bookRepository.save(book);

            return "Book registered successfully!";
        } catch (IOException e) {
            return "Error uploading image: " + e.getMessage();
        }
    }
    
    // Save a new book (you can add methods like saveBook, etc.)
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
    
    public List<Book> getAllBooks() {
        List<Book> books = bookRepository.findAll(); // Fetch all books

        // Convert byte[] image data to Base64 encoded string for each book
        for (Book book : books) {
            if (book.getImage() != null) {
                // Convert image byte[] to Base64 string
                String encodedImage = Base64.getEncoder().encodeToString(book.getImage());
                book.setEncodedImage(encodedImage); // Set the Base64 string
            }
        }
        return books;
    }

    
    
    public List<Book> getTreasuredBooks() {
        return bookRepository.findByIsTreasureTrue();
    }
    
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
    }

    
    // Method to add book to treasure (set treasure = true)
    public void addToTreasure(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Invalid book ID"));
        
        if (!book.isTreasure()) {
            book.setTreasure(true); // Set treasure status to true
            bookRepository.save(book); // Save the updated book
        }
    }

    // Method to remove book from treasure (set treasure = false)
    public void removeFromTreasure(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Invalid book ID"));
        
        if (book.isTreasure()) {
            book.setTreasure(false); // Set treasure status to false
            bookRepository.save(book); // Save the updated book
        }
    }
     
    // Method to add a book to the 'treasure' list
    public boolean addBookToTreasure(Long bookId) {
        try {
            // Find the book by its ID
            Book book = bookRepository.findById(bookId).orElse(null);

            if (book != null) {
                // Set the 'treasure' status to true (indicating it's added to the treasure)
                book.setTreasure(true);
                bookRepository.save(book); // Save the updated book
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace(); // You can also log the error
        }
        return false;
    }

    // Method to toggle the treasure status (add or remove)
    public void toggleTreasureStatus(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Invalid book ID"));

        // Toggle the treasure status
        book.setTreasure(!book.isTreasure());
        bookRepository.save(book);  // Save the updated book
    }

    public String updateBook(Book book) {
        bookRepository.save(book); // `save()` does both insert and update
        return "Book updated successfully!";
    }
    
    
    
    public void addMoreTreasure(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();

            // Toggle the treasure status
            book.setTreasure(!book.isTreasure());

            bookRepository.save(book); // Save the updated book
        }
    }
    
    
    }

   

