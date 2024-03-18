package com.demo.neudesic.book.util;

import com.demo.neudesic.book.dto.BookDto;
import com.demo.neudesic.book.entity.Book;
 
public class DTOConverter {
	
	public static Book convertToEntity(BookDto bookDto) {
		Book book = new Book();
		book.setName(bookDto.getName());
		book.setAuthor(bookDto.getAuthor());
		book.setGenre(bookDto.getGenre());
		book.setPublicationYear(bookDto.getPublicationYear());
		book.setId(bookDto.getId());
		return book;
	}

}
