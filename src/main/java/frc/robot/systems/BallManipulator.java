package frc.robot.systems;

import edu.wpi.first.wpilibj.SpeedController;
import frc.robot.components.speed.SpeedControllers;
import frc.robot.constants.wiring.CANWiring;
import frc.robot.constants.wiring.PWMWiring;

/**
 * Represents the manipulation of the ball using the elevator and shooter
 */
public class BallManipulator{

    /** Constants */

    /**
     * The speed for lifting a ball in the elevator
     */
    public final double ELEVATOR_UP_SPEED = 0.5;

    /**
     * The speed for lowering a ball in the elevator
     */
    public final double ELEVATOR_DOWN_SPEED = -0.5;

    /**
     * The speed for shooting a ball out in the shooter
     */
    public final double SHOOTER_OUT_SPEED = -0.5;

    /**
     * The speed for shooting a ball in in the shooter
     */
    public final double SHOOTER_IN_SPEED = 0.5;

    /**
     * The instance of the system
     */
    private static BallManipulator instance;

    /**
     * Returns (and possibly creates) the system instance
     * @return the instance 
     */
    public static BallManipulator getInstance (){
        // If not initialized yet,
        if (instance == null){
            // then initialize:

            // Initialize the elevator motor
            SpeedController elevatorMotor = SpeedControllers.getSparkMaxBrushless(CANWiring.BALL_ELEVATOR);

            // Initialize the shooter motor
            SpeedController shooterMotor = SpeedControllers.getSparkMaxBrushless(CANWiring.BALL_SHOOTER);
            
            // Initialize the instance
            instance = new BallManipulator(elevatorMotor, shooterMotor);

        }
        // Return the instance
        return instance;
    }

    /**
     * The speed controller for the ball elevator
     */
    private SpeedController ballElevator;

    /**
     * The speed controller for the ball shooter
     */
    private SpeedController ballShooter;

    /**
     * Initializes the ball manipulator with an elevator and shooter
     * @param ballElevator - the speed controller for the ball elevator
     * @param ballShooter - the speed controller for the ball shooter
     */
    public BallManipulator (SpeedController ballElevator, SpeedController ballShooter){

        // Save the ball elevator
        this.ballElevator = ballElevator;

        // Save the ball shooter
        this.ballShooter = ballShooter;

    }

    /**
     * Halts the elevator and the shooter
     */
    public void stop (){
        this.ballElevator.stopMotor();
        this.ballShooter.stopMotor();
    }

    /**
     * Halts only the elevator
     */
    public void stopElevator (){
        this.ballElevator.stopMotor();
    }

    /**
     * Halts only the shooter
     */
    public void stopShooter (){
        this.ballShooter.stopMotor();
    }

    /**
     * Lifts a ball using the elevator
     */
    public void liftBall (){
        this.ballElevator.set(ELEVATOR_UP_SPEED);
    }

    /**
     * Lowers a ball using the elevator
     */
    public void lowerBall (){
        this.ballElevator.set(ELEVATOR_DOWN_SPEED);
    }

    /**
     * Shoots out a ball using the shooter
     */
    public void shooterOut (){
        this.ballShooter.set(SHOOTER_OUT_SPEED);
    }

    /**
     * Reverses the ball shooter
     */
    public void shooterIn (){
        this.ballShooter.set(SHOOTER_IN_SPEED);
    }


}