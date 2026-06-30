package br.gov.joaopessoa.alura.model.dto;

import java.util.List;

import br.gov.joaopessoa.alura.model.Member;
import br.gov.joaopessoa.alura.model.SchoolClass;
import br.gov.joaopessoa.alura.model.enumaration.PerfilMembro;

public record MemberResponse(
		String id,
		String nome,
		String email,
		PerfilMembro perfil,
		List<String> turmas) {

	public MemberResponse(Member member) {
		this(
				member.getId(),
				member.getNome(),
				member.getEmail(),
				member.getPerfil(),
				member.getTurmas().stream()
						.map(SchoolClass::getCodigo)
						.toList());
	}
}
