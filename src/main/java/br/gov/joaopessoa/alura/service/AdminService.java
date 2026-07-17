package br.gov.joaopessoa.alura.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;

import br.gov.joaopessoa.alura.exceptions.DatabaseException;
import br.gov.joaopessoa.alura.exceptions.ResourceNotFoundException;
import br.gov.joaopessoa.alura.integration.AuthURLAdmin;
import br.gov.joaopessoa.alura.model.Admin;
import br.gov.joaopessoa.alura.model.dto.AdminRequest;
import br.gov.joaopessoa.alura.model.dto.AdminResponse;
import br.gov.joaopessoa.alura.model.dto.AluraPageResponse;
import br.gov.joaopessoa.alura.repository.AdminRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

	private static final String API_KEY_HEADER = "x-api-key";

	private final RestClient aluraRestClient;
	private final AuthURLAdmin authURLAdmin;
	private final AdminRepository adminRepository;

	@Value("${alura.api.key}")
	private String apiKey;

	public void enviarTodos() {
		var url = "https://cursos.alura.com.br/start/api/v1/admins";
		adminRepository.findAll()
			.stream()
			.map(AdminRequest::from)
			.forEach(admin -> authURLAdmin.postUrl(url, admin, "Admin"));
	}

	public void atualizarPorId(String id) {
		Admin admin = adminRepository.findById(id).orElseThrow(() -> new
				ResourceNotFoundException(HttpStatus.NOT_FOUND, "Admin não encontrada: " + id));
				
		var url = "https://cursos.alura.com.br/start/api/v1/admins/"+id;
		authURLAdmin.putUrl(url, AdminRequest.from(admin), "Admin atualizado por id com sucesso");
	}
	
	public void deletarPorId(String id) {

		if (!adminRepository.existsById(id)) {
			throw new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Administrador não encontrada");
		}
		try {
			adminRepository.deleteById(id);
		} catch (DatabaseException e) {
			throw new DatabaseException("Falha de integridade referencial");
		}
		var url = "https://cursos.alura.com.br/start/api/v1/admins/"+ id;
		authURLAdmin.deleteUrl(url, "Deletado classe com sucesso!");
	}
	
	public AluraPageResponse<AdminResponse> listar() {

		return aluraRestClient.get().uri("/admins").headers(headers -> {
					if (StringUtils.hasText(apiKey)) {
						headers.set(API_KEY_HEADER, apiKey);
					}
				})
				.retrieve()
				.body(new ParameterizedTypeReference<
						AluraPageResponse<AdminResponse>>() {
				});
	}
	
	public Page<AdminRequest> listarTodosDB(Pageable pageable) {
		return adminRepository.findAll(pageable).map(AdminRequest::new);
	}
}
