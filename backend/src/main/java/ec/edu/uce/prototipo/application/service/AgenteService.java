package ec.edu.uce.prototipo.application.service;

import java.util.List;

import ec.edu.uce.prototipo.domain.model.Agente;
import ec.edu.uce.prototipo.infrastructure.repository.AgenteRepositoryImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

// Lógica de agente
@ApplicationScoped
@Transactional
public class AgenteService {

    @Inject
    AgenteRepositoryImpl agenteRepositoryImpl;

    public List<Agente> buscarTodos() {
        return this.agenteRepositoryImpl.listarOrdenados();
    }

    public Agente buscarPorId(Integer id) {
        return this.agenteRepositoryImpl.findById(id);
    }

    public boolean guardar(Agente agente) {

        if (this.agenteRepositoryImpl.buscarPorNombre(agente.getNombre()) != null) {
            return false;
        }

        agente.setActivo(true);

        this.agenteRepositoryImpl.persist(agente);

        return true;
    }

    public void actualizar(Agente agente, Integer id) {

        Agente agenteBase = this.buscarPorId(id);

        agenteBase.setNombre(agente.getNombre());
        agenteBase.setInstrucciones(agente.getInstrucciones());
        agenteBase.setMaximoTokens(agente.getMaximoTokens());
        agenteBase.setUsaDocumentos(agente.getUsaDocumentos());

    }

    public void desactivar(Integer id) {

        Agente agenteBase = this.buscarPorId(id);

        agenteBase.setActivo(false);
    }

    public void activar(Integer id) {

        Agente agenteBase = this.buscarPorId(id);

        agenteBase.setActivo(true);
    }
}
