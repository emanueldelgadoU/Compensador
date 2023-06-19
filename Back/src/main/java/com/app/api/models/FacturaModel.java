package com.app.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "Factura")
public class FacturaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "numeroFactura")
    private Long numeroFactura;
    private String comentario;
    private Integer importe;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "id_deudor")
    private UserModel deudor;



    //CONTRUCTORES
    public FacturaModel() {
    }

    public FacturaModel(Long numeroFactura, String comentario, Integer importe, UserModel user, UserModel deudor) {
        this.numeroFactura = numeroFactura;
        this.comentario = comentario;
        this.importe = importe;
        this.user = user;
        this.deudor=deudor;
    }


    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(Long numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getImporte() {
        return importe;
    }

    public void setImporte(Integer importe) {
        this.importe = importe;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public UserModel getDeudor() {
        return deudor;
    }

    public void setDeudor(UserModel deudor) {
        this.deudor = deudor;
    }
}
