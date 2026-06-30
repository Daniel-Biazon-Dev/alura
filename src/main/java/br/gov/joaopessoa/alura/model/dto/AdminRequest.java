package br.gov.joaopessoa.alura.model.dto;

import br.gov.joaopessoa.alura.model.Admin;

public record AdminRequest(
		String id,
		String nome,
		String email) {

	public static AdminRequest from(Admin admin) {
		return new AdminRequest(
				admin.getId(),
				admin.getNome(),
				admin.getEmail());
	}
}
