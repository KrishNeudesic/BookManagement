package com.demo.neudesic.book.service;

import java.util.List;
import java.util.Optional;

import com.demo.neudesic.book.entity.Book;

public interface BookService {
	
	public Book save(Book book);

	public List<Book> findAll();

	public Optional<Book> findById(Long id);
	
	public Optional<Book> findByName(String name);
	
	public List<Book> findByAuthor(String author);
	
	public List<Book> findByGenre(String genre);

	public void delete(Book book);

}
