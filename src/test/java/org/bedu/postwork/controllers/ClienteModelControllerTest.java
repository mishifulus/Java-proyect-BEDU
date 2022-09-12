package org.bedu.postwork.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bedu.postwork.model.ClienteModel;
import org.bedu.postwork.services.ClienteService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
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
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

@AutoConfigureRestDocs
@WebMvcTest(ClienteController.class)
public class ClienteModelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Test
    void getCliente() throws Exception {
        BDDMockito.given(clienteService.obtenerCliente(anyLong())).willReturn(
                Optional.of(ClienteModel.builder().id(1L).nombre("Nombre").correoContacto("cliente@contacto.com").build()));

        mockMvc.perform(MockMvcRequestBuilders.get("/cliente/1")
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.correoContacto", Matchers.is("cliente@contacto.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre", Matchers.is("Nombre")));

                /*.andDo(document("cliente/get-cliente",
                        pathParameters(
                                parameterWithName("clienteId").description("Identificador del cliente")
                        )));*/
    }

    @Test
    void getClientes() throws Exception {

        List<ClienteModel> clientesModels = Arrays.asList(
                ClienteModel.builder().id(1L).nombre("Ciente 1").direccion("Direccion 1").numeroEmpleados("10").correoContacto("contacto@cliente1.com").build(),
                ClienteModel.builder().id(2L).nombre("Ciente 2").direccion("Direccion 2").numeroEmpleados("10").correoContacto("contacto@cliente2.com").build(),
                ClienteModel.builder().id(3L).nombre("Ciente 3").direccion("Direccion 3").numeroEmpleados("10").correoContacto("contacto@cliente3.com").build()
        );

        BDDMockito.given(clienteService.listarClientes()).willReturn(clientesModels);

        mockMvc.perform(MockMvcRequestBuilders.get("/cliente")
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].correoContacto", Matchers.is("contacto@cliente1.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].nombre", Matchers.is("Ciente 3")));
    }

    @Test
    void creaCliente() throws Exception {
        ClienteModel clienteModelParametro = ClienteModel.builder().nombre("Nombre").direccion("Direccion").numeroEmpleados("10").correoContacto("contacto@cliente.com").build();
        ClienteModel clienteModelRespuesta = ClienteModel.builder().id(1L).nombre("Nombre").direccion("Direccion").numeroEmpleados("10").correoContacto("contacto@cliente.com").build();

        BDDMockito.given(clienteService.guardarCliente(clienteModelParametro)).willReturn(clienteModelRespuesta);

        mockMvc.perform(MockMvcRequestBuilders.post("/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(clienteModelParametro)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void actualizaCliente() throws Exception {

        ClienteModel clienteModelParametro = ClienteModel.builder().id(1L).nombre("Nombre").direccion("Direccion").numeroEmpleados("10").correoContacto("contacto@cliente.com").build();

        mockMvc.perform(MockMvcRequestBuilders.put("/cliente/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(clienteModelParametro)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void eliminaCliente() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/cliente/1")
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}