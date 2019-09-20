package ec.gob.mag.rna.personas.dto;

import java.util.Date;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseUpdate {
	@ApiModelProperty(notes =  "Nombre de la entidad", position=1)
	private String entidad;
	@ApiModelProperty(notes =  "ID de la nueva entidad", position=2)
	private Long id;
}
