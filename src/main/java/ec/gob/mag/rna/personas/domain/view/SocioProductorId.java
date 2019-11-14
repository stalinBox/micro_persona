package ec.gob.mag.rna.personas.domain.view;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class SocioProductorId implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long petiId;
	private Long orgId;
}