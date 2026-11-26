package ec.edu.uce.prototipo.web.resource;

import java.util.List;

import ec.edu.uce.prototipo.application.service.AgenteService;
import ec.edu.uce.prototipo.domain.model.Agente;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

// recibe las peticiones y llama al servicio
@Path("/agentes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AgenteResource {

    @Inject
    AgenteService agenteService;

    @Path("/todos")
    @GET
    public List<Agente> buscarTodos() {
        return this.agenteService.buscarTodos();
    }

    @Path("/porid/{id}")
    @GET
    public Agente buscarPorId(@PathParam(value = "id") Integer id) {
        return this.agenteService.buscarPorId(id);
    }

    @Path("/guardar")
    @POST
    public boolean guardar(Agente agente) {
        return this.agenteService.guardar(agente);
    }

    @Path("/actualizar/{id}")
    @PUT
    public void actualizar(Agente agente, @PathParam("id") Integer id) {
        this.agenteService.actualizar(agente, id);
    }

    @Path("/desactivar/{id}")
    @PUT
    public void desactivar(@PathParam("id") Integer id) {
        this.agenteService.desactivar(id);
    }

    @Path("/activar/{id}")
    @PUT
    public void activar(@PathParam("id") Integer id) {
        this.agenteService.activar(id);
    }
}
