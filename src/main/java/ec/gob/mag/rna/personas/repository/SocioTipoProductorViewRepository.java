package ec.gob.mag.rna.personas.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.rna.personas.domain.view.SocioTipoProductorView;

@Repository("sociotipoproductorviewRepository")
public interface SocioTipoProductorViewRepository extends CrudRepository<SocioTipoProductorView, Long> {

	List<SocioTipoProductorView> findAll(Pageable pageable);

	List<SocioTipoProductorView> findByUbiId(Long ubiId);

	List<SocioTipoProductorView> findByOrgId(Long orgId);

	List<SocioTipoProductorView> findByPetiId(Long petiId);

	List<SocioTipoProductorView> findByPerIdentificacion(String perIdentificacion);

}
