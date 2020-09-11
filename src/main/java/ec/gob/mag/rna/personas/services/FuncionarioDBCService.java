package ec.gob.mag.rna.personas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ec.gob.mag.rna.personas.exception.EnumCodeExceptions;
import ec.gob.mag.rna.personas.exception.EnumTypeExceptions;
import ec.gob.mag.rna.personas.dto.FuncionariosDTOBdc;
import ec.gob.mag.rna.personas.exception.MyNotFoundException;
import ec.gob.mag.rna.personas.repository.FuncionariosBDCRepository;
import ec.gob.mag.rna.personas.util.MyExceptionUtility;

/**
 * Clase FuncionarioService.
 *
 * @author PITPPA
 * @version final
 */
@Service("funcionarioDBCService")
public class FuncionarioDBCService {

	@Autowired
	@Qualifier("funcionariosBDCRepository")
	private FuncionariosBDCRepository funcionariosBDCRepository;

	@Autowired
	private MessageSource messageSource;

	/**
	 * Devulve el listado de todos los funcionarios de la BDC
	 *
	 * @return List<FuncionarioView>.
	 */
	public List<FuncionariosDTOBdc> findFuncionarioByApliId(Long apliId) {
		List<FuncionariosDTOBdc> funcionario = funcionariosBDCRepository.findApliId(apliId);
		if (funcionario == null || funcionario.size() == 0) {
			String msg = MyExceptionUtility.buildExceptionJsonString("error.entity_not_exist.message", null,
					this.getClass(), "findFuncionarioByApliId", EnumTypeExceptions.INFO,
					EnumCodeExceptions.DATA_NOT_FOUND_DB, messageSource);
			throw new MyNotFoundException(msg);

		}
		return funcionario;
	}

	public List<FuncionariosDTOBdc> findFuncionarioByRolId(Long rolId) {
		List<FuncionariosDTOBdc> funcionario = funcionariosBDCRepository.findByRolId(rolId);
		if (funcionario == null || funcionario.size() == 0) {
			String msg = MyExceptionUtility.buildExceptionJsonString("error.entity_not_exist.message", null,
					this.getClass(), "findFuncionarioByRolId", EnumTypeExceptions.INFO,
					EnumCodeExceptions.DATA_NOT_FOUND_DB, messageSource);
			throw new MyNotFoundException(msg);

		}
		return funcionario;
	}

}
