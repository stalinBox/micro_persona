package ec.gob.mag.rna.personas.repository;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.rna.personas.domain.view.ProductorView;
import ec.gob.mag.rna.personas.domain.view.SociosTipoProductorView;


@Repository("sociotipoproductorViewRepository")
public interface SociosTipoProductorViewRepository extends CrudRepository<SociosTipoProductorView, Long> {
	
	List<SociosTipoProductorView> findByUbiId(Long ubiId);
	
}
