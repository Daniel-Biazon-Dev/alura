package br.gov.joaopessoa.alura.model.dto;

import java.util.List;

import br.gov.joaopessoa.alura.model.Member;
import br.gov.joaopessoa.alura.model.enumaration.PerfilMembro;

public record MemberResponse(
		String id,
		String nome,
		List<String> emails,
		PerfilMembro perfil) {

	public MemberResponse(Member member) {
		this(
				member.getId(),
				member.getNome(),
				member.getEmail() == null ? List.of() : List.of(member.getEmail()),
				member.getPerfil());
	}
}