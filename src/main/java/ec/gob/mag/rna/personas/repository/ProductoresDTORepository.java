package ec.gob.mag.rna.personas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.rna.personas.dto.ProductoresDTO;

@Repository("productoresDTORepository")
public interface ProductoresDTORepository extends CrudRepository<ProductoresDTO, Long> {

	@Query("select p, pt, pr, pred"
			+" FROM PersonaDTO p" 
			+" INNER JOIN PersonaTipoDTO pt ON p.id = pt.persona" 
			+" JOIN ProductorDTO pr ON pt.id = pr.personaTipo" 
			+" JOIN PredioDTO pred ON pred.personaTipo = pt.id"  
			+" WHERE  pr.proEstado = 11" 
			+" AND pt.petiEstado = 11" 
			+" AND pred.preEstado = 11"  
			+" AND pr.proEliminado = false"  
			+" AND pt.petiEliminado = false" 
			+" AND pred.preEliminado = false"
			+" AND pred.ubiId = ?1")
	List<ProductoresDTO> findByUbiId(Long ubiId);

}
