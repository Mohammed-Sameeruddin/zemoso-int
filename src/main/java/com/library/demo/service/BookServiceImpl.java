package com.library.demo.service;


import com.library.demo.entity.Books;
import com.library.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Books> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Books getBookById(int id) {
        Optional<Books> book = Optional.of(bookRepository.getById(id));
        return book.get();
    }

    @Override
    public Books saveBook(Books theBook) {
        bookRepository.save(theBook);
        return theBook;
    }

    @Override
    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }
}
