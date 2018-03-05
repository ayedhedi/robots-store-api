package lu.rbc.robotsstoreapi.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lu.rbc.robotsstoreapi.domain.enums.ErrorType;

/**
 * Created by Hedi Ayed on 05/03/2018.
 *
 *
 * --------------------
 *
 * @author Hedi Ayed
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasicError {
    private ErrorType type;
    private String message;
}
