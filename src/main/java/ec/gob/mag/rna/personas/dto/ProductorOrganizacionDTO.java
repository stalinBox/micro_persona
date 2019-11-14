package ec.gob.mag.rna.personas.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductorOrganizacionDTO {
	@ApiModelProperty(notes = "Id de Persona Tipo", position = 1)
	@JsonProperty("petiId")
	@JsonInclude(Include.NON_NULL)
	private Long petiId;

	@ApiModelProperty(notes = "Id de la Organizacion", position = 2)
	@JsonProperty("orgId")
	@JsonInclude(Include.NON_NULL)
	private Long orgId;
}
