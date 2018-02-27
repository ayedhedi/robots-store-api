package lu.rbc.robotsstoreapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import lombok.extern.slf4j.Slf4j;
import lu.rbc.robotsstoreapi.domain.model.Robot;
import lu.rbc.robotsstoreapi.repository.RobotRepository;

/**
 * Created by Hedi Ayed on 26/02/2018.
 *
 *
 * --------------------
 *
 * @author Hedi Ayed
 */
@Slf4j
@Service
public class RobotService {

    private final RobotRepository robotRepository;

    @Autowired
    public RobotService(RobotRepository robotRepository) {
        this.robotRepository = robotRepository;
    }


    /**
     * Create and save a new Robot
     * @param robot the robot to save
     * @return the persisted {@link Robot robot} object
     */
    public Robot createRobot(Robot robot) {
        log.info("Creating new robot instance {}", robot);
        return robotRepository.save(robot);
    }

    /**
     * Finds and returns all the robots from the repository
     * @return a {@link java.util.List list} of {@link Robot robots}
     */
    public List<Robot> getRobots() {
        log.info("Finding the list of robots");
        return StreamSupport.stream(robotRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
