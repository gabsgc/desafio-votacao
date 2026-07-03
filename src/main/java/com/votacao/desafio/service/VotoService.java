package com.votacao.desafio.service;

import com.votacao.desafio.controller.dto.cpf.CpfStatusToGetDTO;
import com.votacao.desafio.controller.dto.voto.ResultadoToGetDTO;
import com.votacao.desafio.controller.dto.voto.VotoToCreateDTO;
import com.votacao.desafio.domain.Pauta;
import com.votacao.desafio.domain.Voto;
import com.votacao.desafio.domain.enumeration.OpcaoVoto;
import com.votacao.desafio.repository.PautaRepository;
import com.votacao.desafio.repository.VotoRepository;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.Random;

public class VotoService {

    private final PautaRepository pautaRepository;
    private final VotoRepository votoRepository;

    public VotoService(PautaRepository pautaRepository, VotoRepository votoRepository) {
        this.pautaRepository = pautaRepository;
        this.votoRepository = votoRepository;
    }

    public void registrarVoto(VotoToCreateDTO votoToCreateDTO) {
        Pauta pauta = pautaRepository.findById(votoToCreateDTO.getPautaId())
                .orElseThrow(() -> new EntityNotFoundException("Pauta não encontrada"));

        if (pauta.getDataAbertura() == null) {
            throw new RuntimeException("Sessão de votação não foi aberta para esta pauta.");
        }

        if (LocalDateTime.now().isAfter(pauta.getDataEncerramento())) {
            throw new RuntimeException("Sessão de votação encerrada.");
        }

        OpcaoVoto opcaoEscolhida = OpcaoVoto.valueOf(votoToCreateDTO.getEscolha().toUpperCase());
        Voto voto =  Voto.builder()
                .cpfAssociado(votoToCreateDTO.getCpf())
                .escolha(opcaoEscolhida)
                .pauta(pauta)
                .build();

        votoRepository.save(voto);
    }

    public ResultadoToGetDTO contabilizarVotos(Long pautaId) {
        pautaRepository.findById(pautaId)
                .orElseThrow(() -> new EntityNotFoundException("Pauta não encontrada"));

        long totalVotosPositivos = votoRepository.countByPautaIdAndEscolha(pautaId, OpcaoVoto.SIM);
        long totalVotosNegativos = votoRepository.countByPautaIdAndEscolha(pautaId, OpcaoVoto.NAO);

        String resultado;

        if (totalVotosPositivos > totalVotosNegativos) {
            resultado = "APROVADA";
        } else if (totalVotosPositivos < totalVotosNegativos){
            resultado = "REPROVADA";
        } else {
            resultado = "EMPATE";
        }

        return ResultadoToGetDTO.builder()
                .totalSim(totalVotosPositivos)
                .totalNao(totalVotosNegativos)
                .statusFinal(resultado)
                .build();
    }

    public CpfStatusToGetDTO verificarCpf(String cpf) {
        Random random = new Random();

        if (!random.nextBoolean()) {
           return CpfStatusToGetDTO.builder()
                   .status("UNABLE_TO_VOTE")
                   .build();
        }

        return CpfStatusToGetDTO.builder()
                .status("ABLE_TO_VOTE")
                .build();
    }
}
