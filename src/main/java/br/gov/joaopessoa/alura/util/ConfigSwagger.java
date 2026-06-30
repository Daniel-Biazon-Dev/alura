package br.gov.joaopessoa.alura.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class ConfigSwagger {

	private static final String API_KEY_HEADER = "x-api-key";

	@Value("${alura.api.base-url}")
	private String baseUrl;

	@Value("${alura.api.key}")
	private String apiKey;

	@Bean
	public OpenAPI openAPI() {

		return new OpenAPI()
				.components(new Components().addSecuritySchemes(API_KEY_HEADER,
						new SecurityScheme()
						.type(SecurityScheme
						.Type.APIKEY)
						.in(SecurityScheme.In.HEADER).name(API_KEY_HEADER)))
				.addSecurityItem(new SecurityRequirement().addList(API_KEY_HEADER)).info(
						new Info()
						.title("API Alura Start")
						.description("API para integração com a Alura")
						.version("1.0.1"));
	}
	@Bean
	public RestClient aluraRestClient() {
		RestClient.Builder builder = RestClient.builder().baseUrl(baseUrl);
		if (StringUtils.hasText(apiKey)) {
			builder.defaultHeader(API_KEY_HEADER, apiKey);
		}

		return builder.build();
	}
}
