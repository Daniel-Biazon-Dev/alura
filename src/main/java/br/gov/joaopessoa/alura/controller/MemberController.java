package br.gov.joaopessoa.alura.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.joaopessoa.alura.model.dto.AluraPageResponse;
import br.gov.joaopessoa.alura.model.dto.MemberListRequest;
import br.gov.joaopessoa.alura.model.dto.MemberResponse;
import br.gov.joaopessoa.alura.service.MemberService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@Tag(name = "Membros")
@SecurityRequirement(name = "x-api-key")
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/enviar")
	public ResponseEntity<Void> enviarTodos() {
		memberService.enviarTodos();
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}/atualizar")
	public ResponseEntity<Void> enviarPorId(@PathVariable String id) {
		memberService.atualizarPorId(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/listar")
	public ResponseEntity<AluraPageResponse<MemberResponse>> listar() {
		return ResponseEntity.ok(memberService.listar());
	}
	
	
	@GetMapping("/local")
	public ResponseEntity<List<MemberListRequest>> listarTodos() {
		return ResponseEntity.status(HttpStatus.OK).body(memberService.listarTodos());
	}
}
