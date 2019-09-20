package ec.gob.mag.rna.personas.exception.consumer;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CmNotFoundException extends RuntimeException {
	public CmNotFoundException(String message) {
		super(message);
	}
}
