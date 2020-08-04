package com.example.demo.controllers;

import com.example.demo.entity.Author;
import com.example.demo.entity.Book;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {


    private static final Logger LOGGER = LogManager.getLogger(AuthorController.class);

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/all")
    public List<Book> getAll() {
        return (List<Book>) bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public Book getById(@PathVariable Long id) {
        Optional<Book> optionalAuthor = bookRepository.findById(id);
        return optionalAuthor.orElse(null);
    }

    @PostMapping("/add")
    public Book addNewBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @PutMapping("/{id}")
    public Book replaceAuthor(@RequestBody Book newBook, @PathVariable Long id) {

        return bookRepository.findById(id)
                .map(author -> {
                    author.setName(newBook.getName());
                    return bookRepository.save(author);
                })
                .orElseGet(() -> {
                    newBook.setId(id);
                    return bookRepository.save(newBook);
                });
    }

    @DeleteMapping("/{id}")
    void deleteBook(@PathVariable Long id) {
        bookRepository.deleteById(id);
    }


}
