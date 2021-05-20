package ec.gob.mag.rna.personas.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import ec.gob.mag.rna.personas.domain.Persona;
import ec.gob.mag.rna.personas.domain.PersonaTipo;
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
		if (!persona.isPresent())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					orgIdentificacion));
		
		List<PersonaTipo> tiposPer = persona.get().getPersonaTipos().stream().filter(pt-> pt.getCatTipoPer().equals(new Long (49)) && pt.getPetiEstado().equals(new Integer(11))
				 && pt.getPetiEliminado().equals(new Boolean(false))).collect(Collectors.toList());
		
		persona.get().setPersonaTipos(tiposPer);
		
		return persona;
	}

}
