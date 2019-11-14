package ec.gob.mag.rna.personas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.rna.personas.domain.PersonaTipo;

@Repository("personaTipoRepository")
public interface PersonaTipoRepository extends CrudRepository<PersonaTipo, Long> {
	@SuppressWarnings("unchecked")
	PersonaTipo save(PersonaTipo personaTipo);

	Optional<PersonaTipo> findById(Long id);

	List<PersonaTipo> findByPersona_Id(Long perId);

	List<PersonaTipo> findByareaId(Long areaId);

}
