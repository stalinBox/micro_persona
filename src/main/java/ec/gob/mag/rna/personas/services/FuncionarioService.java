package ec.gob.mag.rna.personas.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ec.gob.mag.rna.personas.exception.EnumCodeExceptions;
import ec.gob.mag.rna.personas.exception.EnumTypeExceptions;
import ec.gob.mag.rna.personas.domain.view.FuncionarioView;
import ec.gob.mag.rna.personas.exception.MyNotFoundException;
import ec.gob.mag.rna.personas.repository.FuncionarioRepository;
import ec.gob.mag.rna.personas.util.MyExceptionUtility;


/**
 * Clase FuncionarioService.
 *
 * @author PITPPA
 * @version final
 */
@Service("funcionarioService")
public class FuncionarioService {
	@Autowired
	@Qualifier("funcionarioRepository")
	private FuncionarioRepository funcionarioRepository;
	@Autowired
	private MessageSource messageSource;

	
	/**
	 * Devulve el listado de todos los funcionarios
	 *
	 * @return List<FuncionarioView>.
	 */
	public List<FuncionarioView> findAll() {
		List<FuncionarioView> tfcs = funcionarioRepository.findAll();
		if (tfcs == null || tfcs.size()==0) {
			String msg= MyExceptionUtility.buildExceptionJsonString
					( "error.entity_not_exist.message", 
							null, 
					  this.getClass(),
					  "findAll",
					  EnumTypeExceptions.INFO,
					  EnumCodeExceptions.DATA_NOT_FOUND_DB, messageSource);
			throw new MyNotFoundException(msg);	
		
		}	
		return tfcs;
	}
	
	
	/**
	 * Busca funcionarios por Jefe(IdPadre), Proyecto  
	 *
	 * @param Long upsIdPadre
	 * @param Long proyId
	 * @param Long tpefId
	 * @return List<FuncionarioView>, que cumplen con la condición.
	 */
	public List<FuncionarioView> findByUpsIdPadreAndProyIdAndTpefId(Long upsIdPadre, Long proyId, Long tpefId) {
		List<FuncionarioView> tfcs = funcionarioRepository.findByUpsIdPadreAndProyIdAndTpefId(upsIdPadre, proyId, tpefId);
		if (tfcs == null || tfcs.size()==0) {
			String msg= MyExceptionUtility.buildExceptionJsonString
					( "error.entity_not_exist.message", 
							upsIdPadre.toString()+" "+proyId.toString()+" "+tpefId.toString(), 
					  this.getClass(),
					  "findByUpsIdPadreAndProyIdAndTpefId",
					  EnumTypeExceptions.INFO,
					  EnumCodeExceptions.DATA_NOT_FOUND_DB, messageSource);
			throw new MyNotFoundException(msg);		}	
		return tfcs;
	}	
	
	
	/**
	 * Busca funcionarios por PersonaId, Proyecto  
	 *
	 * @param Long perId
	 * @param Long proyId
	 * @param Long tpefId
	 * @return List<FuncionarioView>, que cumplen con la condición.
	 */
	public FuncionarioView findByPerIdAndProyIdAndTpefId(Long perId, Long proyId, Long tpefId) {
		List<FuncionarioView> tfcs = funcionarioRepository.findByPerIdAndProyIdAndTpefId(perId, proyId, tpefId);
		if (tfcs == null || tfcs.size()==0) {
			String msg= MyExceptionUtility.buildExceptionJsonString
					( "error.entity_not_exist.message", 
							perId.toString()+" "+proyId.toString()+" "+tpefId.toString(), 
					  this.getClass(),
					  "findByPerIdAndProyIdAndTpefId",
					  EnumTypeExceptions.INFO,
					  EnumCodeExceptions.DATA_NOT_FOUND_DB, messageSource);
			throw new MyNotFoundException(msg);		}	
		return tfcs.get(0);
	}
	
	
	/**
	 * Busca funcionario por upsId, tpefId  
	 *
	 * @param Long upsId
	 * @param Long tpefId
	 * @return FuncionarioView, que cumplen con la condición.
	 */
	public FuncionarioView findByUpsIdAndTpefId(Long upsId, Long tpefId)
	{
		List<FuncionarioView> tfcs = funcionarioRepository.findByUpsIdAndTpefId(upsId,tpefId);
		if(tfcs==null || tfcs.size()==0)
		{
			String msg= MyExceptionUtility.buildExceptionJsonString
					( "error.entity_not_exist.message", 
							upsId.toString()+" "+tpefId.toString(), 
					  this.getClass(),
					  "findByUpsIdAndTpefId",
					  EnumTypeExceptions.INFO,
					  EnumCodeExceptions.DATA_NOT_FOUND_DB, messageSource);
			throw new MyNotFoundException(msg);	
		}
		return tfcs.get(0);
	}
	
	
	/**
	 * Busca funcionario por Id  
	 *
	 * @param Long id
	 * @return FuncionarioView, que cumplen con la condición.
	 */
	public FuncionarioView findById(Long id) {
		FuncionarioView tfcs = funcionarioRepository.findById(id).get();
		if(tfcs==null)
		{
			String msg= MyExceptionUtility.buildExceptionJsonString
					( "error.entity_not_exist.message", 
							id.toString(), 
					  this.getClass(),
					  "findById",
					  EnumTypeExceptions.INFO,
					  EnumCodeExceptions.DATA_NOT_FOUND_DB, messageSource);
			throw new MyNotFoundException(msg);	
			
		}		
		return tfcs;
	}
	
	
	/**
	 * Busca funcionario por Identificacion  
	 *
	 * String perIdentificacion
	 * @return FuncionarioView, que cumplen con la condición.
	 */
	public FuncionarioView findByPerIdentificacion( String perIdentificacion) {
		List<FuncionarioView> tfcs = funcionarioRepository.findByPerIdentificacion(perIdentificacion);
		if(tfcs==null || tfcs.size()==0)
		{
			String msg= MyExceptionUtility.buildExceptionJsonString
					( "error.entity_not_exist.message", 
							perIdentificacion.toString(), 
					  this.getClass(),
					  "findByPerIdentificacion",
					  EnumTypeExceptions.INFO,
					  EnumCodeExceptions.DATA_NOT_FOUND_DB, messageSource);
			throw new MyNotFoundException(msg);
			
		}
			return tfcs.get(0);
	}

    
	
	/**
	 * Busca funcionarios por proyecto y tpefId  
	 *
	 * @param Long proyId
	 * @param Long tpefId 
	 * @return List<FuncionarioView>, que cumplen con la condición.
	 */
	public List<FuncionarioView> findByProyIdAndTpefId(Long proyId, Long tpefId) {
		List<FuncionarioView> tfcs = funcionarioRepository.findByProyIdAndTpefId(proyId, tpefId);
		if (tfcs == null || tfcs.size()==0) {
			String msg= MyExceptionUtility.buildExceptionJsonString
					( "error.entity_not_exist.message", 
							proyId.toString()+" "+tpefId.toString(), 
					  this.getClass(),
					  "findByProyIdAndTpefId",
					  EnumTypeExceptions.INFO,
					  EnumCodeExceptions.DATA_NOT_FOUND_DB, messageSource);
			throw new MyNotFoundException(msg);
		
		}	
		return tfcs;
	}
}
