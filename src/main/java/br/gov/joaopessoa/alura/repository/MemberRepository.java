package br.gov.joaopessoa.alura.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gov.joaopessoa.alura.model.Member;
import br.gov.joaopessoa.alura.projections.CustomTurmaByMembro;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

	@Query(nativeQuery = true, value =
			"SELECT\n"
			+ "	tb_classe_membro.classes_codigo, \n"
			+ "	tb_membro.id, \n"
			+ "	tb_membro.email, \n"
			+ "	tb_membro.nome, \n"
			+ "	tb_membro.perfil\n"
			+ "FROM\n"
			+ "	tb_classe_membro INNER JOIN tb_membro	ON \n"
			+ "	tb_classe_membro.membro_id = tb_membro.id")
	Page<CustomTurmaByMembro> selecaoCustomTurmaByMembro(Pageable pageable);
}
