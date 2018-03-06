package lu.rbc.robotsstoreapi.fixture;

import java.util.HashSet;

import lu.rbc.robotsstoreapi.domain.dto.BasicRobot;
import lu.rbc.robotsstoreapi.domain.enums.RobotCategory;
import lu.rbc.robotsstoreapi.domain.enums.RobotFunction;
import lu.rbc.robotsstoreapi.domain.model.Robot;

/**
 * Created by Hedi Ayed on 06/03/2018.
 *
 *
 * --------------------
 *
 * @author Hedi Ayed
 */
@SuppressWarnings("Duplicates")
public class RobotFixture {

    public static Robot createRobot() {
        Robot robot = new Robot();
        robot.setId(123L);
        robot.setName("robot for testing");
        robot.setCategory(RobotCategory.DOG);
        robot.setCode("robot-123");
        robot.setBrand("tests");
        robot.setDescription("this is a description of robot 123");
        robot.setPrice(12.34f);
        robot.setQuantity(10);
        robot.setImage("image");
        robot.setAvailable(true);
        robot.setFunctions(new HashSet<>());
        robot.getFunctions().add(RobotFunction.CASUAL);
        return robot;
    }

    public static Robot createRandomRobot() {
        Robot robot = new Robot();
        robot.setId(123L);
        robot.setName("Name-"+(Math.random()*1000000));
        robot.setCategory(RobotCategory.values()[(int)(Math.random()*RobotCategory.values().length)]);
        robot.setCode("robot-"+(Math.random()*1000000));
        robot.setBrand("brand-"+(Math.random()*1000000));
        robot.setDescription("description-"+(Math.random()*1000000));
        robot.setPrice((float)((Math.random()*1000000)));
        robot.setQuantity((int)+(Math.random()*1000));
        robot.setImage("image-"+(Math.random()*1000000));
        robot.setAvailable(Math.random() < 0.5);
        robot.setFunctions(new HashSet<>());
        robot.getFunctions().add(RobotFunction.values()[(int)(Math.random()*RobotFunction.values().length)]);
        return robot;
    }

    public static BasicRobot convert(Robot robot) {
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
