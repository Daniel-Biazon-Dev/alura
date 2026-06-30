package br.gov.joaopessoa.alura.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;

import br.gov.joaopessoa.alura.exceptions.ResourceNotFoundException;
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
	private final SchoolClassRepository schoolClassRepository;

	@Value("${alura.api.key}")
	private String apiKey;


	public void enviarTodas() {
		schoolClassRepository.findAll().forEach(this::enviar);
	}

	public void enviarPorCodigo(String codigo) {

		SchoolClass schoolClass = schoolClassRepository.findById(codigo).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Turma não encontrada: " + codigo));
		alterar(schoolClass);
	}
	
	public void deletarPorCodigo(String codigo) {

		schoolClassRepository.findById(codigo).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Turma não encontrada: " + codigo));
		deletar(codigo);
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

	private ResponseEntity<Void> enviar(SchoolClass schoolClass) {

		return aluraRestClient.post().uri("/class").headers(headers -> {
					if (StringUtils.hasText(apiKey)) {
						headers.set(API_KEY_HEADER, apiKey);
					}
				})
				.body(SchoolClassRequest.from(schoolClass))
				.retrieve()
				.toBodilessEntity();
	}
	
	
	private ResponseEntity<Void> alterar(SchoolClass schoolClass) {

		return aluraRestClient.put()
				.uri("/class")
				.headers(headers -> {
					if (StringUtils.hasText(apiKey)) {
						headers.set(API_KEY_HEADER, apiKey);
					}
				})
				.body(SchoolClassRequest.from(schoolClass))
				.retrieve()
				.toBodilessEntity();
	}
	
	private ResponseEntity<Void> deletar(String codigo) {

		return aluraRestClient.delete()
				.uri("/class/"+codigo)
				.headers(headers -> {
					if (StringUtils.hasText(apiKey)) {
						headers.set(API_KEY_HEADER, apiKey);
					}
				})
				.retrieve()
				.toBodilessEntity();
	}
}