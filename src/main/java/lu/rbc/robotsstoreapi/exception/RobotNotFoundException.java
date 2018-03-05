package lu.rbc.robotsstoreapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Hedi Ayed on 05/03/2018.
 *
 *
 * --------------------
 *
 * @author Hedi Ayed
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Robot Not Found")
public class RobotNotFoundException extends Exception {

    public RobotNotFoundException(long id) {
        super("RobotNotFoundException with id: "+id);
    }
}
