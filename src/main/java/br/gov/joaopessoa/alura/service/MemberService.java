package br.gov.joaopessoa.alura.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.gov.joaopessoa.alura.exceptions.DatabaseException;
import br.gov.joaopessoa.alura.exceptions.ResourceNotFoundException;
import br.gov.joaopessoa.alura.integration.AuthURLMember;
import br.gov.joaopessoa.alura.model.Member;
import br.gov.joaopessoa.alura.model.dto.AluraPageResponse;
import br.gov.joaopessoa.alura.model.dto.MemberListRequest;
import br.gov.joaopessoa.alura.model.dto.MemberRequest;
import br.gov.joaopessoa.alura.model.dto.MemberResponse;
import br.gov.joaopessoa.alura.repository.MemberRepository;
import br.gov.joaopessoa.alura.repository.SchoolClassRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final AuthURLMember authUrlService;
	private final MemberRepository memberRepository;
	private final SchoolClassRepository schoolClassRepository;

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
	}
	
	
	public void atualizarPorClassId(String id, String classCode) {
		Member member = memberRepository.findById(id).orElseThrow(() -> new  
				ResourceNotFoundException(HttpStatus.NOT_FOUND,"Membro não encontrado: " + id));

		if (member.getTurmas().isEmpty()) {
			throw new ResourceNotFoundException(HttpStatus.BAD_REQUEST, "Membro sem turma vinculada.");
		}
		
		var url = "https://cursos.alura.com.br/start/api/v1/class/"+classCode+"/members/"+ id;
        authUrlService.putPorClassUrl(url, MemberRequest.from(member), "Membro atualizado com sucesso!");
	}
	
	public void deletarPorId(String id, String classCode) {
		
		if(!memberRepository.existsById(id)) {
			throw new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Membro não encontrado");
		}
		
		if(!schoolClassRepository.existsById(classCode)) {
			throw new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Turma não encontrado");
		}
				
		try {
			memberRepository.deleteById(id);
		} catch (DatabaseException e) {
			throw new DatabaseException("Falha de integridade referencial");
		}
		
		var url = "https://cursos.alura.com.br/start/api/v1/class/"+classCode+"/members/"+ id;
        authUrlService.deleteUrl(url, "Deletado membro da classe com sucesso!");
	}
	
	public AluraPageResponse<MemberResponse> listarTodos() {
		List<MemberResponse> membros = new ArrayList<>();

		schoolClassRepository.findAll()
				.stream()
				.map(schoolClass -> schoolClass.getCodigo())
				.filter(StringUtils::hasText)
				.forEach(codigoTurma -> {
					var url = "/class/" + codigoTurma + "/members";
					AluraPageResponse<MemberResponse> response = authUrlService.listaMembros(
							url,
							"membros da turma " + codigoTurma,
							new ParameterizedTypeReference<AluraPageResponse<MemberResponse>>() {
							});

					if (response.conteudo() != null) {
						membros.addAll(response.conteudo());
					}
				});

		List<MemberResponse> membrosUnicos = new ArrayList<>(membros.stream()
				.collect(Collectors.toMap(
						MemberResponse::id,
						member -> member,
						(memberAtual, memberRepetido) -> memberAtual,
						LinkedHashMap::new))
				.values());

		return new AluraPageResponse<>(membrosUnicos, 1, membrosUnicos.size(), 0, membrosUnicos.size());
	}
	
	public Page<MemberListRequest> listarTodosDB(Pageable pageable) {
	    return memberRepository.selecaoCustomTurmaByMembro(pageable).map(MemberListRequest::new);
	}
}
