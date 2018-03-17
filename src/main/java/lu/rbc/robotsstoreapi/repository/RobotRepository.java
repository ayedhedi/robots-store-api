package lu.rbc.robotsstoreapi.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

import lu.rbc.robotsstoreapi.domain.model.Robot;

/**
 * Created by Hedi Ayed on 26/02/2018.
 *
 *
 * --------------------
 *
 * @author Hedi Ayed
 */
public interface RobotRepository extends PagingAndSortingRepository<Robot, Long> {

    Optional<Robot> findByCode(String code);
}
