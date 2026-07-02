package com.votacao.desafio.domain;

import com.votacao.desafio.domain.enumeration.OpcaoVoto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "voto", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"pauta_id", "cpf_associado"})
})
public class Voto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cpf_associado")
    private String cpfAssociado;

    @Enumerated(EnumType.STRING)
    @Column(name = "escolha")
    private OpcaoVoto escolha;

    @ManyToOne
    private Pauta pauta;

}
