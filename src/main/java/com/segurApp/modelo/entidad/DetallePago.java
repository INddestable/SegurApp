
package com.segurApp.modelo.entidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Detalles_pago")
public class DetallePago {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detalle_id")
    private Integer detalle_id;
    
    @ManyToOne
    @JoinColumn(name = "pago_id", nullable = false)
    private Pago pago;
    
    private String metodo;
    private Float monto;
    
    //constructor con parametros
    public DetallePago(Integer detalle_id, Pago pago, String metodo, Float monto) {
        this.detalle_id = detalle_id;
        this.pago = pago;
        this.metodo = metodo;
        this.monto = monto;
    }
    
    //constructor vacio (si al suicidio colectivo)

    public DetallePago() {
    }
    
    //metodos get y set

    public Integer getDetalle_id() {
        return detalle_id;
    }

    public void setDetalle_id(Integer detalle_id) {
        this.detalle_id = detalle_id;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public Float getMonto() {
        return monto;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }
    
    
}
