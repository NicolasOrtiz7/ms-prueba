package com.microservicios.compras.model;

import lombok.Data;

@Data
public class Producto {

    private Long id;

    private String nombre;
    private String descripcion;

    private Double stock;
    private Double precio;
    private String estado;

    private Categoria categoria;

}
