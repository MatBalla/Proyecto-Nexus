package ec.edu.uce.prototipo.application.service;

import ec.edu.uce.prototipo.domain.model.Usuario;
import ec.edu.uce.prototipo.infrastructure.repository.UsuarioRepositoryImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

// login
@ApplicationScoped
@Transactional
public class UsuarioService {

    @Inject
    UsuarioRepositoryImpl usuarioRepositoryImpl;

    public Usuario iniciarSesion(String correo, String clave) {

        Usuario usuario = this.usuarioRepositoryImpl.buscarPorCorreo(correo);

        if (usuario == null) {
            return null;
        }

        if (!usuario.getClave().equals(clave)) {
            return null;
        }

        return usuario;
    }
}
