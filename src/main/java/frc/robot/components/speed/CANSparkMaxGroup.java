package frc.robot.components.speed;

import com.revrobotics.CANSparkMax;

import com.revrobotics.CANEncoder;

import com.revrobotics.CANPIDController;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * Represnts a group of Spark Maxes controlled together
 */
public class CANSparkMaxGroup implements SpeedController{

    /**
     * Represents the master controller
     */
    private CANSparkMax master;

    /**
     * Represents the slave controllers
     */
    private CANSparkMax[] slaves;

    /**
     * Stores the encoder group for the controllers
     */
    private CANEncoder encoder;

    /**
     * Stores the pid controllers
     */
    private CANPIDController pidController;

    /**
     * Initilaizes with any finite amount of controllers
     * @param controllers
     */
    public CANSparkMaxGroup (CANSparkMax master, CANSparkMax... slaves){
        // Save the master controllers
        this.master = master;
        // Save the slaves
        this.slaves = slaves;
        // Initialize the encoder 
        this.encoder = this.master.getEncoder();
        // Initialize the pid controller group
        this.pidController = this.master.getPIDController();
        // Set the output range for the controller
        this.pidController.setOutputRange(-1, 1);
        // Set up the other controllers as slaves
        for (CANSparkMax slave: slaves){
            slave.follow(this.master);
            System.out.println(slave.getDeviceId() + " is following " + master.getDeviceId());
        }
    }

    /**
     * Sets the speed of all the controllers
     * @param speed
     */
    public void set(double speed){
        master.set(speed);
    }

    /**
     * Returns the pid controller for these controllers
     * @return
     */
    public CANPIDController getPIDController(){
        return this.pidController;
    }

    /**
     * Returns the encoder for these controllers
     * @return
     */
    public CANEncoder getEncoder(){
        return this.encoder;
    }

    /**
     * Returns the average encoder position amongst the encoders
     * @return
     */
    public double getEncoderPosition(){
        double total = 0;
        int n = 1;
        SmartDashboard.putNumber("Spark " + master.getDeviceId() + " Encoder", master.getEncoder().getPosition());
        total += master.getEncoder().getPosition();
        for (CANSparkMax slave : slaves){
            total += slave.getEncoder().getPosition();
            SmartDashboard.putNumber("Spark " + slave.getDeviceId() + " Encoder", slave.getEncoder().getPosition());
            n++;
        }
        return total / n;
    }

    // For SpeedController interface

    public double get(){
        return this.master.get();
    }

    public void setInverted (boolean isInverted){
       master.setInverted(isInverted);
    }

    public boolean getInverted(){
        return this.master.getInverted();
    }

    public void disable(){
        master.disable();
    }

    public void stopMotor(){
        master.stopMotor();
    }

    public void pidWrite (double value){
        set (value);
    }

}