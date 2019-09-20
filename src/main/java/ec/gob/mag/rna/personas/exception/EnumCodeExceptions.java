package ec.gob.mag.rna.personas.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum EnumCodeExceptions {
    DATA_NOT_FOUND_DB ("DATA_NOT_FOUND_DB"),
    ERROR_VALIDATION ("ERROR_VALIDATION"),
    ERROR_UNRECOGNIZABLE ("ERROR_UNRECOGNIZABLE"),
    ERROR_BAD_REQUEST_JSON("ERROR_BAD_REQUEST_JSON"),
    ERROR_DATA_NOT_FOUND_IN_DB("ERROR_DATA_NOT_FOUND_IN_DB"),
    WARNING_CONTRAINT_SHEMA("WARNING_CONTRAINT_SHEMA"),
    ERROR_BAD_REQUEST_PARAMETER("ERROR_BAD_REQUEST_PARAMETER"),
    ERROR_UPDATE_FOR_REQUEST("ERROR_UPDATE_FOR_REQUEST"),
    ERROR_MICRO_SERVICE_OFF("ERROR_MICRO_SERVICE_OFF"),
    ERROR_IN_MICRO_SERVICE("ERROR_IN_MICRO_SERVICE")
    ;
    private final String value;
    
    EnumCodeExceptions(String value) {
        this.value = value;
    }
}
