package com.demo.neudesic.book.util;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@NoArgsConstructor
public class CustomHttpResponse {

	private String message;
	private HttpStatus status;
	private Object data;

	public CustomHttpResponse(String message, HttpStatus status, Object data) {
		this.message = message;
		this.status = status;
		this.data = data;
	}

}
