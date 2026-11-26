package ec.edu.uce.prototipo.infrastructure.proveedor;

import jakarta.enterprise.context.ApplicationScoped;

// Proveeedor simulado
@ApplicationScoped
public class ProveedorSimulado implements ProveedorModelo {

    private static final int LETRAS_POR_TOKEN = 4; //un token = 4 letras

    private static final int MAXIMO_POR_DEFECTO = 1000;

    @Override
    public RespuestaModelo responder(String instrucciones, String entrada, Integer maximoTokens) {

        String texto = "Respuesta: " + " Instrucciones: " + instrucciones + " Texto: " + entrada;

        int permitidas = LETRAS_POR_TOKEN * (maximoTokens == null ? MAXIMO_POR_DEFECTO : maximoTokens);

        if (texto.length() > permitidas) {
            texto = texto.substring(0, permitidas);
        }

        int tokens = (instrucciones.length() + entrada.length() + texto.length()) / LETRAS_POR_TOKEN;

        return new RespuestaModelo(texto, tokens);
    }
}
