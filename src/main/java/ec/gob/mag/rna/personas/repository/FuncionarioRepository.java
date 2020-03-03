package ec.gob.mag.rna.personas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.gob.mag.rna.personas.domain.view.FuncionarioView;

@Repository("funcionarioRepository")
public interface FuncionarioRepository extends CrudRepository<FuncionarioView, Long> {
	List<FuncionarioView> findAll();

	List<FuncionarioView> findByUpsIdAndPefId(Long upsId, Long pefId);

	List<FuncionarioView> findByPerIdentificacion(String perIdentificacion);

	List<FuncionarioView> findByUpsIdPadreAndProyIdAndPefId(Long upsIdPadre, Long proyId, Long pefId);

	List<FuncionarioView> findByPerIdAndProyIdAndPefId(Long perId, Long proyId, Long tpefId);

	List<FuncionarioView> findByProyIdAndPefId(Long proyId, Long tpefId);

	List<FuncionarioView> findByPerId(Long perId);

	List<FuncionarioView> findByPerIdAndProyId(Long perId, Long proyId);

	@Query(value = "SELECT * FROM sc_organizacion.tfcs\r\n"
			+ "WHERE ups_id_padre = (SELECT ups_id FROM sc_organizacion.tfcs\r\n"
			+ "WHERE proy_id = :proyId AND per_id = :perId AND pef_id = :pefIdPadre)AND pef_id = :pefIdHijo AND usup_id \r\n"
			+ "NOT IN (SELECT usup_id FROM sc_renagro.integrante_brigada WHERE intb_estado = 1)", nativeQuery = true)
	List<FuncionarioView> findSupervisores(@Param("perId") Long perId, @Param("pefIdPadre") Long pefIdPadre,
			@Param("pefIdHijo") Long pefIdHijo, @Param("proyId") Long proyId);
}
