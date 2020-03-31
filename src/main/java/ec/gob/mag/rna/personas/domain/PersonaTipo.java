package ec.gob.mag.rna.personas.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import ec.gob.mag.rna.personas.dto.PredioDTO;

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
@Table(name = "persona_tipo", schema = "sc_organizacion", uniqueConstraints = @UniqueConstraint(columnNames = {
		"area_id", "per_id", "cat_tipo_per" }))
public class PersonaTipo implements Serializable {
	private static final long serialVersionUID = 5797912094414225146L;

	@ApiModelProperty(value = "Este campo es  la clave primaria de la tabla Persona Tipo")
	@Id
	@Column(name = "peti_id", unique = true, nullable = false)
	@JsonProperty("petiId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ApiModelProperty(value = "tabla area 1=MinisteriorAgr 2=Ecuador..")
	@JsonProperty("areaId")
	@Column(name = "area_id", nullable = false)
	@JsonInclude(Include.NON_NULL)
	private Long areaId;

	@ApiModelProperty(value = "tabla cargo_puesto Ejemplo: 1=Coord.Proy 2=Lider de area 3=Analista Sistemas 4=Funcionario MAG..")
	@Column(name = "carg_id", nullable = false)
	@JsonProperty("cargoId")
	@JsonInclude(Include.NON_NULL)
	private Long cargoId;

	@ApiModelProperty(value = "45=Funcionario 46=Ciudadano ...")
	@Column(name = "cat_tipo_per")
	@JsonProperty("catTipoPer")
	@JsonInclude(Include.NON_NULL)
	private Long catTipoPer;

	@ApiModelProperty(value = "Comentarios observaciones")
	@Column(name = "peti_observacion", length = 150)
	@JsonProperty("petiObservacion")
	@JsonInclude(Include.NON_NULL)
	private String petiObservacion;

	@ApiModelProperty(value = "Migración de datos")
	@Column(name = "peti_fuente", length = 16)
	@JsonProperty("petiFuente")
	@JsonInclude(Include.NON_NULL)
	private String petiFuente;

	@ApiModelProperty(value = "Fecha de migración de datos")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "peti_fuente_fecha", length = 29)
	@JsonProperty("petiFuenteFecha")
	@JsonInclude(Include.NON_NULL)
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
	private List<Productor> productor;

	@ApiModelProperty(value = "Campo predio")
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "personaTipo")
	@JsonProperty("predios")
	@JsonInclude(Include.NON_NULL)
	@JsonManagedReference(value = "persona-tipos-predio")
	private Set<PredioDTO> predios;

	@ApiModelProperty(value = "Este campo es  la clave primaria de la tabla Persona")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "per_id")
	@JsonProperty("persona")
	@JsonInclude(Include.NON_NULL)
	@JsonBackReference(value = "persona-persona-tipos")
	private Persona persona;

	@PrePersist
	public void prePersist() {
		this.petiEstado = 11;
		this.petiActFecha = null;
		this.petiActUsu = null;
		this.petiEliminado = false;
		this.petiRegFecha = new Date();
	}

}
