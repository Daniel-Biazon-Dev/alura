package br.gov.joaopessoa.alura.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.joaopessoa.alura.model.dto.AluraPageResponse;
import br.gov.joaopessoa.alura.model.dto.MemberListRequest;
import br.gov.joaopessoa.alura.model.dto.MemberResponse;
import br.gov.joaopessoa.alura.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Tag(name = "Membros")
@SecurityRequirement(name = "x-api-key")
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/enviar")
	@Operation(summary = "Enviar todas os Membros", description = "Busca todas as todas os Membros do banco e envia para a Alura")
	public ResponseEntity<Void> enviarTodos() {
		memberService.enviarTodos();
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/atualizar")
	@Operation(summary = "Enviar membro por id", description = "Busca uma membro específica e envia para a Alura")
	public ResponseEntity<Void> enviarPorId(String id) {
		memberService.atualizarPorId(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/listar")
	@Operation(summary = "Listar Membros", description = "Consulta os membros cadastrados na Alura")
	public ResponseEntity<AluraPageResponse<MemberResponse>> listar() {
		return ResponseEntity.ok(memberService.listarTodos());
	}
	
	@DeleteMapping("/deletar")
	@Operation(summary = "Deletar turma por código", description = "Deletar uma turma específica e envia para a Alura")
	public ResponseEntity<Void> deletarPorID(String id, String codigo){
		memberService.deletarPorId(id, codigo);
		return ResponseEntity.noContent().build();
	}
	
	
	
	@GetMapping("/localizarNoDB")
	@Operation(summary = "Listar Membros", description = "Consulta os membros cadastrados no Banco de Dados local")
	public ResponseEntity<Page<MemberListRequest>> listarTodos(Pageable pageable) {
		return ResponseEntity.status(HttpStatus.OK).body(memberService.listarTodosDB(pageable));
	}
}
