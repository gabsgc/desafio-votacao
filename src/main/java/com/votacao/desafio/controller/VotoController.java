package com.votacao.desafio.controller;

import com.votacao.desafio.controller.dto.voto.ResultadoToGetDTO;
import com.votacao.desafio.controller.dto.voto.VotoToCreateDTO;
import com.votacao.desafio.domain.Voto;
import com.votacao.desafio.service.VotoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/votos")
public class VotoController {

    private final VotoService votoService;

    public VotoController(VotoService votoService) {
        this.votoService = votoService;
    }

    @PostMapping
    public ResponseEntity<Voto> votar(@Valid @RequestBody VotoToCreateDTO votoToCreateDTO) {
        votoService.registrarVoto(votoToCreateDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/{pautaId}/resultado")
    public ResponseEntity<ResultadoToGetDTO> obterResultado(@PathVariable Long pautaId) {
        ResultadoToGetDTO resultado = votoService.contabilizarVotos(pautaId);
        return ResponseEntity.ok(resultado);
    }
}
