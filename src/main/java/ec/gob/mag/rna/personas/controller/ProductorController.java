package ec.gob.mag.rna.personas.controller;

import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ec.gob.mag.rna.personas.domain.Persona;
import ec.gob.mag.rna.personas.domain.sp.ProcedureProductor;
import ec.gob.mag.rna.personas.domain.view.ProductorView;
import ec.gob.mag.rna.personas.dto.PersonaDTO;
import ec.gob.mag.rna.personas.dto.ProductoresDTO;
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

	/*****
	 * HECHO POR PAUL --> ESTA IMPLEMENTADO EN EL SICPAS A CONSIDERAR *
	 *******/
	@RequestMapping(value = "/findByCedula/{cedula}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca un productor por numero de cedula", response = ProductorView.class)
	@ResponseStatus(HttpStatus.OK)
	public Persona getProductorByCedula(@Valid @PathVariable String cedula,
			@RequestHeader(name = "Authorization") String token) {
		Persona persona = productorService.findProductorByIdentificacion(cedula);
		LOGGER.info("Productor findByCedula: " + persona.toString());
		return persona;
	}

	/******* A REEMPLAZAR **********/
//	@RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
//	@ApiOperation(value = "Busca un productor por Id", response = ProductorView.class)
//	@ResponseStatus(HttpStatus.OK)
//	public Persona getProductorById(@Valid @PathVariable Long id, @RequestHeader(name = "Authorization") String token) {
//		Persona persona = productorService.findProductorById(id);
//		LOGGER.info("Productor findById: " + persona.toString());
//		return persona;
//	}

	@RequestMapping(value = "/findByOrgId/{orgId}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca productores por Id de la Organizacion", response = ProductorView.class)
	@ResponseStatus(HttpStatus.OK)
	public List<Persona> findByOrgId(@Valid @PathVariable Long orgId,
			@RequestHeader(name = "Authorization") String token) {
		List<Persona> personas = productorService.findByOrgId(orgId);
		LOGGER.info("Productores findByOrgId: " + personas.toString());
		return personas;
	}

	@RequestMapping(value = "/findByUbiId/{ubiId}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca productores por Id de la Ubicacion", response = ProductoresDTO.class,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<ProductoresDTO> getProductorByUbiId(@Valid @PathVariable Long ubiId,
			@RequestHeader(name = "Authorization") String token) {
		List<ProductoresDTO> personas = productorService.findProductorByUbiIdDomicilio(ubiId);
		LOGGER.info("Productores findByUbiId: " + personas.toString());
		return personas;
	}

	@Override
	public String getErrorPath() {
		return PATH;
	}
}
