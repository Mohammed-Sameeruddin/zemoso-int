package com.library.demo.serviceTests;

import com.library.demo.entity.Books;
import com.library.demo.entity.IssuedBook;
import com.library.demo.entity.Users;
import com.library.demo.repository.IssuedBookRepository;
import com.library.demo.service.IssuedBookService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
class IssuedBookServiceTests {

    @MockBean
    private IssuedBookRepository issuedBookRepository;

    @Autowired
    private IssuedBookService issuedBookService;

    @Test
    void getIssuedBooksTest(){
        Books book1 = new Books("DSA","horowitz","McGraw Hill",2);
        Users user1 = new Users("username","test123","9898989898","username@gmail.com",true);
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = date1.plusDays(10);
        Date curr = Date.valueOf(date1);
        Date later = Date.valueOf(date2);
        IssuedBook issuedBook1 = new IssuedBook(curr,later,book1,user1);

        Mockito.when(issuedBookRepository.findAll()).thenReturn(List.of(issuedBook1));

        assertEquals(1,issuedBookService.getIssuedBooks().size());
    }

    @Test
    void getIssuedBookTest(){
        Books book1 = new Books("DSA","horowitz","McGraw Hill",2);
        Users user1 = new Users("username","test123","9898989898","username@gmail.com",true);
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = date1.plusDays(10);
        Date curr = Date.valueOf(date1);
        Date later = Date.valueOf(date2);
        IssuedBook issuedBook1 = new IssuedBook(curr,later,book1,user1);

        Mockito.when(issuedBookRepository.getById(1)).thenReturn(issuedBook1);

        assertEquals(issuedBook1,issuedBookService.getIssuedBook(1));

    }

    @Test
    void getBooksByUser(){
        Books book1 = new Books("DSA","horowitz","McGraw Hill",2);
        Users user1 = new Users("username","test123","9898989898","username@gmail.com",true);
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = date1.plusDays(10);
        Date curr = Date.valueOf(date1);
        Date later = Date.valueOf(date2);
        IssuedBook issuedBook1 = new IssuedBook(curr,later,book1,user1);

        Mockito.when(issuedBookRepository.getBookByUser(user1)).thenReturn(List.of(issuedBook1));

        assertEquals(List.of(issuedBook1),issuedBookService.getBooksByUser(user1));
        assertEquals(1,issuedBookService.getBooksByUser(user1).size());
    }

    @Test
    void saveIssuedBook(){
        Books book1 = new Books("DSA","horowitz","McGraw Hill",2);
        Users user1 = new Users("username","test123","9898989898","username@gmail.com",true);
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = date1.plusDays(10);
        Date curr = Date.valueOf(date1);
        Date later = Date.valueOf(date2);
        IssuedBook issuedBook1 = new IssuedBook(curr,later,book1,user1);

        Mockito.when(issuedBookRepository.save(issuedBook1)).thenReturn(issuedBook1);

        assertEquals(issuedBook1,issuedBookService.saveIssuedBook(issuedBook1));
    }

    @Test
     void deleteIssuedBookTest(){
        Books book1 = new Books("DSA","horowitz","McGraw Hill",2);
        Users user1 = new Users("username","test123","9898989898","username@gmail.com",true);
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = date1.plusDays(10);
        Date curr = Date.valueOf(date1);
        Date later = Date.valueOf(date2);
        IssuedBook issuedBook1 = new IssuedBook(curr,later,book1,user1);

        issuedBookRepository.delete(issuedBook1);
        issuedBookService.deleteIssuedBook(book1.getBookId());
        Mockito.verify(issuedBookRepository,Mockito.times(1)).deleteById(book1.getBookId());
    }

    @Test
    void deleteBookOfUserTest(){
        Books book1 = new Books("DSA","horowitz","McGraw Hill",2);
        Users user1 = new Users("username","test123","9898989898","username@gmail.com",true);
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = date1.plusDays(10);
        Date curr = Date.valueOf(date1);
        Date later = Date.valueOf(date2);
        IssuedBook issuedBook1 = new IssuedBook(curr,later,book1,user1);
        issuedBookRepository.delete(issuedBook1);
        issuedBookService.deleteBooksOfUser(user1);
        Mockito.verify(issuedBookRepository,Mockito.times(1)).deleteBooksOfUser(user1);
    }
}
