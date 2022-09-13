package org.bedu.postwork.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bedu.postwork.model.ProductoModel;
import org.bedu.postwork.services.ProductoService;
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

import java.time.LocalDate;
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
@WebMvcTest(ProductoController.class)
public class ProductoModelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @Test
    void getProducto() throws Exception {
        given(productoService.obtenerProducto(anyLong())).willReturn(Optional.of(ProductoModel.builder().id(1L).nombre("Producto").categoria("Categoria").precio(2.00F).numeroRegistro("00001").fechaCreacion(LocalDate.now()).build()));

        mockMvc.perform(get("/producto/{productoId}", 1)
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.precio", Matchers.is(2.00)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre", Matchers.is("Producto")))

                .andDo(document("producto/get-producto",
                        pathParameters(
                                parameterWithName("productoId").description("Identificador del producto")
                        ),
                        responseFields(
                                fieldWithPath("id").description("Identificador del producto"),
                                fieldWithPath("nombre").description("Nombre del producto"),
                                fieldWithPath("categoria").description("Categoria del producto"),
                                fieldWithPath("precio").description("Precio del producto"),
                                fieldWithPath("numeroRegistro").description("Numero de registro del producto"),
                                fieldWithPath("fechaCreacion").description("Fecha de creacion del producto")
                        )));
    }

    @Test
    void getProductos() throws Exception {

        List<ProductoModel> productosModels = Arrays.asList(
                ProductoModel.builder().id(1L).nombre("Producto 1").categoria("Categoria 1").precio(2.00F).numeroRegistro("00001").fechaCreacion(LocalDate.now()).build(),
                ProductoModel.builder().id(2L).nombre("Producto 2").categoria("Categoria 2").precio(3.00F).numeroRegistro("00001").fechaCreacion(LocalDate.now()).build(),
                ProductoModel.builder().id(3L).nombre("Producto 3").categoria("Categoria 3").precio(4.00F).numeroRegistro("00001").fechaCreacion(LocalDate.now()).build()
        );

        given(productoService.listarProductos()).willReturn(productosModels);

        mockMvc.perform(get("/producto")
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].precio", Matchers.is(2.00)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].nombre", Matchers.is("Producto 3")))

                .andDo(document("producto/get-productos",
                        responseFields(
                                fieldWithPath("[].id").description("Identificador del producto"),
                                fieldWithPath("[].nombre").description("Nombre del producto"),
                                fieldWithPath("[].categoria").description("Categoria del producto"),
                                fieldWithPath("[].precio").description("Precio del producto"),
                                fieldWithPath("[].numeroRegistro").description("Numero de registro del producto"),
                                fieldWithPath("[].fechaCreacion").description("Fecha de creacion del producto")
                        )));
    }

    @Test
    void creaProducto() throws Exception {
        ProductoModel productoModelParametro = ProductoModel.builder().nombre("Producto").categoria("Categoria").precio(2.00F).numeroRegistro("00001").build();
        ProductoModel productoModelRespuesta = ProductoModel.builder().id(1L).nombre("Producto").categoria("Categoria").precio(2.00F).numeroRegistro("00001").build();

        given(productoService.guardarProducto(productoModelParametro)).willReturn(productoModelRespuesta);

        mockMvc.perform(post("/producto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productoModelParametro)))
                .andExpect(MockMvcResultMatchers.status().isCreated())

                .andDo(document("producto/crea-producto",
                        requestFields(
                                fieldWithPath("id").description("Identificador del producto"),
                                fieldWithPath("nombre").description("Nombre del producto"),
                                fieldWithPath("categoria").description("Categoria del producto"),
                                fieldWithPath("precio").description("Precio del producto"),
                                fieldWithPath("numeroRegistro").description("Numero de registro del producto"),
                                fieldWithPath("fechaCreacion").description("Fecha de creacion del producto")
                        ),
                        responseHeaders(
                                headerWithName("Location").description("La ubicación del recurso (su identificador generado")
                        ))
                );
    }

    @Test
    void actualizaproducto() throws Exception {

        ProductoModel productoModelParametro = ProductoModel.builder().id(1L).nombre("Producto").categoria("Categoria").precio(2.00F).numeroRegistro("00001").build();

        mockMvc.perform(put("/producto/{productoId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productoModelParametro)))
                .andExpect(MockMvcResultMatchers.status().isOk())

                .andDo(document("producto/actualiza-producto",
                        pathParameters(
                                parameterWithName("productoId").description("Identificador del producto")
                        ),
                        requestFields(
                                fieldWithPath("id").description("Identificador del producto"),
                                fieldWithPath("nombre").description("Nombre del producto"),
                                fieldWithPath("categoria").description("Categoria del producto"),
                                fieldWithPath("precio").description("Precio del producto"),
                                fieldWithPath("numeroRegistro").description("Numero de registro del producto"),
                                fieldWithPath("fechaCreacion").description("Fecha de creacion del producto")
                        )
                ));
    }

    @Test
    void eliminaproducto() throws Exception {
        mockMvc.perform(delete("/producto/{productoId}", 1)
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())

                .andDo(document("producto/elimina-producto",
                        pathParameters(
                                parameterWithName("productoId").description("Identificador del producto")
                        )));
    }
}
