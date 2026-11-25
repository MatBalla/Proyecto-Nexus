package ec.edu.uce.prototipo.escritorio;

public class Ejecucion {

    private Integer id;
    private Integer agenteId;
    private String agenteNombre;
    private String entrada;
    private String salida;
    private Integer tokens;


    private String fecha;

    public Ejecucion() {
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

    public String getFecha() {
        return this.fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
