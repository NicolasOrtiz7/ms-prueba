package com.microservicios.compras.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Cliente {

    private Long id;
    private String dni;
    private String nombre;
    private String apellido;
    private String email;
    private String foto;
    private Region region;
    private String estado;

}
