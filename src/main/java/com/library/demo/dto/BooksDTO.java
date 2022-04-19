package com.library.demo.dto;

import lombok.Data;

@Data
public class BooksDTO {
    private int bookId;
    private String bookName;
    private String author;
    private String publisher;
    private int quantity;
}
