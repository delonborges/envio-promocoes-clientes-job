package com.delon.enviopromocoesclientesjob.domain;

import lombok.Builder;

@Builder
public record InteresseProdutoCliente(Cliente cliente,
                                      Produto produto) {}
