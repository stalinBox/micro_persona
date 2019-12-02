package ec.gob.mag.rna.personas.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ec.gob.mag.rna.personas.domain.view.FuncionarioView;
import ec.gob.mag.rna.personas.dto.ProyectoDTO;
import ec.gob.mag.rna.personas.exception.EnumCodeExceptions;
import ec.gob.mag.rna.personas.exception.EnumTypeExceptions;
import ec.gob.mag.rna.personas.exception.MyNotFoundException;
import ec.gob.mag.rna.personas.services.FuncionarioService;
import ec.gob.mag.rna.personas.util.MyExceptionUtility;
import ec.gob.mag.rna.personas.util.Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/funcionario")
@Api(value = "Funcionario", description = "Servicios de busqueda de funcionarios", tags = "Funcionario")
@ApiResponses(value = { @ApiResponse(code = 200, message = "Objeto recuperado"),
		@ApiResponse(code = 201, message = "Objeto creado"),
		@ApiResponse(code = 404, message = "Recurso no encontrado"),
		@ApiResponse(code = 500, message = "Error interno") })

public class FuncionarioController implements ErrorController {
	private static final String PATH = "/error";
	public static final Logger LOGGER = LoggerFactory.getLogger(FuncionarioController.class);

	@Autowired
	@Qualifier("funcionarioService")
	private FuncionarioService funcionarioService;

	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/findAll", method = RequestMethod.GET)
	@ApiOperation(value = "Devuelve el listado de todos los funcionarios", response = FuncionarioView.class)
	@ResponseStatus(HttpStatus.OK)
	public List<FuncionarioView> findAll(@RequestHeader(name = "Authorization") String token) {
		List<FuncionarioView> funcionarios = null;
		funcionarios = funcionarioService.findAll();
		LOGGER.info("Funcionarios findAll: " + funcionarios.toString());
		return funcionarios;
	}

	@RequestMapping(value = "/findDistinctAll", method = RequestMethod.GET)
	@ApiOperation(value = "Devuelve el listado de todos los funcionarios haciendo distinción por Id", response = FuncionarioView.class)
	@ResponseStatus(HttpStatus.OK)
	public List<FuncionarioView> findDistinctAll(@RequestHeader(name = "Authorization") String token) {
		List<FuncionarioView> funcionarios = null;
		funcionarios = funcionarioService.findAll();
		funcionarios = funcionarios.stream().filter(Util.distinctByKey(f -> f.getPerId())).collect(Collectors.toList());
		LOGGER.info("Funcionarios findDistinctAll: " + funcionarios.toString());
		return funcionarios;
	}

	@RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca un funcionario por numero de id", response = FuncionarioView.class)
	@ResponseStatus(HttpStatus.OK)
	public FuncionarioView findById(@PathVariable Long id, @RequestHeader(name = "Authorization") String token) {
		FuncionarioView funcionario = funcionarioService.findById(id);
		LOGGER.info("Funcionario findById: " + funcionario.toString());
		return funcionario;
	}

	@RequestMapping(value = "/findByCedula/{cedula}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca un funcionario por numero de cedula", response = FuncionarioView.class)
	@ResponseStatus(HttpStatus.OK)
	public FuncionarioView findByCedula(@PathVariable String cedula,
			@RequestHeader(name = "Authorization") String token) {
		FuncionarioView funcionario = funcionarioService.findByPerIdentificacion(cedula);
		LOGGER.info("Funcionario findByCedula: " + funcionario.toString());
		return funcionario;
	}

	@RequestMapping(value = "/findByPerIdAndProyIdAndTpefId/{perId}/{proyId}/{tpefId}", method = RequestMethod.GET)
	@ApiOperation(value = "Funcionarios por Id de persona, Id del proyecto y perfil de usuario", response = FuncionarioView.class)
	// @ResponseStatus(HttpStatus.OK)
	public List<FuncionarioView> findByPerIdAndProyIdAndTpefId(@PathVariable Long perId, @PathVariable Long proyId,
			@PathVariable Long tpefId, @RequestHeader(name = "Authorization") String token) {
		List<FuncionarioView> funcionarios = new ArrayList<FuncionarioView>();
		// perfil adinistrador 18
		if (tpefId.equals(new Long(18)) && perId.equals(new Long(0))) {
			funcionarios = funcionarioService.findByProyIdAndTpefId(proyId, new Long(16));
		} else {
			// devuelve el registro de la persona en la vista
			FuncionarioView fu = funcionarioService.findByPerIdAndProyIdAndTpefId(perId, proyId, tpefId);
			// consulta los registros hijos
			if (tpefId.equals(new Long(13))) {
				funcionarios = funcionarioService.findByUpsIdPadreAndProyIdAndTpefId(fu.getUpsId(), proyId,
						new Long(5));
			} else if (tpefId.equals(new Long(16))) {
				funcionarios = funcionarioService.findByUpsIdPadreAndProyIdAndTpefId(fu.getUpsId(), proyId,
						new Long(13));
			}
		}

		if (funcionarios == null || funcionarios.size() == 0) {
			String msg = MyExceptionUtility.buildExceptionJsonString("error.entity_not_exist.message",
					proyId.toString(), this.getClass(), "findByUpsIdPadreAndProyIdAndTpefId", EnumTypeExceptions.INFO,
					EnumCodeExceptions.DATA_NOT_FOUND_DB, messageSource);
			throw new MyNotFoundException(msg);
		}

		LOGGER.info("Funcionarios findByPerIdAndProyIdAndTpefId: " + funcionarios.toString());
		return funcionarios;
	}

