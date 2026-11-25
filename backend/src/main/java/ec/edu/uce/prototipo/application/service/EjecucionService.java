package ec.edu.uce.prototipo.application.service;

import java.time.LocalDateTime;
import java.util.List;

import ec.edu.uce.prototipo.domain.model.Agente;
import ec.edu.uce.prototipo.domain.model.Ejecucion;
import ec.edu.uce.prototipo.infrastructure.proveedor.ProveedorModelo;
import ec.edu.uce.prototipo.infrastructure.proveedor.RespuestaModelo;
import ec.edu.uce.prototipo.infrastructure.repository.AgenteRepositoryImpl;
import ec.edu.uce.prototipo.infrastructure.repository.EjecucionRepositoryImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

// Lógica para pronbar agente
@ApplicationScoped
@Transactional
public class EjecucionService {

    @Inject
    EjecucionRepositoryImpl ejecucionRepositoryImpl;

    @Inject
    AgenteRepositoryImpl agenteRepositoryImpl;

    @Inject
    ProveedorModelo proveedorModelo;

    public Ejecucion probar(Integer agenteId, String entrada) {

        Agente agente = this.agenteRepositoryImpl.findById(agenteId);

        if (agente == null || !Boolean.TRUE.equals(agente.getActivo())) {
            return null;
        }

        RespuestaModelo respuesta = this.proveedorModelo.responder(
                agente.getInstrucciones(), entrada, agente.getMaximoTokens());

        Ejecucion ejecucion = new Ejecucion();
        ejecucion.setAgenteId(agente.getId());
        ejecucion.setAgenteNombre(agente.getNombre());
        ejecucion.setEntrada(entrada);
        ejecucion.setSalida(respuesta.getTexto());
        ejecucion.setTokens(respuesta.getTokens());
        ejecucion.setFecha(LocalDateTime.now());

        this.ejecucionRepositoryImpl.persist(ejecucion);

        return ejecucion;
    }

    public List<Ejecucion> buscarPorAgente(Integer agenteId) {
        return this.ejecucionRepositoryImpl.listarPorAgente(agenteId);
    }
}
