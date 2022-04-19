package com.library.demo.service;

import com.library.demo.entity.IssuedBook;
import com.library.demo.entity.Users;
import com.library.demo.repository.IssuedBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class IssuedBookImpl implements IssuedBookService{

    @Autowired
    private IssuedBookRepository issuedBookRepository;

    @Override
    public List<IssuedBook> getIssuedBooks() {
        return issuedBookRepository.findAll();
    }

    @Override
    public IssuedBook getIssuedBook(int id) {
        Optional<IssuedBook> issuedBook = Optional.of(issuedBookRepository.getById(id));
        return issuedBook.get();
    }

    public List<IssuedBook> getBooksByUser(Users user) {
        return issuedBookRepository.getBookByUser(user);
    }

    @Override
    public IssuedBook saveIssuedBook(IssuedBook issuedBook) {
        issuedBookRepository.save(issuedBook);
        return issuedBook;
    }

    @Override
    public void deleteIssuedBook(int id) {
        issuedBookRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteBooksOfUser(Users user) {
        issuedBookRepository.deleteBooksOfUser(user);
    }


}
