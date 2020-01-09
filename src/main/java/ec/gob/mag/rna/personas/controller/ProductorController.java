package ec.gob.mag.rna.personas.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import ec.gob.mag.rna.personas.domain.Persona;
import ec.gob.mag.rna.personas.domain.pagination.AppUtil;
import ec.gob.mag.rna.personas.domain.pagination.DataTableRequest;
import ec.gob.mag.rna.personas.domain.pagination.DataTableResults;
import ec.gob.mag.rna.personas.domain.pagination.PaginationCriteria;
import ec.gob.mag.rna.personas.domain.sp.ProcedureProductor;
import ec.gob.mag.rna.personas.domain.view.ProductorView;
import ec.gob.mag.rna.personas.dto.PersonaDTO;
import ec.gob.mag.rna.personas.dto.ProductoresDTO;
import ec.gob.mag.rna.personas.dto.ProductoresDTOPaginated;
import ec.gob.mag.rna.personas.dto.ResponseUpdate;
import ec.gob.mag.rna.personas.services.PersonaTipoService;
import ec.gob.mag.rna.personas.services.ProductorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/productor")
@Api(value = "Productor", description = "Servicios de busqueda de productores", tags = "Productor")
@ApiResponses(value = { @ApiResponse(code = 200, message = "Objeto recuperado"),
		@ApiResponse(code = 200, message = "Objeto creado"),
		@ApiResponse(code = 404, message = "Recurso no encontrado"),
		@ApiResponse(code = 500, message = "Error interno") })

public class ProductorController implements ErrorController {
	private static final String PATH = "/error";
	public static final Logger LOGGER = LoggerFactory.getLogger(ProductorController.class);

	@Autowired
	@Qualifier("productorService")
	private ProductorService productorService;

	@Qualifier("personaTipoService")
	private PersonaTipoService personaTipoService;

	@PersistenceContext
	private EntityManager entityManager;

