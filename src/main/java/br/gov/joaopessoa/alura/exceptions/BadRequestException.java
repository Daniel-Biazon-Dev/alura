package br.gov.joaopessoa.alura.exceptions;

public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = -6338905299177433932L;

	public BadRequestException(String message) {
		super(message);
	}
}