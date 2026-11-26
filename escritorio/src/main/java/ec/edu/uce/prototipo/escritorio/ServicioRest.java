package ec.edu.uce.prototipo.escritorio;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

// conexionn con backend
public class ServicioRest {

    private static final String DIRECCION = "http://localhost:8080";

    private final HttpClient cliente = HttpClient.newHttpClient();
    private final ObjectMapper traductor = new ObjectMapper();

    public Usuario iniciarSesion(String correo, String clave) {

        Usuario datos = new Usuario();
        datos.setCorreo(correo);
        datos.setClave(clave);

        String respuesta = this.llamar("POST", "/usuarios/login", this.aTexto(datos));

        if (respuesta.isBlank()) {
            return null;
        }

        return this.aUsuario(respuesta);
    }
    ///////////////////////////////////////////////////////////////////////////////////////

    public List<Agente> buscarAgentes() {

        String respuesta = this.llamar("GET", "/agentes/todos", "");

        return this.aListaDeAgentes(respuesta);
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    public boolean guardarAgente(Agente agente) {

        String respuesta = this.llamar("POST", "/agentes/guardar", this.aTexto(agente));

        return Boolean.parseBoolean(respuesta.trim());
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    public void actualizarAgente(Agente agente, Integer id) {
        this.llamar("PUT", "/agentes/actualizar/" + id, this.aTexto(agente));
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    public void desactivarAgente(Integer id) {
        this.llamar("PUT", "/agentes/desactivar/" + id, "");
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    public void activarAgente(Integer id) {
        this.llamar("PUT", "/agentes/activar/" + id, "");
    }

    ///////////////////////////////////////////////////////////////////////////////////////


    public Ejecucion probarAgente(Integer agenteId, String entrada) {

        Ejecucion datos = new Ejecucion();
        datos.setAgenteId(agenteId);
        datos.setEntrada(entrada);

        String respuesta = this.llamar("POST", "/ejecuciones/probar", this.aTexto(datos));

        if (respuesta.isBlank()) {
            return null;
        }

        return this.aEjecucion(respuesta);
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    public List<Ejecucion> buscarPruebas(Integer agenteId) {

        String respuesta = this.llamar("GET", "/ejecuciones/poragente/" + agenteId, "");

        return this.aListaDeEjecuciones(respuesta);
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private String llamar(String metodo, String ruta, String cuerpo) {

        try {

            HttpRequest.BodyPublisher contenido;

            if (cuerpo.isEmpty()) {
                contenido = HttpRequest.BodyPublishers.noBody();
            } else {
                contenido = HttpRequest.BodyPublishers.ofString(cuerpo, StandardCharsets.UTF_8);
            }

            HttpRequest peticion = HttpRequest.newBuilder()
                    .uri(URI.create(DIRECCION + ruta))
                    .header("Content-Type", "application/json")
                    .method(metodo, contenido)
                    .build();

            HttpResponse<String> respuesta = this.cliente.send(peticion, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

            return respuesta.body();

        } catch (Exception error) {

            //error del backend apagado
            throw new RuntimeException("No se pudo conectar con el servidor. Revise que el backend este encendido.");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private String aTexto(Object dato) {
        try {
            return this.traductor.writeValueAsString(dato);
        } catch (Exception error) {
            throw new RuntimeException("No se pudieron preparar los datos");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private Usuario aUsuario(String texto) {
        try {
            return this.traductor.readValue(texto, Usuario.class);
        } catch (Exception error) {
            throw new RuntimeException("No se entendio la respuesta del servidor");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private Ejecucion aEjecucion(String texto) {
        try {
            return this.traductor.readValue(texto, Ejecucion.class);
        } catch (Exception error) {
            throw new RuntimeException("No se entendio la respuesta del servidor");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private List<Agente> aListaDeAgentes(String texto) {
        try {

            // Primero se convierte en un arreglo y despues en una lista
            Agente[] arreglo = this.traductor.readValue(texto, Agente[].class);

            return new ArrayList<>(Arrays.asList(arreglo));

        } catch (Exception error) {
            throw new RuntimeException("No se entendio la respuesta del servidor");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private List<Ejecucion> aListaDeEjecuciones(String texto) {
        try {

            Ejecucion[] arreglo = this.traductor.readValue(texto, Ejecucion[].class);

            return new ArrayList<>(Arrays.asList(arreglo));

        } catch (Exception error) {
            throw new RuntimeException("No se entendio la respuesta del servidor");
        }
    }
}
