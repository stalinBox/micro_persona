package ec.gob.mag.rna.personas.exception.consumer;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CmInternalErrorException extends RuntimeException {
	public CmInternalErrorException(String message) {
		super(message);
	}
}
