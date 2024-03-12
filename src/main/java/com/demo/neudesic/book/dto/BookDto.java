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

}
