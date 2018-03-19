package lu.rbc.robotsstoreapi.domain.dto;

import java.util.Collection;

import io.swagger.annotations.ApiModelProperty;
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
@Setter
@Getter
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
