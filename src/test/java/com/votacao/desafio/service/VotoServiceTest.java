package com.votacao.desafio.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.Optional;

import com.votacao.desafio.controller.dto.voto.ResultadoToGetDTO;
import com.votacao.desafio.controller.dto.voto.VotoToCreateDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.votacao.desafio.domain.Pauta;
import com.votacao.desafio.domain.enumeration.OpcaoVoto;
import com.votacao.desafio.repository.PautaRepository;
import com.votacao.desafio.repository.VotoRepository;

@ExtendWith(MockitoExtension.class)
class VotoServiceTest {

    @InjectMocks
    private VotoService votoService;

    @Mock
    private PautaRepository pautaRepository;

    @Mock
    private VotoRepository votoRepository;

    @Test
    void dadoUmVoto_quandoRegistrarVoto_DeveSalvarVotoComSucesso() {
        final long PAUTA_ID = 1L;

        Pauta pauta = Pauta.builder()
                .id(PAUTA_ID)
                .dataEncerramento(LocalDateTime.now().plusMinutes(10))
                .build();

        VotoToCreateDTO votoToCreateDTO = VotoToCreateDTO.builder()
                .pautaId(PAUTA_ID)
                .cpf("12345678901")
                .escolha("SIM")
                .build();

        Mockito.when(pautaRepository.findById(PAUTA_ID))
                .thenReturn(Optional.of(pauta));

        assertDoesNotThrow(() -> votoService.registrarVoto(votoToCreateDTO));
    }

    @Test
    void dadoUmVoto_quandoSessaoEncerrada_deveLancarExcecao() {
        final long PAUTA_ID = 1L;
        Pauta pauta = Pauta.builder()
                .id(PAUTA_ID)
                .dataEncerramento(LocalDateTime.now().plusMinutes(5))
                .build();

        VotoToCreateDTO votoToCreateDTO = VotoToCreateDTO.builder()
                .pautaId(PAUTA_ID)
                .cpf("12345678901")
                .escolha("SIM")
                .build();

        Mockito.when(pautaRepository.findById(PAUTA_ID))
                .thenReturn(Optional.of(pauta));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> votoService.registrarVoto(votoToCreateDTO));

        assertEquals("Sessão de votação encerrada.", ex.getMessage());
    }

    @Test
    void dadoVotos_quandoContabilizarVotos_DeveRetornarResultadoCorreto() {
        final long PAUTA_ID = 1L;
        Pauta pauta = Pauta.builder()
                .id(PAUTA_ID)
                .dataEncerramento(LocalDateTime.now().plusMinutes(5))
                .build();

        Mockito.when(pautaRepository.findById(PAUTA_ID))
                .thenReturn(Optional.of(pauta));

        Mockito.when(votoRepository.countByPautaIdAndEscolha(PAUTA_ID, OpcaoVoto.SIM))
                .thenReturn(5L);
        Mockito.when(votoRepository.countByPautaIdAndEscolha(PAUTA_ID, OpcaoVoto.NAO))
                .thenReturn(3L);

        ResultadoToGetDTO resultado = votoService.contabilizarVotos(PAUTA_ID);

        assertThat(resultado.getStatusFinal()).isEqualTo("APROVADA");
        assertThat(resultado.getTotalSim()).isEqualTo(5);
        assertThat(resultado.getTotalNao()).isEqualTo(3);
    }
}