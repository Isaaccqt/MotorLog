package com.isaac.motorlog.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marca;
    private String modelo;
    private String matricula;
    private Integer kilometros;

    // Relación OneToMany con borrado en cascada
    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Intervencion> intervenciones;

    public Vehiculo() {}

    public Vehiculo(String marca, String modelo, String matricula, Integer kilometros) {
        this.marca = marca;
        this.modelo = modelo;
        this.matricula = matricula;
        this.kilometros = kilometros;
    }

    // --- GETTERS Y SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public Integer getKilometros() { return kilometros; }
    public void setKilometros(Integer kilometros) { this.kilometros = kilometros; }

    public List<Intervencion> getIntervenciones() { return intervenciones; }
    public void setIntervenciones(List<Intervencion> intervenciones) { this.intervenciones = intervenciones; }
}