package ec.gob.mag.rna.personas.controller;

import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

import ec.gob.mag.rna.personas.domain.Persona;
import ec.gob.mag.rna.personas.dto.ResponseUpdate;
import ec.gob.mag.rna.personas.services.PersonaService;
import ec.gob.mag.rna.personas.services.PersonaTipoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/persona")
@Api(value = "Persona", description = "Rest Api de persona", tags = "Persona")
@ApiResponses(value = { @ApiResponse(code = 200, message = "Objeto recuperado"),
		@ApiResponse(code = 201, message = "Objeto creado"),
		@ApiResponse(code = 404, message = "Recurso no encontrado"),
		@ApiResponse(code = 500, message = "Error interno") })
public class PersonaController implements ErrorController {
	private static final String PATH = "/error";
	public static final Logger LOGGER = LoggerFactory.getLogger(PersonaController.class);

	@Autowired
	@Qualifier("personaService")
	private PersonaService personaService;

	@Qualifier("personaTipoService")
	private PersonaTipoService personaTipoService;

	@RequestMapping(value = "/findByCedula/{cedula}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca persona por número de cédula", response = Persona.class)
	@ResponseStatus(HttpStatus.OK)
	public Persona getPersonaByCedula(@Valid @PathVariable String cedula,
			@RequestHeader(name = "Authorization") String token) {
		Persona persona = personaService.findByPerIdentificacion(cedula);
		LOGGER.info("Persona findByCedula: " + persona.toString());
		return persona;

	}

	@PreAuthorize("#username == authentication.principal.username")
	@RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca persona por perId", response = Persona.class)
	@ResponseStatus(HttpStatus.OK)
	public Persona getPersonaById(@Valid @PathVariable Long id, @RequestHeader(name = "Authorization") String token) {
		Persona persona = personaService.findById(id);
		LOGGER.info("Persona findById: " + persona.toString());
		return persona;

	}

	@RequestMapping(value = "/findByPesonaTipoId/{petiId}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca persona por Persona Tipo Id", response = Persona.class)
	@ResponseStatus(HttpStatus.OK)
	public Persona findByPerId(@Valid @PathVariable Long petiId, @RequestHeader(name = "Authorization") String token) {
		Persona persona = personaService.PesonaTipoId(petiId);
		LOGGER.info("Persona findByPesonaTipoId: " + persona.toString());
		return persona;
	}

	@RequestMapping(value = "/findByTipo/{tipo}/{hoja}/{items}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca personas por Tipo. Devuelve paginado", response = Persona.class)
	@ResponseStatus(HttpStatus.OK)
	public List<Persona> findByTipo(@PathVariable Long tipo, @PathVariable Integer hoja, @PathVariable Integer items,
			@RequestHeader(name = "Authorization") String token) {
		List<Persona> personas = personaService.findByTipo(tipo, hoja, items);
		LOGGER.info("Personas findByTipo: " + personas.toString());
		return personas;
	}

	@RequestMapping(value = "/findByCedulaAndTipo/{cedula}/{tipo}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca un persona por número de cédula y Tipo", response = Persona.class)
	@ResponseStatus(HttpStatus.OK)
	public Persona findByCedulaAndTipo(@PathVariable String cedula, @PathVariable Long tipo,
			@RequestHeader(name = "Authorization") String token) {
		Persona persona = personaService.findByPerIdentificacionAndTipo(cedula, tipo);
		LOGGER.info("Persona findByCedulaAndTipo: " + persona.toString());
		return persona;

	}

	@RequestMapping(value = "/findAllByAreasAndTipo/{tipo}/areas", method = RequestMethod.GET)
	@ApiOperation(value = "Busca persona por tipo y areas", response = Persona.class)
	@ResponseStatus(HttpStatus.OK)
	public List<Persona> findAllByAreasAndTipo(@RequestParam(value = "areaIds", required = false) List<Long> areaIds,
			@PathVariable Integer tipo, @RequestHeader(name = "Authorization") String token) {
		List<Persona> personas = personaService.findByTipoAndAreasIn(tipo, areaIds);
		LOGGER.info("Persona findAllByAreasAndTipo: " + personas.toString());
		return personas;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ApiOperation(value = "Crea una nueva persona", response = ResponseUpdate.class)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseUpdate createPersona(@Valid @RequestBody Persona persona,
			@RequestHeader(name = "Authorization") String token) {
		Persona personaResponse = personaService.savePersona(persona);
		LOGGER.info("Persona Create: " + personaResponse.toString());
		return new ResponseUpdate("persona", personaResponse.getId());
	}

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return PATH;
	}
}
