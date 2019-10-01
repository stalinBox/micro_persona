package ec.gob.mag.rna.personas.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ec.gob.mag.rna.personas.domain.Persona;
import ec.gob.mag.rna.personas.domain.view.ProductorView;
import ec.gob.mag.rna.personas.dto.ProductorOrganizacionDTO;
import ec.gob.mag.rna.personas.services.SocioTipoProductorViewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/sociotipoproductorview")
@Api(value = "Socio Tipo Productor", description = "Servicios de busqueda de socios tipo productores", tags = "Socio Productor")
@ApiResponses(value = { @ApiResponse(code = 200, message = "Objeto recuperado"),
		@ApiResponse(code = 201, message = "Objeto creado"),
		@ApiResponse(code = 404, message = "Recurso no encontrado"),
		@ApiResponse(code = 500, message = "Error interno") })
public class SocioTipoProductorViewController implements ErrorController {
	
	private static final String PATH = "/error";
	public static final Logger LOGGER = LoggerFactory.getLogger(ProductorController.class);

	@Autowired
	@Qualifier("sociotipoproductorviewService")
	private SocioTipoProductorViewService sociotipoproductorviewService;


	@RequestMapping(value = "/findByCedula/{cedula}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca un socio tipo productor por numero de cedula", response = ProductorView.class)
	@ResponseStatus(HttpStatus.OK)
	public Persona getProductorByCedula(@Valid @PathVariable String cedula) {
		Persona persona = sociotipoproductorviewService.findProductorByIdentificacion(cedula);
		LOGGER.info("Socio Tipo Productor findByCedula: " + persona.toString());
		return persona;
	}

	@RequestMapping(value = "/findByOrgId/{orgId}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca socios tipo productores por Id de la Organizacion", response = ProductorView.class)
	@ResponseStatus(HttpStatus.OK)
	public List<Persona> findByOrgId(@Valid @PathVariable Long orgId) {
		List<Persona> personas = sociotipoproductorviewService.findByOrgId(orgId);
		LOGGER.info("Socio Tipo Productores findByOrgId: " + personas.toString());
		return personas;
	}

	@RequestMapping(value = "/findByUbiId/{ubiId}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca socios tipo productores por Id de la Ubicacion", response = ProductorView.class)
	@ResponseStatus(HttpStatus.OK)
	public List<Persona> getProductorByUbiId(@Valid @PathVariable Long ubiId) {
		List<Persona> personas = sociotipoproductorviewService.findByUbiId(ubiId);
		LOGGER.info("Socio Tipos Productores findByUbiId: " + personas.toString());
		return personas;
	}
	
	@RequestMapping(value = "/findByPetiId/{petiId}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca socios por PetiId", response = ProductorView.class)
	@ResponseStatus(HttpStatus.OK)
	public List<ProductorOrganizacionDTO> findByPetiId(@Valid @PathVariable Long petiId) {
		List<ProductorOrganizacionDTO> proorgs = sociotipoproductorviewService.findByPetiId(petiId);
		LOGGER.info("Socio Tipo Productores findByPetiId: " + proorgs.toString());
		return proorgs;
	}
	

	@Override
	public String getErrorPath() {
		return PATH;
	}
}

