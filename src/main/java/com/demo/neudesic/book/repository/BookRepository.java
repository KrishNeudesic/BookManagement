package com.demo.neudesic.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.demo.neudesic.book.entity.Book;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	public Book save(Book book);

	public List<Book> findAll();

	public Optional<Book> findById(Long id);

	public Optional<Book> findByName(String name);

	public List<Optional<Book>> findByAuthor(String author);

	public List<Optional<Book>> findByGenre(String genre);

	public void delete(Book book);

}