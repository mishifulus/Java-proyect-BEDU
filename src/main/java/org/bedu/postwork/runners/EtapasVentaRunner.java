package org.bedu.postwork.runners;

import lombok.RequiredArgsConstructor;
import org.bedu.postwork.persistance.*;
import org.bedu.postwork.persistance.entities.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EtapasVentaRunner implements CommandLineRunner {

    private final ClienteRepository clienteRepository;
    private final VisitaRepository visitaRepository;
    private final ProductoRepository productoRepository;
    private final EtapaRepository etapaRepository;
    private final VentaRepository ventaRepository;


    @Override
    public void run(String... args) throws Exception {

        //CLIENTES
        Cliente cliente = crearCliente("Juan", "juan@metroflog.com", "10", "Cesar Madero");
        Cliente cliente1 = crearCliente("Anna", "juan@metroflog.com", "10", "Cesar Madero");
        Cliente cliente2 = crearCliente("Dennis", "juan@metroflog.com", "10", "Cesar Madero");
        Cliente cliente3 = crearCliente("Citlali", "juan@metroflog.com", "10", "Cesar Madero");
        Cliente cliente4 = crearCliente("Juana", "juan@metroflog.com", "10", "Cesar Madero");
        Cliente cliente5 = crearCliente("Katy", "juan@metroflog.com", "10", "Cesar Madero");

        List<Cliente> list = Arrays.asList(cliente,cliente1,cliente2,cliente3,cliente4,cliente5);
        clienteRepository.saveAll(list);


        //ETAPAS
        Etapa etapa1 = creaEtapa("En espera", 0);
        Etapa etapa2 = creaEtapa("Reunión de exploración", 1);
        Etapa etapa3 = creaEtapa("Metas establecidas", 2);
        Etapa etapa4 = creaEtapa("Plan de acción presentado.", 3);
        Etapa etapa5 = creaEtapa("Contrato firmado", 4);
        Etapa etapa6 = creaEtapa("Venta ganada", 5);
        Etapa etapa7 = creaEtapa("Venta perdida", 6);

        List<Etapa> etapas = Arrays.asList(etapa1, etapa2, etapa3, etapa4, etapa5, etapa6, etapa7);
        etapaRepository.saveAll(etapas);


        //PRODUCTOS
        Producto producto = crearProducto("Coca Cola", "Bebidas", (float) 17.50, "00001", LocalDate.now());
        Producto producto1 = crearProducto("Pepsi", "Bebidas", (float) 17.50, "00001", LocalDate.now());
        Producto producto2 = crearProducto("Galletas Maria", "Bebidas", (float) 17.50, "00001", LocalDate.now());
        Producto producto3 = crearProducto("Chetos Flaming", "Bebidas", (float) 17.50, "00001", LocalDate.now());
        Producto producto4 = crearProducto("Caguama", "Bebidas", (float) 17.50, "00001", LocalDate.now());

        List<Producto> productoList = Arrays.asList(producto,producto1,producto2,producto3,producto4);
        productoRepository.saveAll(productoList);


        //VISITAS
        Visita visita = crearVisita(clienteRepository.findById(1L).get(), LocalDateTime.now(),"Direccion 1","Acordar precios de mayoreo", "Juanito");
        Visita visita1 = crearVisita(clienteRepository.findById(2L).get(),LocalDateTime.now(),"Direccion 2","Acordar precios de menudeo", "Juanito");
        Visita visita2 = crearVisita(clienteRepository.findById(3L).get(),LocalDateTime.now(),"Direccion 3","Acordar precios de compra", "Juanito");
        Visita visita3 = crearVisita(clienteRepository.findById(4L).get(),LocalDateTime.now(),"Direccion 4","Acordar precios de prica fresas", "Juanito");
        Visita visita4 = crearVisita(clienteRepository.findById(5L).get(),LocalDateTime.now(),"Direccion 5","Carnita Asada", "Juanito");
        List<Visita> visitaList = Arrays.asList(visita,visita1,visita2,visita3,visita4);
        visitaRepository.saveAll(visitaList);


        //VENTAS
        Venta venta = crearVenta(10, productoList, clienteRepository.findById(1L).get(),LocalDateTime.now());
        Venta venta1 = crearVenta(10, productoList, clienteRepository.findById(2L).get(),LocalDateTime.now());
        Venta venta2 = crearVenta(10, productoList, clienteRepository.findById(3L).get(),LocalDateTime.now());
        Venta venta3 = crearVenta(10, productoList, clienteRepository.findById(4L).get(),LocalDateTime.now());
        Venta venta4 = crearVenta(10, productoList, clienteRepository.findById(5L).get(),LocalDateTime.now());
        List<Venta> ventaList = Arrays.asList(venta, venta1, venta2, venta3, venta4);
        ventaRepository.saveAll(ventaList);
    }

    private Cliente crearCliente(String nombre, String correo, String numEmpleados, String direccion){
        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setCorreoContacto(correo);
        cliente.setNumeroEmpleados(numEmpleados);
        cliente.setDireccion(direccion);

        return cliente;
    }

    private Producto crearProducto(String nombre, String categoria, float precio, String numeroRegistro, LocalDate fechaCreacion){
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setCategoria(categoria);
        producto.setPrecio(precio);
        producto.setNumeroRegistro(numeroRegistro);
        producto.setFechaCreacion(fechaCreacion);
        return producto;
    }

    private Etapa creaEtapa(String nombre, Integer orden) {
        Etapa etapa = new Etapa();
        etapa.setNombre(nombre);
        etapa.setOrden(orden);

        return etapa;
    }

    private Venta crearVenta(float monto, List<Producto> productoList, Cliente cliente, LocalDateTime localDateTime){
        Venta venta = new Venta();
        float total = 0;
        for(Producto value: productoList){
            total += value.getPrecio();
        }
        venta.setMonto(total);
        venta.setProducto(productoList);
        venta.setCliente(cliente);
        venta.setFechaCreacion(localDateTime);
        return venta;
    }

    private Visita crearVisita(Cliente cliente, LocalDateTime fechaProgramada, String direccion, String proposito, String vendedor){
        Visita visita = new Visita();
        visita.setCliente(cliente);
        visita.setFechaProgramada(fechaProgramada);
        visita.setDireccion(direccion);
        visita.setProposito(proposito);
        visita.setVendedor(vendedor);
        return visita;
    }
}
