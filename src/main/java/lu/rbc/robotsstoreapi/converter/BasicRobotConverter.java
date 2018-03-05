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
public class BasicRobotConverter implements Converter<BasicRobot, Robot> {

    @Override
    public Robot convert(BasicRobot basicRobot) {
        Robot robot = new Robot();
        robot.setId(basicRobot.getId());
        robot.setName(basicRobot.getName());
        robot.setCategory(basicRobot.getCategory());
        robot.setCode(basicRobot.getCode());
        robot.setBrand(basicRobot.getBrand());
        robot.setDescription(basicRobot.getDescription());
        robot.setPrice(basicRobot.getPrice());
        robot.setQuantity(basicRobot.getQuantity());
        robot.setImage(basicRobot.getImage());
        robot.setAvailable(basicRobot.getAvailable());
        robot.setFunctions(new HashSet<>());
        basicRobot.getFunctions()
                .forEach(robotFunction -> robot.getFunctions().add(robotFunction));
        return robot;
    }
}
