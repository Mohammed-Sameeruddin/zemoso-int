package com.library.demo.controller;

import com.library.demo.entity.Books;
import com.library.demo.entity.IssuedBook;
import com.library.demo.entity.Users;
import com.library.demo.service.BookService;
import com.library.demo.service.IssuedBookService;
import com.library.demo.service.RoleService;
import com.library.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    private IssuedBookService issuedBookService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/books")
    public String showBooks(Model theModel){
        List<Books> booksList = new ArrayList<>();

        booksList = bookService.getBooks();

        theModel.addAttribute("books",booksList);

        return "list-books";
    }

    @GetMapping("/borrow")
    public String borrowBook(@RequestParam("bookId") int bookId, @RequestParam("username") String username, RedirectAttributes redirectAttributes){
        Books book = bookService.getBookById(bookId);
        Users user = userService.getByUsername(username);
        List<IssuedBook> issuedBookList = issuedBookService.getBooksByUser(user);
        for(IssuedBook tempBook : issuedBookList){
            if(tempBook.getBook().getBookId() == book.getBookId()){
                redirectAttributes.addFlashAttribute("error","You have already borrowed " + book.getBookName());
                return "redirect:/api/books";
            }
        }
        LocalDate issueDate = LocalDate.now();
        LocalDate returnDate = issueDate.plusDays(10);
        Date curr = Date.valueOf(issueDate);
        Date later = Date.valueOf(returnDate);
        IssuedBook issuedBook = new IssuedBook(curr,later,book,user);
        if(book.getQuantity() == 0){
            redirectAttributes.addFlashAttribute("error",book.getBookName()+ " is not available");
            return "redirect:/api/books";
        }
        book.setQuantity(book.getQuantity() - 1);
        bookService.saveBook(book);
        issuedBookService.saveIssuedBook(issuedBook);
        redirectAttributes.addFlashAttribute("success",book.getBookName() + " is added to My Books");
        return "redirect:/api/books";
    }

    @GetMapping("/borrowedBooks")
    public String showBorrowedBooks(Model theModel){
        List<IssuedBook> issuedBookList = issuedBookService.getIssuedBooks();
        theModel.addAttribute("issuedBooks",issuedBookList);
        return "list-issued";
    }

    @GetMapping("/myBooks")
    public String borrowedBooksByUser(@RequestParam("username") String username,Model theModel){
        Users user = userService.getByUsername(username);
        List<IssuedBook> issuedBookList = user.getBooksList();
        theModel.addAttribute("issuedBooks",issuedBookList);
        return "user-issued";
    }

    @GetMapping("/return")
    public String returnBook(@RequestParam("issueId") int issueId,@RequestParam("bookId") int bookId){
        Books book = bookService.getBookById(bookId);
        book.setQuantity(book.getQuantity() + 1);
        bookService.saveBook(book);
        issuedBookService.deleteIssuedBook(issueId);
        return "redirect:/api/books";
    }

    @GetMapping("/addBook")
    public String showFormForAdd(Model theModel){
        Books book = new Books();
        theModel.addAttribute("book",book);
        return "add-book";
    }

    @GetMapping("/updateBook")
    public String showFormForUpdate(@RequestParam("bookId") int id,Model theModel){
        Books theBook = bookService.getBookById(id);
        theModel.addAttribute("book",theBook);
        return "add-book";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("bookId") int id){
        bookService.deleteBook(id);
        return "redirect:/api/books";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("book") Books theBook, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "add-book";
        }
        bookService.saveBook(theBook);
        return "redirect:/api/books";
    }

}
