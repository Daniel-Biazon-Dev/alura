package br.gov.joaopessoa.alura.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.joaopessoa.alura.model.dto.AluraPageResponse;
import br.gov.joaopessoa.alura.model.dto.SchoolClassResponse;
import br.gov.joaopessoa.alura.service.SchoolClassService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/class")
@RequiredArgsConstructor
@Tag(name = "Turmas")
@SecurityRequirement(name = "x-api-key")
public class SchoolClassController {

	private final SchoolClassService schoolClassService;

	@PostMapping("/enviar")
	@Operation(summary = "Enviar todas as turmas", description = "Busca todas as turmas do banco e envia para a Alura")
	public ResponseEntity<Void> enviarTodas() {
		schoolClassService.enviarTodas();
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/atualizar")
	@Operation(summary = "Enviar turma por código", description = "Busca uma turma específica e envia para a Alura")
	public ResponseEntity<Void> enviarPorCodigo(String codigo) {
		schoolClassService.atualizarPorId(codigo);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/deletar")
	@Operation(summary = "Deletar turma por código", description = "Deletar uma turma específica e envia para a Alura")
	public ResponseEntity<Void> deletarPorCodigo(String codigo) {
		schoolClassService.deletarPorCodigo(codigo);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/listar")
	@Operation(summary = "Listar turmas", description = "Consulta as turmas cadastradas na Alura")
	public ResponseEntity<AluraPageResponse<SchoolClassResponse>> listar() {
		return ResponseEntity.ok(schoolClassService.listar());
	}
}