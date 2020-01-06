package ec.gob.mag.rna.personas.dto;
import java.io.Serializable;

import javax.persistence.Column;
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
@ToString(of = "perId")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ProductoresDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@ApiModelProperty(notes = "Id Persona", position = 1)
	@JsonProperty("perId")
	private Long perId;

	@ApiModelProperty(value = "Campo que almacena el nombre de la persona", position = 2)
	@JsonProperty("perNombre")
	@Column(name = "perNombre")
	private String perNombre;

	@ApiModelProperty(value = "Campo que almacena el apellido de la persona", position = 3)
	@JsonProperty("perApellido")
	@Column(name = "perApellido")
	private String perApellido;

	@ApiModelProperty(value = "Campo que almacena los nombres de la persona", position = 4)
	@JsonProperty("perNombres")
	@Column(name = "perNombres")
	private String perNombres;

	@ApiModelProperty(value = "Campo que almacena el numero de cedula de la persona", position = 5)
	@JsonProperty("perIdentificacion")
	@Column(name = "perIdentificacion")
	private String perIdentificacion;
	
	@ApiModelProperty(value = "Dirección del domicilio de la persona", position = 6)
	@JsonProperty("perDirDomicilio")
	@Column(name = "perDirDomicilio")
	private String perDirDomicilio;

	@ApiModelProperty(value = "Id de lo Ubicacion del domicilio", position = 7)
	@JsonProperty("ubiId")
	@Column(name = "ubiId")
	private Long ubiId;
		
	@ApiModelProperty(value = "Número de Teléfono de la persona", position = 8)
	@JsonProperty("perTelefono")
	@Column(name = "perTelefono")
	private String perTelefono;

	@ApiModelProperty(value = "Número de Celular de la persona", position = 9)
	@JsonProperty("perCelular")
	@Column(name = "perCelular")
	private String perCelular;

	@ApiModelProperty(value = "Correo o email de la persona", position = 10)
	@JsonProperty("perCorreo")
	@Column(name = "perCorreo")
	private String perCorreo;
	
	@ApiModelProperty(value = "Id de peti id como productor",  position = 11)
	@JsonProperty("petiId")
	@Column(name = "petiId")
	private Long petiId;
	
}
