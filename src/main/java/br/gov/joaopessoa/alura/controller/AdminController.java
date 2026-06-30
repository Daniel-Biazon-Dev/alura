package br.gov.joaopessoa.alura.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.joaopessoa.alura.model.dto.AdminResponse;
import br.gov.joaopessoa.alura.service.AdminService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdminController {

	private final AdminService adminService;

	@PostMapping("/send")
	public ResponseEntity<Void> enviarTodos() {
		adminService.enviarTodos();
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/{id}/send")
	public ResponseEntity<Void> enviarPorId(@PathVariable String id) {
		adminService.enviarPorId(id);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}/send")
	public ResponseEntity<Void> deletarPorId(@PathVariable String id) {
		adminService.enviarPorId(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<AdminResponse>> listar() {
		return ResponseEntity.ok(adminService.listar());
	}
}
