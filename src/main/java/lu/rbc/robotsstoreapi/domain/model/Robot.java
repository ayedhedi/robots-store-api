package lu.rbc.robotsstoreapi.domain.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
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
 * Created by Hedi Ayed on 26/02/2018.
 *
 *
 * --------------------
 *
 * @author Hedi Ayed
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Robot {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    private String name;
    @Enumerated(value = EnumType.STRING)
    private RobotCategory category;
    @Column(unique = true)
    private String code;
    private String brand;
    @Column(length = 1024)
    private String description;
    private float price;
    private int quantity;
    private String image;
    private boolean available;

    @ElementCollection(targetClass = RobotFunction.class)
    @Enumerated(value = EnumType.STRING)
    private Collection<RobotFunction> functions;
}
