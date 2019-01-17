
package frc.robot.systems;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drive extends DifferentialDrive{

    /**
     * Default constructor for just the motors and no encoders
     * @param left - the left motors
     * @param right - the right motors
     */
    public Drive (SpeedController left, SpeedController right){
        super (left, right);
    }

}