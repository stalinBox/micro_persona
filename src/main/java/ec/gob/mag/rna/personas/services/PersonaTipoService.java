package ec.gob.mag.rna.personas.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ec.gob.mag.rna.personas.domain.PersonaTipo;
import ec.gob.mag.rna.personas.dto.ArrayIdRequest;
import ec.gob.mag.rna.personas.exception.EnumCodeExceptions;
import ec.gob.mag.rna.personas.exception.EnumTypeExceptions;
import ec.gob.mag.rna.personas.exception.MyNotFoundException;
import ec.gob.mag.rna.personas.repository.PersonaTipoRepository;
import ec.gob.mag.rna.personas.util.MyExceptionUtility;

/**
 * Clase PersonaTipoService.
 *
 * @author PITPPA
 * @version final
 */
@Service("personaTipoService")
public class PersonaTipoService {
	@Autowired
	@Qualifier("personaTipoRepository")
	private PersonaTipoRepository personaTipoRepository;
	@Autowired
	private MessageSource messageSource;

	public PersonaTipo saveProductor(PersonaTipo productor) {
		return personaTipoRepository.save(productor);
	}

	/**
	 * Buscar Listado de PersonaTipo por Listado de Id
	 *
	 * @param List<ArrayIdRequest> ptsreq
	 * @return List<PersonaTipo>, si cumple la condición. Exception, si no cumple.
	 */
	public List<PersonaTipo> findByListId(List<ArrayIdRequest> ptsreq) {
		List<PersonaTipo> personaTipos = new ArrayList<>();
		for (ArrayIdRequest arr : ptsreq) {
			Optional<PersonaTipo> persona = personaTipoRepository.findById(arr.getId());
			personaTipos.add(persona.get());
		}
		if (personaTipos.size() == 0) {
			String msg = MyExceptionUtility.buildExceptionJsonString("error.entity_not_exist.message", null,
					this.getClass(), "findByListId", EnumTypeExceptions.INFO, EnumCodeExceptions.DATA_NOT_FOUND_DB,
					messageSource);
			throw new MyNotFoundException(msg);

		}
		personaTipos.stream().map(pt -> {
			pt.setPersona(null);
			return pt;
		}).collect(Collectors.toList());
		return personaTipos;
	}

	/**
	 * Buscar Listado de PersonaTipo por Id de Persona
	 *
	 * @param Long perid
	 * @return List<PersonaTipo> , si cumple la condición. Exception, si no cumple.
	 */
	public List<PersonaTipo> findPersonaTipoByPerId(Long perid) {
		List<PersonaTipo> lista = personaTipoRepository.findByPersona_Id(perid);
		if (lista == null || lista.size() == 0) {
			String msg = MyExceptionUtility.buildExceptionJsonString("error.entity_not_exist.message", perid.toString(),
					this.getClass(), "findPersonaTipoByPerId", EnumTypeExceptions.INFO,
					EnumCodeExceptions.DATA_NOT_FOUND_DB, messageSource);
			throw new MyNotFoundException(msg);
		}
		return lista;
	}

	public List<PersonaTipo> findByAreaId(Long areaId) {
		List<PersonaTipo> lista = personaTipoRepository.findByareaId(areaId);
		if (lista == null || lista.size() == 0) {
			String msg = MyExceptionUtility.buildExceptionJsonString("error.entity_not_exist.message",
					areaId.toString(), this.getClass(), "findPersonaTipoByPerId", EnumTypeExceptions.INFO,
					EnumCodeExceptions.DATA_NOT_FOUND_DB, messageSource);
			throw new MyNotFoundException(msg);
		}
		return lista;
	}
}
