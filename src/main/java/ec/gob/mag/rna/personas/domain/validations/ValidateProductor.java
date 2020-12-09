package ec.gob.mag.rna.personas.domain.validations;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ec.gob.mag.rna.personas.domain.constraint.OneOfInteger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//============== LOMBOK =============
@Data
@Getter
@Setter
@ToString(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
//========== JPA ======================
@Entity
public class ValidateProductor implements Serializable {
	private static final long serialVersionUID = -2262203418770500667L;

	@ApiModelProperty(value = "Este campo es  la clave primaria de la tabla productor")
	@Id
	@Column(name = "pro_id", unique = true, nullable = false)
	@JsonProperty("proId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ApiModelProperty(value = "368=act.agricola 369=act.pecuaria 370=act.forestal...")
	@Column(name = "cat_act_economica")
	@JsonProperty("catActEconomica")
	@JsonInclude(Include.NON_NULL)
	@OneOfInteger(value = { 501, 502, 503, 504, 505, 506, 507,
			508 }, domainShow = "[501, 502, 503, 504, 505, 506, 507, 508]")
	private Long catActEconomica;

	@ApiModelProperty(value = "Es su fuente de ingreso? SI-NO")
	@Column(name = "cat_fuente_ingreso")
	@JsonProperty("catFuenteIngreso")
	@JsonInclude(Include.NON_NULL)
	@OneOfInteger(value = { 393, 394, 728, 729, 730, 731, 732 }, domainShow = "[393, 394, 728, 729, 730, 731, 732]")
	private Long catFuenteIngreso;

	@ApiModelProperty(value = "El productor o propietario vive en el terreno:S,N")
	@Column(name = "pro_vive_terreno", length = 1)
	@JsonProperty("proViveTerreno")
	@JsonInclude(Include.NON_NULL)
	private String proViveTerreno;

	@ApiModelProperty(value = "11=activo  12=inactivo")
	@Column(name = "pro_estado")
	@JsonProperty("proEstado")
	@JsonInclude(Include.NON_NULL)
	private Integer proEstado;

	@ApiModelProperty(value = "Este campo almacena los valores f =false para eliminado logico  y t= true para indicar que está activo")
	@Column(name = "pro_eliminado")
	@JsonProperty("proEliminado")
	@JsonInclude(Include.NON_NULL)
	private Boolean proEliminado;

	@ApiModelProperty(value = "Este campo almacena el codigo del usuario que realiza el ingreso de información, Campo que almacena el usuario que registra a la persona")
	@Column(name = "pro_reg_usu")
	@JsonProperty("proRegUsu")
	@JsonInclude(Include.NON_NULL)
	@NotNull(message = "_error.field_notnull_constraint.message")
	private Long proRegUsu;

	@ApiModelProperty(value = "Este campo almacena la fecha en la que el usuario realiza el ingreso de información")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "pro_reg_fecha", length = 29)
	@CreationTimestamp
	@JsonProperty("proRegFecha")
	@JsonInclude(Include.NON_NULL)
	private Date proRegFecha;

	@ApiModelProperty(value = "Este campo almacena el codigo del usuario que realiza la actualización de información")
	@Column(name = "pro_act_usu")
	@JsonProperty("proActUsu")
	@JsonInclude(Include.NON_NULL)
	private Long proActUsu;

	@ApiModelProperty(value = "Este campo almacena la fecha en la que hizo la actualización el usuario")
	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	@Column(name = "pro_act_fecha", length = 29)
	@JsonProperty("proActFecha")
	@JsonInclude(Include.NON_NULL)
	private Date proActFecha;

	@ApiModelProperty(value = "Numero de Personas que trabajan")
	@Column(name = "pro_num_personas_remuneradas")
	@JsonProperty("proNumPersonasRemuneradas")
	@JsonInclude(Include.NON_NULL)
	private Long proNumPersonasRemuneradas;

	@ApiModelProperty(value = "Numero de Personas que no trabajan")
	@Column(name = "pro_num_personas_no_remuneradas")
	@JsonProperty("proNumPersonasNoRemuneradas")
	@JsonInclude(Include.NON_NULL)
	private Long proNumPersonasNoRemuneradas;

	@ApiModelProperty(value = "Numero de Personas con trabajo temporal")
	@Column(name = "pro_num_remuneradas_temporal")
	@JsonProperty("proNumRemuneradasTemporal")
	@JsonInclude(Include.NON_NULL)
	private Long proNumRemuneradasTemporal;

	@ApiModelProperty(value = "Total personas con trabajo remuneradas y temporales")
	@Column(name = "pro_total_mano_obra")
	@JsonProperty("proTotalManoObra")
	@JsonInclude(Include.NON_NULL)
	private Long proTotalManoObra;

	@Column(name = "pro_grupo_0_12")
	@JsonProperty("proGrupo_0_12")
	@JsonInclude(Include.NON_NULL)
	private Long proGrupo_0_12;

	@Column(name = "pro_grupo_12_18")
	@JsonProperty("proGrupo_12_18")
	@JsonInclude(Include.NON_NULL)
	private Long proGrupo_12_18;

	@Column(name = "pro_grupo_18_60")
	@JsonProperty("proGrupo_18_60")
	@JsonInclude(Include.NON_NULL)
	private Long proGrupo_18_60;

	@Column(name = "pro_grupo_mayor_60")
	@JsonProperty("proGrupoMayor_60")
	@JsonInclude(Include.NON_NULL)
	private Long proGrupoMayor_60;

	@Column(name = "pro_grupo_especiales_0_12")
	@JsonProperty("proGrupoEspeciales_0_12")
	@JsonInclude(Include.NON_NULL)
	private Long proGrupoEspeciales_0_12;

	@Column(name = "pro_grupo_especiales_12_18")
	@JsonProperty("proGrupoEspeciales_12_18")
	@JsonInclude(Include.NON_NULL)
	private Long proGrupoEspeciales_12_18;

	@Column(name = "pro_grupo_especiales_18_60")
	@JsonProperty("proGrupoEspeciales_18_60")
	@JsonInclude(Include.NON_NULL)
	private Long proGrupoEspeciales_18_60;

	@Column(name = "pro_grupo_especiales_mayor_60")
	@JsonProperty("proGrupoEspecialesMayores60")
	@JsonInclude(Include.NON_NULL)
	private Long proGrupoEspecialesMayores60;

	@Column(name = "pro_total_recibe_bono_desarrollo")
	@JsonProperty("proTotalRecibeBonoDesarrollo")
	@JsonInclude(Include.NON_NULL)
	private Long proTotalRecibeBonoDesarrollo;

	/********** RELACIONES JPA ***********/
	@ApiModelProperty(value = "Este campo es  la clave primaria de la tabla persona tipo")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "peti_id")
	@JsonProperty("personaTipo")
	@JsonInclude(Include.NON_NULL)
	@JsonBackReference(value = "persona-tipos-productor")
	private ValidatePersonaTipo personaTipo;

}
