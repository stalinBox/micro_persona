package ec.gob.mag.rna.personas.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import ec.gob.mag.rna.personas.domain.Persona;
import ec.gob.mag.rna.personas.domain.pagination.AppUtil;
import ec.gob.mag.rna.personas.domain.pagination.DataTableRequest;
import ec.gob.mag.rna.personas.domain.pagination.DataTableResults;
import ec.gob.mag.rna.personas.domain.pagination.PaginationCriteria;
import ec.gob.mag.rna.personas.dto.PersonaDTO;
import ec.gob.mag.rna.personas.dto.PersonaDTOPaginated;
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

	@PersistenceContext
	private EntityManager entityManager;

	@RequestMapping(value = "/findByCedula/{cedula}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca persona por número de cédula", response = Persona.class)
	@ResponseStatus(HttpStatus.OK)
	public Persona getPersonaByCedula(@Valid @PathVariable String cedula,
			@RequestHeader(name = "Authorization") String token) {
		Persona persona = personaService.findByPerIdentificacion(cedula);
		LOGGER.info("Persona findByCedula: " + persona.toString());
		return persona;
	}

	@RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca persona por perId", response = Persona.class)
	@ResponseStatus(HttpStatus.OK)
	public Optional<Persona> getPersonaById(@Valid @PathVariable Long id,
			@RequestHeader(name = "Authorization") String token) {
		Optional<Persona> persona = personaService.findById(id);
		LOGGER.info("Persona findById: " + persona.toString());
		return persona;
	}

	@RequestMapping(value = "/findByPesonaTipoId/{petiId}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca persona por Persona Tipo Id", response = Persona.class)
	@ResponseStatus(HttpStatus.OK)
	@Deprecated
	public Persona findByPerId(@Valid @PathVariable Long petiId, @RequestHeader(name = "Authorization") String token) {
		Persona persona = personaService.PesonaTipoId(petiId);
		LOGGER.info("Persona findByPesonaTipoId: " + persona.toString());
		return persona;
	}

	@RequestMapping(value = "/findByTipo/{tipo}/{hoja}/{items}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca personas por Tipo. Devuelve paginado", response = Persona.class)
	@ResponseStatus(HttpStatus.OK)
	@Deprecated
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
	@Deprecated
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

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findPerFuncionariosByIdRol/{rolId}", method = RequestMethod.GET)
	@ResponseBody
	public String listAplicationPaginated(HttpServletRequest request, HttpServletResponse response, Model model,
			@PathVariable Long rolId, @RequestHeader(name = "Authorization") String token) {
		DataTableRequest<PersonaDTO> dataTableInRQ = new DataTableRequest<PersonaDTO>(request);
		PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
		String baseQuery = "SELECT CAST(up.usup_id AS TEXT) AS \"id\", up.per_id AS \"perId\",\n"
				+ "p.per_nombres AS \"perNombres\", p.per_cedula AS \"perCedula\",\n"
				+ "r.rol_nombre AS \"rolNombre\",( SELECT count(DISTINCT(up.usup_login)) \n"
				+ "FROM sc_seguridad.usuario_persona up INNER JOIN sc_organizacion.persona p ON up.per_id = p.per_id\n"
				+ "INNER JOIN sc_seguridad_sicpas.tbl_rol_usuario ru ON ru.usup_id = up.usup_id\n"
				+ "INNER JOIN sc_seguridad_sicpas.tbl_roles r ON ru.rol_id = r.rol_id \n" + "WHERE  ru.rol_id = "
				+ rolId + " AND up.usup_eliminado = false and up.usup_estado = 11 ) AS totalRecords\n"
				+ "FROM sc_seguridad.usuario_persona up INNER JOIN sc_organizacion.persona p ON up.per_id = p.per_id\n"
				+ "INNER JOIN sc_seguridad_sicpas.tbl_rol_usuario ru ON ru.usup_id = up.usup_id\n"
				+ "INNER JOIN sc_seguridad_sicpas.tbl_roles r ON ru.rol_id = r.rol_id \n" + "WHERE ru.rol_id = " + rolId
				+ " AND up.usup_eliminado = false AND up.usup_estado = 11";
		String paginatedQuery = AppUtil.buildPaginatedQuery(baseQuery, pagination);
		Query query = entityManager.createNativeQuery(paginatedQuery, PersonaDTOPaginated.class);
		List<PersonaDTOPaginated> userList = query.getResultList();
		DataTableResults<PersonaDTOPaginated> dataTableResult = new DataTableResults<PersonaDTOPaginated>();
		dataTableResult.setDraw(dataTableInRQ.getDraw());
		dataTableResult.setListOfDataObjects(userList);
		if (!AppUtil.isObjectEmpty(userList)) {
			dataTableResult.setRecordsTotal(((PersonaDTOPaginated) userList.get(0)).getTotalRecords().toString());
			if (dataTableInRQ.getPaginationRequest().isFilterByEmpty())
				dataTableResult
						.setRecordsFiltered(((PersonaDTOPaginated) userList.get(0)).getTotalRecords().toString());
			else
				dataTableResult.setRecordsFiltered(Integer.toString(userList.size()));
		}
		return (new Gson()).toJson(dataTableResult);
	}

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return PATH;
	}
}
