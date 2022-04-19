package com.library.demo.service;

import com.library.demo.entity.IssuedBook;
import com.library.demo.entity.Users;

import java.util.List;

public interface IssuedBookService {

    public List<IssuedBook> getIssuedBooks();

    public IssuedBook getIssuedBook(int id);

    public List<IssuedBook> getBooksByUser(Users user);

    public IssuedBook saveIssuedBook(IssuedBook issuedBook);

    public void deleteIssuedBook(int id);

    public void deleteBooksOfUser(Users user);
}
