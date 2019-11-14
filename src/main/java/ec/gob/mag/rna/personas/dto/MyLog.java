package ec.gob.mag.rna.personas.dto;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyLog {
	@ApiModelProperty(value = "Nombre del proyecto")
	private String proyecto;

	@ApiModelProperty(value = "Nombre del la clase")
	private String clase;

	@ApiModelProperty(value = "Excepcion")
	private String excepcion;

	@ApiModelProperty(value = "Codigo de Excepcion")
	private String codigoExcepcion;

	@ApiModelProperty(value = "Tipo de Excepcion")
	private String tipoExcepcion;

	@ApiModelProperty(value = "stack de Excepcion")
	private String stack;

	@ApiModelProperty(value = "Este campo almacena la fecha en la que se produjo la excepcion")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;

}