package lu.rbc.robotsstoreapi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

import lu.rbc.robotsstoreapi.domain.model.Robot;

/**
 * Created by Hedi Ayed on 05/03/2018.
 *
 *
 * --------------------
 *
 * @author Hedi Ayed
 */
public class SimpleMain {

    public static void main(String[] args) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        List<Robot> robots =
                mapper.readValue(new File("src/main/resources/robots.json"),
                new TypeReference<List<Robot>>() { });

        System.out.println(robots.size());
    }
}
