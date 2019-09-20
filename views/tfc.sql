
create VIEW tfcs as
 
 SELECT row_number() OVER () AS id,
    per.per_id,
    proy.proy_id,
    ps.ups_id,
    ps.ups_id_padre,
    usu.usu_id,
    usup.usup_id,
    upef.upef_id,
    pef.tpef_id,
    per.per_identificacion,
    per.per_nombre,
    per.per_apellido,
    per.per_dir_domicilio,
    per.per_celular,
    per.per_correo,
    per.per_fecha_nac
   FROM sc_organizacion.proyecto proy
     JOIN sc_organizacion.proyecto_servicio proyserv ON proy.proy_id = proyserv.proy_id AND proyserv.ps_estado = 11 AND proyserv.ps_eliminado = false
     JOIN sc_seguridad.usuario_perfil_ps ps ON ps.ps_id = proyserv.ps_id AND ps.ups_estado = 11 AND ps.ups_eliminado = false
     JOIN sc_seguridad.usuario_perfil upef ON upef.upef_id = ps.upef_id AND upef.upef_estado = 11 AND upef.upef_eliminado = false
     JOIN sc_seguridad.perfil pef ON pef.pef_id = upef.pef_id AND pef.pef_estado = 11 AND pef.pef_eliminado = false
     JOIN sc_seguridad.usuario usu ON usu.usu_id = upef.usu_id AND usu.usu_estado = 11 AND usu.usu_eliminado = false
     JOIN sc_seguridad.usuario_persona usup ON usup.usup_id = usu.usup_id AND usup.usup_estado = 11 AND usup.usup_eliminado = false
     JOIN sc_organizacion.persona per ON per.per_id = usup.per_id AND per.per_estado = 11 AND per.per_eliminado = false
  WHERE upef.upef_estado = 11 AND upef.upef_eliminado = false AND per.per_nombre!='' AND per.per_apellido!=''
  ORDER BY per.per_nombres;