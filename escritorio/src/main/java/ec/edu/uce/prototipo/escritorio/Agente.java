package ec.edu.uce.prototipo.escritorio;

public class Agente {

    private Integer id;
    private String nombre;
    private String instrucciones;
    private Integer maximoTokens;
    private Boolean usaDocumentos;
    private Boolean activo;

    public Agente() {
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
}
