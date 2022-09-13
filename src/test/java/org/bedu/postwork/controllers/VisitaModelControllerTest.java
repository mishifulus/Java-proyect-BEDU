package org.bedu.postwork.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bedu.postwork.model.ClienteModel;
import org.bedu.postwork.model.VisitaModel;
import org.bedu.postwork.services.VisitaService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@WebMvcTest(VisitaController.class)
public class VisitaModelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VisitaService visitaService;

    @Test
    void getVisita() throws Exception {
        given(visitaService.obtenerVisita(anyLong())).willReturn(Optional.of(VisitaModel.builder().id(1L).direccion("Direccion").proposito("Proposito").vendedor("Vendedor").build()));

        mockMvc.perform(get("/visita/{visitaId}", 1)
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.direccion", Matchers.is("Direccion")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vendedor", Matchers.is("Vendedor")))

                .andDo(document("visita/get-visita",
                        pathParameters(
                                parameterWithName("visitaId").description("Identificador de la visita")
                        ),
                        responseFields(
                                fieldWithPath("id").description("Identificador de la visita"),
                                fieldWithPath("proposito").description("Proposito de la visita"),
                                fieldWithPath("direccion").description("Direccon de la visita"),
                                fieldWithPath("vendedor").description("Vendedor de la visita"),
                                fieldWithPath("clienteModel").description("Cliente de la visita"),
                                fieldWithPath("fechaProgramada").description("Fecha programada de la visita")
                        )));
    }

    @Test
    void getVisitas() throws Exception {

        List<VisitaModel> visitasModels = Arrays.asList(
                VisitaModel.builder().id(1L).direccion("Direccion 1").proposito("Proposito 1").vendedor("Vendedor 1").build(),
                VisitaModel.builder().id(2L).direccion("Direccion 2").proposito("Proposito 2").vendedor("Vendedor 2").build(),
                VisitaModel.builder().id(3L).direccion("Direccion 3").proposito("Proposito 3").vendedor("Vendedor 3").build()
        );

        given(visitaService.listarVisitas()).willReturn(visitasModels);

        mockMvc.perform(get("/visita")
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].vendedor", Matchers.is("Vendedor 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].direccion", Matchers.is("Direccion 3")))

                .andDo(document("visita/get-visitas",
                        responseFields(
                                fieldWithPath("[].id").description("Identificador de la visita"),
                                fieldWithPath("[].proposito").description("Proposito de la visita"),
                                fieldWithPath("[].direccion").description("Direccon de la visita"),
                                fieldWithPath("[].vendedor").description("Vendedor de la visita"),
                                fieldWithPath("[].clienteModel").description("Cliente de la visita"),
                                fieldWithPath("[].fechaProgramada").description("Fecha programada de la visita")
                        )));
    }

    @Test
    void creaVisita() throws Exception {
        ClienteModel clienteParametro = ClienteModel.builder().id(1L).nombre("Cliente").correoContacto("correoCliente@correo.com").direccion("Direccion").numeroEmpleados(10).build();
        VisitaModel visitaModelParametro = VisitaModel.builder().clienteModel(clienteParametro).direccion("Direccion 1").proposito("Proposito 1").vendedor("Vendedor 1").build();
        VisitaModel visitaModelRespuesta = VisitaModel.builder().id(1L).clienteModel(clienteParametro).direccion("Direccion 1").proposito("Proposito 1").vendedor("Vendedor 1").build();

        given(visitaService.guardarVisita(visitaModelParametro)).willReturn(visitaModelRespuesta);

        mockMvc.perform(post("/visita")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(visitaModelParametro)))
                .andExpect(MockMvcResultMatchers.status().isCreated())

                .andDo(document("visita/crea-visita",
                        requestFields(
                                fieldWithPath("id").description("Identificador de la visita"),
                                fieldWithPath("proposito").description("Proposito de la visita"),
                                fieldWithPath("direccion").description("Direccon de la visita"),
                                fieldWithPath("vendedor").description("Vendedor de la visita"),
                                fieldWithPath("clienteModel").description("Cliente de la visita"),
                                fieldWithPath("fechaProgramada").description("Fecha programada de la visita"),
                                fieldWithPath("clienteModel.id").description("El identificador del nuevo cliente"),
                                fieldWithPath("clienteModel.nombre").description("El nombre del cliente"),
                                fieldWithPath("clienteModel.direccion").description("La dirección del cliente"),
                                fieldWithPath("clienteModel.correoContacto").description("La dirección de correo electrónico de contacto"),
                                fieldWithPath("clienteModel.numeroEmpleados").description("El número de personas que trabajan en las oficinas del cliente")

                        ),
                        responseHeaders(
                                headerWithName("Location").description("La ubicación del recurso (su identificador generado")
                        ))
                );
    }

    @Test
    void actualizaVisita() throws Exception {

        ClienteModel clienteParametro = ClienteModel.builder().id(1L).nombre("Cliente").correoContacto("correoCliente@correo.com").direccion("Direccion").numeroEmpleados(10).build();
        VisitaModel visitaModelParametro = VisitaModel.builder().id(1L).clienteModel(clienteParametro).direccion("Direccion 1").proposito("Proposito 1").vendedor("Vendedor 1").build();

        mockMvc.perform(put("/visita/{visitaId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(visitaModelParametro)))
                .andExpect(MockMvcResultMatchers.status().isOk())

                .andDo(document("visita/actualiza-visita",
                        pathParameters(
                                parameterWithName("visitaId").description("Identificador de la visita")
                        ),
                        requestFields(
                                fieldWithPath("id").description("Identificador de la visita"),
                                fieldWithPath("proposito").description("Proposito de la visita"),
                                fieldWithPath("direccion").description("Direccon de la visita"),
                                fieldWithPath("vendedor").description("Vendedor de la visita"),
                                fieldWithPath("clienteModel").description("Cliente de la visita"),
                                fieldWithPath("fechaProgramada").description("Fecha programada de la visita"),
                                fieldWithPath("clienteModel.id").description("El identificador del nuevo cliente"),
                                fieldWithPath("clienteModel.nombre").description("El nombre del cliente"),
                                fieldWithPath("clienteModel.direccion").description("La dirección del cliente"),
                                fieldWithPath("clienteModel.correoContacto").description("La dirección de correo electrónico de contacto"),
                                fieldWithPath("clienteModel.numeroEmpleados").description("El número de personas que trabajan en las oficinas del cliente")
                        )
                ));
    }

    @Test
    void eliminaVisita() throws Exception {
        mockMvc.perform(delete("/visita/{visitaId}", 1)
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())

                .andDo(document("visita/elimina-visita",
                        pathParameters(
                                parameterWithName("visitaId").description("Identificador de la visita")
                        )));
    }
}
