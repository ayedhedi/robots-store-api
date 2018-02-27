package lu.rbc.robotsstoreapi.repository;

import org.springframework.data.repository.CrudRepository;

import lu.rbc.robotsstoreapi.domain.model.Robot;

/**
 * Created by Hedi Ayed on 26/02/2018.
 *
 *
 * --------------------
 *
 * @author Hedi Ayed
 */
public interface RobotRepository extends CrudRepository<Robot, Long> {
}
