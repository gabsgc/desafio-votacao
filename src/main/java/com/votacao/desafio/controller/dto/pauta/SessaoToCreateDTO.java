package com.votacao.desafio.controller.dto.pauta;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessaoToCreateDTO implements Serializable {

    @NotNull
    private Long duracaoMinutos;
}
