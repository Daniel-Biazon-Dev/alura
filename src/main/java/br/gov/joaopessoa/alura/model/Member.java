package br.gov.joaopessoa.alura.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.gov.joaopessoa.alura.model.enumaration.PerfilMembro;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_membro")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"turmas"})
public class Member implements Serializable{

	private static final long serialVersionUID = 6915170796962052941L;

	@Id
	@EqualsAndHashCode.Include
	private String id;

	private String nome;
	private String email;

	@Enumerated(EnumType.STRING)
	private PerfilMembro perfil;

	@ManyToMany
	@JoinTable(
	    name = "tb_classe_membro",
	    joinColumns = @JoinColumn(name = "membro_id", referencedColumnName = "id"),
	    inverseJoinColumns = @JoinColumn(name = "classes_codigo", referencedColumnName = "codigo")
	)
	@JsonIgnoreProperties("membros")
	private Set<SchoolClass> turmas = new HashSet<>();
}
