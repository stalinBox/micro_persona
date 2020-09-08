package ec.gob.mag.rna.personas.dto;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class PersonaDTOPaginated implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(notes = "Id Persona", position = 1)
	@JsonProperty("id")
	private Long id;

	@ApiModelProperty(value = "Campo que almacena el nombre de la persona", position = 2)
	@JsonProperty("perId")
	private Long perId;

	@ApiModelProperty(value = "Campo que almacena los nombres de la persona", position = 4)
	@JsonProperty("perNombres")
	private String perNombres;

	@ApiModelProperty(value = "Campo que almacena el numero de cedula de la persona", position = 5)
	@JsonProperty("perCedula")
	private String perCedula;

	@ApiModelProperty(value = "Número de Teléfono de la persona", position = 9)
	@JsonProperty("rolNombre")
	private String rolNombre;

	private Integer totalRecords;

}
