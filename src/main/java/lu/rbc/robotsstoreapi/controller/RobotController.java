package lu.rbc.robotsstoreapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import lu.rbc.robotsstoreapi.domain.dto.BasicRobot;
import lu.rbc.robotsstoreapi.domain.model.Robot;
import lu.rbc.robotsstoreapi.exception.InvalidDataException;
import lu.rbc.robotsstoreapi.exception.RobotNotFoundException;
import lu.rbc.robotsstoreapi.service.impl.RobotServiceImpl;

/**
 * Created by Hedi Ayed on 26/02/2018.
 *
 *
 * --------------------
 *
 * @author Hedi Ayed
 */
@Slf4j
@RestController
@RequestMapping("/api/robots")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('ROLE_USER')")
public class RobotController {

    private final RobotServiceImpl robotService;
    private final ConversionService conversionService;

    @Autowired
    public RobotController(@Qualifier("ServiceConverter") ConversionService conversionService,
                           RobotServiceImpl robotService) {

        this.robotService = robotService;
        this.conversionService = conversionService;
    }


    @GetMapping
    public List<BasicRobot> findRobots() {
        log.info("[API][GET] find robots");
        return robotService.getRobots()
                .stream()
                .map(robot -> conversionService.convert(robot, BasicRobot.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BasicRobot getRobot(@PathVariable long id) throws RobotNotFoundException {
        log.info("[API][GET] get robot {} ", id);
        Optional<Robot> robotOptional = robotService.findRobotById(id);

        if (robotOptional.isPresent()) {
            return conversionService.convert(robotOptional.get(), BasicRobot.class);
        }else {
            throw new RobotNotFoundException(id);
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public BasicRobot createRobot(@RequestBody BasicRobot robot) throws InvalidDataException {
        log.info("[API][POST] create new robot");
        Robot newRobot = robotService.createRobot(conversionService.convert(robot, Robot.class));
        return conversionService.convert(newRobot, BasicRobot.class);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public BasicRobot updateRobot(@PathVariable long id,
                                  @RequestBody BasicRobot robot)
            throws RobotNotFoundException, InvalidDataException {

        log.info("[API][GET] update robot {} ", id);
        Robot newRobot = robotService.updateRobot(id, conversionService.convert(robot, Robot.class));
        return conversionService.convert(newRobot, BasicRobot.class);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteRobot(@PathVariable long id) throws RobotNotFoundException {
        log.info("[API][GET] delete robot {} ", id);
        Optional<Robot> robotOptional = robotService.findRobotById(id);
        if (robotOptional.isPresent()) {
            robotService.deleteRobot(id);
        }else {
            throw new RobotNotFoundException(id);
        }
    }
}
