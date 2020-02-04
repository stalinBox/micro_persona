package ec.gob.mag.rna.personas.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
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
}
