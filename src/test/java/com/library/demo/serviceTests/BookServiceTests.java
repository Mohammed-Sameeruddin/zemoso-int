package com.library.demo.serviceTests;

import com.library.demo.dto.BooksDTO;
import com.library.demo.entity.Books;
import com.library.demo.repository.BookRepository;
import com.library.demo.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
class BookServiceTests {
    @MockBean
    private BookRepository bookRepository;
    @Autowired
    private BookService bookService;

    @Test
    void getBooksTest(){
        Books book1 = new Books("DSA","horowitz","McGraw Hill",2);
        Books book2 = new Books("CN","author","Pearson",3);
        List<Books> booksList = new ArrayList<>(Arrays.asList(book1,book2));
        Mockito.when(bookRepository.findAll()).thenReturn(booksList);
        assertEquals(2,bookService.getBooks().size());
    }

    @Test
     void getBookByIdTest(){
        Books book1 = new Books("DSA","horowitz","McGraw Hill",2);
        book1.setBookId(10);
        Mockito.when(bookRepository.getById(10)).thenReturn(book1);
        assertEquals(book1,bookService.getBookById(10));
        assertEquals("DSA",bookService.getBookById(10).getBookName());
    }

    @Test
     void saveBookTest(){
        Books book1 = new Books("DSA","horowitz","McGraw Hill",2);
        BooksDTO booksDTO = bookService.getBookDTO(book1);
        Mockito.when(bookRepository.save(book1)).thenReturn(book1);
        assertEquals(bookService.getEntity(booksDTO).getBookName(),bookService.saveBook(booksDTO).getBookName());
    }

    @Test
     void deleteBookTest(){
        Books book1 = new Books("DSA","horowitz","McGraw Hill",2);
        bookRepository.delete(book1);
        bookService.deleteBook(book1.getBookId());
        Mockito.verify(bookRepository,Mockito.times(1)).deleteById(book1.getBookId());
    }
}
