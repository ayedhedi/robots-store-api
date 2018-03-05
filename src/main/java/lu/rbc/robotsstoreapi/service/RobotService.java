package lu.rbc.robotsstoreapi.service;

import java.util.List;
import java.util.Optional;

import lu.rbc.robotsstoreapi.domain.model.Robot;
import lu.rbc.robotsstoreapi.exception.RobotNotFoundException;

/**
 * Created by Hedi Ayed on 05/03/2018.
 *
 *
 * --------------------
 *
 * @author Hedi Ayed
 */
public interface RobotService {
    Robot createRobot(Robot robot);

    List<Robot> getRobots();

    Optional<Robot> findRobotById(long id);

    Robot updateRobot(long id, Robot newRobot) throws RobotNotFoundException;

    void deleteRobot(long id) throws RobotNotFoundException;
}
