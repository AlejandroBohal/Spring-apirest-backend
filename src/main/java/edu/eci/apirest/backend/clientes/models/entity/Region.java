package edu.eci.apirest.backend.clientes.models.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="regiones")
public class Region implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private static final long serialVersionUID =2L;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
