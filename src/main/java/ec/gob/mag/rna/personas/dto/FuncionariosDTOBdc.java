package ec.gob.mag.rna.personas.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(of = "usupId")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class FuncionariosDTOBdc {

	@Id
	@ApiModelProperty(value = "Este campo es  la clave primaria de la tabla Persona", required = false, readOnly = true)
	@Column(name = "usup_id")
	@JsonProperty("usupId")
	private Long usupId;

	@ApiModelProperty(value = "Este campo es  la clave primaria de la tabla Persona", required = false, readOnly = true)
	@Column(name = "per_id")
	@JsonProperty("perId")
	private Long perId;

	@ApiModelProperty(value = "Campo que almacena los nombres de la persona", required = false)
	@Column(name = "per_nombres", length = 100)
	@JsonProperty("perNombres")
	@JsonInclude(Include.NON_NULL)
	private String perNombres;

	@ApiModelProperty(value = "Campo que almacena el numero de cedula de la persona", required = true)
	@Column(name = "per_identificacion", unique = true, length = 30)
	@JsonProperty("perIdentificacion")
	@JsonInclude(Include.NON_NULL)
	private String perIdentificacion;

	@ApiModelProperty(value = "Identificación de la aplicacion origen", required = false)
	@Column(name = "rol_id")
	@JsonProperty("rolId")
	@JsonInclude(Include.NON_NULL)
	private Integer rolId;

	@ApiModelProperty(value = "Identificación de la aplicacion origen", required = false)
	@Column(name = "rol_nombre")
	@JsonProperty("rolNombre")
	@JsonInclude(Include.NON_NULL)
	private String rolNombre;

	@ApiModelProperty(value = "Identificación de la aplicacion origen", required = false)
	@Column(name = "apli_id")
	@JsonProperty("apliId")
	@JsonInclude(Include.NON_NULL)
	private Integer apliId;

	@ApiModelProperty(value = "Identificación de la aplicacion origen", required = false)
	@Column(name = "apli_nombre")
	@JsonProperty("apliNombre")
	@JsonInclude(Include.NON_NULL)
	private String apliNombre;
}
