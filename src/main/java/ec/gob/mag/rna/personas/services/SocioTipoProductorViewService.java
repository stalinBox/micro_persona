package ec.gob.mag.rna.personas.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ec.gob.mag.rna.personas.domain.Persona;
import ec.gob.mag.rna.personas.domain.view.SocioTipoProductorView;
import ec.gob.mag.rna.personas.dto.ProductorOrganizacionDTO;
import ec.gob.mag.rna.personas.exception.EnumCodeExceptions;
import ec.gob.mag.rna.personas.exception.EnumTypeExceptions;
import ec.gob.mag.rna.personas.exception.MyNotFoundException;
import ec.gob.mag.rna.personas.repository.PersonaRepository;
import ec.gob.mag.rna.personas.repository.SocioTipoProductorViewRepository;
import ec.gob.mag.rna.personas.util.MyExceptionUtility;


/**
 * Clase ProductorService.
 *
 * @author PITPPA
 * @version final
 */
@Service("sociotipoproductorviewService")
public class SocioTipoProductorViewService {


	@Autowired
	@Qualifier("sociotipoproductorviewRepository")
	private SocioTipoProductorViewRepository sociotipoproductorRepository;
	
	@Autowired
	@Qualifier("personaRepository")
	private PersonaRepository personaRepository;

	@Autowired
	private MessageSource messageSource;
	
	
	private Persona objectPersona(SocioTipoProductorView sp)
	{
		Persona per= personaRepository.findByPersonaTipos_Id(sp.getPetiId());
		return per;
	}
	
	
	private List<Persona> listPersona(List<SocioTipoProductorView> lsp)
	{
		List<Persona> personas=new ArrayList<Persona>();
		lsp.stream().map( sp->{
			Persona per=this.objectPersona(sp);
			if (per!=null)
			{
				personas.add(per);
			}		
		return sp;
		}).collect(Collectors.toList());
	return personas;
	}

	/**
	 * Buscar productor por cédula
	 *
	 * @param String identificacion
	 * @return Persona, si cumple la condición. Exception, si no cumple.
	 */
	public Persona findProductorByIdentificacion(String identificacion) {
		List<SocioTipoProductorView> productores = sociotipoproductorRepository.findByPerIdentificacion(identificacion);
		if (productores == null || productores.size()==0) {
			String msg = MyExceptionUtility.buildExceptionJsonString("error.entity_not_exist.message",
					identificacion.toString(), this.getClass(), "findProductorByIdentificacion",
					EnumTypeExceptions.INFO, EnumCodeExceptions.DATA_NOT_FOUND_DB, messageSource);
			throw new MyNotFoundException(msg);
		}
		return objectPersona(productores.get(0));
	}

	/**
	 * Buscar productor por Id de Ubicación Domicilio
	 *
	 * @param Long ubiId
	 * @return List<Persona>, si cumple la condición. Exception, si no cumple.
	 */
	public List<Persona> findByUbiId(Long ubiId) {
		List<SocioTipoProductorView> productores = sociotipoproductorRepository.findByUbiId(ubiId);
		if (productores == null || productores.size() == 0) {
			String msg = MyExceptionUtility.buildExceptionJsonString("error.entity_not_exist.message", ubiId.toString(),
					this.getClass(), "findProductorByUbiIdDomicilio", EnumTypeExceptions.INFO,
					EnumCodeExceptions.DATA_NOT_FOUND_DB, messageSource);
			throw new MyNotFoundException(msg);
		}
		return listPersona(productores);
	}

	/**
	 * Buscar productores por Organización Id.
	 *
	 * @param Long ubiId
	 * @return List<Persona>, que cumplen con la condición. Exception, si no cumple.
	 */
	public List<Persona> findByOrgId(Long orgId) {
		List<SocioTipoProductorView> productores = sociotipoproductorRepository.findByOrgId(orgId);
		if (productores == null || productores.size() == 0) {
			String msg = MyExceptionUtility.buildExceptionJsonString("error.entity_not_exist.message", orgId.toString(),
					this.getClass(), "findByOrgId", EnumTypeExceptions.INFO, EnumCodeExceptions.DATA_NOT_FOUND_DB,
					messageSource);
			throw new MyNotFoundException(msg);
		}				
		return listPersona(productores);
	}
	
	
	/**
	 * Buscar productores por Peti Id.
	 *
	 * @param Long petiId
	 * @return List<ProductorOrganizacionDTO>, que cumplen con la condición. Exception, si no cumple.
	 */
	public List<ProductorOrganizacionDTO> findByPetiId(Long petiId) {
		List<SocioTipoProductorView> productores = sociotipoproductorRepository.findByPetiId(petiId);
		if (productores == null || productores.size() == 0) {
			String msg = MyExceptionUtility.buildExceptionJsonString("error.entity_not_exist.message", petiId.toString(),
					this.getClass(), "findByPetiId", EnumTypeExceptions.INFO, EnumCodeExceptions.DATA_NOT_FOUND_DB,
					messageSource);
			throw new MyNotFoundException(msg);
		}
		
		List<ProductorOrganizacionDTO> polist = new ArrayList<ProductorOrganizacionDTO>();
		productores.stream().map( pro ->{
			ProductorOrganizacionDTO po = new ProductorOrganizacionDTO();
			po.setOrgId(pro.getOrgId());
			po.setPetiId(pro.getPetiId());
			polist.add(po);
			return pro;
		}).collect(Collectors.toList());
		
		return polist;
	}
}