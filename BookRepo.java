package com.example.repository;

import entity.Book;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    // Custom query methods can be added here if needed
	 @Query("SELECT b FROM Book b")
	    List<Book> getAllBooks();
	 List<Book> findByIsTreasureTrue();
	


}
