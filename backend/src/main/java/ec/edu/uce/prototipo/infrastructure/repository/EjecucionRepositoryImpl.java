package ec.edu.uce.prototipo.infrastructure.repository;

import java.util.List;

import ec.edu.uce.prototipo.domain.model.Ejecucion;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class EjecucionRepositoryImpl implements PanacheRepositoryBase<Ejecucion, Integer> {

    public List<Ejecucion> listarPorAgente(Integer agenteId) {
        return this.list("agenteId = ?1 order by fecha desc", agenteId);
    }
}
