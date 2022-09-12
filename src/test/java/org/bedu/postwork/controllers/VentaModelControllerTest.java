package org.bedu.postwork.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bedu.postwork.model.ClienteModel;
import org.bedu.postwork.model.ProductoModel;
import org.bedu.postwork.model.VentaModel;
import org.bedu.postwork.services.VentaService;
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

@WebMvcTest(VentaController.class)
public class VentaModelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VentaService ventaService;

    @Test
    void getVenta() throws Exception {
        BDDMockito.given(ventaService.obtenerVenta(anyLong())).willReturn(Optional.of(VentaModel.builder().ventaId(1L).monto(10.0F).build()));

        mockMvc.perform(MockMvcRequestBuilders.get("/venta/1")
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ventaId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.monto", Matchers.is(10.0)));
    }

    @Test
    void getVentas() throws Exception {

        List<VentaModel> ventasModels = Arrays.asList(
                VentaModel.builder().ventaId(1L).monto(10.0F).build(),
                VentaModel.builder().ventaId(2L).monto(11.0F).build(),
                VentaModel.builder().ventaId(3L).monto(12.0F).build()
        );

        BDDMockito.given(ventaService.listarVentas()).willReturn(ventasModels);

        mockMvc.perform(MockMvcRequestBuilders.get("/venta")
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].ventaId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].ventaId", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].ventaId", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].monto", Matchers.is(10.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].monto", Matchers.is(12.0)));
    }

    @Test
    void creaVenta() throws Exception {
        ClienteModel clienteParametro = ClienteModel.builder().id(1L).nombre("Cliente").correoContacto("correoCliente@correo.com").direccion("Direccion").numeroEmpleados("10").build();

        ProductoModel productoParametro1 = ProductoModel.builder().id(1L).nombre("Producto 1").categoria("Categoria 1").precio(2.00F).numeroRegistro("00001").build();
        ProductoModel productoParametro2 = ProductoModel.builder().id(2L).nombre("Producto 2").categoria("Categoria 2").precio(2.00F).numeroRegistro("00001").build();
        List<ProductoModel> listproductos = Arrays.asList(productoParametro1,productoParametro2);

        VentaModel ventaModelParametro = VentaModel.builder().monto(10.0F).clienteModel(clienteParametro).productoModels(listproductos).build();
        VentaModel ventaModelRespuesta = VentaModel.builder().ventaId(1L).monto(10.0F).build();

        BDDMockito.given(ventaService.guardarVenta(ventaModelParametro)).willReturn(ventaModelRespuesta);

        mockMvc.perform(MockMvcRequestBuilders.post("/venta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(ventaModelParametro)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void actualizaVenta() throws Exception {

        ClienteModel clienteParametro = ClienteModel.builder().id(1L).nombre("Cliente").correoContacto("correoCliente@correo.com").direccion("Direccion").numeroEmpleados("10").build();

        ProductoModel productoParametro1 = ProductoModel.builder().id(1L).nombre("Producto 1").categoria("Categoria 1").precio(2.00F).numeroRegistro("00001").build();
        ProductoModel productoParametro2 = ProductoModel.builder().id(2L).nombre("Producto 2").categoria("Categoria 2").precio(2.00F).numeroRegistro("00001").build();
        List<ProductoModel> listproductos = Arrays.asList(productoParametro1,productoParametro2);

        VentaModel ventaModelParametro = VentaModel.builder().ventaId(1L).monto(10.0F).clienteModel(clienteParametro).productoModels(listproductos).build();

        mockMvc.perform(MockMvcRequestBuilders.put("/venta/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(ventaModelParametro)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void eliminaVenta() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/venta/1")
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
