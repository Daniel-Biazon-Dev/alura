package br.gov.joaopessoa.alura.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import br.gov.joaopessoa.alura.model.Admin;
import br.gov.joaopessoa.alura.model.dto.AdminRequest;
import br.gov.joaopessoa.alura.model.dto.AdminResponse;
import br.gov.joaopessoa.alura.repository.AdminRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

	private static final String API_KEY_HEADER = "x-api-key";

	private final RestClient aluraRestClient;
	private final AdminRepository adminRepository;

	@Value("${alura.api.key}")
	private String apiKey;

	public List<AdminResponse> listar() {
		return adminRepository.findAll().stream()
				.map(AdminResponse::new)
				.toList();
	}

	public void enviarTodos() {
		enviar(adminRepository.findAll().stream()
				.map(AdminRequest::from)
				.toList());
	}

	public void deletarPorId(String id) {
		Admin admin = adminRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Gestor nao encontrado: " + id));

		enviar(List.of(AdminRequest.from(admin)));
	}

	public void enviarPorId(String id) {
		Admin admin = adminRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Gestor nao encontrado: " + id));

		enviar(List.of(AdminRequest.from(admin)));
	}
	
	private void enviar(List<AdminRequest> admins) {
		aluraRestClient.post()
				.uri("/admins")
				.headers(headers -> {
					if (StringUtils.hasText(apiKey)) {
						headers.set(API_KEY_HEADER, apiKey);
					}
				})
				.body(admins)
				.retrieve()
				.toBodilessEntity();
	}
}
