package lu.rbc.robotsstoreapi.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import lu.rbc.robotsstoreapi.domain.model.Robot;
import lu.rbc.robotsstoreapi.fixture.RobotFixture;

/**
 * Created by Hedi Ayed on 07/03/2018.
 *
 *
 * --------------------
 *
 * @author Hedi Ayed
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class RobotRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private RobotRepository robotRepository;

    @Test
    public void whenFindByCode_thenReturnRobot(){
        //Given
        Robot robot = RobotFixture.createRobot();
        robot.setId(null);
        entityManager.persistAndFlush(robot);

        //when
        Optional<Robot> robotOptional = robotRepository.findByCode(robot.getCode());

        //Then
        Assert.assertTrue(robotOptional.isPresent());
    }
}



















