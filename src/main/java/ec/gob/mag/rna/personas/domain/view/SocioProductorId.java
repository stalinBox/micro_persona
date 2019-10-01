package ec.gob.mag.rna.personas.domain.view;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocioProductorId implements Serializable {
    private Long petiId; 
    private Long orgId;
    }