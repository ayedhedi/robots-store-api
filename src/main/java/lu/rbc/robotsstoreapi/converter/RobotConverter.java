package lu.rbc.robotsstoreapi.converter;

import org.springframework.core.convert.converter.Converter;

import lu.rbc.robotsstoreapi.domain.dto.BasicRobot;
import lu.rbc.robotsstoreapi.domain.model.Robot;

/**
 * Created by Hedi Ayed on 27/02/2018.
 *
 *
 * --------------------
 *
 * @author Hedi Ayed
 */
public class RobotConverter implements Converter<Robot, BasicRobot>{

    @Override
    public BasicRobot convert(Robot robot) {
        return new BasicRobot(robot.getId(), robot.getName());
    }
}
