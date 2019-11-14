package ec.gob.mag.rna.personas.services;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ec.gob.mag.rna.personas.domain.Persona;
import ec.gob.mag.rna.personas.domain.PersonaTipo;
import ec.gob.mag.rna.personas.exception.EnumCodeExceptions;
import ec.gob.mag.rna.personas.exception.EnumTypeExceptions;
import ec.gob.mag.rna.personas.exception.MyNotFoundException;
import ec.gob.mag.rna.personas.repository.PersonaRepository;
import ec.gob.mag.rna.personas.util.MyExceptionUtility;

/**
 * Clase PersonaService.
 *
 * @author PITPPA
 * @version final
 */

@Service("personaService")
public class PersonaService {
	@Autowired
	@Qualifier("personaRepository")
	private PersonaRepository personaRepository;
	@Autowired
	private MessageSource messageSource;

	/**
	 * 
	 *
	 * @param List<PersonaTipo> personasTipo
	 * @param Long              tipoEvaluado
	 * @return Boolean.
	 */

	public Boolean checkPersonaTipo(List<PersonaTipo> personasTipo, Long tipoEvaluado) {
		if (personasTipo != null)
			for (PersonaTipo personaTipo : personasTipo) {
				if (personaTipo.getCatTipoPer() == tipoEvaluado)
					return true;
			}
		return false;
	}

	/**
	 * Busca una Persona por Cédula
	 *
	 * @param String identificacion
	 * @return Persona, devuelve el objeto con información de Tipos de Personas.
	 */
	public Persona findByPerIdentificacion(String identificacion) {
		Persona persona = personaRepository.findByPerIdentificacion(identificacion).get();
		if (persona == null) {
			String msg = MyExceptionUtility.buildExceptionJsonString("error.entity_not_exist.message", identificacion,
					this.getClass(), "findByPerIdentificacion", EnumTypeExceptions.INFO,
					EnumCodeExceptions.DATA_NOT_FOUND_DB, messageSource);
			throw new MyNotFoundException(msg);
		}
		// persona.setPersonaTipos(null);
		return persona;
	}

	/**
	 * Busca una Persona por Id
	 *
	 * @param Long id
	 * @return Persona, devuelve el objeto con información de Tipos de Personas.
	 */
	public Persona findById(Long id) {
		Persona persona = personaRepository.findById(id).get();
		if (persona == null) {
			String msg = MyExceptionUtility.buildExceptionJsonString("error.entity_not_exist.message", id.toString(),
					this.getClass(), "findById", EnumTypeExceptions.INFO, EnumCodeExceptions.DATA_NOT_FOUND_DB,
					messageSource);
			throw new MyNotFoundException(msg);
		}
		// persona.setPersonaTipos(null);
		return persona;
	}

	/**
	 * Busca una Persona por Cédula y Tipo
	 *
	 * @param String identificacion
	 * @param Long   id
	 * @return Persona, devuelve el objeto sin información de Tipos de Personas.
	 */
	public Persona findByPerIdentificacionAndTipo(String identificacion, Long tipo) {
		Persona persona = personaRepository.findByPerIdentificacion(identificacion).get();
		if (persona == null || checkPersonaTipo(persona.getPersonaTipos(), tipo) == false) {
			String msg = MyExceptionUtility.buildExceptionJsonString("error.entity_not_exist.message",
					identificacion.toString() + " " + tipo.toString(), this.getClass(),
					"findByPerIdentificacionAndTipo", EnumTypeExceptions.INFO, EnumCodeExceptions.DATA_NOT_FOUND_DB,
					messageSource);
			throw new MyNotFoundException(msg);
		}
		persona.setPersonaTipos(null);
		return persona;
	}

	/**
	 * Busca Personas por Tipo
	 *
	 * @param Long    tipo
	 * @param Integer hoja
	 * @param Integer items
	 * @return List<Persona>, que cumplen con la condición.
	 */
	public List<Persona> findByTipo(Long tipo, Integer hoja, Integer items) {

		List<Persona> personas = personaRepository.findByPersonaTipos_CatTipoPer(tipo, PageRequest.of(hoja, items));
		if (personas == null || personas.size() == 0) {
			String msg = MyExceptionUtility.buildExceptionJsonString("error.entity_not_exist.message",
					tipo.toString() + " " + hoja.toString() + " " + items.toString(), this.getClass(), "findByTipo",
					EnumTypeExceptions.INFO, EnumCodeExceptions.DATA_NOT_FOUND_DB, messageSource);
			throw new MyNotFoundException(msg);
		}
		personas.stream().map(p -> {
			p.setPersonaTipos(null);
			return p;
		}).collect(Collectors.toList());
		return personas;
	}

	/**
	 * Busca Personas por Tipo y que se encuentre dentro de una Area
	 *
	 * @param Integer    tipo
	 * @param List<Long> areaIds
	 * @return List<Persona>, que cumplen con la condición.
	 */
	public List<Persona> findByTipoAndAreasIn(Integer tipo, List<Long> areaIds) {
		List<Persona> personas = personaRepository.findByPersonaTipos_CatTipoPerAndPersonaTipos_AreaIdIn(tipo, areaIds);
		if (personas == null || personas.size() == 0) {
			String msg = MyExceptionUtility.buildExceptionJsonString("error.entity_not_exist.message",
					tipo.toString() + " " + areaIds.toString(), this.getClass(), "findByTipoAndAreasIn",
					EnumTypeExceptions.INFO, EnumCodeExceptions.DATA_NOT_FOUND_DB, messageSource);
			throw new MyNotFoundException(msg);
		}

		for (Persona persona : personas) {
			persona.setPersonaTipos(null);
		}
		return personas;
	}

	/**
	 * Crea una nueva persona
	 *
	 * @param Persona persona
	 * @return Persona.
	 */
	public Persona savePersona(Persona persona) {
		return personaRepository.save(persona);
	}

	/**
	 * Busca una persona dado un PersonaTipoId
	 *
	 * @param Long perId
	 * @return Persona. que cumple con la condición
	 */
	public Persona PesonaTipoId(Long perId) {
		Persona persona = personaRepository.findByPersonaTipos_Id(perId);
		if (persona == null) {
			String msg = MyExceptionUtility.buildExceptionJsonString("error.entity_not_exist.message", perId.toString(),
					this.getClass(), "findByPersonaTipos_Id", EnumTypeExceptions.INFO,
					EnumCodeExceptions.DATA_NOT_FOUND_DB, messageSource);
			throw new MyNotFoundException(msg);
		}
		persona.setPersonaTipos(null);
		return persona;
	}

}
