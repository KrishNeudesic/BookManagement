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

	@Override
	public Optional<Book> findByName(String name) {
		return bookRepository.findByName(name);
	}

	@Override
	public List<Book> findByAuthor(String author) {
		return bookRepository.findByAuthor(author).stream().filter(Optional::isPresent).map(Optional::get).toList();
	}

	@Override
	public List<Book> findByGenre(String genre) {
		return bookRepository.findByGenre(genre).stream().filter(Optional::isPresent).map(Optional::get).toList();
	}

}
