package ec.gob.mag.rna.personas.controller;

import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ec.gob.mag.rna.personas.domain.Persona;
import ec.gob.mag.rna.personas.domain.view.ProductorView;
import ec.gob.mag.rna.personas.dto.PersonaDTO;
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
		@ApiResponse(code = 201, message = "Objeto creado"),
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

	@RequestMapping(value = "/findByCedula/{cedula}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca un productor por numero de cedula", response = ProductorView.class)
	@ResponseStatus(HttpStatus.OK)
	public Persona getProductorByCedula(@Valid @PathVariable String cedula) {
		Persona persona = productorService.findProductorByIdentificacion(cedula);
		LOGGER.info("Productor findByCedula: " + persona.toString());
		return persona;
	}

	@RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca un productor por Id", response = ProductorView.class)
	@ResponseStatus(HttpStatus.OK)
	public Persona getProductorById(@Valid @PathVariable Long id) {
		Persona persona = productorService.findProductorById(id);
		LOGGER.info("Productor findById: " + persona.toString());
		return persona;
	}

	@RequestMapping(value = "/findByOrgId/{orgId}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca productores por Id de la Organizacion", response = ProductorView.class)
	@ResponseStatus(HttpStatus.OK)
	public List<Persona> findByOrgId(@Valid @PathVariable Long orgId) {
		List<Persona> personas = productorService.findByOrgId(orgId);
		LOGGER.info("Productores findByOrgId: " + personas.toString());
		return personas;
	}

	@RequestMapping(value = "/findByUbiId/{ubiId}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca productores por Id de la Ubicacion", response = ProductorView.class)
	@ResponseStatus(HttpStatus.OK)
	public List<Persona> getProductorByUbiId(@Valid @PathVariable Long ubiId) {
		List<Persona> personas = productorService.findProductorByUbiIdDomicilio(ubiId);
		LOGGER.info("Productores findByUbiId: " + personas.toString());
		return personas;
	}

	@RequestMapping(value = "/socio/findByUbiId/{ubiId}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca un socio productor por Id de la Ubicacion", response = ProductorView.class)
	@ResponseStatus(HttpStatus.OK)
	public List<Persona> getSocioProductorByUbiId(@Valid @PathVariable Long ubiId) {
		List<Persona> personas = productorService.findSocioProductorByUbiIdDomicilio(ubiId);
		LOGGER.info("SocioProductor findByUbiId: " + personas.toString());
		return personas;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ApiOperation(value = "Crea un nuevo productor", response = ResponseUpdate.class)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseUpdate createProductor(@Valid @RequestBody PersonaDTO productor) {
		PersonaDTO productorResponse = productorService.saveProductor(productor);
		LOGGER.info("Productor Create: " + productorResponse.toString());
		return new ResponseUpdate("productor", productorResponse.getId());
	}

	@Override
	public String getErrorPath() {
		return PATH;
	}
}
