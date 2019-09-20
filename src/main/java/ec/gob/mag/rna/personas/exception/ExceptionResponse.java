package ec.gob.mag.rna.personas.exception;

import java.util.Date;
import java.util.List;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExceptionResponse {
	private HttpStatus status;
	private Date timestamp;
	private String message;
	@JsonInclude(Include.NON_NULL)
	private String details;
	@JsonInclude(Include.NON_NULL)
	private String detailsJson;
	@JsonInclude(Include.NON_NULL)
	private String proyect;
	
	@JsonInclude(Include.NON_NULL)
	private String classe;
	
	@JsonInclude(Include.NON_NULL)
	private String method;
	
	@JsonInclude(Include.NON_NULL)
	private EnumCodeExceptions numberCode;
	

	
	@JsonInclude(Include.NON_NULL)
	private EnumTypeExceptions type;
	
	@JsonInclude(Include.NON_NULL)
	private String stack;
	
}
