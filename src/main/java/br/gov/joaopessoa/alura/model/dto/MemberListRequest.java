package br.gov.joaopessoa.alura.model.dto;

import java.util.List;

import br.gov.joaopessoa.alura.model.Member;

public record MemberListRequest(
		List<MemberRequest> members) {

	public static MemberListRequest from(List<Member> listMember) {
		return new MemberListRequest(
				listMember.stream()
				.map(MemberRequest::from)
				.toList());
	}
}
