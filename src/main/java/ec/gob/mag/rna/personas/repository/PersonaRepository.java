package ec.gob.mag.rna.personas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.rna.personas.domain.Persona;

@Repository("personaRepository")
public interface PersonaRepository extends CrudRepository<Persona, Long> {
	List<Persona> findAll();

	Optional<Persona> findByPerIdentificacion(String perIdentificacion);

	@Query("SELECT p FROM Persona p WHERE p.id=?1")
	Optional<Persona> findById(Long id);

	@SuppressWarnings("unchecked")
	Persona save(Persona persona);

	List<Persona> findByUbiIdDomicilio(Long ubiId);

	List<Persona> findByPersonaTipos_CatTipoPer(Integer tipo, Pageable pageable);

	List<Persona> findByPersonaTipos_CatTipoPerAndPersonaTipos_AreaIdIn(Integer tipo, List<Long> areaIds);

	List<Persona> findByPersonaTipos_AreaId(Long areaId);

	List<Persona> findByPersonaTipos_AreaIdIn(List<Long> idsL);

	List<Persona> findByPersonaTipos_CatTipoPer(Long tipo, Pageable pageable);

	Persona findByPersonaTipos_Id(Long perId);

	@Query("SELECT p FROM PersonaTipo pt LEFT JOIN Productor pr ON pt.id = pr.personaTipo "
			+ "INNER JOIN Persona p ON p.id = pt.persona WHERE p.perIdentificacion=?1 AND "
			+ "pt.petiEstado = 11 AND pt.petiEliminado = FALSE AND pt.catTipoPer=46")
	Optional<Persona> findByperIdentificacion(String identificacion);
	
	@Query(value = "select * from sc_organizacion.persona p" + 
			" inner join sc_organizacion.persona_tipo pt on p.per_id = pt.per_id" + 
			" join sc_organizacion.area a on pt.area_id = a.area_id" + 
			" join sc_organizacion.organizacion o on a.org_id = o.org_id" + 
			" where o.org_identificacion = ?1 and pt.cat_tipo_per = 49"+
			" and pt.peti_eliminado=false and pt.peti_estado=11 and p.per_estado=11 and p.per_eliminado=false",  nativeQuery = true)
	Optional<Persona> findRepresentanteLegal(String orgId);
	
	
	@Query(value="select * from sc_organizacion.persona p"
			+ " inner join sc_organizacion.socios s on p.per_id = s.per_id"
			+ " join sc_organizacion.organizacion o on o.org_id = s.org_id"
			+ " where o.org_id = ?1 and p.per_estado = 11 and p.per_eliminado =false", nativeQuery = true)
	List<Persona> findSocios(Long orgId);
}
