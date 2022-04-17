package com.library.demo.repository;

import com.library.demo.entity.IssuedBook;
import com.library.demo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssuedBookRepository extends JpaRepository<IssuedBook,Integer> {
    @Query("SELECT u FROM IssuedBook u WHERE u.user = :user")
    List<IssuedBook> getBookByUser(@Param("user") Users user);
}
