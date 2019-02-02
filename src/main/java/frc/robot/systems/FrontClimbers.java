package frc.robot.systems;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.components.LimitSwitch;
import frc.robot.components.LimitSwitch.SwitchConfiguration;
import frc.robot.components.LimitSwitch.WiringConfiguration;
import frc.robot.components.speed.SpeedControllers;
import frc.robot.components.speed.SwitchRangedController;
import frc.robot.constants.wiring.CANWiring;
import frc.robot.constants.wiring.DIOWiring;

/**
 * The climbers in the front of the robot used to create an angle for climbing lvl 2/3
 * Utilizes two front "spider legs" which extend below the robot with a wheel at the end
 * Controls the legs through a limitswitch-ranged motor on each side
 * 
 * Note: (RAW) Positive power on left motor should represent extending
 * Note: (RAW) Positive power on right motor should represent retracting
 * 
 */
public class FrontClimbers extends Subsystem{

    /**
     * The instance of the system
     */
    private FrontClimbers instance;

    /**
     * Returns (and possibly creates) the system instance
     * @return
     */
    public FrontClimbers getInstance (){
        // If not initialized yet,
        if (instance == null){
            // then initialize:

            //// Initialize the left side
            // Initialize the left motor 
            SpeedController leftMotor = SpeedControllers.getTalonSRX(CANWiring.FRONT_CLIMBER_LEFT);
            // Initialize the left retracted switch
            LimitSwitch leftRetractedSwitch = new LimitSwitch(DIOWiring.FRONT_CLIMBER_LEFT_RETRACTED, SwitchConfiguration.NC, WiringConfiguration.S_GND);
            // Initialize the left extended switch
            LimitSwitch leftExtendedSwitch = new LimitSwitch(DIOWiring.FRONT_CLIMBER_LEFT_EXTENDED, SwitchConfiguration.NC, WiringConfiguration.S_GND);

            //// Initialize the right side
            // Initialize the right motor 
            SpeedController rightMotor = SpeedControllers.getTalonSRX(CANWiring.FRONT_CLIMBER_RIGHT);
            // Initialize the right retracted switch
            LimitSwitch rightRetractedSwitch = new LimitSwitch(DIOWiring.FRONT_CLIMBER_RIGHT_RETRACTED, SwitchConfiguration.NC, WiringConfiguration.S_GND);
            // Initialize the right extended switch
            LimitSwitch rightExtendedSwitch = new LimitSwitch(DIOWiring.FRONT_CLIMBER_RIGHT_EXTENDED, SwitchConfiguration.NC, WiringConfiguration.S_GND);

            // Initialize the object
            instance = new FrontClimbers(leftMotor, leftRetractedSwitch, leftExtendedSwitch, rightMotor, rightRetractedSwitch, rightExtendedSwitch);

        }
        // Return the instance
        return instance;
    }

    /**
     * The power granted during extension
     */
    public double EXTEND_POWER = 1.0;

    /**
     * The power granted during retraction
     */
    public double RETRACT_POWER = -1.0;

    /**
     * The switch ranged controller representing left
     */
    private SwitchRangedController leftMotor;
    /**
     * The switch ranged controller representing right
     */
    private SwitchRangedController rightMotor;

    /**
     * Initializes the front climbers with the following
     * @param leftMotor - motor on the left side of the robot
     * @param leftRetractedSwitch - switch that is activated when left is fully retracted
     * @param leftExtendedSwitch - switch that is activated when left is fully extended
     * @param rightMotor - motor on the right side of the robot
     * @param rightRetractedSwitch - switch that is activated when right is fully retracted
     * @param rightExtendedSwitch - switch that is activated when right is fully extended
     */
    public FrontClimbers (SpeedController leftMotor, LimitSwitch leftRetractedSwitch, LimitSwitch leftExtendedSwitch,
    SpeedController rightMotor, LimitSwitch rightRetractedSwitch, LimitSwitch rightExtendedSwitch){

        // Don't invert the left motor
        leftMotor.setInverted(false);
        
        // Initialize the left motor ranged controller with
        // the left extended switch representing the switch that halts motion in the + [Raw] (extending) direction when activated
        // and the left retracted switch representing the switch that halts motion in the - [Raw] (retracting) direction when activated
        leftMotor = new SwitchRangedController(leftMotor, leftExtendedSwitch, leftRetractedSwitch);

        // Invert the right motor (due to symmetry)
        rightMotor.setInverted(true);

        // Initialize the right motor ranged controller with
        // the right extended switch representing the switch that halts motion in the + [Set] (extending) direction when activated
        // and the right retracted switch representing the switch that halts motion in the - [Set] (retracting) direction when activated
        rightMotor = new SwitchRangedController(rightMotor, rightExtendedSwitch, rightRetractedSwitch);

    }

    /**
     * Extends both front motors together (each will independently halt when they hit their switch)
     * @return - true if both finished
     */
    public boolean extend (){
        return this.leftMotor.setControlled(EXTEND_POWER) && this.rightMotor.setControlled(EXTEND_POWER);
    }

    /**
     * Retracts both front motors together (each will independently halt when they hit their switch)
     * @return - true if both finished
     */
    public boolean retract (){
        return this.leftMotor.setControlled(RETRACT_POWER) && this.rightMotor.setControlled(RETRACT_POWER);
    }

    /**
     * Whether or not both legs are fully extended
     * @return - true if both fully extended
     */
    public boolean isFullyExtended(){
        return this.leftMotor.fowardSwitchActivated() && this.rightMotor.fowardSwitchActivated();
    }

    /**
     * Whether or not both legs are fully retracted
     * @return - true if both fully retracted
     */
    public boolean isFullyRetracted(){
        return this.leftMotor.reverseSwitchActivated() && this.rightMotor.reverseSwitchActivated();
    }

    /**
     * Sets the default command to none
     */
    public void initDefaultCommand(){
    }


}