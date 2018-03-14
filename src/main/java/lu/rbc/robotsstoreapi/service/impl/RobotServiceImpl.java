package lu.rbc.robotsstoreapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import lombok.extern.slf4j.Slf4j;
import lu.rbc.robotsstoreapi.domain.model.Robot;
import lu.rbc.robotsstoreapi.exception.InvalidDataException;
import lu.rbc.robotsstoreapi.exception.RobotNotFoundException;
import lu.rbc.robotsstoreapi.repository.RobotRepository;
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
@Service
public class RobotServiceImpl implements RobotService{

    private final RobotRepository robotRepository;

    @Autowired
    public RobotServiceImpl(RobotRepository robotRepository) {
        this.robotRepository = robotRepository;
    }


    /**
     * Create and save a new Robot
     * @param robot the robot to save
     * @return the persisted {@link Robot robot} object
     */
    @Override
    public Robot createRobot(Robot robot) throws InvalidDataException {
        log.info("[SRV][ROBOT] Creating new robot instance {}", robot);
        robot.setId(null);

        //cannot create robot with the same code
        Optional<Robot> robotOptional = robotRepository.findByCode(robot.getCode());
        if (robotOptional.isPresent()) {
            log.warn("Cannot create robot: the same code is user {}", robot.getCode());
            throw new InvalidDataException("Robot code is used");
        }else {
            return robotRepository.save(robot);
        }
    }

    /**
     * Finds and returns all the robots from the repository
     * @return a {@link java.util.List list} of {@link Robot robots}
     */
    @Override
    public List<Robot> getRobots() {
        log.info("[SRV][ROBOT] Finding the list of robots");
        return StreamSupport.stream(robotRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Find one robot by its id
     * @param id the id of the {@link Robot robot} to find
     * @return an {@link Optional optional}
     */
    @Override
    public Optional<Robot> findRobotById(long id) {
        log.info("[SRV][ROBOT] Looking for robot {}", id);
        Robot robot = robotRepository.findOne(id);
        return robot == null ? Optional.empty() : Optional.of(robot);
    }

    /**
     * Update a robot, given by its id/
     * @param id the id of the robot to update
     * @param newRobot the new robot
     * @return the new persisted robot
     * @throws RobotNotFoundException: thrown if the robot not found
     */
    @Override
    public Robot updateRobot(long id, Robot newRobot) throws RobotNotFoundException, InvalidDataException {
        log.info("[SRV][ROBOT] Updating robot {} ", id);
        Optional<Robot> optionalRobot = findRobotById(id);
        if (!optionalRobot.isPresent()) {
            throw new RobotNotFoundException(id);
        }else {
            Robot old = optionalRobot.get();

            //if code is modified and already exists throws error
            if (!Objects.equals(old.getCode(), newRobot.getCode()) &&
                    robotRepository.findByCode(newRobot.getCode()).isPresent()){
                throw new InvalidDataException("Robot code is used");
            }


            old.setAvailable(newRobot.isAvailable());
            old.setImage(newRobot.getImage());
            old.setQuantity(newRobot.getQuantity());
            old.setPrice(newRobot.getPrice());
            old.setDescription(newRobot.getDescription());
            old.setBrand(newRobot.getBrand());
            old.setCode(newRobot.getCode());
            old.setCategory(newRobot.getCategory());
            old.setName(newRobot.getName());
            old.setFunctions(new HashSet<>(newRobot.getFunctions()));
            return robotRepository.save(old);
        }
    }

    /**
     * Delete robot with an id
     * @param id the id of the robot to delete
     * @throws RobotNotFoundException
     */
    @Override
    public void deleteRobot(long id) throws RobotNotFoundException {
        log.info("[SRV][ROBOT] Delete robot {} ", id);
        Optional<Robot> optionalRobot = findRobotById(id);
        if (!optionalRobot.isPresent()) {
            throw new RobotNotFoundException(id);
        }else {
            robotRepository.delete(id);
        }
    }
}
