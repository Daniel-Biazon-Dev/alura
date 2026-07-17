package br.gov.joaopessoa.alura.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.joaopessoa.alura.model.dto.AdminResponse;
import br.gov.joaopessoa.alura.model.dto.AluraPageResponse;
import br.gov.joaopessoa.alura.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
@Tag(name = "Admins")
@SecurityRequirement(name = "x-api-key")
public class AdminController {

	private final AdminService adminService;

	@PostMapping("/enviar")
	@Operation(summary = "Enviar todas as Admins", description = "Busca todas as admins do banco e envia para a Alura")
	public ResponseEntity<Void> enviarTodos() {
		adminService.enviarTodos();
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/atualizar")
	@Operation(summary = "Enviar Admins por código", description = "Busca uma Admins específica e envia para a Alura")
	public ResponseEntity<Void> enviarPorId(String id) {
		adminService.atualizarPorId(id);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/deletar")
	@Operation(summary = "Deletar Admin por código", description = "Deletar um Admin específica e envia para a Alura")
	public ResponseEntity<Void> deletarPorId(String id) {
		adminService.deletarPorId(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/listar")
	@Operation(summary = "Listar Admins", description = "Consulta os Admins cadastradas na Alura")
	public ResponseEntity<AluraPageResponse<AdminResponse>> listar() {
		return ResponseEntity.ok(adminService.listar());
	}
}
