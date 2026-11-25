package ec.edu.uce.prototipo.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "agente")
public class Agente extends PanacheEntityBase {

    @Id
    @SequenceGenerator(
            name = "seq_agente_generador",
            sequenceName = "seq_agente",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_agente_generador"
    )
    @Column(name = "age_id")
    private Integer id;

    @Column(name = "age_nombre")
    private String nombre;

    @Column(name = "age_instrucciones", length = 2000)
    private String instrucciones;

    @Column(name = "age_maximo_tokens")
    private Integer maximoTokens;

    @Column(name = "age_usa_documentos")
    private Boolean usaDocumentos;

    @Column(name = "age_activo")
    private Boolean activo;

    public Agente() {
    }

    public Agente(Integer id, String nombre, String instrucciones,
            Integer maximoTokens, Boolean usaDocumentos, Boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.instrucciones = instrucciones;
        this.maximoTokens = maximoTokens;
        this.usaDocumentos = usaDocumentos;
        this.activo = activo;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getInstrucciones() {
        return this.instrucciones;
    }

    public void setInstrucciones(String instrucciones) {
        this.instrucciones = instrucciones;
    }

    public Integer getMaximoTokens() {
        return this.maximoTokens;
    }

    public void setMaximoTokens(Integer maximoTokens) {
        this.maximoTokens = maximoTokens;
    }

    public Boolean getUsaDocumentos() {
        return this.usaDocumentos;
    }

    public void setUsaDocumentos(Boolean usaDocumentos) {
        this.usaDocumentos = usaDocumentos;
    }

    public Boolean getActivo() {
        return this.activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Agente [id=" + this.id
                + ", nombre=" + this.nombre
                + ", maximoTokens=" + this.maximoTokens
                + ", activo=" + this.activo + "]";
    }
}
