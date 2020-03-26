package ec.gob.mag.rna.personas.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ec.gob.mag.rna.personas.domain.Persona;
import ec.gob.mag.rna.personas.domain.PersonaTipo;
import ec.gob.mag.rna.personas.domain.Productor;
//import ec.gob.mag.rna.personas.domain.view.ProductorView;
import ec.gob.mag.rna.personas.exception.EnumCodeExceptions;
import ec.gob.mag.rna.personas.exception.EnumTypeExceptions;
import ec.gob.mag.rna.personas.exception.MyNotFoundException;
import ec.gob.mag.rna.personas.repository.PersonaRepository;
import ec.gob.mag.rna.personas.repository.PersonaTipoRepository;
import ec.gob.mag.rna.personas.repository.ProductorRepository;
import ec.gob.mag.rna.personas.util.MyExceptionUtility;

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
	@Qualifier("personaTipoRepository")
	private PersonaTipoRepository personaTipoRepository;

	@Autowired
	@Qualifier("productorResository")
	private ProductorRepository productorResository;

	@Autowired
	private MessageSource messageSource;

	/**
	 * Guarda productor
	 *
	 * @param Persona persona
	 * @return Persona, nuevo objeto creado.
	 */
	public Object saveProductor(Persona persona) {

		Optional<Persona> person = personaRepository.findByPerIdentificacion(persona.getPerIdentificacion());
		PersonaTipo personatipo = new PersonaTipo();
		Productor productor = new Productor();
		personatipo = persona.getPersonaTipos().get(0);
		productor = personatipo.getProductor().get(0);

		if (!person.isPresent()) {
			// GUARDAR EN LAS 3 TABLAS
			return personaRepository.save(persona);
		} else {
			List<PersonaTipo> pt = personaTipoRepository.findByPersona_Id(person.get().getId());
			pt = pt.stream().filter(mpr -> mpr.getCatTipoPer() == 46).collect(Collectors.toList());
			if (pt.size() == 1) {
				// Guardar en 1 TABLA productores;
				productor.setPersonaTipo(person.get().getPersonaTipos().get(0));
				return productorResository.save(productor);
			} else {
				// Guardar en 2 TABLAS pt - productores
				personatipo.setPersona(person.get());
				return personaTipoRepository.save(personatipo);
			}
		}
	}

	public List<Persona> findProductorSPByIdentificacion(String identificacion) {
		List<Persona> productor = personaRepository.findByperIdentificacion(identificacion);
		if (productor == null || productor.size() == 0) {
			String msg = MyExceptionUtility.buildExceptionJsonString("error.entity_not_exist.message",
					identificacion.toString(), this.getClass(), "findProductorByIdentificacion",
					EnumTypeExceptions.INFO, EnumCodeExceptions.DATA_NOT_FOUND_DB, messageSource);
			throw new MyNotFoundException(msg);
		}
		return productor;
	}

	public Optional<Persona> findByperId(Long perId) {
		Optional<Persona> productor = personaRepository.findById(perId);
		if (!productor.isPresent()) {
			String msg = MyExceptionUtility.buildExceptionJsonString("error.entity_not_exist.message", perId.toString(),
					this.getClass(), "findByperId", EnumTypeExceptions.INFO, EnumCodeExceptions.DATA_NOT_FOUND_DB,
					messageSource);
			throw new MyNotFoundException(msg);
		}
		return productor;
	}

//
//	/**
//	 * Buscar productor por cédula HECHO POR PAUL
//	 *
//	 * @param String identificacion
//	 * @return Persona, si cumple la condición. Exception, si no cumple.
//	 */
//	public Persona findProductorByIdentificacion(String identificacion) {
//		ProductorView productor = productorRepository.findByPerIdentificacion(identificacion).get();
//		if (productor == null) {
//			String msg = MyExceptionUtility.buildExceptionJsonString("error.entity_not_exist.message",
//					identificacion.toString(), this.getClass(), "findProductorByIdentificacion",
//					EnumTypeExceptions.INFO, EnumCodeExceptions.DATA_NOT_FOUND_DB, messageSource);
//			throw new MyNotFoundException(msg);
//		}
//		return Util.parseToPersona(productor);
//	}

//	/**
//	 * Buscar productores por Organización Id.
//	 *
//	 * @param Long ubiId
//	 * @return List<Persona>, que cumplen con la condición. Exception, si no cumple.
//	 */
//	public List<Persona> findByOrgId(Long orgId) {
//		List<ProductorView> productores = productorRepository.findByOrgId(orgId);
//		if (productores == null || productores.size() == 0) {
//			String msg = MyExceptionUtility.buildExceptionJsonString("error.entity_not_exist.message", orgId.toString(),
//					this.getClass(), "findByOrgId", EnumTypeExceptions.INFO, EnumCodeExceptions.DATA_NOT_FOUND_DB,
//					messageSource);
//			throw new MyNotFoundException(msg);
//		}
//		return Util.parseToListPersona(productores);
//	}

//	/**
//	 * Busca productor por Id.
//	 *
//	 * @param Long id
//	 * @return Persona, que cumplen con la condición. Exception, si no cumple.
//	 */
//	public Persona findProductorById(Long id) {
//		ProductorView productor = productorRepository.findById(id).get();
//		if (productor == null) {
//			String msg = MyExceptionUtility.buildExceptionJsonString("error.entity_not_exist.message", id.toString(),
//					this.getClass(), "findProductorById", EnumTypeExceptions.INFO, EnumCodeExceptions.DATA_NOT_FOUND_DB,
//					messageSource);
//			throw new MyNotFoundException(msg);
//		}
//		return Util.parseToPersona(productor);
//	}

//	/** CAMBIAR/ ELIMINAR ***/
//	/**
//	 * Buscar productor por Id de Ubicación Domicilio
//	 *
//	 * @param Long ubiId
//	 * @return List<Persona>, si cumple la condición. Exception, si no cumple.
//	 */
//	public List<Persona> findProductorByUbiIdDomicilio(Long ubiId) {
//		List<ProductorView> productores = productorRepository.findByUbiIdDomicilio(ubiId);
//		if (productores == null || productores.size() == 0) {
//			String msg = MyExceptionUtility.buildExceptionJsonString("error.entity_not_exist.message", ubiId.toString(),
//					this.getClass(), "findProductorByUbiIdDomicilio", EnumTypeExceptions.INFO,
//					EnumCodeExceptions.DATA_NOT_FOUND_DB, messageSource);
//			throw new MyNotFoundException(msg);
//		}
//		return Util.parseToListPersona(productores);
//	}
}