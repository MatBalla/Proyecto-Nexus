package ec.edu.uce.prototipo.infrastructure.repository;

import java.util.List;

import ec.edu.uce.prototipo.domain.model.Agente;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

// Capa de infraestructura. Solo habla con la base de datos.
@ApplicationScoped
@Transactional
public class AgenteRepositoryImpl implements PanacheRepositoryBase<Agente, Integer> {

    public List<Agente> listarOrdenados() {
        return this.list("order by nombre");
    }

    // Devuelve null si no existe.
    public Agente buscarPorNombre(String nombre) {
        return this.find("nombre = ?1", nombre).firstResult();
    }
}
