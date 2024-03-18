package com.demo.neudesic.book.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.neudesic.book.dto.BookDto;
import com.demo.neudesic.book.entity.Book;
import com.demo.neudesic.book.service.BookService;
import com.demo.neudesic.book.util.CustomHttpResponse;
import com.demo.neudesic.book.util.DTOConverter;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/book")
public class BookController {

	private final BookService bookService;

	private static final Logger logger = LoggerFactory.getLogger(BookController.class);
	private static final String SUCCESS = "SUCCESS";
	private static final String NOT_FOUND = "Book not found";
	private static final String EXCEPTION_MSG = "Exception caught ";
	private static final String BOOK_NOT_FOUND = "Book not found!";
	private static final String EXCEPTION = "EXCEPTION";
	private static final String BOOK_RESERVED = "BOOK RESERVED!";

	@Autowired
	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestBody BookDto book) {
		try {
			Book bookVal = bookService.save(DTOConverter.convertToEntity(book));
			logger.info("Saving book data");
			return ResponseEntity.status(HttpStatus.OK).body(new CustomHttpResponse(SUCCESS, HttpStatus.OK, bookVal));
		} catch (Exception e) {
			logger.error(EXCEPTION_MSG, e);
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new CustomHttpResponse(EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR, null));
		}
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> delete(@RequestBody BookDto book) {
		Optional<Book> isBookPresent = bookService.findById(book.getId());
		if (!isBookPresent.isPresent()) {
			logger.warn(BOOK_NOT_FOUND);
			return ResponseEntity.status(HttpStatus.OK).body(new CustomHttpResponse(NOT_FOUND, HttpStatus.OK, null));
		} else {
			try {
				bookService.delete(DTOConverter.convertToEntity(book));
				logger.info("Deleting book data");
			} catch (Exception e) {
				logger.error(EXCEPTION_MSG, e);
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new CustomHttpResponse(EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR, null));
			}
			return ResponseEntity.status(HttpStatus.OK).body(new CustomHttpResponse(SUCCESS, HttpStatus.OK, null));
		}
	}

	@GetMapping("/find/all")
	public ResponseEntity<?> getAllBooks() {
		logger.info("Fetching all book data");
		List<Book> books = bookService.findAll();
		if (books.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(new CustomHttpResponse(NOT_FOUND, HttpStatus.OK, null));
		}
		return ResponseEntity.status(HttpStatus.OK).body(new CustomHttpResponse(SUCCESS, HttpStatus.OK, books));
	}

	@GetMapping("/find/{bookId}")
	public ResponseEntity<?> getBook(@PathVariable Long bookId) {
		Optional<Book> book = bookService.findById(bookId);
		if (!book.isPresent()) {
			logger.warn(BOOK_NOT_FOUND);
			return ResponseEntity.status(HttpStatus.OK).body(new CustomHttpResponse(NOT_FOUND, HttpStatus.OK, null));
		}
		String message = String.format("Fetching book by id: %d", bookId);
		logger.info(message);
		return ResponseEntity.status(HttpStatus.OK).body(new CustomHttpResponse(SUCCESS, HttpStatus.OK, book.get()));
	}

	@GetMapping("/find/title")
	public ResponseEntity<?> findBookByName(@RequestParam(name = "name") String bookName) {
		Optional<Book> book = bookService.findByName(bookName);
		try {
			if (!book.isPresent()) {
				logger.warn(BOOK_NOT_FOUND);
				return ResponseEntity.status(HttpStatus.OK)
						.body(new CustomHttpResponse(NOT_FOUND, HttpStatus.OK, null));
			}
			String message = String.format("Fetching book by name: %s", bookName);
			logger.info(message);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new CustomHttpResponse(SUCCESS, HttpStatus.OK, book.get()));
		} catch (Exception e) {
			logger.error(EXCEPTION_MSG, e);
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new CustomHttpResponse(EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR, null));
		}
	}

	@GetMapping("/find/author")
	public ResponseEntity<?> findBookByAuthor(@RequestParam(name = "author") String author) {
		List<Book> book = bookService.findByAuthor(author);
		try {
			if (book.isEmpty()) {
				logger.warn(BOOK_NOT_FOUND);
				return ResponseEntity.status(HttpStatus.OK)
						.body(new CustomHttpResponse(NOT_FOUND, HttpStatus.OK, null));
			}
			String message = String.format("Fetching book by author: %s", author);
			logger.info(message);
			return ResponseEntity.status(HttpStatus.OK).body(new CustomHttpResponse(SUCCESS, HttpStatus.OK, book));
		} catch (Exception e) {
			logger.error(EXCEPTION_MSG, e);
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new CustomHttpResponse(EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR, null));
		}
	}

	@GetMapping("/find/genre")
	public ResponseEntity<?> findBookByGenre(@RequestParam(name = "genre") String genre) {
		try {
			List<Book> book = bookService.findByGenre(genre);
			if (book.isEmpty()) {
				logger.warn(BOOK_NOT_FOUND);
				return ResponseEntity.status(HttpStatus.OK)
						.body(new CustomHttpResponse(NOT_FOUND, HttpStatus.OK, null));
			}
			String message = String.format("Fetching book by genre: %s", genre);
			logger.info(message);
			return ResponseEntity.status(HttpStatus.OK).body(new CustomHttpResponse(SUCCESS, HttpStatus.OK, book));
		} catch (Exception e) {
			logger.error(EXCEPTION_MSG, e);
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new CustomHttpResponse(EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR, null));
		}
	}

	// To be updated to tag to respective user id
	@Transactional
	@PostMapping("/reserve")
	public ResponseEntity<?> reserveBook(@RequestBody BookDto book) {
		try {
			Optional<Book> bookPresent = bookService.findById(book.getId());
			if (bookPresent.isEmpty()) {
				logger.warn(BOOK_NOT_FOUND);
				return ResponseEntity.status(HttpStatus.OK)
						.body(new CustomHttpResponse(NOT_FOUND, HttpStatus.OK, null));
			}
			if (Boolean.TRUE.equals(bookPresent.get().getIsBooked())) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new CustomHttpResponse("Book Already Booked!", HttpStatus.OK, null));
			}
			bookPresent.get().setIsBooked(true);
			bookService.save(bookPresent.get());
			return ResponseEntity.status(HttpStatus.OK)
					.body(new CustomHttpResponse(BOOK_RESERVED, HttpStatus.OK, bookPresent.get()));
		} catch (Exception e) {
			logger.error(EXCEPTION_MSG, e);
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new CustomHttpResponse(EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR, null));
		}
	}

}
