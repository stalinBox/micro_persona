package ec.gob.mag.rna.personas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.rna.personas.domain.sp.ProcedureProductor;

@Repository("productorSPRepository")
public interface ProductorSPRepositoy extends CrudRepository<ProcedureProductor, Long> {

	@Query("SELECT p FROM PersonaTipoDTO pt LEFT JOIN ProductorDTO pr ON pt.id = pr.personaTipo "
			+ "INNER JOIN PersonaDTO p ON p.id = pt.persona WHERE p.perIdentificacion=?1 AND "
			+ "pt.petiEstado = 11 AND pt.petiEliminado = FALSE AND pt.catTipoPer=46")
	List<ProcedureProductor> findByperIdentificacion(String identificacion);

	@Query("SELECT p FROM PersonaTipoDTO pt LEFT JOIN ProductorDTO pr ON pt.id = pr.personaTipo "
			+ "INNER JOIN PersonaDTO p ON p.id = pt.persona WHERE pt.id=?1 AND "
			+ "pt.petiEstado = 11 AND pt.petiEliminado = FALSE AND pt.catTipoPer=46")
	List<ProcedureProductor> findByperId(Long perId);

	@Query("SELECT p FROM PersonaTipoDTO pt LEFT JOIN ProductorDTO pr ON pt.id = pr.personaTipo "
			+ "INNER JOIN PersonaDTO p ON p.id = pt.persona WHERE "
			+ "pt.petiEstado = 11 AND pt.petiEliminado = FALSE AND pt.catTipoPer=46")
	List<ProcedureProductor> findByAll();
}
