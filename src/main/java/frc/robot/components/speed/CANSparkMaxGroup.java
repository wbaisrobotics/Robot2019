package frc.robot.components.speed;

import com.revrobotics.CANSparkMax;

import frc.robot.components.encoders.CANEncoderGroup;

import frc.robot.components.encoders.CANPIDControllerGroup;

import edu.wpi.first.wpilibj.SpeedController;


/**
 * Represnts a group of Spark Maxes controlled together
 */
public class CANSparkMaxGroup implements SpeedController{
    /**
     * Represents the array of controllers included in the group
     */
    private CANSparkMax[] controllers;

    /**
     * Stores the encoder group for the controllers
     */
    private CANEncoderGroup encoders;

    /**
     * Stores the pid controllers
     */
    private CANPIDControllerGroup pidControllers;

    /**
     * Initilaizes with any finite amount of controllers
     * @param controllers
     */
    public CANSparkMaxGroup (CANSparkMax... controllers){
        // Save the controllers
        this.controllers = controllers;
        // Initialize the encoder group
        this.encoders = new CANEncoderGroup (controllers);
        // Initialize the pid controller group
        this.pidControllers = new CANPIDControllerGroup (controllers);
    }

    /**
     * Sets the speed of all the controllers
     * @param speed
     */
    public void set(double speed){
        // Iterate through each controller
        for (CANSparkMax controller : this.controllers){
            // Run the function on the controller
            controller.set(speed);
        }   
    }

    /**
     * Returns the pid controller group for these controllers
     * @return
     */
    public CANPIDControllerGroup getPIDControllerGroup(){
        return this.pidControllers;
    }

    /**
     * Returns the encoder group for these controllers
     * @return
     */
    public CANEncoderGroup getEncoderControllerGroup(){
        return this.encoders;
    }

    // For SpeedController interface

    public double get(){
        return this.controllers[0].get();
    }

    public void setInverted (boolean isInverted){
        // Iterate through each controller
        for (CANSparkMax controller : this.controllers){
            // Run the function on the controller
            controller.setInverted(isInverted);
        }   
    }

    public boolean getInverted(){
        return this.controllers[0].getInverted();
    }

    public void disable(){
        // Iterate through each controller
        for (CANSparkMax controller : this.controllers){
            // Run the function on the controller
            controller.disable();
        }   
    }

    public void stopMotor(){
        // Iterate through each controller
        for (CANSparkMax controller : this.controllers){
            // Run the function on the controller
            controller.stopMotor();
        }   
    }

    public void pidWrite (double value){
        set (value);
    }

}