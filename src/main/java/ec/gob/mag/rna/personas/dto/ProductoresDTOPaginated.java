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
public class ProductoresDTOPaginated implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(notes = "Id Persona", position = 1)
	@JsonProperty("perId")
	private Long id;

	@ApiModelProperty(value = "Campo que almacena el nombre de la persona", position = 2)
	@JsonProperty("perNombre")
	private String perNombre;

	@ApiModelProperty(value = "Campo que almacena el apellido de la persona", position = 3)
	@JsonProperty("perApellido")
	private String perApellido;

	@ApiModelProperty(value = "Campo que almacena los nombres de la persona", position = 4)
	@JsonProperty("perNombres")
	private String perNombres;

	@ApiModelProperty(value = "Campo que almacena el numero de cedula de la persona", position = 5)
	@JsonProperty("perIdentificacion")
	private String perIdentificacion;

	@ApiModelProperty(value = "Dirección del domicilio de la persona", position = 6)
	@JsonProperty("perDirDomicilio")
	private String perDirDomicilio;

//	@ApiModelProperty(value = "Este campo es  la clave primaria de la tabla predio", position = 7)
//	@JsonProperty("preId")
//	private Long preId;

//	@ApiModelProperty(value = "Id de lo Ubicacion del domicilio", position = 8)
//	@JsonProperty("ubiId")
//	private Long ubiId;

	@ApiModelProperty(value = "Número de Teléfono de la persona", position = 9)
	@JsonProperty("perTelefono")
	private String perTelefono;

	@ApiModelProperty(value = "Número de Celular de la persona", position = 10)
	@JsonProperty("perCelular")
	private String perCelular;

	@ApiModelProperty(value = "Correo o email de la persona", position = 11)
	@JsonProperty("perCorreo")
	private String perCorreo;

	@ApiModelProperty(value = "Id de peti id como productor", position = 12)
	@JsonProperty("petiId")
	private Long petiId;

	private Integer totalRecords;
}
