package ec.gob.mag.rna.personas.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.rna.personas.domain.Productor;

@Repository("productorResository")
public interface ProductorRepository extends CrudRepository<Productor, Long> {

	@SuppressWarnings("unchecked")
	Productor save(Productor productor);

	List<Productor> findByPersonaTipo_Id(Long pt);
	// List<ProductorView> findAll(Pageable pageable);
//
//	Optional<ProductorView> findById(Long perIdentificacion);
//
//	List<ProductorView> findByUbiIdDomicilio(Long ubiIdDomicilio);
//
//	List<ProductorView> findByOrgId(Long orgId);
//
//	Optional<ProductorView> findByPerIdentificacion(String perIdentificacion);

}
