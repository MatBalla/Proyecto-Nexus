package ec.edu.uce.prototipo.infrastructure.proveedor;

// Lo que devuelve un proveedor, el texto de la respuesta y cuantos tokens costo.
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
