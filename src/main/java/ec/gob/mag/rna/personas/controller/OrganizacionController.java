package ec.gob.mag.rna.personas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ec.gob.mag.rna.personas.domain.Persona;
import ec.gob.mag.rna.personas.services.OrganizacionService;
import ec.gob.mag.rna.personas.util.Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/organizacion")
@Api(value = "Organizacion", description = "Busqueda representante legal de Organizacion", tags = "Organizacion")
@ApiResponses(value = { @ApiResponse(code = 200, message = "Objeto recuperado"),
		@ApiResponse(code = 200, message = "SUCESS"), @ApiResponse(code = 404, message = "RESOURCE NOT FOUND"),
		@ApiResponse(code = 400, message = "BAD REQUEST"), @ApiResponse(code = 201, message = "CREATED"),
		@ApiResponse(code = 401, message = "UNAUTHORIZED"),
		@ApiResponse(code = 415, message = "UNSUPPORTED TYPE - Representation not supported for the resource"),
		@ApiResponse(code = 500, message = "SERVER ERROR") })
public class OrganizacionController implements ErrorController {
	private static final String PATH = "/error";
	public static final Logger LOGGER = LoggerFactory.getLogger(OrganizacionController.class);

	@Autowired
	@Qualifier("organizacionService")
	private OrganizacionService organizacionService;
	
	@Autowired
	@Qualifier("util")
	private Util util;

	@RequestMapping(value = "/findRepresentanteLegal/{orgIdentificacion}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca el repreentante legal de una organizacion", response = Persona.class)
	@ResponseStatus(HttpStatus.OK)
	public Persona findRepresentanteLegal(@PathVariable String orgIdentificacion,@RequestHeader(name = "Authorization") String token) {
		Persona persona = organizacionService.findRepresentanteLegal(orgIdentificacion).get();
		LOGGER.info("/organizacion/findRepresentanteLegal/" + " usuario: " + util.filterUsuId(token));
		return persona;
	}
	
	
	@RequestMapping(value = "/findSocios/{orgId}", method = RequestMethod.GET)
	@ApiOperation(value = "Devuelve  los socios de una organizacion", response = Persona.class)
	@ResponseStatus(HttpStatus.OK)
	public List<Persona> findSocios(@PathVariable Long orgId, @RequestHeader(name = "Authorization") String token) {
		List<Persona> personas = organizacionService.findSocios(orgId);
		LOGGER.info("/organizacion/findSocios/ " + orgId.toString() + " usuario: " + util.filterUsuId(token));
		return personas;
	}
	
	
	

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return PATH;
	}
}
