package br.gov.joaopessoa.alura.model.dto;

import java.util.List;

public record AluraListResponse<T>(
        List<T> conteudo,
        Integer totalPaginas,
        Integer totalElementos,
        Integer paginaAtual,
        Integer elementosPorPagina) {
}