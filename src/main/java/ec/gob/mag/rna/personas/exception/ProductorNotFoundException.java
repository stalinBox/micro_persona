package ec.gob.mag.rna.personas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductorNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ProductorNotFoundException(String message) {
		super(message);
	}
}
