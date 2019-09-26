package ec.gob.mag.rna.personas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import ec.gob.mag.rna.personas.domain.Persona;
import ec.gob.mag.rna.personas.domain.view.ProductorView;
import ec.gob.mag.rna.personas.domain.view.SocioTipoProductorView;
import ec.gob.mag.rna.personas.dto.PersonaDTO;
import ec.gob.mag.rna.personas.dto.PersonaTipoDTO;
import ec.gob.mag.rna.personas.dto.ProductorDTO;
import ec.gob.mag.rna.personas.exception.EnumCodeExceptions;
import ec.gob.mag.rna.personas.exception.EnumTypeExceptions;
import ec.gob.mag.rna.personas.exception.MyNotFoundException;
import ec.gob.mag.rna.personas.repository.PersonaRepository;
import ec.gob.mag.rna.personas.repository.ProductorViewRepository;
import ec.gob.mag.rna.personas.repository.SocioTipoProductorViewRepository;
import ec.gob.mag.rna.personas.util.MyExceptionUtility;
import ec.gob.mag.rna.personas.util.Util;

/**
 * Clase ProductorService.
 *
 * @author PITPPA
 * @version final
 */
@Service("productorService")
public class ProductorService {
	@Autowired
	@Qualifier("personaRepository")
	private PersonaRepository personaRepository;

	@Autowired
	@Qualifier("productorViewRepository")
	private ProductorViewRepository productorRepository;

	/*@Autowired
	@Qualifier("sociotipoproductorViewRepository")
	private SociosTipoProductorViewRepository sociotipoproductorRepository;*/

	@Autowired
	private MessageSource messageSource;

	/**
	 * Buscar productor por cédula
	 *
	 * @param String identificacion
	 * @return Persona, si cumple la condición. Exception, si no cumple.
	 */
	public Persona findProductorByIdentificacion(String identificacion) {
		ProductorView productor = productorRepository.findByPerIdentificacion(identificacion).get();
		if (productor == null) {
			String msg = MyExceptionUtility.buildExceptionJsonString("error.entity_not_exist.message",
					identificacion.toString(), this.getClass(), "findProductorByIdentificacion",
					EnumTypeExceptions.INFO, EnumCodeExceptions.DATA_NOT_FOUND_DB, messageSource);
			throw new MyNotFoundException(msg);
		}
		return Util.parseToPersona(productor);
	}

	/**
	 * Buscar productor por Id de Ubicación Domicilio
	 *
	 * @param Long ubiId
	 * @return List<Persona>, si cumple la condición. Exception, si no cumple.
	 */
	public List<Persona> findProductorByUbiIdDomicilio(Long ubiId) {
		List<ProductorView> productores = productorRepository.findByUbiIdDomicilio(ubiId);
		if (productores == null || productores.size() == 0) {
			String msg = MyExceptionUtility.buildExceptionJsonString("error.entity_not_exist.message", ubiId.toString(),
					this.getClass(), "findProductorByUbiIdDomicilio", EnumTypeExceptions.INFO,
					EnumCodeExceptions.DATA_NOT_FOUND_DB, messageSource);
			throw new MyNotFoundException(msg);
		}
		return Util.parseToListPersona(productores);
	}

	/**
	 * Buscar socio productor por Id de Ubicación Domicilio. La busqueda se ejecuta
	 * sobre una Vista.
	 *
	 * @param Long ubiId
	 * @return List<Persona>, si cumple la condición. Exception, si no cumple.
	 */
	/*public List<Persona> findSocioProductorByUbiIdDomicilio(Long ubiId) {
		List<SocioTipoProductorView> socios = sociotipoproductorRepository.findByUbiId(ubiId);
		if (socios == null || socios.size() == 0) {
			String msg = MyExceptionUtility.buildExceptionJsonString("error.entity_not_exist.message", ubiId.toString(),
					this.getClass(), "findSocioProductorByUbiIdDomicilio", EnumTypeExceptions.INFO,
					EnumCodeExceptions.DATA_NOT_FOUND_DB, messageSource);
			throw new MyNotFoundException(msg);
		}
		return Util.parseSociosToListPersonas(socios);
	}*/

	/**
	 * Buscar productores por Organización Id.
	 *
	 * @param Long ubiId
	 * @return List<Persona>, que cumplen con la condición. Exception, si no cumple.
	 */
	public List<Persona> findByOrgId(Long orgId) {
		List<ProductorView> productores = productorRepository.findByOrgId(orgId);
		if (productores == null || productores.size() == 0) {
			String msg = MyExceptionUtility.buildExceptionJsonString("error.entity_not_exist.message", orgId.toString(),
					this.getClass(), "findByOrgId", EnumTypeExceptions.INFO, EnumCodeExceptions.DATA_NOT_FOUND_DB,
					messageSource);
			throw new MyNotFoundException(msg);
		}
		return Util.parseToListPersona(productores);
	}

	/**
	 * Busca productor por Id.
	 *
	 * @param Long id
	 * @return Persona, que cumplen con la condición. Exception, si no cumple.
	 */
	public Persona findProductorById(Long id) {
		ProductorView productor = productorRepository.findById(id).get();
		if (productor == null) {
			String msg = MyExceptionUtility.buildExceptionJsonString("error.entity_not_exist.message", id.toString(),
					this.getClass(), "findProductorById", EnumTypeExceptions.INFO, EnumCodeExceptions.DATA_NOT_FOUND_DB,
					messageSource);
			throw new MyNotFoundException(msg);
		}
		return Util.parseToPersona(productor);
	}

	/**
	 * Guarda productor
	 *
	 * @param Persona persona
	 * @return Persona, nuevo objeto creado.
	 */
	public PersonaDTO saveProductor(PersonaDTO persona) {
		List<PersonaTipoDTO> personaTipos = persona.getPersonaTipos();
		List<ProductorDTO> productor = null;

		if (personaTipos == null || personaTipos.size() > 1)
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.tipop_not_specify.message=", null, LocaleContextHolder.getLocale()),
					Persona.class));
		PersonaTipoDTO pt = new PersonaTipoDTO();
		pt = persona.getPersonaTipos().get(0);

		if (pt.getProductor() == null || pt.getProductor().size() > 1)
			throw new MyNotFoundException(String.format(messageSource.getMessage("error.productor_not_specify.message",
					null, LocaleContextHolder.getLocale()), Persona.class));
		ProductorDTO prod = new ProductorDTO();
		prod = pt.getProductor().get(0);

		if (pt.getCatTipoPer() != 46)
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.catalogo_not_equal.message", null, LocaleContextHolder.getLocale()),
					Persona.class));
		return personaRepository.save(persona);
	}

}