	/************ METODOS SAVE *************/
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ApiOperation(value = "Crea un nuevo productor", response = ResponseUpdate.class)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseUpdate createProductor(@Valid @RequestBody PersonaDTO productor,
			@RequestHeader(name = "Authorization") String token) {
		PersonaDTO productorResponse = productorService.saveProductor(productor);
		LOGGER.info("Productor Create: " + productorResponse.toString());
		return new ResponseUpdate("productor", productorResponse.getId());
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ApiOperation(value = "Crea un nuevo productor", response = ResponseUpdate.class)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseUpdate updateProductor(@Valid @RequestBody PersonaDTO productor,
			@RequestHeader(name = "Authorization") String token) {
		PersonaDTO productorResponse = productorService.saveProductor(productor);
		LOGGER.info("Productor update: " + productorResponse.toString());
		return new ResponseUpdate("productor", productorResponse.getId());
	}

	/*********** METODOS SELECT ***************/
	/******************************************/

	/*********** PRODUCTOR párametro cedula ***********/
	@RequestMapping(value = "/findByCedulaProductor/{cedula}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca un productor PERSONAS-PERSONATIPO-PRODUCTOR por numero de cedula", response = ProcedureProductor.class)
	@ResponseStatus(HttpStatus.OK)
	public List<ProcedureProductor> getSPProductorByCedula(@Valid @PathVariable String cedula,
			@RequestHeader(name = "Authorization") String token) {
		List<ProcedureProductor> procedureProductor = productorService.findProductorSPByIdentificacion(cedula);
		LOGGER.info("Productor findByCedula: " + procedureProductor.toString());
		return procedureProductor;
	}

	/*********** PRODUCTOR párametro per_id ***********/
	@RequestMapping(value = "/findById/{perId}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca un productor PERSONAS-PERSONATIPO-PRODUCTOR por perId", response = ProcedureProductor.class)
	@ResponseStatus(HttpStatus.OK)
	public List<ProcedureProductor> getSPProductorByPerId(@Valid @PathVariable Long perId,
			@RequestHeader(name = "Authorization") String token) {
		List<ProcedureProductor> procedureProductor = productorService.findByperId(perId);
		LOGGER.info("Productor findById: " + procedureProductor.toString());
		return procedureProductor;
	}

	/************ PRODUCTOR parámetro Id Organización ***************/
	@RequestMapping(value = "/findByOrgId/{orgId}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca productores por Id de la Organizacion", response = ProductorView.class)
	@ResponseStatus(HttpStatus.OK)
	public List<Persona> findByOrgId(@Valid @PathVariable Long orgId,
			@RequestHeader(name = "Authorization") String token) {
		List<Persona> personas = productorService.findByOrgId(orgId);
		LOGGER.info("Productores findByOrgId: " + personas.toString());
		return personas;
	}

	/*************** ELIMINAR ***************/
	@RequestMapping(value = "/findByUbiId/{ubiId}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca productores por Id de la Ubicacion", response = ProductorView.class)
	@ResponseStatus(HttpStatus.OK)
	public List<Persona> getProductorByUbiId(@Valid @PathVariable Long ubiId,
			@RequestHeader(name = "Authorization") String token) {
		List<Persona> personas = productorService.findProductorByUbiIdDomicilio(ubiId);
		LOGGER.info("Productores findByUbiId: " + personas.toString());
		return personas;
	}

	/************ PAGINACION PRODUCTOR parámetro id Ubicación *********/
	/** UTILIZAR EN VEZ DE /findByUbiId/{ubiId} */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findByUbiIdPaginated/{ubiId}", method = RequestMethod.GET)
	@ResponseBody
	public String listAplicationPaginated(HttpServletRequest request, HttpServletResponse response, Model model,
			@PathVariable Long ubiId, @RequestHeader(name = "Authorization") String token) {
		DataTableRequest<ProductoresDTO> dataTableInRQ = new DataTableRequest<ProductoresDTO>(request);
		PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
		String baseQuery = "SELECT p.per_id AS \"id\",\r\n" + " p.per_nombre AS \"perNombre\",\r\n"
				+ " p.per_apellido AS \"perApellido\",\r\n" + " p.per_nombres AS \"perNombres\",\r\n"
				+ " p.per_identificacion AS \"perIdentificacion\",\r\n"
				+ " p.per_dir_domicilio AS \"perDirDomicilio\",\r\n" + " pred.pre_id AS \"preId\",\r\n"
				+ " pred.ubi_id AS \"ubiId\",\r\n" + " p.per_telefono AS \"perTelefono\",\r\n"
				+ " p.per_celular AS \"perCelular\",\r\n" + " p.per_correo AS \"perCorreo\",\r\n"
				+ " pt.peti_id AS \"petiId\",\r\n" + " (SELECT COUNT(1) FROM sc_organizacion.persona p\r\n"
				+ " INNER JOIN sc_organizacion.persona_tipo pt ON p.per_id = pt.per_id\r\n"
				+ " INNER JOIN sc_agricola.productor pr ON pt.peti_id = pr.peti_id\r\n"
				+ " INNER JOIN sc_agricola.predio pred ON pred.peti_id = pt.peti_id\r\n"
				+ " WHERE  pr.pro_estado = 11 \r\n" + " AND pt.peti_estado = 11 \r\n" + " AND pred.pre_estado = 11 \r\n"
				+ " AND pr.pro_eliminado = false \r\n" + " AND pt.peti_eliminado = false \r\n"
				+ " AND pred.pre_eliminado = false\r\n" + " AND pred.ubi_id = " + ubiId + ") as totalRecords\r\n"
				+ " FROM sc_organizacion.persona p\r\n"
				+ " INNER JOIN sc_organizacion.persona_tipo pt ON p.per_id = pt.per_id\r\n"
				+ " INNER JOIN sc_agricola.productor pr ON pt.peti_id = pr.peti_id\r\n"
				+ " INNER JOIN sc_agricola.predio pred ON pred.peti_id = pt.peti_id\r\n"
				+ " WHERE  pr.pro_estado = 11 \r\n" + " AND pt.peti_estado = 11 \r\n" + " AND pred.pre_estado = 11 \r\n"
				+ " AND pr.pro_eliminado = false \r\n" + " AND pt.peti_eliminado = false \r\n"
				+ " AND pred.pre_eliminado = false\r\n" + " AND pred.ubi_id = " + ubiId;
		String paginatedQuery = AppUtil.buildPaginatedQuery(baseQuery, pagination);
		Query query = entityManager.createNativeQuery(paginatedQuery, ProductoresDTOPaginated.class);
		List<ProductoresDTOPaginated> userList = query.getResultList();
		DataTableResults<ProductoresDTOPaginated> dataTableResult = new DataTableResults<ProductoresDTOPaginated>();
		dataTableResult.setDraw(dataTableInRQ.getDraw());
		dataTableResult.setListOfDataObjects(userList);
		if (!AppUtil.isObjectEmpty(userList)) {
			dataTableResult.setRecordsTotal(((ProductoresDTOPaginated) userList.get(0)).getTotalRecords().toString());
			if (dataTableInRQ.getPaginationRequest().isFilterByEmpty())
				dataTableResult
						.setRecordsFiltered(((ProductoresDTOPaginated) userList.get(0)).getTotalRecords().toString());
			else
				dataTableResult.setRecordsFiltered(Integer.toString(userList.size()));
		}
		return (new Gson()).toJson(dataTableResult);
	}

	@Override
	public String getErrorPath() {
		return PATH;
	}
}
