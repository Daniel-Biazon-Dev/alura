package br.gov.joaopessoa.alura.model.dto;

import lombok.Getter;

@Getter
public class FieldMessage {

	private String fieldMessage;
	private String message;
	
	public FieldMessage(String fieldMessage, String message) {
		this.fieldMessage = fieldMessage;
		this.message = message;
	}
	
	
}
