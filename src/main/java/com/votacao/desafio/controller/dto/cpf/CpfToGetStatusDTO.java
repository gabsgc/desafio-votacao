package com.votacao.desafio.controller.dto.cpf;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CpfToGetStatusDTO implements Serializable {

    @NotBlank(message = "O CPF é obrigatório")
    @CPF(message = "COF inválido")
    private String cpfAssociado;

}
