package ec.gob.mag.rna.personas.dto;

import java.util.Date;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j;

@Getter
@Setter
@ToString(of="id")
@EqualsAndHashCode(of="id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Log4j
public class ArrayIdRequest {
	@ApiModelProperty(notes =  "ID de la entidad a procesar", position=1)
	private Long id;
}
