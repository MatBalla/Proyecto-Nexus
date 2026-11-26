package ec.edu.uce.prototipo.infrastructure.proveedor;

// devuelta del agente
public class RespuestaModelo {

    private final String texto;
    private final Integer tokens;

    public RespuestaModelo(String texto, Integer tokens) {
        this.texto = texto;
        this.tokens = tokens;
    }

    public String getTexto() {
        return this.texto;
    }

    public Integer getTokens() {
        return this.tokens;
    }
}
