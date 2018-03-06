package lu.rbc.robotsstoreapi.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import lu.rbc.robotsstoreapi.domain.dto.BasicError;
import lu.rbc.robotsstoreapi.domain.enums.ErrorType;
import lu.rbc.robotsstoreapi.exception.InvalidDataException;
import lu.rbc.robotsstoreapi.exception.RobotNotFoundException;

/**
 * Created by Hedi Ayed on 05/03/2018.
 *
 *
 * --------------------
 *
 * @author Hedi Ayed
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RobotNotFoundException.class)
    public BasicError handleRobotNotFoundException(HttpServletRequest request, RobotNotFoundException ex){
        log.info(" URL="+request.getRequestURL());
        return new BasicError(ErrorType.USER, ex.getMessage());
    }

    @ExceptionHandler(InvalidDataException.class)
    public BasicError handleInvalidDataException(HttpServletRequest request, InvalidDataException ex) {
        log.info(" URL="+request.getRequestURL());
        return new BasicError(ErrorType.USER, ex.getMessage());
    }
}
