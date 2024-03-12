package com.demo.neudesic.book.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.neudesic.book.entity.Book;
import com.demo.neudesic.book.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {
	
	private final BookRepository bookRepository;

	@Autowired
	public BookServiceImpl(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public Book save(Book book) {
		return bookRepository.save(book);
	}

	@Override
	public List<Book> findAll() {
		return bookRepository.findAll();
	}

	@Override
	public Optional<Book> findById(Long id) {
		return bookRepository.findById(id);
	}

	@Override
	public void delete(Book book) {
	 bookRepository.delete(book);
	}

}
