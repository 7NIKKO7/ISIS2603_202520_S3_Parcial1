package co.edu.uniandes.dse.parcial1.services;

import org.springframework.stereotype.Service;
import java.util.Optional;

import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.repositories.RutaRepository;
import jakarta.transaction.Transactional;
import co.edu.uniandes.dse.parcial1.repositories.EstacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import co.edu.uniandes.dse.parcial1.entities.RutaEntity;
import co.edu.uniandes.dse.parcial1.entities.EstacionEntity;

@Service
public class EstacionRutaService {

    @Autowired
    private EstacionRepository estacionRepository;

    @Autowired
    private RutaRepository rutaRepository;

    @Transactional
    public RutaEntity addRutaToEstacion(Long estacionId, Long rutaId) throws EntityNotFoundException {

        Optional<EstacionEntity> estacionEntity = estacionRepository.findById(estacionId);
        if (estacionEntity.isEmpty())
            throw new EntityNotFoundException("Estacin no encontrada");

        Optional<RutaEntity> rutaEntity = rutaRepository.findById(rutaId);
        if (rutaEntity.isEmpty())
            throw new EntityNotFoundException("Ruta no encontrada");

        RutaEntity ruta = rutaEntity.get();
        EstacionEntity estacion = estacionEntity.get();
        if (ruta.getEstaciones().contains(estacion)) {
           throw new IllegalArgumentException("La estacion ya esta asociada a ruta");
        }

    ruta.getEstaciones().add(estacion);
    estacion.getRutas().add(ruta);

    return ruta;
    }
}
