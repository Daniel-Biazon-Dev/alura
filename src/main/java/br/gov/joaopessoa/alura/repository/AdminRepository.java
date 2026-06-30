package br.gov.joaopessoa.alura.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.joaopessoa.alura.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, String> {
}
