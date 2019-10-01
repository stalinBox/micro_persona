package ec.gob.mag.rna.personas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.rna.personas.domain.view.ProductorView;

@Repository("productorViewRepository")
public interface ProductorViewRepository extends CrudRepository<ProductorView, Long> {
	List<ProductorView> findAll(Pageable pageable);

	Optional<ProductorView> findById(Long perIdentificacion);

	List<ProductorView> findByUbiIdDomicilio(Long ubiIdDomicilio);

	List<ProductorView> findByOrgId(Long orgId);

	Optional<ProductorView> findByPerIdentificacion(String perIdentificacion);

}
