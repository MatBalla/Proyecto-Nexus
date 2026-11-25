package ec.edu.uce.prototipo.web.resource;

import ec.edu.uce.prototipo.application.service.UsuarioService;
import ec.edu.uce.prototipo.domain.model.Usuario;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

// Recibe las peticiones y llama al servicio.
@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    UsuarioService usuarioService;

    @Path("/login")
    @POST
    public Usuario iniciarSesion(Usuario usuario) {
        return this.usuarioService.iniciarSesion(usuario.getCorreo(), usuario.getClave());
    }

}
