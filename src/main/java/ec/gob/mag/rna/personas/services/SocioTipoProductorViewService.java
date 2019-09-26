package ec.gob.mag.rna.personas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ec.gob.mag.rna.personas.domain.Persona;
import ec.gob.mag.rna.personas.domain.view.SocioTipoProductorView;

import ec.gob.mag.rna.personas.exception.EnumCodeExceptions;
import ec.gob.mag.rna.personas.exception.EnumTypeExceptions;
import ec.gob.mag.rna.personas.exception.MyNotFoundException;
import ec.gob.mag.rna.personas.repository.SocioTipoProductorViewRepository;
import ec.gob.mag.rna.personas.util.MyExceptionUtility;
import ec.gob.mag.rna.personas.util.Util;

/**
 * Clase ProductorService.
 *
 * @author PITPPA
 * @version final
 */
@Service("sociotipoproductorviewService")
public class SocioTipoProductorViewService {


	@Autowired
	@Qualifier("sociotipoproductorviewRepository")
	private SocioTipoProductorViewRepository sociotipoproductorRepository;

	@Autowired
	private MessageSource messageSource;

	/**
	 * Buscar productor por cédula
	 *
	 * @param String identificacion
	 * @return Persona, si cumple la condición. Exception, si no cumple.
	 */
	public Persona findProductorByIdentificacion(String identificacion) {
		SocioTipoProductorView productor = sociotipoproductorRepository.findByPerIdentificacion(identificacion).get();
		if (productor == null) {
			String msg = MyExceptionUtility.buildExceptionJsonString("error.entity_not_exist.message",
					identificacion.toString(), this.getClass(), "findProductorByIdentificacion",
					EnumTypeExceptions.INFO, EnumCodeExceptions.DATA_NOT_FOUND_DB, messageSource);
			throw new MyNotFoundException(msg);
		}
		return Util.parseSocioToPersona(productor);
	}

	/**
	 * Buscar productor por Id de Ubicación Domicilio
	 *
	 * @param Long ubiId
	 * @return List<Persona>, si cumple la condición. Exception, si no cumple.
	 */
	public List<Persona> findByUbiId(Long ubiId) {
		List<SocioTipoProductorView> productores = sociotipoproductorRepository.findByUbiId(ubiId);
		if (productores == null || productores.size() == 0) {
			String msg = MyExceptionUtility.buildExceptionJsonString("error.entity_not_exist.message", ubiId.toString(),
					this.getClass(), "findProductorByUbiIdDomicilio", EnumTypeExceptions.INFO,
					EnumCodeExceptions.DATA_NOT_FOUND_DB, messageSource);
			throw new MyNotFoundException(msg);
		}
		return Util.parseSociosToListPersonas(productores);
	}

	/**
	 * Buscar productores por Organización Id.
	 *
	 * @param Long ubiId
	 * @return List<Persona>, que cumplen con la condición. Exception, si no cumple.
	 */
	public List<Persona> findByOrgId(Long orgId) {
		List<SocioTipoProductorView> productores = sociotipoproductorRepository.findByOrgId(orgId);
		if (productores == null || productores.size() == 0) {
			String msg = MyExceptionUtility.buildExceptionJsonString("error.entity_not_exist.message", orgId.toString(),
					this.getClass(), "findByOrgId", EnumTypeExceptions.INFO, EnumCodeExceptions.DATA_NOT_FOUND_DB,
					messageSource);
			throw new MyNotFoundException(msg);
		}
		return Util.parseSociosToListPersonas(productores);
	}

	
}