package lu.rbc.robotsstoreapi.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Hedi Ayed on 04/03/2018.
 *
 *
 * --------------------
 *
 * @author Hedi Ayed
 */
@Getter
@AllArgsConstructor
public enum RobotCategory {
    ROBOT("Robot"),
    INTELLIGENT("Intelligent Robot"),
    HUMANOID("Humanoid"),
    ELECTRONIC_PET("Electronic Pet"),
    DOG("Robot Dog")
    ;

    private String name;
}
