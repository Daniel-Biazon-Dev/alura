package br.gov.joaopessoa.alura.model.dto;

import br.gov.joaopessoa.alura.model.SchoolClass;
import br.gov.joaopessoa.alura.model.enumaration.TipoEnsino;

public record SchoolClassRequest(
		String codigo,
		String nome,
		String codigoEscola,
		String nomeEscola,
		TipoEnsino tipoEnsino,
		Integer serie,
		Integer serieEquivalente,
		String codigoRegiao,
		String regiao) {

	public static SchoolClassRequest from(SchoolClass schoolClass) {
		return new SchoolClassRequest(
				schoolClass.getCodigo(),
				schoolClass.getNome(),
				schoolClass.getCodigoEscola(),
				schoolClass.getNomeEscola(),
				schoolClass.getTipoEnsino(),
				schoolClass.getSerie(),
				schoolClass.getSerieEquivalente(),
				schoolClass.getCodigoRegiao(),
				schoolClass.getRegiao());
	}
}
