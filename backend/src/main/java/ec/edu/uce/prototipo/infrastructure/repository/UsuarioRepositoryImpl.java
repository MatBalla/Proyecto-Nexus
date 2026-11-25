package ec.edu.uce.prototipo.infrastructure.repository;

import ec.edu.uce.prototipo.domain.model.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

// Capa de infraestructura. PanacheRepositoryBase ya trae hechos persist, findById y listAll.
@ApplicationScoped
@Transactional
public class UsuarioRepositoryImpl implements PanacheRepositoryBase<Usuario, Integer> {

    // El ?1 es el primer parametro y Panache lo reemplaza de forma segura.
    public Usuario buscarPorCorreo(String correo) {
        return this.find("correo = ?1", correo).firstResult();
    }
}
