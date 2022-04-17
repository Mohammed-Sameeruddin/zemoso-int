package com.library.demo.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name="books")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Books {

    @Id
    @Column(name="book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;

    @Column(name="book_name")
    @NotNull(message="is required")
    @Size(min=3,message="length should be atleast 3")
    private String bookName;

    @Column(name="author")
    @NotNull(message="is required")
    @Size(min=3,message="length should be atleast 3")
    private String author;

    @Column(name="publisher")
    @NotNull(message = "is required")
    @Size(min=3,message="length should be atleast 3")
    private String publisher;

    @Column(name="quantity")
    @Min(0)
    private int quantity;

    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL)
    private List<IssuedBook> issuedBookList;


    public Books(String bookName,String author,String publisher,int quantity){
        this.bookName = bookName;
        this.author = author;
        this.publisher = publisher;
        this.quantity = quantity;
    }
}
