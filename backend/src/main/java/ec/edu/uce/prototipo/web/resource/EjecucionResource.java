package ec.edu.uce.prototipo.web.resource;

import java.util.List;

import ec.edu.uce.prototipo.application.service.EjecucionService;
import ec.edu.uce.prototipo.domain.model.Ejecucion;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

// Recibe las peticiones y llama al servicio.
@Path("/ejecuciones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EjecucionResource {

    @Inject
    EjecucionService ejecucionService;

    @Path("/probar")
    @POST
    public Ejecucion probar(Ejecucion ejecucion) {
        return this.ejecucionService.probar(ejecucion.getAgenteId(), ejecucion.getEntrada());
    }

    @Path("/poragente/{agenteId}")
    @GET
    public List<Ejecucion> buscarPorAgente(@PathParam("agenteId") Integer agenteId) {
        return this.ejecucionService.buscarPorAgente(agenteId);
    }
}
