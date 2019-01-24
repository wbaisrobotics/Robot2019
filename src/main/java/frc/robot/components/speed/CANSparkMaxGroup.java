package frc.robot.components.speed;

import com.revrobotics.CANSparkMax;

import frc.robot.components.encoders.CANEncoderGroup;

/**
 * Represnts a group of Spark Maxes controlled together
 */
public class CANSparkMaxGroup{

    /**
     * Represents the array of controllers included in the group
     */
    private CANSparkMax[] controllers;

    /**
     * Stores the encoder group for the controllers
     */
    private CANEncoderGroup encoders;

    /**
     * Initilaizes with any finite amount of controllers
     * @param controllers
     */
    public CANSparkMaxGroup (CANSparkMax... controllers){
        // Save the controllers
        this.controllers = controllers;
        // Save the encoder group
        this.encoders = new CANEncoderGroup (controllers);
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

}