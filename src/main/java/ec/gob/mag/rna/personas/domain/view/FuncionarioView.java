package ec.gob.mag.rna.personas.domain.view;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import io.swagger.annotations.ApiModelProperty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//============== LOMBOK =============
@Getter
@Setter
@ToString(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
//========== JPA ======================
@Entity
@Table(name = "tfcs", schema = "public")
public class FuncionarioView implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4731304523828751692L;

	@Id
	@ApiModelProperty(value = "Este campo es  la clave primaria de la tabla Persona", required = false, readOnly = true)
	@Column(name = "id", unique = true, nullable = false)
	@JsonProperty("id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ApiModelProperty(value = "Campo que extrae el id de la persona", allowableValues = "")
	@Column(name = "per_id")
	@JsonProperty("perId")
	@JsonInclude(Include.NON_NULL)
	private Long perId;

	@ApiModelProperty(value = "Campo que extrae el id de la tabla proyecto", allowableValues = "")
	@Column(name = "proy_id")
	@JsonProperty("proyId")
	@JsonInclude(Include.NON_NULL)
	private Long proyId;

	@ApiModelProperty(value = "Campo que extrae el id de la tabla usuario persona ps", allowableValues = "")
	@Column(name = "ups_id")
	@JsonProperty("upsId")
	@JsonInclude(Include.NON_NULL)
	private Long upsId;

	@ApiModelProperty(value = "Campo que extrae el id de la tabla padre correspondiente a usuario perfil ps", allowableValues = "")
	@Column(name = "ups_id_padre")
	@JsonProperty("upsIdPadre")
	@JsonInclude(Include.NON_NULL)
	private Long upsIdPadre;

	@ApiModelProperty(value = "Campo que extrae el id del usuario", allowableValues = "")
	@Column(name = "usu_id")
	@JsonProperty("usuId")
	@JsonInclude(Include.NON_NULL)
	private Long usuId;

	@ApiModelProperty(value = "Campo que extrae el id de la tabla usuario persona", allowableValues = "")
	@Column(name = "usup_id")
	@JsonProperty("usupId")
	@JsonInclude(Include.NON_NULL)
	private Long usupId;

	@ApiModelProperty(value = "Campo que extrae el id de la usuario perfil", allowableValues = "")
	@Column(name = "upef_id")
	@JsonProperty("upefId")
	@JsonInclude(Include.NON_NULL)
	private Long upefId;

	@ApiModelProperty(value = "Campo que extrae el id del perfil del usuario", allowableValues = "")
	@Column(name = "pef_id")
	@JsonProperty("pefId")
	@JsonInclude(Include.NON_NULL)
	private Long pefId;

	@ApiModelProperty(value = "Campo que extrae el id del perfil", allowableValues = "")
	@Column(name = "tpef_id")
	@JsonProperty("tpefId")
	@JsonInclude(Include.NON_NULL)
	private Long tpefId;

	@ApiModelProperty(value = "Campo que extrae la cedula de la persona", allowableValues = "")
	@Column(name = "per_identificacion")
	@JsonProperty("perIdentificacion")
	@JsonInclude(Include.NON_NULL)
	private String perIdentificacion;

	@ApiModelProperty(value = "Campo que extrae el nombre de la persona", allowableValues = "")
	@Column(name = "nombres_apellidos")
	@JsonProperty("perNombresApellidos")
	@JsonInclude(Include.NON_NULL)
	private String perNombresApellidos;

	@ApiModelProperty(value = "Campo que extrae el nombre de la persona", allowableValues = "")
	@Column(name = "per_nombre")
	@JsonProperty("perNombre")
	@JsonInclude(Include.NON_NULL)
	private String perNombre;

	@ApiModelProperty(value = "Campo que extrae el apellido de la persona")
	@Column(name = "per_apellido")
	@JsonProperty("perApellido")
	@JsonInclude(Include.NON_NULL)
	private String perApellido;

	@ApiModelProperty(value = "Campo que extrae la direccion de la persona")
	@Column(name = "per_dir_domicilio")
	@JsonProperty("perDirDomicilio")
	@JsonInclude(Include.NON_NULL)
	private String perDirDomicilio;

	@ApiModelProperty(value = "Campo que extrae el celular de la persona")
	@Column(name = "per_celular")
	@JsonProperty("perCelular")
	@JsonInclude(Include.NON_NULL)
	private String perCelular;

	@ApiModelProperty(value = "Campo que extrae el correo de la persona")
	@Column(name = "per_correo")
	@JsonProperty("perCorreo")
	@JsonInclude(Include.NON_NULL)
	private String perCorreo;

	@ApiModelProperty(value = "Campo que extrae la fecha de nacimiento del persona", required = false)
	@Temporal(TemporalType.DATE)
	@Column(name = "per_fecha_nac", length = 13)
	@JsonProperty("perFechaNac")
	@JsonInclude(Include.NON_NULL)
	private Date perFechaNac;

	@ApiModelProperty(value = "Campo que extrae el id de la ubicacion", allowableValues = "")
	@Column(name = "ubi_id")
	@JsonProperty("ubiId")
	@JsonInclude(Include.NON_NULL)
	private Long ubiId;

	@ApiModelProperty(value = "Nombre del proyecto al que pertenece la persona")
	@Column(name = "proy_nombre", length = 200)
	@JsonProperty("proyNombre")
	@JsonInclude(Include.NON_NULL)
	private String proyNombre;

	@ApiModelProperty(value = "Abreviatura del proyecto al que pertenece la persona")
	@Column(name = "proy_abreviatura", length = 15)
	@JsonProperty("proyAbreviatura")
	@JsonInclude(Include.NON_NULL)
	private String proyAbreviatura;

	@ApiModelProperty(value = "Nombre Perfil Persona")
	@Column(name = "tpef_nombre", length = 100)
	@JsonProperty("tpefNombre")
	@JsonInclude(Include.NON_NULL)
	private String tpefNombre;

}
