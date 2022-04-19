package com.library.demo.service;


import com.library.demo.dto.BooksDTO;
import com.library.demo.entity.Books;
import com.library.demo.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Books> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Books getBookById(int id) {
        Optional<Books> book = Optional.of(bookRepository.getById(id));
        return book.get();
    }

    @Override
    public Books saveBook(BooksDTO theBook) {
        Books book = convertDtoToEntity(theBook);
        bookRepository.save(book);
        return book;
    }

    @Override
    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }

    @Override
    public BooksDTO getBookDTO(Books book){
        return convertEntityToDto(book);
    }

    @Override
    public Books getEntity(BooksDTO booksDTO) { return convertDtoToEntity(booksDTO);}

    public BooksDTO convertEntityToDto(Books books){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        BooksDTO booksDTO;
        booksDTO = modelMapper.map(books, BooksDTO.class);
        return booksDTO;
    }

    public Books convertDtoToEntity(BooksDTO booksDTO){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        Books books;
        books = modelMapper.map(booksDTO, Books.class);
        return books;
    }
}
