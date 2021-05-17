package ec.gob.mag.rna.personas.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ec.gob.mag.rna.personas.domain.Persona;
import ec.gob.mag.rna.personas.exception.EnumCodeExceptions;
import ec.gob.mag.rna.personas.exception.EnumTypeExceptions;
import ec.gob.mag.rna.personas.exception.MyNotFoundException;
import ec.gob.mag.rna.personas.repository.PersonaRepository;
import ec.gob.mag.rna.personas.util.MyExceptionUtility;

/**
 * Clase OrganizacionService.
 *
 * @author PITPPA
 * @version final
 */

@Service("organizacionService")
public class OrganizacionService {
	
	@Autowired
	@Qualifier("personaRepository")
	private PersonaRepository personaRepository;
	
	@Autowired
	private MessageSource messageSource;

	/**
	 * Busca un Representante Legal por OrgId
	 *
	 * @param Long orgId
	 * @return Persona, reprsentante legal organizacion
	 */
	public Optional<Persona> findRepresentanteLegal(String orgIdentificacion) {
		Optional<Persona> persona = personaRepository.findRepresentanteLegal(orgIdentificacion);
		if (persona.equals(null)) {
			String msg = MyExceptionUtility.buildExceptionJsonString("error.entity_not_exist.message", orgIdentificacion,
					this.getClass(), "findRepresentanteLegal", EnumTypeExceptions.INFO, EnumCodeExceptions.DATA_NOT_FOUND_DB,
					messageSource);
			throw new MyNotFoundException(msg);
		}
		return persona;
	}

}
