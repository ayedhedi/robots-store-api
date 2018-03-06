package lu.rbc.robotsstoreapi.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.StringWriter;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import io.jsonwebtoken.lang.Assert;
import lu.rbc.robotsstoreapi.converter.BasicRobotConverter;
import lu.rbc.robotsstoreapi.converter.RobotConverter;
import lu.rbc.robotsstoreapi.domain.dto.BasicRobot;
import lu.rbc.robotsstoreapi.domain.model.Robot;
import lu.rbc.robotsstoreapi.exception.InvalidDataException;
import lu.rbc.robotsstoreapi.exception.RobotNotFoundException;
import lu.rbc.robotsstoreapi.fixture.RobotFixture;
import lu.rbc.robotsstoreapi.service.impl.RobotServiceImpl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class RobotsStoreApiApplicationTest {

	@Mock
	private RobotServiceImpl robotService;

	private MockMvc mockMvc;
	private ObjectMapper mapper;

	@Before
	public void setUp(){

		RobotConverter robotConverter = new RobotConverter();
		BasicRobotConverter basicRobotConverter = new BasicRobotConverter();

		GenericConversionService genericConversionService = new GenericConversionService();
		genericConversionService.addConverter(robotConverter);
		genericConversionService.addConverter(basicRobotConverter);

		RobotController controller = new RobotController(genericConversionService, robotService);
		mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.build();
		mapper = new ObjectMapper();
	}

	@Test
	public void getRobots_found() throws Exception {
		//Given: a list of one robot
		Robot robot = RobotFixture.createRobot();
		Mockito.when(robotService.getRobots()).thenReturn(Collections.singletonList(robot));

		//When: GET /api/robots
		String content =
				mockMvc.perform(get("/api/robots"))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse().getContentAsString();

		List<BasicRobot> robots = convertRobots(content);

		//Then
		assert robots != null;
		Assert.notNull(robots, "Response content is not valid");
		Assert.notEmpty(robots, "Robots list is empty ");
		Assert.isTrue(Objects.equals(robots.get(0).getId(), robot.getId()));
	}

	@Test
	public void getRobot_idNotFound() throws Exception {
		//Given: no robot with id = 1
		Mockito.when(robotService.findRobotById(1)).thenReturn(Optional.empty());

		//When
		mockMvc.perform(get("/api/robots/1"))
				.andExpect(status().is4xxClientError());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void updateRobot_found() throws Exception {
		//Given
		Robot robot = RobotFixture.createRobot();
		BasicRobot basic = RobotFixture.convert(robot);

		StringWriter sw = new StringWriter();
		mapper.writeValue(sw, basic);

		Mockito.when(robotService.updateRobot(any(Long.class), any(Robot.class))).thenReturn(robot);

		//When
		String content = mockMvc.perform(put("/api/robots/"+robot.getId())
				.content(sw.toString())
				.contentType(MediaType.APPLICATION_JSON))
		//Then
				.andExpect(status().is(202))
				.andReturn()
				.getResponse()
				.getContentAsString();

		Assert.notNull(content);
		Assert.notNull(convertRobot(content));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void updateRobot_notFound() throws Exception {
		//Given
		Robot robot = RobotFixture.createRobot();
		BasicRobot basic = RobotFixture.convert(robot);
		StringWriter sw = new StringWriter();
		mapper.writeValue(sw, basic);

		when(robotService.updateRobot(any(Long.class), any(Robot.class)))
				.thenThrow(RobotNotFoundException.class);

		//When
		mockMvc.perform(put("/api/robots/"+robot.getId())
				.content(sw.toString())
		//Then
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(404));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void updateRobot_codeIsUsed() throws Exception {
		//Given
		Robot robot = RobotFixture.createRobot();
		BasicRobot basic = RobotFixture.convert(robot);
		StringWriter sw = new StringWriter();
		mapper.writeValue(sw, basic);

		when(robotService.updateRobot(any(Long.class), any(Robot.class)))
				.thenThrow(InvalidDataException.class);

		//When
		mockMvc.perform(put("/api/robots/"+robot.getId())
				.content(sw.toString())
				//Then
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(400));
	}

	@Test
	public void deleteRobot_found() throws Exception {
		//Given
		Robot robot = RobotFixture.createRobot();

		Mockito.when(robotService.findRobotById(robot.getId())).thenReturn(Optional.of(robot));
		Mockito.doNothing().when(robotService).deleteRobot(robot.getId());

		//When
		mockMvc.perform(delete("/api/robots/"+robot.getId()))

		//Then
				.andExpect(status().is(202));

	}

	@Test
	public void deleteRobot_notFound() throws Exception {
		//Given
		Robot robot = RobotFixture.createRobot();

		Mockito.when(robotService.findRobotById(robot.getId())).thenReturn(Optional.empty());

		//When
		mockMvc.perform(delete("/api/robots/"+robot.getId()))

		//Then
				.andExpect(status().is(404));

	}

	@Test
	public void createRobot_created() throws Exception {
		//Given
		Robot robot = RobotFixture.createRobot();
		BasicRobot basic = RobotFixture.convert(robot);

		StringWriter sw = new StringWriter();
		mapper.writeValue(sw, basic);

		Mockito.when(robotService.createRobot(any(Robot.class))).thenReturn(robot);

		//When
		String content = mockMvc.perform(post("/api/robots/")
				.content(sw.toString())
				.contentType(MediaType.APPLICATION_JSON))
		//Then
				.andExpect(status().is(202))
				.andReturn()
				.getResponse()
				.getContentAsString();

		Assert.notNull(content);
		Assert.notNull(convertRobot(content));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void createRobot_codeUsed() throws Exception {
		//Given
		Robot robot = RobotFixture.createRobot();
		BasicRobot basic = RobotFixture.convert(robot);

		StringWriter sw = new StringWriter();
		mapper.writeValue(sw, basic);

		when(robotService.createRobot(any(Robot.class))).thenThrow(InvalidDataException.class);

		//When
		mockMvc.perform(post("/api/robots/")
				.content(sw.toString())
				.contentType(MediaType.APPLICATION_JSON))
				//Then
				.andExpect(status().is(400));
	}


	private BasicRobot convertRobot(String json){
		try{
			return mapper.readValue(json, BasicRobot.class);
		} catch (Exception e) {
			return null;
		}
	}

	private List<BasicRobot> convertRobots(String json){
		try {
			return mapper.readValue(json, new TypeReference<List<BasicRobot>>() { });
		}catch (Exception e) {
			return null;
		}
	}
}
