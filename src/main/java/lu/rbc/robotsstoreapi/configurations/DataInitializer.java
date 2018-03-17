package lu.rbc.robotsstoreapi.configurations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import lu.rbc.robotsstoreapi.domain.model.Robot;
import lu.rbc.robotsstoreapi.repository.RobotRepository;

/**
 * Created by Hedi Ayed on 05/03/2018.
 *
 *
 * --------------------
 *
 * @author Hedi Ayed
 */
@Slf4j
@Component
public class DataInitializer implements InitializingBean {

    private final RobotRepository robotRepository;

    public DataInitializer(RobotRepository robotRepository) {
        this.robotRepository = robotRepository;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (robotRepository.count() == 0) {
            log.info("The repository is empty: let's create some robots !!");
            try {
                List<Robot> robots = readRobots();
                robots.forEach(robot -> {
                    if (Math.random()<0.5){
                        robot.setQuantity(0);
                    }
                });
                robotRepository.save(robots);
            }catch ( IOException  ex) {
                log.warn("Cannot read robots from JSON: {}", ex.getMessage());
            }
        }
    }

    private List<Robot> readRobots() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File("src/main/resources/robots.json"),
                new TypeReference<List<Robot>>() { });
    }
}
