package org.bedu.postwork.controllers;

import org.bedu.postwork.model.ClienteModel;
import org.bedu.postwork.services.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@WebMvcTest(ClienteController.class)
public class ClienteModelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Test
    void getCliente() throws Exception {
        given(clienteService.obtenerCliente(anyLong())).willReturn(
                Optional.of(ClienteModel.builder().id(1L).nombre("Nombre").correoContacto("cliente@contacto.com").build()));

        mockMvc.perform(get("/cliente/{clienteId}", 1)
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.correoContacto", is("cliente@contacto.com")))
                .andExpect(jsonPath("$.nombre", is("Nombre")))

                .andDo(document("cliente/get-cliente",
                        pathParameters(
                                parameterWithName("clienteId").description("Identificador del cliente")
                        ),
                        responseFields(
                                fieldWithPath("id").description("Identificador del cliente"),
                                fieldWithPath("nombre").description("Nombre del cliente"),
                                fieldWithPath("correoContacto").description("Correo de contacto del cliente"),
                                fieldWithPath("numeroEmpleados").description("Número de trabajadores del cliente"),
                                fieldWithPath("direccion").description("Domicilio del cliente")
                        )));
    }

    @Test
    void getClientes() throws Exception {

        List<ClienteModel> clientesModels = Arrays.asList(
                ClienteModel.builder().id(1L).nombre("Ciente 1").direccion("Direccion 1").numeroEmpleados(10).correoContacto("contacto@cliente1.com").build(),
                ClienteModel.builder().id(2L).nombre("Ciente 2").direccion("Direccion 2").numeroEmpleados(10).correoContacto("contacto@cliente2.com").build(),
                ClienteModel.builder().id(3L).nombre("Ciente 3").direccion("Direccion 3").numeroEmpleados(10).correoContacto("contacto@cliente3.com").build()
        );

        given(clienteService.listarClientes()).willReturn(clientesModels);

        mockMvc.perform(MockMvcRequestBuilders.get("/cliente")
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].correoContacto", Matchers.is("contacto@cliente1.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].nombre", Matchers.is("Ciente 3")))

                .andDo(document("cliente/get-clientes",
                        responseFields(
                                fieldWithPath("[].id").description("Identificador del cliente"),
                                fieldWithPath("[].nombre").description("Nombre del cliente"),
                                fieldWithPath("[].correoContacto").description("Correo de contacto del cliente"),
                                fieldWithPath("[].numeroEmpleados").description("Número de trabajadores del cliente"),
                                fieldWithPath("[].direccion").description("Domicilio del cliente")
                        )));
    }

    @Test
    void creaCliente() throws Exception {
        ClienteModel clienteModelParametro = ClienteModel.builder().nombre("Nombre").direccion("Direccion").numeroEmpleados(10).correoContacto("contacto@cliente.com").build();
        ClienteModel clienteModelRespuesta = ClienteModel.builder().id(1L).nombre("Nombre").direccion("Direccion").numeroEmpleados(10).correoContacto("contacto@cliente.com").build();

        given(clienteService.guardarCliente(clienteModelParametro)).willReturn(clienteModelRespuesta);

        mockMvc.perform(MockMvcRequestBuilders.post("/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(clienteModelParametro)))
                .andExpect(MockMvcResultMatchers.status().isCreated())

                .andDo(document("cliente/crea-cliente",
                        requestFields(
                                fieldWithPath("id").description("El identificador del nuevo cliente"),
                                fieldWithPath("nombre").description("El nombre del cliente"),
                                fieldWithPath("direccion").description("La dirección del cliente"),
                                fieldWithPath("correoContacto").description("La dirección de correo electrónico de contacto"),
                                fieldWithPath("numeroEmpleados").description("El número de personas que trabajan en las oficinas e cliente")
                        ),
                        responseHeaders(
                                headerWithName("Location").description("La ubicación del recurso (su identificador generado")
                        ))
                );
    }

    @Test
    void actualizaCliente() throws Exception {

        ClienteModel clienteModelParametro = ClienteModel.builder().id(1L).nombre("Nombre").direccion("Direccion").numeroEmpleados(10).correoContacto("contacto@cliente.com").build();

        mockMvc.perform(put("/cliente/{clienteId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(clienteModelParametro)))
                .andExpect(MockMvcResultMatchers.status().isOk())

                .andDo(document("cliente/actualiza-cliente",
                        pathParameters(
                                parameterWithName("clienteId").description("Identificador del cliente")
                        ),
                        requestFields(
                                fieldWithPath("id").description("El identificador del nuevo cliente"),
                                fieldWithPath("nombre").description("El nombre del cliente"),
                                fieldWithPath("direccion").description("La dirección del cliente"),
                                fieldWithPath("correoContacto").description("La dirección de correo electrónico de contacto"),
                                fieldWithPath("numeroEmpleados").description("El número de personas que trabajan en las oficinas e cliente")
                        )
                ));
    }

    @Test
    void eliminaCliente() throws Exception {
        mockMvc.perform(delete("/cliente/{clienteId}", 1)
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())

                .andDo(document("cliente/elimina-cliente",
                        pathParameters(
                                parameterWithName("clienteId").description("Identificador del cliente")
                        )));
    }

}
