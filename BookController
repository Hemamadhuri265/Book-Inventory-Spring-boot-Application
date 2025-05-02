package controller;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import entity.Book;
import service.BookService;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    // Homepage
    @GetMapping("/")
    public String index() {
        return "index";  // Display the homepage (index.html)
    }

    // Register user page
    @GetMapping("/registerUser")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new Book());  // Bind 'user' object to the form
        return "register"; // Ensure the view is 'register.html'
    }

    // Register new book
    @PostMapping("/register")
    public String registerBook(
            @RequestParam("bookName") String bookName,
            @RequestParam("author") String author,
            @RequestParam("price") double price,
            @RequestParam("image") MultipartFile image,
            Model model) {

        try {
            // Convert the uploaded image to a byte array
            byte[] imageBytes = image.getBytes();

            // Create a new Book object and set its properties, including the image
            Book book = new Book(bookName, author, price, imageBytes);

            // Register the book using the service
            String message = bookService.registerBook(book, image);

            // Add the message to the model
            model.addAttribute("message", message);

        } catch (IOException e) {
            model.addAttribute("message", "Error uploading image: " + e.getMessage());
        }

        // Return the register page with the success or error message
        return "register";  // This returns the same register page with the message
    }

    // Show book library page
    @GetMapping("/library")
    public String showLibraryPage(Model model) {
        List<Book> books = bookService.getAllBooks();

        // Convert image byte[] to Base64 for each book
        for (Book book : books) {
            if (book.getImage() != null) {
                // Convert byte array to Base64 string
                String encodedImage = Base64.getEncoder().encodeToString(book.getImage());
                // Set the Base64 encoded image directly on the book object
                book.setEncodedImage(encodedImage);
            }
        }

        // Add books to the model for Thymeleaf template
        model.addAttribute("books", books);

        // Return the view name (booklibrary.html)
        return "booklibrary";  // This will render booklibrary.html
    }

    // Show treasured books
    @GetMapping("/treasure")
    public String showTreasurePage(Model model) {
        List<Book> treasures = bookService.getTreasuredBooks();

        // Convert image byte[] to Base64 for each book
        for (Book book : treasures) {
            if (book.getImage() != null) {
                String encodedImage = Base64.getEncoder().encodeToString(book.getImage());
                book.setEncodedImage(encodedImage);
            }
        }

        model.addAttribute("books", treasures);
        return "mytreasure";
    }

    // Add book to treasure
    @GetMapping("/addToTreasure/{id}")
    public String addToTreasure(@PathVariable Long id, Model model) {
        bookService.addToTreasure(id);

        // Load updated treasure list
        List<Book> treasures = bookService.getTreasuredBooks();
        for (Book book : treasures) {
            if (book.getImage() != null) {
                String encodedImage = Base64.getEncoder().encodeToString(book.getImage());
                book.setEncodedImage(encodedImage);
            }
        }

        model.addAttribute("books", treasures);
        return "mytreasure";
    }

    // Edit book (this is for updating a book)
    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id); // Get book by id
        model.addAttribute("book", book); // Pass book object to the form
        return "register"; // Return the register form to update the book
    }

    // Update book details
    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable Long id,
                             @RequestParam("bookName") String bookName,
                             @RequestParam("author") String author,
                             @RequestParam("price") double price,
                             @RequestParam("image") MultipartFile image,
                             Model model) {
        try {
            // Fetch the existing book from the database
            Book existingBook = bookService.getBookById(id);

            // Update book fields with new data
            existingBook.setName(bookName);
            existingBook.setAuthor(author);
            existingBook.setPrice(price);

            // If a new image was uploaded, update it
            if (!image.isEmpty()) {
                byte[] imageBytes = image.getBytes();
                existingBook.setImage(imageBytes);
            }

            // Save the updated book to the database
            String message = bookService.updateBook(existingBook);

            // Add success message to model
            model.addAttribute("message", message);

        } catch (IOException e) {
            model.addAttribute("message", "Error uploading image: " + e.getMessage());
        }

        // Redirect back to the book library after the update
        return "redirect:/booklibrary";
    }

    // Remove a book from the treasure
    @GetMapping("/removeFromTreasure/{id}")
    public String removeFromTreasure(@PathVariable Long id) {
        bookService.toggleTreasureStatus(id);  // This will toggle the treasure status (set it to false)
        return "redirect:/"; // Redirect to home or book list after removing
    }

  @GetMapping("/Add")
  public String  Addnewbook() {
	  return "register";
  }


   

    // Display book library
    @GetMapping("/booklibrary")
    public String viewBookLibrary(Model model) {
        List<Book> books = bookService.getAllBooks();  // Get all books from the library
        model.addAttribute("books", books);  // Add books to model
        return "booklibrary";  // Return the view for the book library
    }
}
