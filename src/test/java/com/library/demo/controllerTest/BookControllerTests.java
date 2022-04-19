package com.library.demo.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.library.demo.controller.BookController;
import com.library.demo.entity.Books;
import com.library.demo.entity.IssuedBook;
import com.library.demo.entity.Users;
import com.library.demo.service.BookService;
import com.library.demo.service.IssuedBookService;
import com.library.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(SpringRunner.class)
@SpringBootTest
class BookControllerTests {
    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();
    @Mock
    private BookService bookService;
    @Mock
    private UserService userService;
    @Mock
    private IssuedBookService issuedBookService;
    @InjectMocks
    private BookController bookController;
    Books book1 = new Books("DSA","horowitz","McGraw Hill",2);
    Books book2 = new Books("CN","author","Pearson",3);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    void showBooksTest() throws Exception {
        List<Books> booksList = new ArrayList<>(Arrays.asList(book1,book2));

        Mockito.when(bookService.getBooks()).thenReturn(booksList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/books").contentType(MediaType.ALL))
                .andExpect(status().isOk());
    }

    @Test
    void saveBookTest() throws Exception {
        book1.setBookId(1);
        Mockito.when(bookService.saveBook(bookService.getBookDTO(book1))).thenReturn(book1);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/save")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("bookName", "CN")
                        .param("author", "author")
                        .param("publisher", "Pearson")
                        .param("quantity", String.valueOf(3)))
                .andExpect(status().isMovedTemporarily())
                .andExpect(view().name("redirect:/api/books"))
                .andExpect(redirectedUrl("/api/books"))
                .andExpect(model().attribute("book", notNullValue()));

        ArgumentCaptor<Books> formObjectArgument = ArgumentCaptor.forClass(Books.class);
        Mockito.verify(bookService, Mockito.times(1)).saveBook(bookService.getBookDTO(formObjectArgument.capture()));

        Books formObject = formObjectArgument.getValue();

        assertEquals("DSA", formObject.getBookName());

    }

    @Test
    void saveQuantityTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/save")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("quantity", String.valueOf(0)))
                .andExpect(view().name("redirect:/api/books"));
    }

    @Test
    void addBookTest() throws Exception{
        book1.setBookId(1);
        Mockito.when(bookService.saveBook(bookService.getBookDTO(book1))).thenReturn(book1);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/addBook")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("bookName", "CN")
                        .param("author", "author")
                        .param("publisher", "Pearson")
                        .param("quantity", String.valueOf(3)))
                .andExpect(status().isOk())
                .andExpect(view().name("add-book"))
                .andExpect(model().attribute("book",notNullValue()));
    }

    @Test
    void showBorrowedBooksTest() throws Exception{
        Users user1 = new Users("username","test123","9898989898","username@gmail.com",true);
        IssuedBook issuedBook = new IssuedBook(Date.valueOf(LocalDate.now()),Date.valueOf(LocalDate.now().plusDays(10)),book1,user1);
        Mockito.when(issuedBookService.getIssuedBooks()).thenReturn(List.of(issuedBook));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/borrowedBooks"))
                .andExpect(status().isOk())
                .andExpect(view().name("list-issued"));
    }

    @Test
    void returnBookTest() throws Exception {
        book1.setBookId(1);
        Mockito.when(bookService.getBookById(1)).thenReturn(book1);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/return")
                .param("issueId", String.valueOf(1))
                .param("bookId", String.valueOf(1)))
                .andExpect(status().isMovedTemporarily())
                .andExpect(view().name("redirect:/api/books"));
    }

    @Test
    void updateBookTest() throws Exception{
        book1.setBookId(1);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/updateBook")
                        .contentType(MediaType.TEXT_HTML)
                        .param("bookId", String.valueOf(1)))
                .andExpect(view().name("add-book"));
    }

    @Test
    void borrowedBooksByUserTest() throws Exception{
        Users user1 = new Users("username","test123","9898989898","username@gmail.com",true);

        Mockito.when(userService.getByUsername("username")).thenReturn(user1);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/myBooks").param("username","username"))
                .andExpect(status().isOk())
                .andExpect(view().name("user-issued"));
    }

    @Test
    void deleteTest() throws Exception{
        book1.setBookId(1);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/delete")
                        .contentType(MediaType.TEXT_HTML)
                        .param("bookId", String.valueOf(1)))
                .andExpect(status().isMovedTemporarily())
                .andExpect(view().name("redirect:/api/books"));
    }

    @Test
    void borrowBookTest() throws Exception {
        book1.setBookId(1);
        Users user1 = new Users("username","test123","9898989898","username@gmail.com",true);
        Mockito.when(bookService.getBookById(1)).thenReturn(book1);
        Mockito.when(userService.getByUsername("username")).thenReturn(user1);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/borrow")
                .param("bookId", String.valueOf(1))
                .param("username","username"))
                .andExpect(status().isMovedTemporarily())
                .andExpect(view().name("redirect:/api/books"));
    }

}
