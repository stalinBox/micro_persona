package ec.gob.mag.rna.personas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.gob.mag.rna.personas.dto.FuncionariosDTOBdc;

@Repository("funcionariosBDCRepository")
public interface FuncionariosBDCRepository extends CrudRepository<FuncionariosDTOBdc, Long> {

	@Query(value = "SELECT  up.usup_id, up.per_id,	p.per_nombres, p.per_identificacion, \n"
			+ "r.rol_id, r.rol_nombre, r.apli_id, apli.apli_nombre FROM sc_seguridad.usuario_persona up\n"
			+ "INNER JOIN  sc_organizacion.persona p ON up.per_id = p.per_id\n"
			+ "INNER JOIN sc_seguridad_sicpas.tbl_rol_usuario ru ON ru.usup_id = up.usup_id\n"
			+ "INNER JOIN sc_seguridad_sicpas.tbl_roles r ON ru.rol_id = r.rol_id\n"
			+ "INNER JOIN sc_seguridad.aplicacion apli on apli.apli_id = r.apli_id and apli_estado = 11 and apli_eliminado = false\n"
			+ "WHERE r.apli_id = :apliId AND up.usup_eliminado = false \n" + "AND up.usup_estado = 11\n"
			+ "AND p.per_eliminado = false AND p.per_estado = 11 and ru.rol_usu_estado = 1 "
			+ "and r.rol_estado = 1", nativeQuery = true)
	List<FuncionariosDTOBdc> findApliId(@Param("apliId") Long apliId);

	@Query(value = "SELECT  up.usup_id, up.per_id, p.per_nombres, p.per_identificacion, r.rol_id,"
			+ "r.rol_nombre, r.apli_id, apli.apli_nombre FROM sc_seguridad.usuario_persona up "
			+ "INNER JOIN  sc_organizacion.persona p ON up.per_id = p.per_id "
			+ "INNER JOIN sc_seguridad_sicpas.tbl_rol_usuario ru ON ru.usup_id = up.usup_id "
			+ "INNER JOIN sc_seguridad_sicpas.tbl_roles r ON ru.rol_id = r.rol_id "
			+ "INNER JOIN sc_seguridad.aplicacion apli on apli.apli_id = r.apli_id and apli_estado = 11 and apli_eliminado = false "
			+ "WHERE r.rol_id = :rolId AND up.usup_eliminado = false AND up.usup_estado = 11 "
			+ "AND p.per_eliminado = false AND p.per_estado = 11 and ru.rol_usu_estado = 1 "
			+ "and r.rol_estado = 1", nativeQuery = true)
	List<FuncionariosDTOBdc> findByRolId(@Param("rolId") Long rolId);
}
