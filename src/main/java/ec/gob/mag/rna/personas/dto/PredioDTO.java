package ec.gob.mag.rna.personas.dto;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import ec.gob.mag.rna.personas.domain.PersonaTipo;

import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
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
//@EqualsAndHashCode(of="preId")
@NoArgsConstructor
@AllArgsConstructor
@Builder
//========== JPA ======================
@Entity
@Table(name = "predio", schema = "sc_agricola")
public class PredioDTO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(value = "Este campo es  la clave primaria de la tabla predio", position = 1)
	@Column(name = "pre_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("preId")
	private Long id;

	@ApiModelProperty(value = "Predio nombre", position = 8)
	@Column(name = "pre_nombre", length = 256)
	@JsonProperty("preNombre")
	@JsonInclude(Include.NON_NULL)
	private String preNombre;

	@ApiModelProperty(value = "Este campo es  la clave primaria de la tabla ubicacion", position = 2)
	@Column(name = "ubi_id")
	@JsonProperty("ubiId")
	@JsonInclude(Include.NON_NULL)
	private Long ubiId;

	@ApiModelProperty(value = "11=activo  12=inactivo", position = 3)
	@Column(name = "pre_estado", length = 10)
	@JsonProperty("preEstado")
	@JsonInclude(Include.NON_NULL)
	private Long preEstado;

	@ApiModelProperty(value = "Este campo almacena los valores f =false para eliminado logico  y t= true para indicar que está activo", position = 4)
	@Column(name = "pre_eliminado", length = 5)
	@JsonProperty("preEliminado")
	@JsonInclude(Include.NON_NULL)
	private Boolean preEliminado;

	@ApiModelProperty(value = "Este campo es la clave primaria de la tabla persona tipo", position = 5)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "peti_id")
	@JsonProperty("personaTipo")
	@JsonInclude(Include.NON_NULL)
	@JsonBackReference(value = "persona-tipos-predio")
	private PersonaTipo personaTipo;

}
