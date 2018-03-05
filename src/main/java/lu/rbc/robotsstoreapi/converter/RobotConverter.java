package lu.rbc.robotsstoreapi.converter;

import org.springframework.core.convert.converter.Converter;

import java.util.HashSet;

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
        BasicRobot basic = new BasicRobot();
        basic.setId(robot.getId());
        basic.setName(robot.getName());
        basic.setCategory(robot.getCategory());
        basic.setCode(robot.getCode());
        basic.setBrand(robot.getBrand());
        basic.setDescription(robot.getDescription());
        basic.setPrice(robot.getPrice());
        basic.setQuantity(robot.getQuantity());
        basic.setImage(robot.getImage());
        basic.setAvailable(robot.isAvailable());
        basic.setFunctions(new HashSet<>());
        robot.getFunctions().forEach(robotFunction -> basic.getFunctions().add(robotFunction));
        return basic;
    }
}
