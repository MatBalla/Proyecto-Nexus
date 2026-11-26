package ec.edu.uce.prototipo.infrastructure.proveedor;

public interface ProveedorModelo {

    RespuestaModelo responder(String instrucciones, String entrada, Integer maximoTokens);
}
