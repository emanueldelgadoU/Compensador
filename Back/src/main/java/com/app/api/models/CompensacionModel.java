package com.app.api.models;


import com.app.api.models.UserModel;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "compensacion")
public class CompensacionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "importe")
    private double importe;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private UserModel user;

    public CompensacionModel() {
    }

    public CompensacionModel(Date fecha, double importe, UserModel user) {
        this.fecha = fecha;
        this.importe = importe;
        this.user = user;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}