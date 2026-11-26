package ec.edu.uce.prototipo.domain.model;

import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "ejecucion")
public class Ejecucion extends PanacheEntityBase {

    @Id
    @SequenceGenerator(
            name = "seq_ejecucion_generador",
            sequenceName = "seq_ejecucion",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_ejecucion_generador"
    )
    @Column(name = "eje_id")
    private Integer id;

    @Column(name = "eje_agente_id")
    private Integer agenteId;

    @Column(name = "eje_agente_nombre")
    private String agenteNombre;

    @Column(name = "eje_entrada", length = 2000)
    private String entrada;

    @Column(name = "eje_salida", length = 2000)
    private String salida;

    @Column(name = "eje_tokens")
    private Integer tokens;

    @Column(name = "eje_fecha")
    private LocalDateTime fecha;

    public Ejecucion() {
    }

    public Ejecucion(Integer id, Integer agenteId, String agenteNombre, String entrada,
            String salida, Integer tokens, LocalDateTime fecha) {
        this.id = id;
        this.agenteId = agenteId;
        this.agenteNombre = agenteNombre;
        this.entrada = entrada;
        this.salida = salida;
        this.tokens = tokens;
        this.fecha = fecha;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAgenteId() {
        return this.agenteId;
    }

    public void setAgenteId(Integer agenteId) {
        this.agenteId = agenteId;
    }

    public String getAgenteNombre() {
        return this.agenteNombre;
    }

    public void setAgenteNombre(String agenteNombre) {
        this.agenteNombre = agenteNombre;
    }

    public String getEntrada() {
        return this.entrada;
    }

    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }

    public String getSalida() {
        return this.salida;
    }

    public void setSalida(String salida) {
        this.salida = salida;
    }

    public Integer getTokens() {
        return this.tokens;
    }

    public void setTokens(Integer tokens) {
        this.tokens = tokens;
    }

    public LocalDateTime getFecha() {
        return this.fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Ejecucion [id=" + this.id + ", agenteNombre=" + this.agenteNombre + ", tokens=" + this.tokens + ", fecha=" + this.fecha + "]";
    }
}
