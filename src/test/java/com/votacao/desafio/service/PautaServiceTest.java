package com.votacao.desafio.service;

import com.votacao.desafio.controller.dto.pauta.PautaToCreateDTO;
import com.votacao.desafio.controller.dto.pauta.SessaoToCreateDTO;
import com.votacao.desafio.domain.Pauta;
import com.votacao.desafio.repository.PautaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PautaServiceTest {

    @InjectMocks
    private PautaService pautaService;

    @Mock
    private PautaRepository pautaRepository;

    @Test
    void dadoUmaPauta_quandoCriar_deveRetornarPautaCriadaComSucesso() {
        String nome = "Pauta Teste";
        Pauta pauta = Pauta.builder()
                .id(1L)
                .nome(nome)
                .build();
        PautaToCreateDTO dto = PautaToCreateDTO.builder()
                .nome(nome)
                .build();

        Mockito.when(pautaRepository.save(Mockito.any(Pauta.class)))
                .thenReturn(pauta);

        Pauta resultado = pautaService.criar(dto);

        assertThat(nome)
                .isNotNull()
                .isEqualTo(resultado.getNome());
    }

    @Test
    void dadoUmaSessao_quandoAbrirSessao_deveAbrirSessaoComSucesso() {
        final long PAUTA_ID = 1L;

        Pauta pauta = Pauta.builder()
                .id(PAUTA_ID)
                .nome("Pauta Teste")
                .build();
        SessaoToCreateDTO sessaoToCreateDTO = SessaoToCreateDTO.builder()
                .duracaoMinutos(10L)
                .build();

        Mockito.when(pautaRepository.findById(PAUTA_ID))
                .thenReturn(Optional.of(pauta));

        assertDoesNotThrow(() -> pautaService.abrirSessao(PAUTA_ID, sessaoToCreateDTO));

        verify(pautaRepository).save(pauta);
    }

    @Test
    void dadoUmaSessao_quandoPautaNaoEncontrada_deveLancarExcecao() {
        Mockito.when(pautaRepository.findById(1L))
                .thenReturn(Optional.empty());

        SessaoToCreateDTO sessaoToCreateDTO = SessaoToCreateDTO.builder()
                .duracaoMinutos(10L)
                .build();

        assertThrows(EntityNotFoundException.class, () ->
                pautaService.abrirSessao(1L, sessaoToCreateDTO));
    }


    @Test
    void dadoUmaSessao_quandoSessaoJaEncerrada_deveLancarExcecao() {
        final long PAUTA_ID = 1L;
        Pauta pauta = Pauta.builder()
                .id(PAUTA_ID)
                .nome("Pauta Teste")
                .dataEncerramento(LocalDateTime.now().minusMinutes(5))
                .build();

        SessaoToCreateDTO sessaoToCreateDTO = SessaoToCreateDTO.builder()
                .duracaoMinutos(1L)
                .build();

        Mockito.when(pautaRepository.findById(PAUTA_ID))
                .thenReturn(Optional.of(pauta));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> pautaService.abrirSessao(PAUTA_ID, sessaoToCreateDTO));

        assertEquals("Essa sessão de votação já foi encerrada.", exception.getMessage());
    }

    @Test
    void dadoUmaSessao_quandoSessaoJaEstaAberta_deveLancarExcecao() {
        final long PAUTA_ID = 1L;
        Pauta pauta = Pauta.builder()
                .id(PAUTA_ID)
                .nome("Pauta Teste")
                .dataEncerramento(LocalDateTime.now().minusMinutes(10))
                .build();

        SessaoToCreateDTO sessaoToCreateDTO = SessaoToCreateDTO.builder()
                .duracaoMinutos(1L)
                .build();

        Mockito.when(pautaRepository.findById(PAUTA_ID))
                .thenReturn(Optional.of(pauta));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> pautaService.abrirSessao(PAUTA_ID, sessaoToCreateDTO));

        assertEquals("Essa sessão de votação já está aberta.", exception.getMessage());
    }
}