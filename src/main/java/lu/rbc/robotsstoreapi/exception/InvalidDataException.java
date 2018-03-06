package lu.rbc.robotsstoreapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Hedi Ayed on 06/03/2018.
 *
 *
 * --------------------
 *
 * @author Hedi Ayed
 */
@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Invalid user data")
public class InvalidDataException extends Exception {

    public InvalidDataException(String message) {
        super(message);
    }
}
