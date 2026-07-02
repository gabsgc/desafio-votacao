package com.votacao.desafio.controller.dto.voto;

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
public class ResultadoToGetDTO implements Serializable {

    private Long totalSim;

    private Long totalNao;

    private String statusFinal;
}
