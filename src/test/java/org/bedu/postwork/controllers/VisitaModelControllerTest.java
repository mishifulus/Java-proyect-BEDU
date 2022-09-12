package org.bedu.postwork.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bedu.postwork.model.ClienteModel;
import org.bedu.postwork.model.VisitaModel;
import org.bedu.postwork.services.VisitaService;
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

@WebMvcTest(VisitaController.class)
public class VisitaModelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VisitaService visitaService;

    @Test
    void getVisita() throws Exception {
        BDDMockito.given(visitaService.obtenerVisita(anyLong())).willReturn(Optional.of(VisitaModel.builder().id(1L).direccion("Direccion").proposito("Proposito").vendedor("Vendedor").build()));

        mockMvc.perform(MockMvcRequestBuilders.get("/visita/1")
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.direccion", Matchers.is("Direccion")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vendedor", Matchers.is("Vendedor")));
    }

    @Test
    void getVisitas() throws Exception {

        List<VisitaModel> visitasModels = Arrays.asList(
                VisitaModel.builder().id(1L).direccion("Direccion 1").proposito("Proposito 1").vendedor("Vendedor 1").build(),
                VisitaModel.builder().id(2L).direccion("Direccion 2").proposito("Proposito 2").vendedor("Vendedor 2").build(),
                VisitaModel.builder().id(3L).direccion("Direccion 3").proposito("Proposito 3").vendedor("Vendedor 3").build()
        );

        BDDMockito.given(visitaService.listarVisitas()).willReturn(visitasModels);

        mockMvc.perform(MockMvcRequestBuilders.get("/visita")
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].vendedor", Matchers.is("Vendedor 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].direccion", Matchers.is("Direccion 3")));
    }

    @Test
    void creaVisita() throws Exception {
        ClienteModel clienteParametro = ClienteModel.builder().id(1L).nombre("Cliente").correoContacto("correoCliente@correo.com").direccion("Direccion").numeroEmpleados("10").build();
        VisitaModel visitaModelParametro = VisitaModel.builder().clienteModel(clienteParametro).direccion("Direccion 1").proposito("Proposito 1").vendedor("Vendedor 1").build();
        VisitaModel visitaModelRespuesta = VisitaModel.builder().id(1L).clienteModel(clienteParametro).direccion("Direccion 1").proposito("Proposito 1").vendedor("Vendedor 1").build();

        BDDMockito.given(visitaService.guardarVisita(visitaModelParametro)).willReturn(visitaModelRespuesta);

        mockMvc.perform(MockMvcRequestBuilders.post("/visita")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(visitaModelParametro)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void actualizaVisita() throws Exception {

        ClienteModel clienteParametro = ClienteModel.builder().id(1L).nombre("Cliente").correoContacto("correoCliente@correo.com").direccion("Direccion").numeroEmpleados("10").build();
        VisitaModel visitaModelParametro = VisitaModel.builder().id(1L).clienteModel(clienteParametro).direccion("Direccion 1").proposito("Proposito 1").vendedor("Vendedor 1").build();

        mockMvc.perform(MockMvcRequestBuilders.put("/visita/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(visitaModelParametro)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void eliminaVisita() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/visita/1")
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
