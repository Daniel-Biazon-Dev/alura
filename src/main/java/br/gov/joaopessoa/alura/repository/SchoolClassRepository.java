package br.gov.joaopessoa.alura.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.joaopessoa.alura.model.SchoolClass;

public interface SchoolClassRepository extends JpaRepository<SchoolClass, String> {
}
