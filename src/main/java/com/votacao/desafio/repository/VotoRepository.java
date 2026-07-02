package com.votacao.desafio.repository;

import com.votacao.desafio.domain.Voto;
import com.votacao.desafio.domain.enumeration.OpcaoVoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotoRepository extends JpaRepository<Voto, Long> {

    long countByPautaIdAndEscolha(Long pautaId, OpcaoVoto escolha);

}
