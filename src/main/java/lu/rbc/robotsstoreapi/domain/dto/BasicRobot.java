package lu.rbc.robotsstoreapi.domain.dto;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lu.rbc.robotsstoreapi.domain.enums.RobotCategory;
import lu.rbc.robotsstoreapi.domain.enums.RobotFunction;

/**
 * Created by Hedi Ayed on 27/02/2018.
 *
 *
 * --------------------
 *
 * @author Hedi Ayed
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasicRobot {
    private Long id;
    private String name;
    private RobotCategory category;
    private String code;
    private String brand;
    private String description;
    private Float price;
    private Integer quantity;
    private String image;
    private Boolean available;
    private Collection<RobotFunction> functions;
}
