package com.example.demo.controllers;

import com.example.demo.entity.Author;
import com.example.demo.repository.AuthorRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private static final Logger LOGGER = LogManager.getLogger(AuthorController.class);

    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping("/all")
    public List<Author> getAll() {
        return (List<Author>) authorRepository.findAll();
    }

    @GetMapping("/{id}")
    public Author getById(@PathVariable Long id) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        return optionalAuthor.orElse(null);
    }

    @PostMapping("/add")
    public void addNewAuthor(@RequestBody Author author) {
        if (author != null) {
            authorRepository.save(author);
        }
    }

    @PutMapping("/{id}")
    public Author replaceAuthor(@RequestBody Author newAuthor, @PathVariable Long id) {

        return authorRepository.findById(id)
                .map(author -> {
                    author.setName(newAuthor.getName());
                    return authorRepository.save(author);
                })
                .orElseGet(() -> {
                    newAuthor.setId(id);
                    return authorRepository.save(newAuthor);
                });
    }

    @DeleteMapping("/{id}")
    void deleteAuthor(@PathVariable Long id) {
        authorRepository.deleteById(id);
    }

}
