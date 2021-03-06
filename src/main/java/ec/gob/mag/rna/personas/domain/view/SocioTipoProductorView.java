package ec.gob.mag.rna.personas.domain.view;

import java.util.Date;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//============== LOMBOK =============
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//========== JPA ======================
@Entity
@IdClass(SocioProductorId.class)
@Table(name = "vw_socios_tipo_productor", schema = "sc_organizacion")
public class SocioTipoProductorView implements java.io.Serializable {
	private static final long serialVersionUID = 6540875718900178877L;

	@ApiModelProperty(value = "Campo que almacena el nombre de la persona", required = true, allowableValues = "")
	@Column(name = "per_nombre", length = 50)
	@JsonProperty("perNombre")
	@JsonInclude(Include.NON_NULL)
	private String perNombre;

	@ApiModelProperty(value = "Campo que almacena el apellido de la persona", required = true)
	@Column(name = "per_apellido", length = 50)
	@JsonProperty("perApellido")
	@JsonInclude(Include.NON_NULL)
	private String perApellido;

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

	@ApiModelProperty(value = "Campo que almacena la fecha de nacimiento del persona", required = false)
	@Temporal(TemporalType.DATE)
	@Column(name = "per_fecha_nac", length = 13)
	@JsonProperty("perFechaNac")
	@JsonInclude(Include.NON_NULL)
	private Date perFechaNac;

	@ApiModelProperty(value = "Campo almacena la fecha en la que el usuario realiza el ingreso de informaci??n", hidden = true)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "per_reg_fecha", length = 29)
	@JsonProperty("perRegFecha")
	@JsonInclude(Include.NON_NULL)
	private Date perRegFecha;

	@ApiModelProperty(value = "Campo almacena la fecha en la que hizo la actualizaci??n el usuario", readOnly = true)
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "per_act_fecha", length = 29)
	@JsonProperty("perActFecha")
	@JsonInclude(Include.NON_NULL)
	private Date perActFecha;

	@ApiModelProperty(value = "Direcci??n del domicilio de la persona")
	@Column(name = "per_dir_domicilio", length = 500)
	@JsonProperty("perDirDomicilio")
	@JsonInclude(Include.NON_NULL)
	private String perDirDomicilio;

	@ApiModelProperty(value = "N??mero de Tel??fono de la persona")
	@Column(name = "per_telefono", length = 64)
	@JsonProperty("perTelefono")
	@JsonInclude(Include.NON_NULL)
	private String perTelefono;

	@ApiModelProperty(value = "N??mero de Celular de la persona")
	@Column(name = "per_celular", length = 64)
	@JsonProperty("perCelular")
	@JsonInclude(Include.NON_NULL)
	private String perCelular;

	@ApiModelProperty(value = "Correo o email de la persona")
	@Column(name = "per_correo", length = 128)
	@JsonProperty("perCorreo")
	@JsonInclude(Include.NON_NULL)
	private String perCorreo;

	@ApiModelProperty(value = "Tipo de Contribuyente viene del SRI")
	@Column(name = "per_tipo_contr_sri", length = 128)
	@JsonProperty("perTipoContrSri")
	@JsonInclude(Include.NON_NULL)
	private String perTipoContrSri;

	@ApiModelProperty(value = "C??dula de la persona")
	@Column(name = "per_cedula", unique = true, length = 10)
	@JsonProperty("perCedula")
	@JsonInclude(Include.NON_NULL)
	private String perCedula;

	@ApiModelProperty(value = "Lugar Nacimiento de la persona")
	@Column(name = "per_lugar_nac_rc", length = 512)
	@JsonProperty("perLugarNacRc")
	@JsonInclude(Include.NON_NULL)
	private String perLugarNacRc;

	@Id
	@ApiModelProperty(value = "Id de lo Organizacion a la que pertenece")
	@Column(name = "org_id")
	@JsonProperty("orgId")
	@JsonInclude(Include.NON_NULL)
	private Long orgId;

	@ApiModelProperty(value = "Nombre de la organizacion a la que pertenece")
	@Column(name = "org_nombre_comercial", length = 512)
	@JsonProperty("orgNombreComercial")
	@JsonInclude(Include.NON_NULL)
	private String orgNombreComercial;

	@Id
	@ApiModelProperty(value = "Id de peti id como productor")
	@Column(name = "peti_id")
	@JsonProperty("petiId")
	@JsonInclude(Include.NON_NULL)
	private Long petiId;

	@ApiModelProperty(value = "Id de lo Ubicacion del domicilio")
	@Column(name = "ubi_id_domicilio", length = 512)
	@JsonProperty("ubiId")
	@JsonInclude(Include.NON_NULL)
	private Long ubiId;

}
