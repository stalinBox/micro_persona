package ec.gob.mag.rna.personas.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class UsuarioPerfilDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "upef_id", unique = true)
	@ApiModelProperty(value = "Id del usuario perfil", position = 1)
	@JsonProperty("upefId")
	private Long id;

	@Column(name = "pef_id")
	@ApiModelProperty(value = "Id del perfil", notes = "***", position = 2)
	@JsonProperty("pefId")
	@JsonInclude(Include.NON_NULL)
	private Long pefId;

}
