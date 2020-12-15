package ec.gob.mag.rna.personas.domain.validations;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import ec.gob.mag.rna.personas.domain.constraint.OneOfInteger;

import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(of = "id")
//========== JPA ======================
@Entity
public class ValidatePersonaTipo implements Serializable {
	private static final long serialVersionUID = 5797912094414225146L;

	@Id
	@ApiModelProperty(value = "Este campo es  la clave primaria de la tabla Persona Tipo")
	@Column(name = "peti_id", unique = true, nullable = false)
	@JsonProperty("petiId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ApiModelProperty(value = "tabla area 1=MinisteriorAgr 2=Ecuador..")
	@JsonProperty("areaId")
	@Column(name = "area_id", nullable = false)
	@JsonInclude(Include.NON_NULL)
	@OneOfInteger(value = { 2 }, domainShow = "[2]")
	private Integer areaId;

	@ApiModelProperty(value = "tabla cargo_puesto Ejemplo: 1=Coord.Proy 2=Lider de area 3=Analista Sistemas 4=Funcionario MAG..")
	@Column(name = "carg_id", nullable = false)
	@JsonProperty("cargoId")
	@JsonInclude(Include.NON_NULL)
	@OneOfInteger(value = { 7 }, domainShow = "[7]")
	private Integer cargoId;

	@ApiModelProperty(value = "45=Funcionario 46=Ciudadano ...")
	@Column(name = "cat_tipo_per")
	@JsonProperty("catTipoPer")
	@JsonInclude(Include.NON_NULL)
	@OneOfInteger(value = { 46 }, domainShow = "[46]")
	private Integer catTipoPer;

	@ApiModelProperty(value = "Comentarios observaciones")
	@Column(name = "peti_observacion", length = 150)
	@JsonProperty("petiObservacion")
	@JsonInclude(Include.NON_NULL)
	private String petiObservacion;

	@ApiModelProperty(value = "Migración de datos")
	@Column(name = "peti_fuente", length = 16)
	@JsonProperty("petiFuente")
	@JsonInclude(Include.NON_NULL)
	@NotBlank(message = "_error.field_notBlank_constraint.message")
	@NotNull(message = "_error.field_notnull_constraint.message")
	private String petiFuente;

	@ApiModelProperty(value = "Fecha de migración de datos")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "peti_fuente_fecha", length = 29)
	@JsonProperty("petiFuenteFecha")
	@JsonInclude(Include.NON_NULL)
	@NotNull(message = "_error.field_notnull_constraint.message")
	private Date petiFuenteFecha;

	@ApiModelProperty(value = "11=activo  12=inactivo")
	@Column(name = "peti_estado")
	@JsonProperty("petiEstado")
	@JsonInclude(Include.NON_NULL)
	private Integer petiEstado;

	@ApiModelProperty(value = "Este campo almacena los valores f =false para eliminado logico  y t= true para indicar que está activo")
	@Column(name = "peti_eliminado")
	@JsonProperty("petiEliminado")
	@JsonInclude(Include.NON_NULL)
	private Boolean petiEliminado;

	@ApiModelProperty(value = "Este campo almacena el codigo del usuario que realiza el ingreso de información, Campo que almacena el usuario que registra a la persona")
	@Column(name = "peti_reg_usu")
	@JsonProperty("petiRegUsu")
	@JsonInclude(Include.NON_NULL)
	@NotNull(message = "_error.field_notnull_constraint.message")
	private Integer petiRegUsu;

	@ApiModelProperty(value = "Este campo almacena la fecha en la que el usuario realiza el ingreso de información")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "peti_reg_fecha", length = 29)
	@CreationTimestamp
	@JsonProperty("petiRegFecha")
	@JsonInclude(Include.NON_NULL)
	private Date petiRegFecha;

	@ApiModelProperty(value = "Este campo almacena el codigo del usuario que realiza la actualización de información")
	@Column(name = "peti_act_usu")
	@JsonProperty("petiActUsu")
	@JsonInclude(Include.NON_NULL)
	private Integer petiActUsu;

	@ApiModelProperty(value = "Este campo almacena la fecha en la que hizo la actualización el usuario")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "peti_act_fecha", length = 29)
	@UpdateTimestamp
	@JsonProperty("petiActFecha")
	@JsonInclude(Include.NON_NULL)
	private Date petiActFecha;

	/*********** RELACIONES JPA ***********/
	@ApiModelProperty(value = "Campo productor")
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "personaTipo", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH }, orphanRemoval = true)
	@JsonProperty("productor")
	@JsonInclude(Include.NON_NULL)
	@JsonManagedReference(value = "persona-tipos-productor")
	private List<ValidateProductor> productor;

	@ApiModelProperty(value = "Este campo es  la clave primaria de la tabla Persona")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "per_id")
	@JsonProperty("persona")
	@JsonInclude(Include.NON_NULL)
	@JsonBackReference(value = "persona-persona-tipos")
	private ValidatePersona persona;
}
