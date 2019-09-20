package ec.gob.mag.rna.personas.repository;

import java.util.List;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.rna.personas.domain.view.FuncionarioView;

@Repository("funcionarioRepository")
public interface FuncionarioRepository extends CrudRepository<FuncionarioView, Long> {
	List<FuncionarioView> findAll();
	
	List<FuncionarioView> findByUpsIdAndTpefId(Long upsId, Long tpefId);

	List<FuncionarioView> findByPerIdentificacion(String perIdentificacion);

	List<FuncionarioView> findByUpsIdPadreAndProyIdAndTpefId(Long upsIdPadre, Long proyId, Long tpefId);
	
	List<FuncionarioView> findByPerIdAndProyIdAndTpefId(Long perId, Long proyId, Long tpefId);

	List<FuncionarioView> findByProyIdAndTpefId(Long proyId, Long tpefId);
}
