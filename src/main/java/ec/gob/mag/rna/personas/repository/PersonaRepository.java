package ec.gob.mag.rna.personas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.rna.personas.domain.Persona;
import ec.gob.mag.rna.personas.dto.PersonaDTO;

@Repository("personaRepository")
public interface PersonaRepository extends CrudRepository<Persona, Long> {
	List<Persona> findAll();

	Optional<Persona> findByPerIdentificacion(String perIdentificacion);

	Optional<Persona> findById(Long id);

	PersonaDTO save(PersonaDTO persona);

	@SuppressWarnings("unchecked")
	Persona save(Persona persona);

	List<Persona> findByUbiIdDomicilio(Long ubiId);

	List<Persona> findByPersonaTipos_CatTipoPer(Integer tipo, Pageable pageable);

	List<Persona> findByPersonaTipos_CatTipoPerAndPersonaTipos_AreaIdIn(Integer tipo, List<Long> areaIds);

	List<Persona> findByPersonaTipos_AreaId(Long areaId);

	List<Persona> findByPersonaTipos_AreaIdIn(List<Long> idsL);

	List<Persona> findByPersonaTipos_CatTipoPer(Long tipo, Pageable pageable);

	Persona findByPersonaTipos_Id(Long perId);

}
