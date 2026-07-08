package br.gov.joaopessoa.alura.integration;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.joaopessoa.alura.model.dto.SchoolClassRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthURLSchoolClass {
	
	private static final String API_KEY_HEADER = "x-api-key";
	
	private final RestClient restClient;
	private final ObjectMapper mapper = new ObjectMapper();
	
	@Value("${alura.api.key}")
	private String apiKey;

	public Map<String, Object> postUrl(String url,  List<SchoolClassRequest> classes, String tipo) {
		try {
			
			Map<String, Object> response = restClient
					.post()
					.uri(url)
					.headers(headers -> {
						if (StringUtils.hasText(apiKey)) {
							headers.set(API_KEY_HEADER, apiKey);
						}
					})
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.body(classes)
					.retrieve()
					.body(new ParameterizedTypeReference<Map<String, Object>>() {
					});

			return Optional.ofNullable(response).orElse(Map.of("mensagem", "Resposta vazia da API de " + tipo));

		} catch (RestClientResponseException e) {
			return tratarErro("Erro de cliente na API externa", e.getStatusCode(), e.getResponseBodyAsString());
		} catch (ResourceAccessException e) {
			return Map.of("sucesso", false, "mensagem", "Não foi possível acessar a API externa (timeout/conexão).",
					"detalhe", e.getMessage());
		} catch (Exception e) {
			return Map.of("sucesso", false, "mensagem", "Erro inesperado ao importar " + tipo + ".", "detalhe",
					e.getMessage());
		}
	}
	
	public Map<String, Object> putUrl(String url,  SchoolClassRequest schoolClass, String tipo) {
		try {
			
			Map<String, Object> response = restClient
					.put()
					.uri(url)
					.headers(headers -> {
						if (StringUtils.hasText(apiKey)) {
							headers.set(API_KEY_HEADER, apiKey);
						}
					})
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.body(schoolClass)
					.retrieve()
					.body(new ParameterizedTypeReference<Map<String, Object>>() {
					});
			
			return Optional.ofNullable(response).orElse(Map.of("mensagem", "Resposta vazia da API de " + tipo));

		} catch (RestClientResponseException e) {
			return tratarErro("Erro de cliente na API externa", e.getStatusCode(), e.getResponseBodyAsString());
		} catch (ResourceAccessException e) {
			return Map.of("sucesso", false, "mensagem", "Não foi possível acessar a API externa (timeout/conexão).",
					"detalhe", e.getMessage());
		} catch (Exception e) {
			return Map.of("sucesso", false, "mensagem", "Erro inesperado ao importar " + tipo + ".", "detalhe",
					e.getMessage());
		}
	}
	
	public Map<String, Object> deleteUrl(String url,  String tipo) {
		try {
			
			Map<String, Object> response = restClient
					.delete()
					.uri(url)
					.headers(headers -> {
						if (StringUtils.hasText(apiKey)) {
							headers.set(API_KEY_HEADER, apiKey);
						}
					})
					.accept(MediaType.APPLICATION_JSON)
					.retrieve()
					.body(new ParameterizedTypeReference<Map<String, Object>>() {
					});
			
			return Optional.ofNullable(response).orElse(Map.of("mensagem", "Resposta vazia da API de " + tipo));

		} catch (RestClientResponseException e) {
			return tratarErro("Erro de cliente na API externa", e.getStatusCode(), e.getResponseBodyAsString());
		} catch (ResourceAccessException e) {
			return Map.of("sucesso", false, "mensagem", "Não foi possível acessar a API externa (timeout/conexão).",
					"detalhe", e.getMessage());
		} catch (Exception e) {
			return Map.of("sucesso", false, "mensagem", "Erro inesperado ao importar " + tipo + ".", "detalhe",
					e.getMessage());
		}
	}
	
	private Map<String, Object> tratarErro(String mensagem, HttpStatusCode status, String detalhe) {
		try {
			Object detalheJson = mapper.readValue(detalhe, Map.class);
			return Map.of("sucesso", false, "mensagem", mensagem + ": " + status, "detalhe", detalheJson);
		} catch (Exception e) {
			return Map.of("sucesso", false, "mensagem", mensagem + ": " + status, "detalhe", detalhe);
		}
	}
}