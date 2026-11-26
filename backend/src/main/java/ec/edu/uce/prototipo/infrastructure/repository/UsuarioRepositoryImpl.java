package ec.edu.uce.prototipo.infrastructure.repository;

import ec.edu.uce.prototipo.domain.model.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class UsuarioRepositoryImpl implements PanacheRepositoryBase<Usuario, Integer> {

    public Usuario buscarPorCorreo(String correo) {
        return this.find("correo = ?1", correo).firstResult();
    }
}
