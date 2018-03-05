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
public enum RobotFunction {
    WIRELESS("Wireless"),
    YOGA("Yoga"),
    REMOTE_CONTROL("Remote Control"),
    VOICE_CONTROL("Voice Control"),
    CASUAL("Casual"),
    DANCING("Dancing"),
    SHOOTING("Shooting"),
    SINGING("Singing"),
    WALKING("Walking"),
    TALKING("Talking"),
    JUMPING("Jumping"),
    PROGRAMMABLE("Programmable"),

    ;
    private String name;
}
