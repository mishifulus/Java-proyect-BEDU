package org.bedu.postwork.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bedu.postwork.model.ClienteModel;
import org.bedu.postwork.model.ProductoModel;
import org.bedu.postwork.model.VentaModel;
import org.bedu.postwork.services.VentaService;
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
@WebMvcTest(VentaController.class)
public class VentaModelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VentaService ventaService;

    @Test
    void getVenta() throws Exception {
        given(ventaService.obtenerVenta(anyLong())).willReturn(Optional.of(VentaModel.builder().ventaId(1L).monto(10.0F).build()));

        mockMvc.perform(get("/venta/{ventaId}", 1)
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ventaId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.monto", Matchers.is(10.0)))

                .andDo(document("venta/get-venta",
                        pathParameters(
                                parameterWithName("ventaId").description("Identificador de la venta")
                        ),
                        responseFields(
                                fieldWithPath("ventaId").description("Identificador de la venta"),
                                fieldWithPath("monto").description("Monto de la venta"),
                                fieldWithPath("productoModels").description("Productos de la venta"),
                                fieldWithPath("clienteModel").description("Cliente de la venta"),
                                fieldWithPath("fechaCreacion").description("Fecha de creacion de la venta")
                        )));
    }

    @Test
    void getVentas() throws Exception {

        List<VentaModel> ventasModels = Arrays.asList(
                VentaModel.builder().ventaId(1L).monto(10.0F).build(),
                VentaModel.builder().ventaId(2L).monto(11.0F).build(),
                VentaModel.builder().ventaId(3L).monto(12.0F).build()
        );

        given(ventaService.listarVentas()).willReturn(ventasModels);

        mockMvc.perform(get("/venta")
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].ventaId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].ventaId", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].ventaId", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].monto", Matchers.is(10.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].monto", Matchers.is(12.0)))

                .andDo(document("venta/get-ventas",
                        responseFields(
                                fieldWithPath("[].ventaId").description("Identificador de la venta"),
                                fieldWithPath("[].monto").description("Monto de la venta"),
                                fieldWithPath("[].productoModels").description("Productos de la venta"),
                                fieldWithPath("[].clienteModel").description("Cliente de la venta"),
                                fieldWithPath("[].fechaCreacion").description("Fecha de creacion de la venta")
                        )));
    }

    @Test
    void creaVenta() throws Exception {
        ClienteModel clienteParametro = ClienteModel.builder().id(1L).nombre("Cliente").correoContacto("correoCliente@correo.com").direccion("Direccion").numeroEmpleados(10).build();

        ProductoModel productoParametro1 = ProductoModel.builder().id(1L).nombre("Producto 1").categoria("Categoria 1").precio(2.00F).numeroRegistro("00001").build();
        ProductoModel productoParametro2 = ProductoModel.builder().id(2L).nombre("Producto 2").categoria("Categoria 2").precio(2.00F).numeroRegistro("00001").build();
        List<ProductoModel> listproductos = Arrays.asList(productoParametro1,productoParametro2);

        VentaModel ventaModelParametro = VentaModel.builder().monto(10.0F).clienteModel(clienteParametro).productoModels(listproductos).build();
        VentaModel ventaModelRespuesta = VentaModel.builder().ventaId(1L).monto(10.0F).build();

        given(ventaService.guardarVenta(ventaModelParametro)).willReturn(ventaModelRespuesta);

        mockMvc.perform(MockMvcRequestBuilders.post("/venta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(ventaModelParametro)))
                .andExpect(MockMvcResultMatchers.status().isCreated())

                .andDo(document("venta/crea-venta",
                        requestFields(
                                fieldWithPath("ventaId").description("Identificador de la venta"),
                                fieldWithPath("monto").description("Monto de la venta"),
                                fieldWithPath("productoModels").description("Producto de la venta"),
                                fieldWithPath("clienteModel").description("Cliente de la venta"),
                                fieldWithPath("fechaCreacion").description("Fecha de creacion de la venta"),
                                //fieldWithPath("categoria").description("Categoria del producto"),
                                //fieldWithPath("numeroRegistro").description("Numero de registro de la venta"),
                                fieldWithPath("productoModels[].id").description("Identificador del producto"),
                                fieldWithPath("productoModels[].nombre").description("Nombre del producto"),
                                fieldWithPath("productoModels[].categoria").description("Categoria del producto"),
                                fieldWithPath("productoModels[].precio").description("Precio del producto"),
                                fieldWithPath("productoModels[].numeroRegistro").description("Numero de registro del producto"),
                                fieldWithPath("productoModels[].fechaCreacion").description("Fecha de creacion del producto"),
                                fieldWithPath("clienteModel.id").description("El identificador del nuevo cliente"),
                                fieldWithPath("clienteModel.nombre").description("El nombre del cliente"),
                                fieldWithPath("clienteModel.direccion").description("La dirección del cliente"),
                                fieldWithPath("clienteModel.correoContacto").description("La dirección de correo electrónico de contacto"),
                                fieldWithPath("clienteModel.numeroEmpleados").description("El número de personas que trabajan en las oficinas e cliente")

                        ),
                        responseHeaders(
                                headerWithName("Location").description("La ubicación del recurso (su identificador generado")
                        ))
                );
    }

    @Test
    void actualizaVenta() throws Exception {

        ClienteModel clienteParametro = ClienteModel.builder().id(1L).nombre("Cliente").correoContacto("correoCliente@correo.com").direccion("Direccion").numeroEmpleados(10).build();

        ProductoModel productoParametro1 = ProductoModel.builder().id(1L).nombre("Producto 1").categoria("Categoria 1").precio(2.00F).numeroRegistro("00001").build();
        ProductoModel productoParametro2 = ProductoModel.builder().id(2L).nombre("Producto 2").categoria("Categoria 2").precio(2.00F).numeroRegistro("00001").build();
        List<ProductoModel> listproductos = Arrays.asList(productoParametro1,productoParametro2);

        VentaModel ventaModelParametro = VentaModel.builder().ventaId(1L).monto(10.0F).clienteModel(clienteParametro).productoModels(listproductos).build();
        //VentaModel ventaModelParametro = VentaModel.builder().ventaId(1L).monto(10.0F).build();

        mockMvc.perform(put("/venta/{ventaId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(ventaModelParametro)))
                .andExpect(MockMvcResultMatchers.status().isOk())

                .andDo(document("venta/actualiza-venta",
                        pathParameters(
                                parameterWithName("ventaId").description("Identificador de la venta")
                        ),
                        requestFields(
                                fieldWithPath("ventaId").description("Identificador de la venta"),
                                fieldWithPath("monto").description("Monto de la venta"),
                                fieldWithPath("productoModels").description("Producto de la venta"),
                                fieldWithPath("clienteModel").description("Cliente de la venta"),
                                fieldWithPath("fechaCreacion").description("Fecha de creacion de la venta"),
                                //fieldWithPath("categoria").description("Categoria del producto"),
                                //fieldWithPath("numeroRegistro").description("Numero de registro de la venta"),
                                fieldWithPath("productoModels[].id").description("Identificador del producto"),
                                fieldWithPath("productoModels[].nombre").description("Nombre del producto"),
                                fieldWithPath("productoModels[].categoria").description("Categoria del producto"),
                                fieldWithPath("productoModels[].precio").description("Precio del producto"),
                                fieldWithPath("productoModels[].numeroRegistro").description("Numero de registro del producto"),
                                fieldWithPath("productoModels[].fechaCreacion").description("Fecha de creacion del producto"),
                                fieldWithPath("clienteModel.id").description("El identificador del nuevo cliente"),
                                fieldWithPath("clienteModel.nombre").description("El nombre del cliente"),
                                fieldWithPath("clienteModel.direccion").description("La dirección del cliente"),
                                fieldWithPath("clienteModel.correoContacto").description("La dirección de correo electrónico de contacto"),
                                fieldWithPath("clienteModel.numeroEmpleados").description("El número de personas que trabajan en las oficinas e cliente")

                        )
                ));
    }

    @Test
    void eliminaVenta() throws Exception {
        mockMvc.perform(delete("/venta/{ventaId}", 1)
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())

                .andDo(document("venta/elimina-venta",
                        pathParameters(
                                parameterWithName("ventaId").description("Identificador de la venta")
                        )));
    }
}
