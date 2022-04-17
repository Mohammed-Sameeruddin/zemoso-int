package com.library.demo.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="issued_books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IssuedBook {

    @Id
    @Column(name="issue_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int issueId;

    @Column(name="return_date")
    private Date returnDate;

    @Column(name="issue_date")
    private Date issueDate;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name="book_id")
    private Books book;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="user_id")
    private Users user;


    public IssuedBook(Date issueDate,Date returnDate,Books book,Users user){
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.book  = book;
        this.user = user;
    }

}
