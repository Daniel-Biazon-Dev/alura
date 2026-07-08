package br.gov.joaopessoa.alura.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;

import br.gov.joaopessoa.alura.exceptions.ResourceNotFoundException;
import br.gov.joaopessoa.alura.integration.AuthURLMember;
import br.gov.joaopessoa.alura.model.Member;
import br.gov.joaopessoa.alura.model.dto.AluraPageResponse;
import br.gov.joaopessoa.alura.model.dto.MemberListRequest;
import br.gov.joaopessoa.alura.model.dto.MemberRequest;
import br.gov.joaopessoa.alura.model.dto.MemberResponse;
import br.gov.joaopessoa.alura.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private static final String API_KEY_HEADER = "x-api-key";

	private final AuthURLMember authUrlService;
	private final RestClient aluraRestClient;
	private final MemberRepository memberRepository;

	@Value("${alura.api.key}")
	private String apiKey;

	public void enviarTodos() {		
		memberRepository.findAll()
        .stream()
        .flatMap(member -> member.getTurmas().stream().map(turma -> Map.entry(turma.getCodigo(), MemberRequest.from(member))))
        .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue,Collectors.toList())))
        .forEach((classCode, members) -> {
            var url = "https://cursos.alura.com.br/start/api/v1/class/" + classCode + "/members";
            authUrlService.postUrl(url, members, "Aluno por turma!");
        });
	}
	
	public void atualizarPorId(String id) {
		Member member = memberRepository.findById(id).orElseThrow(() -> new  
				ResourceNotFoundException(HttpStatus.NOT_FOUND,"Membro não encontrado: " + id));

		if (member.getTurmas().isEmpty()) {
			throw new ResourceNotFoundException(HttpStatus.BAD_REQUEST, "Membro sem turma vinculada.");
		}
		
		var url = "https://cursos.alura.com.br/start/api/v1/members/"+ id;
        authUrlService.putUrl(url, MemberRequest.from(member), "Membro atualizado com sucesso!");

//		member.getTurmas()
//				.stream()
//				.map(SchoolClass::getCodigo)
//				.forEach(classCode ->  {
//		            var url = "https://cursos.alura.com.br/start/api/v1/members"+member.getId();
//		            authUrlService.putUrl(url, MemberRequest.from(member), "Membro atualizado com sucesso!");
//		        });
	}
	
	public AluraPageResponse<MemberResponse> listarTodos() {

		return aluraRestClient.get().uri("/class/"+123+"/members")
				.headers(headers -> {
					if (StringUtils.hasText(apiKey)) {
						headers.set(API_KEY_HEADER, apiKey);
					}
				})
				.retrieve()
				.body(new ParameterizedTypeReference<
						AluraPageResponse<MemberResponse>>() {
				});
	}
	
	public List<MemberListRequest> listarTodosDB() {
	    return memberRepository.selecaoCustomTurmaByMembro()
	            .stream()
	            .map(MemberListRequest::new)
	            .toList();
	}
}
