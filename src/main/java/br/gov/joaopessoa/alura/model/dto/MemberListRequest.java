package br.gov.joaopessoa.alura.model.dto;

import br.gov.joaopessoa.alura.projections.CustomTurmaByMembro;

public record MemberListRequest(
		String codigo,
		Long id,
		String email,
		String nome,
		String perfil
) {

	public MemberListRequest(CustomTurmaByMembro customTurmaByMembro) {
		this(customTurmaByMembro.getClasses_codigo(), customTurmaByMembro.getId(), customTurmaByMembro.getEmail(), customTurmaByMembro.getNome(), customTurmaByMembro.getPerfil());
		
	}
}
