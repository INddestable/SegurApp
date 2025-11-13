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
@Table(name="Pagos")
public class Pago {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pago_id")
    private Integer pago_id;
    
    @ManyToOne
    @JoinColumn(name = "id_poliza", nullable = false)
    private PolizaCliente poliza;
    
    @Column(name = "fecha_pago")
    private String fecha_pago;
    
    private Float total;
    
    @ManyToOne
    @JoinColumn(name = "factura_id", nullable = true)
    private Factura factura;
    
    @OneToMany(mappedBy = "pago")
    private List<DetallePago> detalles_pago;
    
    //constructor con parametros (revisar detalles pago)

    public Pago(Integer pago_id, PolizaCliente poliza, String fecha_pago, Float total, Factura factura, List<DetallePago> detalles_pago) {
        this.pago_id = pago_id;
        this.poliza = poliza;
        this.fecha_pago = fecha_pago;
        this.total = total;
        this.factura = factura;
        this.detalles_pago = detalles_pago;
    }
    
    //constructor vacio

    public Pago() {
    }
    
    //metodos get y set

    public Integer getPago_id() {
        return pago_id;
    }

    public void setPago_id(Integer pago_id) {
        this.pago_id = pago_id;
    }

    public PolizaCliente getPoliza() {
        return poliza;
    }

    public void setPoliza(PolizaCliente poliza) {
        this.poliza = poliza;
    }

    public String getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(String fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public List<DetallePago> getDetalles_pago() {
        return detalles_pago;
    }

    public void setDetalles_pago(List<DetallePago> detalles_pago) {
        this.detalles_pago = detalles_pago;
    }
    
    
}
