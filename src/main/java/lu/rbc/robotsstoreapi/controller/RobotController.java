package lu.rbc.robotsstoreapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import lu.rbc.robotsstoreapi.domain.dto.BasicRobot;
import lu.rbc.robotsstoreapi.domain.model.Robot;
import lu.rbc.robotsstoreapi.service.RobotService;

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
public class RobotController {

    private final RobotService robotService;
    private final ConversionService conversionService;

    @Autowired
    public RobotController(@Qualifier("ServiceConverter") ConversionService conversionService,
                           RobotService robotService) {

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

    @PostMapping
    public BasicRobot createRobot(@RequestBody BasicRobot robot){
        log.info("[API][POST] create new robot");
        Robot newRobot = robotService.createRobot(conversionService.convert(robot, Robot.class));
        return conversionService.convert(newRobot, BasicRobot.class);
    }


}
