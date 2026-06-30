package br.gov.joaopessoa.alura.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_administrador")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Admin implements Serializable{
	
	private static final long serialVersionUID = 7632903057262350858L;

	@Id
	@EqualsAndHashCode.Include
	private String id;

	private String nome;
	private String email;
}
