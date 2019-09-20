package ec.gob.mag.rna.personas.controller;

import java.util.ArrayList;
import java.util.List;


import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import ec.gob.mag.rna.personas.domain.PersonaTipo;
import ec.gob.mag.rna.personas.dto.ArrayIdRequest;
import ec.gob.mag.rna.personas.services.PersonaTipoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/personaTipo")
@Api(value = "PersonaTipo", description = "Rest Api de productor", tags = "Persona-Tipo")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Objeto recuperado"),
        @ApiResponse(code = 201, message = "Objeto creado"),
        @ApiResponse(code = 404, message = "Recurso no encontrado"),
        @ApiResponse(code = 500, message = "Error interno")
})
public class PersonaTipoController implements ErrorController {
	private static final String PATH = "/error";
	public static final Logger LOGGER = LoggerFactory.getLogger(PersonaTipoController.class);


	@Autowired
	@Qualifier("personaTipoService")
	private PersonaTipoService personaTipoService;

	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/findByListId/{petiIds}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca PersonaTipos tipos por Ids", response = PersonaTipo.class)
	@ResponseStatus(HttpStatus.OK)
	public List<PersonaTipo> getProductorByCedula(@Valid @RequestBody List<ArrayIdRequest> ids) {
	    List<PersonaTipo> personas =null;
	    personas= personaTipoService.findByListId(ids);
		LOGGER.info("PersonaTipo findByListId: " + personas.toString());
		return personas;
	}
	
	
	@RequestMapping(value = "/findByPerId/{perId}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca PersonaTipo por PerId", response = PersonaTipo.class)
	@ResponseStatus(HttpStatus.OK)
	public List<PersonaTipo> findPersonaTipoByPerId(@Valid @PathVariable Long perId) {
	    List<PersonaTipo> personas =new ArrayList<PersonaTipo>();
	    personas= personaTipoService.findPersonaTipoByPerId(perId);
		LOGGER.info("PersonaTipos findByPerId: " + personas.toString());
		return personas;
	}
	
	
	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return PATH;
	}
}
