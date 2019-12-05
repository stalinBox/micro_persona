package ec.gob.mag.rna.personas.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProyectoDTO {
	@ApiModelProperty(notes = "Id de la persona", position = 1)
	@JsonProperty("perId")
	@JsonInclude(Include.NON_NULL)
	private Long perId;

	@ApiModelProperty(notes = "Id del proyecto", position = 2)
	@JsonProperty("proyId")
	@JsonInclude(Include.NON_NULL)
	private Long proyId;

	@ApiModelProperty(notes = "Nombre del Proyecto", position = 3)
	@JsonProperty("proyNombre")
	@JsonInclude(Include.NON_NULL)
	private String proyNombre;

	@ApiModelProperty(notes = "Abreviatura del Proyecto", position = 4)
	@JsonProperty("proyAbreviatura")
	@JsonInclude(Include.NON_NULL)
	private String proyAbreviatura;

	@ApiModelProperty(notes = "Perfiles", position = 5)
	@JsonProperty("perfiles")
	@JsonInclude(Include.NON_NULL)
	private List<PerfilDTO> perfiles = new ArrayList<PerfilDTO>();

}
