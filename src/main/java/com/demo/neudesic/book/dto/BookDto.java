package com.demo.neudesic.book.dto;

import java.time.LocalDate;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class BookDto {

	private Long id;
	private String name;
	private String author;
	private String genre;
	private LocalDate publicationYear;

	public BookDto(Long id, String name, String author, String genre, LocalDate publicationYear) {
		this.id = id;
		this.name = name;
		this.author = author;
		this.genre = genre;
		this.publicationYear = publicationYear;
	}

	public BookDto() {
	}

}
