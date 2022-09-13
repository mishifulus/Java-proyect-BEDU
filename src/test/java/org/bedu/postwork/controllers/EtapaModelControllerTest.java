package org.bedu.postwork.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bedu.postwork.model.EtapaModel;
import org.bedu.postwork.services.EtapaService;
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
@WebMvcTest(EtapaController.class)
public class EtapaModelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EtapaService etapaService;

    @Test
    void getEtapa() throws Exception {
        given(etapaService.obtenerEtapa(anyLong())).willReturn(
                Optional.of(EtapaModel.builder().etapaId(1).nombre("Etapa").orden(1).build()));

        mockMvc.perform(get("/etapa/{etapaId}", 1)
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.etapaId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre", Matchers.is("Etapa")))

                .andDo(document("etapa/get-etapa",
                        pathParameters(
                                parameterWithName("etapaId").description("Identificador de la etapa")
                        ),
                        responseFields(
                                fieldWithPath("etapaId").description("Identificador de la etapa"),
                                fieldWithPath("nombre").description("Nombre de la etapa"),
                                fieldWithPath("orden").description("Orden de la etapa")
                        )));
    }

    @Test
    void getEtapas() throws Exception {

        List<EtapaModel> etapasModels = Arrays.asList(
                EtapaModel.builder().etapaId(1L).nombre("Etapa 1").orden(1).build(),
                EtapaModel.builder().etapaId(2L).nombre("Etapa 2").orden(2).build(),
                EtapaModel.builder().etapaId(3L).nombre("Etapa 3").orden(3).build()
        );

        given(etapaService.listarEtapas()).willReturn(etapasModels);

        mockMvc.perform(get("/etapa")
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].etapaId", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].etapaId", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].orden", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].nombre", Matchers.is("Etapa 3")))

                .andDo(document("etapa/get-etapas",
                        responseFields(
                                fieldWithPath("[].etapaId").description("Identificador de la etapa"),
                                fieldWithPath("[].nombre").description("Nombre de la etapa"),
                                fieldWithPath("[].orden").description("Orden de la etapa")
                        )));
    }

    @Test
    void creaEtapa() throws Exception {
        EtapaModel etapaModelParametro = EtapaModel.builder().nombre("Etapa").orden(1).build();
        EtapaModel etapaModelRespuesta = EtapaModel.builder().etapaId(1L).nombre("Etapa").orden(1).build();

        given(etapaService.guardarEtapa(etapaModelParametro)).willReturn(etapaModelRespuesta);

        mockMvc.perform(post("/etapa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(etapaModelParametro)))
                .andExpect(MockMvcResultMatchers.status().isCreated())

                .andDo(document("etapa/crea-etapa",
                        requestFields(
                                fieldWithPath("etapaId").description("Identificador de la etapa"),
                                fieldWithPath("nombre").description("Nombre de la etapa"),
                                fieldWithPath("orden").description("Orden de la etapa")
                        ),
                        responseHeaders(
                                headerWithName("Location").description("La ubicación del recurso (su identificador generado")
                        ))
                );
    }

    @Test
    void actualizaEtapa() throws Exception {

        EtapaModel etapaModelParametro = EtapaModel.builder().etapaId(1L).nombre("Etapa 1").orden(1).build();

        mockMvc.perform(put("/etapa/{etapaId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(etapaModelParametro)))
                .andExpect(MockMvcResultMatchers.status().isOk())

                .andDo(document("etapa/actualiza-etapa",
                        pathParameters(
                                parameterWithName("etapaId").description("Identificador de la etapa")
                        ),
                        requestFields(
                                fieldWithPath("etapaId").description("Identificador de la etapa"),
                                fieldWithPath("nombre").description("Nombre de la etapa"),
                                fieldWithPath("orden").description("Orden de la etapa")
                        )
                ));
    }

    @Test
    void eliminaEtapa() throws Exception {
        mockMvc.perform(delete("/etapa/{etapaId}", 1)
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())

                .andDo(document("etapa/elimina-cliente",
                        pathParameters(
                                parameterWithName("etapaId").description("Identificador de la etapa")
                        )));

    }
}
