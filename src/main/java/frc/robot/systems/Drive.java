
package frc.robot.systems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.components.speed.SpeedControllers;
import frc.robot.constants.wiring.CANWiring;

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

            instance = new Drive(left, right);
        }
        return instance;
    }

    /**
     * Default constructor for just the motors and no encoders
     * @param left - the left motors
     * @param right - the right motors
     */
    public Drive (SpeedController left, SpeedController right){
        super (left, right);
    }

        /**
     * Default constructor for the motors and encoders
     * @param left - the left motors
     * @param right - the right motors
     */
    public Drive (SpeedController left, SpeedController right, Encoder leftEncoder, Encoder rightEncoder){
        super (left, right);
        this.leftEncoder = leftEncoder;
        this.rightEncoder = rightEncoder;
        pid = new PIDController(0, 0, 0, 0, this.leftEncoder, e -> leftOutput = e);
        pid.enable();
        SmartDashboard.putNumber ("P", 0);
        SmartDashboard.putNumber ("I", 0);
        SmartDashboard.putNumber("D", 0);
        SmartDashboard.putNumber("FF", 0);
        SmartDashboard.putNumber("Setpoint", 0);
    }

    public void reset (){
        this.leftEncoder.reset();
        this.rightEncoder.reset();
        this.pid.reset();
        this.pid.enable();
    }

    public void updatePID(){
        pid.setPID(SmartDashboard.getNumber("P", 0), SmartDashboard.getNumber("I", 0), SmartDashboard.getNumber("D", 0), SmartDashboard.getNumber("FF", 0));
        pid.setSetpoint(SmartDashboard.getNumber("Setpoint", 0));
    }

    public void driveAuto(){
        super.arcadeDrive(leftOutput, 0);
    }


}