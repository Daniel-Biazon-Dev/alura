package br.gov.joaopessoa.alura.exceptions;

public class DatabaseException extends RuntimeException {

	private static final long serialVersionUID = 8032206251123211366L;

	public DatabaseException(String message) {
		super(message);
	}
}