package com.library.demo.service;

import com.library.demo.entity.Books;

import java.util.List;

public interface BookService {

    public List<Books> getBooks();

    public Books getBookById(int id);

    public Books saveBook(Books theBook);

    public void deleteBook(int id);
}
