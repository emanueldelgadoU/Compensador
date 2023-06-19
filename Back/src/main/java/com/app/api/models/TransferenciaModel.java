package com.app.api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "Transferencias")
public class TransferenciaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private UserModel user;

    private double importe;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fecha;

    // Constructor vacío
    public TransferenciaModel() {
    }

    // Constructor con parámetros
    public TransferenciaModel(UserModel user, double importe, LocalDateTime fecha) {
        this.user = user;
        this.importe = importe;
        this.fecha = fecha;
    }

    // Getter y Setter para 'id'
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter y Setter para 'user'
    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    // Getter y Setter para 'importe'
    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    // Getter y Setter para 'fecha'
    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
