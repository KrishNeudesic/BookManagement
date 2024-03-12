package com.demo.neudesic.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.neudesic.book.dto.BookDto;
import com.demo.neudesic.book.entity.Book;
import com.demo.neudesic.book.service.BookService;
import com.demo.neudesic.book.util.DTOConverter;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {

	@Autowired
	private BookService bookService;

	@PostMapping("/save")
	public void save(BookDto book) {
		bookService.save(DTOConverter.convertToEntity(book));
	}

	@DeleteMapping("/delete")
	public void delete(BookDto book) {
		bookService.delete(DTOConverter.convertToEntity(book));
	}

	@GetMapping("/find/all")
	public List<Book> getAllBooks() {
		return bookService.findAll();
	}

	@GetMapping("/find/{bookId}")
	public Optional<Book> getBook(@PathVariable Long bookId) {
		return bookService.findById(bookId);
	}

}
