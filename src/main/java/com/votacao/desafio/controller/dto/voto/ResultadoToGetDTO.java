package com.votacao.desafio.controller.dto.voto;

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
public class ResultadoToGetDTO implements Serializable {

    private Long totalSim;

    private Long totalNao;

    private String statusFinal;
}
