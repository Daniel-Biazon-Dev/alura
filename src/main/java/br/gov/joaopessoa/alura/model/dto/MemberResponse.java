package br.gov.joaopessoa.alura.model.dto;

import br.gov.joaopessoa.alura.model.Member;
import br.gov.joaopessoa.alura.model.enumaration.PerfilMembro;

public record MemberResponse(
		String id,
		String nome,
		String email,
		PerfilMembro perfil) {

	public MemberResponse(Member member) {
		this(
				member.getId(),
				member.getNome(),
				member.getEmail(),
				member.getPerfil());
	}
}
