package br.gov.joaopessoa.alura.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import br.gov.joaopessoa.alura.exceptions.ResourceNotFoundException;
import br.gov.joaopessoa.alura.model.Member;
import br.gov.joaopessoa.alura.model.SchoolClass;
import br.gov.joaopessoa.alura.model.dto.MemberRequest;
import br.gov.joaopessoa.alura.model.dto.MemberResponse;
import br.gov.joaopessoa.alura.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private static final String API_KEY_HEADER = "x-api-key";

	private final RestClient aluraRestClient;
	private final MemberRepository memberRepository;

	@Value("${alura.api.key}")
	private String apiKey;

	public List<MemberResponse> listar() {

		return memberRepository.findAll()
				.stream()
				.map(MemberResponse::new)
				.toList();
	}

	public void enviarTodos() {

		Map<String, List<MemberRequest>> membersByClassCode =
				memberRepository.findAll()
						.stream()
						.flatMap(member ->
								member.getTurmas().stream().map(turma -> Map.entry(turma.getCodigo(),MemberRequest.from(member))))
						.collect(Collectors.groupingBy(Map.Entry::getKey,
								Collectors.mapping(Map.Entry::getValue,Collectors.toList())));

		membersByClassCode.forEach(this::enviarPorTurma);
	}
	
	
	
	public List<MemberRequest> listarTodos() {
		List<Member> list = memberRepository.findAll();
		List<MemberRequest> listMem = list.stream().map(e -> MemberRequest.from(e)).toList();
		return listMem;
	}

	public void atualizarPorId(String id) {

		Member member = memberRepository.findById(id).orElseThrow(() -> new  ResourceNotFoundException(HttpStatus.NOT_FOUND,"Membro não encontrado: " + id));

		if (member.getTurmas().isEmpty()) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST,
					"Membro sem turma vinculada.");
		}

		member.getTurmas()
				.stream()
				.map(SchoolClass::getCodigo)
				.forEach(classCode ->
						enviarPorTurma(
								classCode,
								List.of(MemberRequest.from(member))));
	}

	private ResponseEntity<Void> enviarPorTurma(String classCode, List<MemberRequest> members) {

		System.out.println(members);
		return aluraRestClient.post()
				.uri("/class/{classCode}/members", classCode)
				.headers(headers -> {
					if (StringUtils.hasText(apiKey)) {
						headers.set(API_KEY_HEADER, apiKey);
					}
				})
				.retrieve()
				.toBodilessEntity();
	}
}