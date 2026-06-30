package br.gov.joaopessoa.alura.model.dto;

import br.gov.joaopessoa.alura.model.Member;
import br.gov.joaopessoa.alura.model.enumaration.PerfilMembro;

public record MemberRequest(
		String id,
		String nome,
		PerfilMembro perfil,
		String email) {

	public static MemberRequest from(Member member) {
		return new MemberRequest(
				member.getId(),
				member.getNome(),
				member.getPerfil(),
				member.getEmail());
	}
}
