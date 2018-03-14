package lu.rbc.robotsstoreapi;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.StringWriter;
import java.util.Optional;

import lu.rbc.robotsstoreapi.configurations.security.TokenAuthenticationService;
import lu.rbc.robotsstoreapi.domain.dto.BasicRobot;
import lu.rbc.robotsstoreapi.domain.model.Robot;
import lu.rbc.robotsstoreapi.fixture.RobotFixture;
import lu.rbc.robotsstoreapi.repository.RobotRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Hedi Ayed on 07/03/2018.
 *
 *
 * --------------------
 *
 * @author Hedi Ayed
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = RobotsStoreApiApplication.class)
@AutoConfigureMockMvc
public class RobotApiIntegrationTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private RobotRepository repository;

    @Test
    public void givenNoAuthentication_whenGetRobots_thenStatusForbidden() throws Exception {
        mvc.perform(get("/api/robots"))
                .andExpect(status().is(403));
    }

    @Test
    public void givenInvalidToken_whenGetRobots_thenStatusForbidden() throws Exception {
        mvc.perform(get("/api/robots")
                .header("Authorization", "Bearer this_is_an_invalid_token"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenRobot_whenGetRobots_thenStatusOk() throws Exception {
        RobotFixture.createAndSave(repository);
        String token = TokenAuthenticationService.getToken("user");

        mvc.perform(get("/api/robots")
                .header("Authorization", token))
                .andExpect(status().isOk());
    }

    @Test
    public void givenRobot_whenGetRobotById_thenStatusOk() throws Exception {
        Robot robot = RobotFixture.createAndSave(repository);
        String token = TokenAuthenticationService.getToken("user");

        mvc.perform(get("/api/robots/"+robot.getId())
                .header("Authorization", token))
                .andExpect(status().isOk());
    }

    @Test
    public void givenBasicRobot_whenUserCreate_thenStatusForbidden() throws Exception {
        BasicRobot basicRobot = RobotFixture.convert(RobotFixture.createRandomRobot());
        StringWriter json = new StringWriter();
        (new ObjectMapper()).writeValue(json, basicRobot);

        String token = TokenAuthenticationService.getToken("user");

        //API call is not accepted
        mvc.perform(post("/api/robots")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString()))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));

        //No robot is created with the unique code
        Assert.assertTrue(!repository.findByCode(basicRobot.getCode()).isPresent());
    }

    @Test
    public void givenBasicRobot_whenAdminCreate_thenStatusCreated() throws Exception {
        BasicRobot basicRobot = RobotFixture.convert(RobotFixture.createRandomRobot());
        StringWriter json = new StringWriter();
        (new ObjectMapper()).writeValue(json, basicRobot);

        String token = TokenAuthenticationService.getToken("admin");

        //API call is accepted
        mvc.perform(post("/api/robots")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString()))
                .andExpect(status().is(HttpStatus.CREATED.value()));

        //The robot can be found in the repository
        Assert.assertTrue(repository.findByCode(basicRobot.getCode()).isPresent());
    }

    @Test
    public void givenRobot_whenUserDelete_thenStatusForbidden() throws Exception {
        Robot robot = RobotFixture.createAndSave(repository);

        String token = TokenAuthenticationService.getToken("user");

        //API call is not accepted
        mvc.perform(delete("/api/robots/"+robot.getId())
                .header("Authorization", token))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));

        //The robot is not deleted
        Assert.assertTrue(repository.findByCode(robot.getCode()).isPresent());
    }

    @Test
    public void givenRobot_whenAdminDelete_thenStatusAccepted() throws Exception {
        Robot robot = RobotFixture.createAndSave(repository);

        String token = TokenAuthenticationService.getToken("admin");

        //API call is accepted
        mvc.perform(delete("/api/robots/"+robot.getId())
                .header("Authorization", token))
                .andExpect(status().is(HttpStatus.ACCEPTED.value()));

        //The robot is no more present in the repository
        Assert.assertTrue(!repository.findByCode(robot.getCode()).isPresent());
    }

    @Test
    public void givenRobot_whenUserUpdate_thenStatusForbidden() throws Exception {
        Robot robot = RobotFixture.createAndSave(repository);
        BasicRobot basicRobot = RobotFixture.convert(robot);
        basicRobot.setImage("new_image");
        basicRobot.setDescription("new_description");
        basicRobot.setName("new_name");
        basicRobot.setQuantity(1234);
        StringWriter json = new StringWriter();
        (new ObjectMapper()).writeValue(json, basicRobot);

        String token = TokenAuthenticationService.getToken("user");

        //API call is not accepted
        mvc.perform(put("/api/robots/"+robot.getId())
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString()))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));

        //robot exists and is not updated
        Optional<Robot> op  = repository.findByCode(basicRobot.getCode());
        Assert.assertTrue(op.isPresent());
        Assert.assertTrue(!"new_image".equals(op.get().getImage()));

    }

    @Test
    public void givenRobot_whenAdminUpdate_thenStatusAccepted() throws Exception {
        Robot robot = RobotFixture.createAndSave(repository);
        BasicRobot basicRobot = RobotFixture.convert(robot);
        basicRobot.setImage("new_image");
        basicRobot.setDescription("new_description");
        basicRobot.setName("new_name");
        basicRobot.setQuantity(1234);
        StringWriter json = new StringWriter();
        (new ObjectMapper()).writeValue(json, basicRobot);

        String token = TokenAuthenticationService.getToken("admin");

        //API call is accepted
        mvc.perform(put("/api/robots/"+robot.getId())
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString()))
                .andExpect(status().is(HttpStatus.ACCEPTED.value()));

        //robot exists and is updated
        Optional<Robot> op  = repository.findByCode(basicRobot.getCode());
        Assert.assertTrue(op.isPresent());
        Assert.assertTrue("new_image".equals(op.get().getImage()));
        Assert.assertTrue("new_description".equals(op.get().getDescription()));
        Assert.assertTrue("new_name".equals(op.get().getName()));
        Assert.assertTrue(1234 == op.get().getQuantity());

    }
}
