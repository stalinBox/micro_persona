package ec.gob.mag.rna.personas.dto;




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
public class PerfilDTO {
	@ApiModelProperty(notes = "Id del Perfil", position=1)
	@JsonProperty("tpefId")
	@JsonInclude(Include.NON_NULL)
	private Long tpefId;
	
	@ApiModelProperty(notes = "Nombre del Perfil", position=2)
	@JsonProperty("tpefNombre")
	@JsonInclude(Include.NON_NULL)
	private String tpefNombre;
	
	@ApiModelProperty(notes = "Id del proyecto", position=3)
	@JsonProperty("proyId")
	@JsonInclude(Include.NON_NULL)
	private Long proyId;
}
