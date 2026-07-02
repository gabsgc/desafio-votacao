package com.votacao.desafio.controller;

import com.votacao.desafio.controller.dto.pauta.PautaToCreateDTO;
import com.votacao.desafio.controller.dto.pauta.SessaoToCreateDTO;
import com.votacao.desafio.domain.Pauta;
import com.votacao.desafio.service.PautaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pautas")
public class PautaController {

    private final PautaService pautaService;

    public PautaController(PautaService pautaService) {
        this.pautaService = pautaService;
    }

    @PostMapping
    public ResponseEntity<Pauta> criar(@Valid @RequestBody PautaToCreateDTO pautaToCreateDTO) {
        Pauta pautaCriada = pautaService.criar(pautaToCreateDTO);

        return ResponseEntity.ok(pautaCriada);
    }

    @PostMapping("/{id}/abrir-sessao")
    public ResponseEntity<Void> abrirSessao(
            @PathVariable Long id,
            @Valid @RequestBody SessaoToCreateDTO sessaoToCreateDTO
    ) {
        pautaService.abrirSessao(id, sessaoToCreateDTO);

        return ResponseEntity.noContent().build();
    }
}
