package lu.rbc.robotsstoreapi.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lu.rbc.robotsstoreapi.domain.model.Robot;
import lu.rbc.robotsstoreapi.exception.InvalidDataException;
import lu.rbc.robotsstoreapi.exception.RobotNotFoundException;
import lu.rbc.robotsstoreapi.fixture.RobotFixture;
import lu.rbc.robotsstoreapi.repository.RobotRepository;
import lu.rbc.robotsstoreapi.service.impl.RobotServiceImpl;

import static org.mockito.Matchers.any;

/**
 * Created by Hedi Ayed on 06/03/2018.
 *
 *
 * --------------------
 *
 * @author Hedi Ayed
 */
@RunWith(MockitoJUnitRunner.class)
public class RobotServiceTest {

    @Mock
    private RobotRepository robotRepository;

    private RobotService robotService;

    @Before
    public void setUp(){
        robotService = new RobotServiceImpl(robotRepository);
    }

    @Test
    public void createRobot_created() throws InvalidDataException {
        //Given
        Robot robot = RobotFixture.createRobot();
        Mockito.when(robotRepository.save(robot)).thenReturn(robot);
        Mockito.when(robotRepository.findByCode(any(String.class))).thenReturn(Optional.empty());

        //When
        robot = robotService.createRobot(robot);

        //Then
        Assert.assertNotNull(robot);
    }

    @Test(expected = InvalidDataException.class)
    public void createRobot_codeUsed() throws InvalidDataException {
        //Given
        Robot robot = RobotFixture.createRobot();
        Mockito.when(robotRepository.save(robot)).thenReturn(robot);
        Mockito.when(robotRepository.findByCode(any(String.class)))
                .thenReturn(Optional.of(RobotFixture.createRobot()));

        //When
        robot = robotService.createRobot(robot);

        //Then
        Assert.assertNotNull(robot);
    }

    @Test
    public void findRobots_found() {
        //Given
        Mockito.when(robotRepository.findAll()).thenReturn(Collections.singleton(RobotFixture.createRobot()));

        //When
        List<Robot> robots = robotService.getRobots();

        //Then
        Assert.assertNotNull(robots);
        Assert.assertTrue(robots.size() == 1);
    }

    @Test
    public void findRobot_found(){
        //Given
        Robot robot = RobotFixture.createRobot();
        Mockito.when(robotRepository.findOne(robot.getId())).thenReturn(robot);

        //When
        Optional<Robot> optional = robotService.findRobotById(robot.getId());

        //Then
        Assert.assertTrue(optional.isPresent());
    }

    @Test
    public void findRobot_notFound(){
        //Given
        Mockito.when(robotRepository.findOne(1L)).thenReturn(null);

        //When
        Optional<Robot> optional = robotService.findRobotById(1L);

        //Then
        Assert.assertTrue(!optional.isPresent());
    }

    @Test
    public void updateRobot_updated() throws RobotNotFoundException, InvalidDataException {
        //Given
        Robot robot = RobotFixture.createRobot();
        Mockito.when(robotRepository.save(any(Robot.class))).thenReturn(robot);
        Mockito.when(robotRepository.findOne(robot.getId()))
                .thenReturn(RobotFixture.createRandomRobot());
        Mockito.when(robotRepository.findByCode(robot.getCode()))
                .thenReturn(Optional.empty());

        //When
        Robot newRobot = robotService.updateRobot(robot.getId(), robot);

        //Then
        Assert.assertNotNull(newRobot);
        Assert.assertEquals(robot.getId(), newRobot.getId());
        Assert.assertEquals(robot.getCode(), newRobot.getCode());
        Assert.assertEquals(robot.getBrand(), newRobot.getBrand());
        Assert.assertEquals(robot.getCategory(), newRobot.getCategory());
        Assert.assertEquals(robot.getDescription(), newRobot.getDescription());
        Assert.assertEquals(robot.getImage(), newRobot.getImage());
        Assert.assertEquals(robot.getName(), newRobot.getName());
        Assert.assertEquals(robot.getPrice(), newRobot.getPrice(), 0);
        Assert.assertEquals(robot.getQuantity(), newRobot.getQuantity());
        Assert.assertNotNull(newRobot.getFunctions());
        Assert.assertTrue(!newRobot.getFunctions().isEmpty());
        Assert.assertEquals(robot.getFunctions(), newRobot.getFunctions());
    }

    @Test(expected = RobotNotFoundException.class)
    public void updateRobot_notFound() throws RobotNotFoundException, InvalidDataException {
        //Given
        Robot robot = RobotFixture.createRobot();
        Mockito.when(robotRepository.findOne(robot.getId())).thenReturn(null);

        //Then
        robotService.updateRobot(robot.getId(), robot);
    }

    @Test(expected = InvalidDataException.class)
    public void updateRobot_codeIsUsed() throws RobotNotFoundException, InvalidDataException {
        //Given
        Robot robot = RobotFixture.createRobot();
        Mockito.when(robotRepository.save(any(Robot.class))).thenReturn(robot);
        Mockito.when(robotRepository.findOne(robot.getId()))
                .thenReturn(RobotFixture.createRandomRobot());
        Mockito.when(robotRepository.findByCode(robot.getCode())).thenReturn(Optional.of(robot));

        //Then
        robotService.updateRobot(robot.getId(), robot);
    }

    @Test
    public void deleteRobot_deleted() throws RobotNotFoundException {
        //Given
        Mockito.doNothing().when(robotRepository).delete(any(Long.class));
        Mockito.when(robotRepository.findOne(1L)).thenReturn(RobotFixture.createRobot());

        //Then
        robotService.deleteRobot(1L);
    }

    @Test(expected = RobotNotFoundException.class)
    public void deleteRobot_notFound() throws RobotNotFoundException {
        //Given
        Mockito.doNothing().when(robotRepository).delete(any(Long.class));
        Mockito.when(robotRepository.findOne(1L)).thenReturn(null);

        //Then
        robotService.deleteRobot(1L);
    }

}
