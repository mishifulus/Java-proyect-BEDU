package org.bedu.postwork.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bedu.postwork.model.EtapaModel;
import org.bedu.postwork.services.EtapaService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;

@WebMvcTest(EtapaController.class)
public class EtapaModelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EtapaService etapaService;

    @Test
    void getEtapa() throws Exception {
        BDDMockito.given(etapaService.obtenerEtapa(anyLong())).willReturn(
                Optional.of(EtapaModel.builder().etapaId(1L).nombre("Etapa").orden(1).build()));

        mockMvc.perform(MockMvcRequestBuilders.get("/etapa/1")
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.etapaId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre", Matchers.is("Etapa")));
    }

    @Test
    void getEtapas() throws Exception {

        List<EtapaModel> etapasModels = Arrays.asList(
                EtapaModel.builder().etapaId(1L).nombre("Etapa 1").orden(1).build(),
                EtapaModel.builder().etapaId(2L).nombre("Etapa 2").orden(2).build(),
                EtapaModel.builder().etapaId(3L).nombre("Etapa 3").orden(3).build()
        );

        BDDMockito.given(etapaService.listarEtapas()).willReturn(etapasModels);

        mockMvc.perform(MockMvcRequestBuilders.get("/etapa")
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].etapaId", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].etapaId", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].orden", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].nombre", Matchers.is("Etapa 3")));
    }

    @Test
    void creaEtapa() throws Exception {
        EtapaModel etapaModelParametro = EtapaModel.builder().nombre("Etapa").orden(1).build();
        EtapaModel etapaModelRespuesta = EtapaModel.builder().etapaId(1L).nombre("Etapa").orden(1).build();

        BDDMockito.given(etapaService.guardarEtapa(etapaModelParametro)).willReturn(etapaModelRespuesta);

        mockMvc.perform(MockMvcRequestBuilders.post("/etapa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(etapaModelParametro)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void actualizaEtapa() throws Exception {

        EtapaModel etapaModelParametro = EtapaModel.builder().etapaId(1L).nombre("Etapa 1").orden(1).build();

        mockMvc.perform(MockMvcRequestBuilders.put("/etapa/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(etapaModelParametro)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void eliminaEtapa() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/etapa/1")
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
