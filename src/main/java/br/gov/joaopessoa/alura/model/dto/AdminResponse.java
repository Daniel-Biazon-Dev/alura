package br.gov.joaopessoa.alura.model.dto;

import br.gov.joaopessoa.alura.model.Admin;

public record AdminResponse(
		String id,
		String nome,
		String email) {

	public AdminResponse(Admin admin) {
		this(
				admin.getId(),
				admin.getNome(),
				admin.getEmail());
	}
}