	@RequestMapping(value = "/findByProyIdAndTpefId/{proyId}/{tpefId}", method = RequestMethod.GET)
	@ApiOperation(value = "Funcionarios por Id del proyecto y perfil de usuario", response = FuncionarioView.class)
	@ResponseStatus(HttpStatus.OK)
	public List<FuncionarioView> findByProyIdAndTpefId(@PathVariable Long proyId, @PathVariable Long tpefId,
			@RequestHeader(name = "Authorization") String token) {
		List<FuncionarioView> funcionarios = null;
		funcionarios = funcionarioService.findByProyIdAndTpefId(proyId, tpefId);
		LOGGER.info("Funcionarios findByProyIdAndTpefId: " + funcionarios.toString());
		return funcionarios;
	}

	@RequestMapping(value = "/findDistinctByProyIdAndTpefId/{proyId}/{tpefId}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca funcionarios por Id del proyecto y perfil de usuario. Eliminando registros repetidos", response = FuncionarioView.class)
	@ResponseStatus(HttpStatus.OK)
	public List<FuncionarioView> findDistinctByProyIdAndTpefId(@PathVariable Long proyId, @PathVariable Long tpefId,
			@RequestHeader(name = "Authorization") String token) {
		List<FuncionarioView> funcionarios = null;
		funcionarios = funcionarioService.findByProyIdAndTpefId(proyId, tpefId);
		funcionarios = funcionarios.stream().filter(Util.distinctByKey(f -> f.getPerId())).collect(Collectors.toList());
		LOGGER.info("Funcionarios findDistinctByProyIdAndTpefId: " + funcionarios.toString());
		return funcionarios;
	}

	@RequestMapping(value = "/findPadreByPerIdAndProyIdAndTpefId/{perId}/{proyId}/{tpefId}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca un funcionario por id de persona, id del proyecto y perfil de usuario", response = FuncionarioView.class)
	@ResponseStatus(HttpStatus.OK)
	public FuncionarioView findPadreByPerIdAndProyIdAndTpefId(@PathVariable Long perId, @PathVariable Long proyId,
			@PathVariable Long tpefId, @RequestHeader(name = "Authorization") String token) {
		List<FuncionarioView> funcionarios = new ArrayList<FuncionarioView>();
		// devuelve el registro de la persona en la vista
		FuncionarioView pfu = funcionarioService.findByPerIdAndProyIdAndTpefId(perId, proyId, tpefId);
		// consulta los registros hijos
		if (tpefId.equals(new Long(5))) {
			pfu = funcionarioService.findByUpsIdAndTpefId(pfu.getUpsIdPadre(), new Long(13));
		} else if (tpefId.equals(new Long(13))) {
			pfu = funcionarioService.findByUpsIdAndTpefId(pfu.getUpsIdPadre(), new Long(16));
		}
		LOGGER.info("Funcionario findPadreByPerIdAndProyIdAndTpefId: " + funcionarios.toString());
		return pfu;
	}

	@RequestMapping(value = "/findByUpsIdPadreAndProyIdAndTpefId/{upsIdPadre}/{proyId}/{tpefId}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca funcionarios por id de padre, id del proyecto y perfil de usuario", response = FuncionarioView.class)
	@ResponseStatus(HttpStatus.OK)
	public List<FuncionarioView> findByUpsIdPadreAndProyIdAndTpefId(@PathVariable Long upsIdPadre,
			@PathVariable Long proyId, @PathVariable Long tpefId, @RequestHeader(name = "Authorization") String token) {
		// devuelve el registro de la persona en la vista
		List<FuncionarioView> lista = funcionarioService.findByUpsIdPadreAndProyIdAndTpefId(upsIdPadre, proyId, tpefId);
		LOGGER.info("Funcionarios findByUpsIdPadreAndProyIdAndTpefId: " + lista.toString());
		return lista;
	}

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return PATH;
	}

	@RequestMapping(value = "/findProyByPerId/{perId}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca proyectos y perfiles de usuario", response = ProyectoDTO.class)
	@ResponseStatus(HttpStatus.OK)
	public List<ProyectoDTO> findByPerId(@PathVariable Long perId,
			@RequestHeader(name = "Authorization") String token) {
		// devuelve el registro de la persona en la vista
		List<ProyectoDTO> lista = funcionarioService.findProyByPerId(perId);
		LOGGER.info("Proyectos por Funcionario: " + lista.toString());
		return lista;
	}
}
