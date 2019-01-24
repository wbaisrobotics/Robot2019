
package frc.robot.systems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.components.speed.SpeedControllers;
import frc.robot.constants.wiring.CANWiring;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;

import frc.robot.components.speed.CANSparkMaxGroup;

public class Drive extends DifferentialDrive{

    /** The sole static instance of Drive */
    private static Drive instance;
    /**
     * Returns an instance of Drive, creating one if necessary
     * @return
     */
    public static Drive getInstance (){
        if (instance == null){

            // Initialize the spark maxes
            CANSparkMax left1 = SpeedControllers.getSpartMaxBrushless(CANWiring.DRIVE_LEFT_1);
            CANSparkMax left2 = SpeedControllers.getSpartMaxBrushless(CANWiring.DRIVE_LEFT_2);
            CANSparkMax left3 = SpeedControllers.getSpartMaxBrushless(CANWiring.DRIVE_LEFT_3);
            CANSparkMax right1 = SpeedControllers.getSpartMaxBrushless(CANWiring.DRIVE_RIGHT_1);
            CANSparkMax right2 = SpeedControllers.getSpartMaxBrushless(CANWiring.DRIVE_RIGHT_2);
            CANSparkMax right3 = SpeedControllers.getSpartMaxBrushless(CANWiring.DRIVE_RIGHT_3);

            instance = new Drive(new CANSparkMaxGroup (left1, left2, left3), new CANSparkMaxGroup (right1, right2, right3));

        }
        return instance;
    }

    private CANSparkMaxGroup left;
    private CANSparkMaxGroup right;

    private ADXRS450_Gyro gyro;

    /**
     * Default constructor for just the motors and no encoders
     * @param left - the left motors
     * @param right - the right motors
     */
    public Drive (CANSparkMaxGroup left, CANSparkMaxGroup right){
        // Call DifferentialDrive with the controllers
        super (left, right);
        // Save the groups
        this.left = left;
        this.right = right;
    }

    /**
     * Resets all sensors
     */
    public void reset (){
        // Reset the gyro
        gyro.reset();
        // TBA for encoders once spark max update comes out
    }

    public void updatePID(){
        // Update the PID Values to what is in the dashboard
        left.getPIDControllerGroup().setP(SmartDashboard.getNumber("P", 0));
        left.getPIDControllerGroup().setI(SmartDashboard.getNumber("I", 0));
        left.getPIDControllerGroup().setD(SmartDashboard.getNumber("D", 0));
        right.getPIDControllerGroup().setP(SmartDashboard.getNumber("P", 0));
        right.getPIDControllerGroup().setI(SmartDashboard.getNumber("I", 0));
        right.getPIDControllerGroup().setD(SmartDashboard.getNumber("D", 0));
    }

    /**
     * Updates the PID Setpoint and starts the onboard pid controller
     * @param left
     * @param right
     */
    public void setPIDSetpoint(double leftSetpoint, double rightSetpoint){
        // Set the setpoint for left
        left.getPIDControllerGroup().setReference(leftSetpoint);
        // Set the setpoint for right
        right.getPIDControllerGroup().setReference(rightSetpoint);
    }

    /**
     * Immediately halts both sides
     */
    public void stop (){
        left.stopMotor();
        right.stopMotor();
    }

}