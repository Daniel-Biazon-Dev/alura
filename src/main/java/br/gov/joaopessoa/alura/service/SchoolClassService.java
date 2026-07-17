package br.gov.joaopessoa.alura.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;

import br.gov.joaopessoa.alura.exceptions.DatabaseException;
import br.gov.joaopessoa.alura.exceptions.ResourceNotFoundException;
import br.gov.joaopessoa.alura.integration.AuthURLSchoolClass;
import br.gov.joaopessoa.alura.model.SchoolClass;
import br.gov.joaopessoa.alura.model.dto.AluraPageResponse;
import br.gov.joaopessoa.alura.model.dto.SchoolClassRequest;
import br.gov.joaopessoa.alura.model.dto.SchoolClassResponse;
import br.gov.joaopessoa.alura.repository.SchoolClassRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SchoolClassService {

	private static final String API_KEY_HEADER = "x-api-key";

	private final RestClient aluraRestClient;
	private final AuthURLSchoolClass authUrlSchoolClass;
	private final SchoolClassRepository schoolClassRepository;

	@Value("${alura.api.key}")
	private String apiKey;


	public void enviarTodas() {
		var url = "https://cursos.alura.com.br/start/api/v1/class";
		schoolClassRepository.findAll()
				.stream()
				.map(SchoolClassRequest::from)
				.forEach(schoolClass -> authUrlSchoolClass.postUrl(url, schoolClass, "Turma"));
	}
	
	public void atualizarPorId(String codigo) {

		SchoolClass schoolClass = schoolClassRepository.findById(codigo)
				.orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Turma não encontrada: " + codigo));
		
		var url = "https://cursos.alura.com.br/start/api/v1/class/"+ codigo;
		authUrlSchoolClass.putUrl(url, SchoolClassRequest.from(schoolClass), "Classe atualizado por id com sucesso!");

	}
	
	public void deletarPorCodigo(String codigo) {

		if (!schoolClassRepository.existsById(codigo)) {
			throw new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Turma não encontrada");
		}
		try {
			schoolClassRepository.deleteById(codigo);
		} catch (DatabaseException e) {
			throw new DatabaseException("Falha de integridade referencial");
		}
		
		var url = "https://cursos.alura.com.br/start/api/v1/class/"+ codigo;
		authUrlSchoolClass.deleteUrl(url, "Deletado classe com sucesso!");
	}
	
	
	public AluraPageResponse<SchoolClassResponse> listar() {

		return aluraRestClient.get().uri("/class").headers(headers -> {
					if (StringUtils.hasText(apiKey)) {
						headers.set(API_KEY_HEADER, apiKey);
					}
				})
				.retrieve()
				.body(new ParameterizedTypeReference<
						AluraPageResponse<SchoolClassResponse>>() {
				});
	}

//	private ResponseEntity<Void> enviar(SchoolClass schoolClass) {
//
//		return aluraRestClient.post().uri("/class").headers(headers -> {
//					if (StringUtils.hasText(apiKey)) {
//						headers.set(API_KEY_HEADER, apiKey);
//					}
//				})
//				.body(SchoolClassRequest.from(schoolClass))
//				.retrieve()
//				.toBodilessEntity();
//	}
}