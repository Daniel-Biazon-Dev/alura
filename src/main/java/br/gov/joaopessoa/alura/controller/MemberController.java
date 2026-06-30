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

import br.gov.joaopessoa.alura.model.dto.MemberRequest;
import br.gov.joaopessoa.alura.model.dto.MemberResponse;
import br.gov.joaopessoa.alura.service.MemberService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/send")
	public ResponseEntity<Void> enviarTodos() {
		memberService.enviarTodos();
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}/send")
	public ResponseEntity<Void> enviarPorId(@PathVariable String id) {
		memberService.atualizarPorId(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<MemberResponse>> listar() {
		return ResponseEntity.ok(memberService.listar());
	}
	
	
	@GetMapping("/local")
	public ResponseEntity<List<MemberRequest>> listarTodos() {
		return ResponseEntity.status(HttpStatus.OK).body(memberService.listarTodos());
	}
}
