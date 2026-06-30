package br.gov.joaopessoa.alura.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.joaopessoa.alura.model.Member;

public interface MemberRepository extends JpaRepository<Member, String> {

	Page<Member> findByTurmasCodigo(String codigo, Pageable pageable);
}
