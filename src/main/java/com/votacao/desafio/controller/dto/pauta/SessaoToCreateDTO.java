package com.votacao.desafio.controller.dto.pauta;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SessaoToCreateDTO implements Serializable {

    @NotNull
    private Long duracaoMinutos;
}
