package br.gov.joaopessoa.alura.model.dto;

import br.gov.joaopessoa.alura.model.Member;

public record MemberRequest(
		String id,
		String nome,
		String email,
		String perfil) {

	public static MemberRequest from(Member member) {
		return new MemberRequest(
				member.getId(),
				member.getNome(),
				member.getEmail(),
				member.getPerfil().toString());
	}
}
