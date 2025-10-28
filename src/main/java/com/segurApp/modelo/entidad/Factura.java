
package com.segurApp.modelo.entidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "Facturas")
public class Factura {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "factura_id")
    private Integer factura_id;
    
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente_id;
    
    @Column(name = "fecha_emision")
    private String fecha_emision;
    
    private Float total;
    
    @OneToMany(mappedBy = "factura")
    private List<Pago> pagos;
    
    @Column(name = "metodo_principal")
    private String metodo_principal;
    
    private String descripcion;

    //constructor con parametros (revisar metodo principal y descripción)
    public Factura(Integer factura_id, Cliente cliente_id, String fecha_emision, Float total, List<Pago> pagos) {
        this.factura_id = factura_id;
        this.cliente_id = cliente_id;
        this.fecha_emision = fecha_emision;
        this.total = total;
        this.pagos = pagos;
        this.metodo_principal = null;
        this.descripcion = null;
    }
    
    //constructo vacio

    public Factura() {
    }
    
    //metodos get y set 

    public Integer getFactura_id() {
        return factura_id;
    }

    public void setFactura_id(Integer factura_id) {
        this.factura_id = factura_id;
    }

    public Cliente getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(Cliente cliente_id) {
        this.cliente_id = cliente_id;
    }

    public String getFecha_emision() {
        return fecha_emision;
    }

    public void setFecha_emision(String fecha_emision) {
        this.fecha_emision = fecha_emision;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public List<Pago> getPagos() {
        return pagos;
    }

    public void setPagos(List<Pago> pagos) {
        this.pagos = pagos;
    }

    public String getMetodo_principal() {
        return metodo_principal;
    }

    public void setMetodo_principal(String metodo_principal) {
        this.metodo_principal = metodo_principal;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
}
