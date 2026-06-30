package br.gov.joaopessoa.alura.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.gov.joaopessoa.alura.model.enumaration.TipoEnsino;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_classe")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"membros"})
public class SchoolClass implements Serializable{

	private static final long serialVersionUID = 4139505487765219961L;

	@Id
	@EqualsAndHashCode.Include
	private String codigo;

	private String nome;
	private String codigoEscola;
	private String nomeEscola;

	@Enumerated(EnumType.STRING)
	private TipoEnsino tipoEnsino;

	private Integer serie;
	private Integer serieEquivalente;
	private String codigoRegiao;
	private String regiao;

	@ManyToMany(mappedBy = "turmas")
	@JsonIgnoreProperties("turmas")
	private Set<Member> membros = new HashSet<>();
}
