
package frc.robot.systems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive extends DifferentialDrive{

    private Encoder leftEncoder;
    private Encoder rightEncoder;

    private PIDController pid;

    private double leftOutput;
    private double rightOutput;

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