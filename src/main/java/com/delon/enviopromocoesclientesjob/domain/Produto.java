package com.delon.enviopromocoesclientesjob.domain;

import lombok.Builder;

@Builder
public record Produto(Integer id,
                      String nome,
                      String descricao,
                      Double preco) {}
