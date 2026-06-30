package br.gov.joaopessoa.alura.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -5390179600922479806L;

	private final HttpStatus status;

	public ResourceNotFoundException(HttpStatus status, String msg) {
		super(msg);
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}
}