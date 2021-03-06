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
import ec.gob.mag.rna.personas.dto.FuncionariosDTOBdc;
import ec.gob.mag.rna.personas.dto.ProyectoDTO;
import ec.gob.mag.rna.personas.exception.EnumCodeExceptions;
import ec.gob.mag.rna.personas.exception.EnumTypeExceptions;
import ec.gob.mag.rna.personas.exception.MyNotFoundException;
import ec.gob.mag.rna.personas.services.FuncionarioService;
import ec.gob.mag.rna.personas.services.FuncionarioDBCService;
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
	@Qualifier("funcionarioDBCService")
	private FuncionarioDBCService funcionarioDBCService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	@Qualifier("util")
	private Util util;

	@RequestMapping(value = "/findFuncionariosBDCByidApli/{idApli}", method = RequestMethod.GET)
	@ApiOperation(value = "Devuelve el listado de todos los funcionarios", response = FuncionariosDTOBdc.class)
	@ResponseStatus(HttpStatus.OK)
	public List<FuncionariosDTOBdc> findFuncionariosBDCByidApli(@RequestHeader(name = "Authorization") String token,
			@PathVariable Long idApli) {
		List<FuncionariosDTOBdc> funcionarios = funcionarioDBCService.findFuncionarioByApliId(idApli);
		LOGGER.info("Funcionarios findFuncionariosBDCByidApli: " + funcionarios.toString() + " usuario: "
				+ util.filterUsuId(token));
		return funcionarios;
	}

	@RequestMapping(value = "/findFuncionariosBDCByidRol/{idRol}", method = RequestMethod.GET)
	@ApiOperation(value = "Devuelve el listado de todos los funcionarios", response = FuncionariosDTOBdc.class)
	@ResponseStatus(HttpStatus.OK)
	public List<FuncionariosDTOBdc> findFuncionariosBDCByidRol(@RequestHeader(name = "Authorization") String token,
			@PathVariable Long idRol) {
		List<FuncionariosDTOBdc> funcionarios = funcionarioDBCService.findFuncionarioByRolId(idRol);
		LOGGER.info("Funcionarios findFuncionariosBDCByidRol: " + funcionarios.toString() + " usuario: "
				+ util.filterUsuId(token));
		return funcionarios;
	}

	@RequestMapping(value = "/findSupervisores/{perId}/{pefIdPadre}/{pefIdHijo}/{proyId}", method = RequestMethod.GET)
	@ApiOperation(value = "Devuelve el listado de todos los funcionarios", response = FuncionarioView.class)
	@ResponseStatus(HttpStatus.OK)
	public List<FuncionarioView> findSupervisores(@RequestHeader(name = "Authorization") String token,
			@PathVariable Long perId, @PathVariable Long pefIdPadre, @PathVariable Long pefIdHijo,
			@PathVariable Long proyId) {
		List<FuncionarioView> funcionarios = null;
		funcionarios = funcionarioService.findSupervidor(perId, pefIdPadre, pefIdHijo, proyId);
		LOGGER.info("Funcionarios findAll: " + funcionarios.toString() + " usuario: " + util.filterUsuId(token));
		return funcionarios;
	}

	@RequestMapping(value = "/findAll", method = RequestMethod.GET)
	@ApiOperation(value = "Devuelve el listado de todos los funcionarios", response = FuncionarioView.class)
	@ResponseStatus(HttpStatus.OK)
	public List<FuncionarioView> findAll(@RequestHeader(name = "Authorization") String token) {
		List<FuncionarioView> funcionarios = null;
		funcionarios = funcionarioService.findAll();
		LOGGER.info("Funcionarios findAll: " + funcionarios.toString() + " usuario: " + util.filterUsuId(token));
		return funcionarios;
	}

	@RequestMapping(value = "/findDistinctAll", method = RequestMethod.GET)
	@ApiOperation(value = "Devuelve el listado de todos los funcionarios haciendo distinci??n por Id", response = FuncionarioView.class)
	@ResponseStatus(HttpStatus.OK)
	public List<FuncionarioView> findDistinctAll(@RequestHeader(name = "Authorization") String token) {
		List<FuncionarioView> funcionarios = null;
		funcionarios = funcionarioService.findAll();
		funcionarios = funcionarios.stream().filter(Util.distinctByKey(f -> f.getPerId())).collect(Collectors.toList());
		LOGGER.info(
				"Funcionarios findDistinctAll: " + funcionarios.toString() + " usuario: " + util.filterUsuId(token));
		return funcionarios;
	}

	@RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca un funcionario por numero de id", response = FuncionarioView.class)
	@ResponseStatus(HttpStatus.OK)
	public FuncionarioView findById(@PathVariable Long id, @RequestHeader(name = "Authorization") String token) {
		FuncionarioView funcionario = funcionarioService.findById(id);
		LOGGER.info("Funcionario findById: " + funcionario.toString() + " usuario: " + util.filterUsuId(token));
		return funcionario;
	}

	@RequestMapping(value = "/findByCedula/{cedula}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca un funcionario por numero de cedula", response = FuncionarioView.class)
	@ResponseStatus(HttpStatus.OK)
	public FuncionarioView findByCedula(@PathVariable String cedula,
			@RequestHeader(name = "Authorization") String token) {
		FuncionarioView funcionario = funcionarioService.findByPerIdentificacion(cedula);
		LOGGER.info("Funcionario findByCedula: " + funcionario.toString() + " usuario: " + util.filterUsuId(token));
		return funcionario;
	}

	@RequestMapping(value = "/findByPerIdAndProyIdAndPefIdPadrePefIdHijos/{perId}/{proyId}/{pefIdPadre}/{pefIdHijos}", method = RequestMethod.GET)
	@ApiOperation(value = "Obtiene los funcionarios a cargo de perId", response = FuncionarioView.class)
	public List<FuncionarioView> findByPerIdAndProyIdAndTpefIdPadreTpefIdHijos(@PathVariable Long perId,
			@PathVariable Long proyId, @PathVariable Long pefIdPadre, @PathVariable Long pefIdHijos,
			@RequestHeader(name = "Authorization") String token) {

		FuncionarioView fu = funcionarioService.findByPerIdAndProyIdAndPefId(perId, proyId, pefIdPadre);
		List<FuncionarioView> funcionarios = new ArrayList<FuncionarioView>();

		funcionarios = funcionarioService.findByUpsIdPadreAndProyIdAndPefId(fu.getUpsId(), proyId, pefIdHijos);

		if (funcionarios.equals(null) || funcionarios.size() == 0) {
			String msg = MyExceptionUtility.buildExceptionJsonString("error.entity_not_exist_find_array.message",
					"perId: " + perId + ", proyId: " + proyId + ",tpefId: " + pefIdPadre, this.getClass(),
					"findByUpsIdPadreAndProyIdAndTpefId", EnumTypeExceptions.INFO, EnumCodeExceptions.DATA_NOT_FOUND_DB,
					messageSource);
			throw new MyNotFoundException(msg);
		}

		LOGGER.info("Funcionarios findByPerIdAndProyIdAndTpefIdPadreTpefIdHijos: " + funcionarios.toString()
				+ " usuario: " + util.filterUsuId(token));
		return funcionarios;
	}

	// ok
	@RequestMapping(value = "/findByProyIdAndPefId/{proyId}/{pefId}", method = RequestMethod.GET)
	@ApiOperation(value = "Funcionarios por Id del proyecto y perfil de usuario", response = FuncionarioView.class)
	@ResponseStatus(HttpStatus.OK)
	public List<FuncionarioView> findByProyIdAndPefId(@PathVariable Long proyId, @PathVariable Long pefId,
			@RequestHeader(name = "Authorization") String token) {
		List<FuncionarioView> funcionarios = null;
		funcionarios = funcionarioService.findByProyIdAndPefId(proyId, pefId);
		LOGGER.info("Funcionarios findByProyIdAndPefId: " + funcionarios.toString() + " usuario: "
				+ util.filterUsuId(token));
		return funcionarios;
	}

	// ok
	@RequestMapping(value = "/findDistinctByProyIdAndPefId/{proyId}/{pefId}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca funcionarios por Id del proyecto y perfil de usuario. Eliminando registros repetidos", response = FuncionarioView.class)
	@ResponseStatus(HttpStatus.OK)
	public List<FuncionarioView> findDistinctByProyIdAndTpefId(@PathVariable Long proyId, @PathVariable Long pefId,
			@RequestHeader(name = "Authorization") String token) {
		List<FuncionarioView> funcionarios = null;
		funcionarios = funcionarioService.findByProyIdAndPefId(proyId, pefId);
		funcionarios = funcionarios.stream().filter(Util.distinctByKey(f -> f.getPerId())).collect(Collectors.toList());
		LOGGER.info("Funcionarios findDistinctByProyIdAndTpefId: " + funcionarios.toString() + " usuario: "
				+ util.filterUsuId(token));
		return funcionarios;
	}

	// ok
	@RequestMapping(value = "/findPadreByPerIdAndProyIdAndPefIdHijoAndPefIdPadre/{perId}/{proyId}/{pefIdHijo}/{pefIdPadre}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca el padre de una persona", response = FuncionarioView.class)
	@ResponseStatus(HttpStatus.OK)
	public FuncionarioView findPadreByPerIdAndProyIdAndTpefIdHijoAndTpefIdPadre(@PathVariable Long perId,
			@PathVariable Long proyId, @PathVariable Long pefIdHijo, @PathVariable Long pefIdPadre,
			@RequestHeader(name = "Authorization") String token) {
		// devuelve el registro de la persona en la vista
		FuncionarioView pfu = funcionarioService.findByPerIdAndProyIdAndPefId(perId, proyId, pefIdHijo);
		// consulta el registro padre
		FuncionarioView f = funcionarioService.findByUpsIdAndPefId(pfu.getUpsIdPadre(), pefIdPadre);
		LOGGER.info("Funcionario findPadreByPerIdAndProyIdAndTpefIdHijoAndTpefIdPadre: " + pfu.toString() + " usuario: "
				+ util.filterUsuId(token));
		return f;
	}

	@RequestMapping(value = "/findByUpsIdPadreAndProyIdAndPefId/{upsIdPadre}/{proyId}/{pefId}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca funcionarios por id de padre, id del proyecto y perfil de usuario", response = FuncionarioView.class)
	@ResponseStatus(HttpStatus.OK)
	public List<FuncionarioView> findByUpsIdPadreAndProyIdAndPefId(@PathVariable Long upsIdPadre,
			@PathVariable Long proyId, @PathVariable Long pefId, @RequestHeader(name = "Authorization") String token) {
		// devuelve el registro de la persona en la vista
		List<FuncionarioView> lista = funcionarioService.findByUpsIdPadreAndProyIdAndPefId(upsIdPadre, proyId, pefId);
		LOGGER.info("Funcionarios findByUpsIdPadreAndProyIdAndTpefId: " + lista.toString() + " usuario: "
				+ util.filterUsuId(token));
		return lista;
	}

	@RequestMapping(value = "/findProyByPerId/{perId}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca proyectos y perfiles de usuario", response = ProyectoDTO.class)
	@ResponseStatus(HttpStatus.OK)
	public List<ProyectoDTO> findByPerId(@PathVariable Long perId,
			@RequestHeader(name = "Authorization") String token) {
		// devuelve el registro de la persona en la vista
		List<ProyectoDTO> lista = funcionarioService.findProyByPerId(perId);
		LOGGER.info("Proyectos por Funcionario: " + lista.toString() + " usuario: " + util.filterUsuId(token));
		return lista;
	}

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return PATH;
	}
}
