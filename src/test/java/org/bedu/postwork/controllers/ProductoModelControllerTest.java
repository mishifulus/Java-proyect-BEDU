package org.bedu.postwork.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bedu.postwork.model.ProductoModel;
import org.bedu.postwork.services.ProductoService;
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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;

@WebMvcTest(ProductoController.class)
public class ProductoModelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @Test
    void getProducto() throws Exception {
        BDDMockito.given(productoService.obtenerProducto(anyLong())).willReturn(Optional.of(ProductoModel.builder().id(1L).nombre("Producto").categoria("Categoria").precio(2.00F).numeroRegistro("00001").fechaCreacion(LocalDate.now()).build()));

        mockMvc.perform(MockMvcRequestBuilders.get("/producto/1")
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.precio", Matchers.is(2.00)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre", Matchers.is("Producto")));
    }

    @Test
    void getProductos() throws Exception {

        List<ProductoModel> productosModels = Arrays.asList(
                ProductoModel.builder().id(1L).nombre("Producto 1").categoria("Categoria 1").precio(2.00F).numeroRegistro("00001").fechaCreacion(LocalDate.now()).build(),
                ProductoModel.builder().id(2L).nombre("Producto 2").categoria("Categoria 2").precio(3.00F).numeroRegistro("00001").fechaCreacion(LocalDate.now()).build(),
                ProductoModel.builder().id(3L).nombre("Producto 3").categoria("Categoria 3").precio(4.00F).numeroRegistro("00001").fechaCreacion(LocalDate.now()).build()
        );

        BDDMockito.given(productoService.listarProductos()).willReturn(productosModels);

        mockMvc.perform(MockMvcRequestBuilders.get("/producto")
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].precio", Matchers.is(2.00)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].nombre", Matchers.is("Producto 3")));
    }

    @Test
    void creaProducto() throws Exception {
        ProductoModel productoModelParametro = ProductoModel.builder().nombre("Producto").categoria("Categoria").precio(2.00F).numeroRegistro("00001").build();
        ProductoModel productoModelRespuesta = ProductoModel.builder().id(1L).nombre("Producto").categoria("Categoria").precio(2.00F).numeroRegistro("00001").build();

        BDDMockito.given(productoService.guardarProducto(productoModelParametro)).willReturn(productoModelRespuesta);

        mockMvc.perform(MockMvcRequestBuilders.post("/producto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productoModelParametro)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void actualizaproducto() throws Exception {

        ProductoModel productoModelParametro = ProductoModel.builder().id(1L).nombre("Producto").categoria("Categoria").precio(2.00F).numeroRegistro("00001").build();

        mockMvc.perform(MockMvcRequestBuilders.put("/producto/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productoModelParametro)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void eliminaproducto() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/producto/1")
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
