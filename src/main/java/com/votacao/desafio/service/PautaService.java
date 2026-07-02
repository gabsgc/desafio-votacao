package com.votacao.desafio.service;

import com.votacao.desafio.controller.dto.pauta.PautaToCreateDTO;
import com.votacao.desafio.controller.dto.pauta.SessaoToCreateDTO;
import com.votacao.desafio.domain.Pauta;
import com.votacao.desafio.repository.PautaRepository;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;

public class PautaService {

    private final PautaRepository pautaRepository;

    public PautaService(PautaRepository pautaRepository) {
        this.pautaRepository = pautaRepository;
    }

    public Pauta criar(PautaToCreateDTO pautaToCreateDTO) {
        Pauta pauta = Pauta.builder()
                .nome(pautaToCreateDTO.getNome())
                .build();

        return pautaRepository.save(pauta);
    }

    public void abrirSessao(Long id, SessaoToCreateDTO sessaoToCreateDTO) {
        Pauta pauta = pautaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pauta não encontrada"));

        LocalDateTime dataHoraAtual = LocalDateTime.now();
        LocalDateTime dataEncerramento = pauta.getDataEncerramento();

        if (dataEncerramento != null) {
            if (dataHoraAtual.isAfter(dataEncerramento)) {
                throw new RuntimeException("Essa sessão de votação já foi encerrada.");
            }
            throw new RuntimeException("Essa sessão de votação já está aberta.");
        }

        pauta.setDataAbertura(dataHoraAtual);

        long duracao = sessaoToCreateDTO.getDuracaoMinutos() != null
                ? sessaoToCreateDTO.getDuracaoMinutos()
                : 1L;

        LocalDateTime dataAbertura = pauta.getDataAbertura();
        pauta.setDataEncerramento(dataAbertura.plusMinutes(duracao));

        pautaRepository.save(pauta);
    }
}
