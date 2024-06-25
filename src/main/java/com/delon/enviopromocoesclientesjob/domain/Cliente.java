package com.delon.enviopromocoesclientesjob.domain;

import lombok.Builder;

@Builder
public record Cliente(Integer id,
                      String nome,
                      String email) {}
