package frc.robot.components.speed;

import edu.wpi.first.wpilibj.SpeedController;
import frc.robot.components.LimitSwitch;

/**
 * Represents a motor with limit switches on each end
 * Automatically halts movement in one direction when switch is hit
 */
public class SwitchRangedController implements SpeedController{

    /**
     * The controller being ranged using the limit switches 
     */
    private SpeedController controller;

    /**
     * Switch that halts movement in the + direction when pressed
     */
    private LimitSwitch forwardSwitch;

    /**
     * Switch that halts movement in the - direction when pressed
     */
    private LimitSwitch reverseSwitch;

    /**
     * Initialize with a controller, forward switch, and reverse switch
     * @param controller
     * @param forwardSwitch
     * @param reverseSwitch
     */
    public SwitchRangedController (SpeedController controller, LimitSwitch forwardSwitch, LimitSwitch reverseSwitch){
        // Save the controller
        this.controller = controller;
        // Save the forward switch
        this.forwardSwitch = forwardSwitch;
        // Save the reverse switch
        this.reverseSwitch = reverseSwitch;
    }

    /**
     * Sets the speed of the controller (halting if attempting to move in the direction that is pressed)
     * @return - true if finished
     */
    public boolean setControlled (double value){
        // If attempting to move in the + direction, and + switch is not pressed
        if (value > 0 && (!fowardSwitchActivated())){
            // then authorize the movement (send the value to the controller)
            set(value);
            // return from the method with a moving message
            return false;
        }
        // Else if attempting to move in the - direction, and - switch is not pressed
        else if (value < 0 && (!reverseSwitchActivated())){
            // then authorize the movement (send the value to the controller)
            set(value);
            // return from the method with a moving message
            return false;
        }
        // If not returned so far (indicating problem with movement or 0 value), then halt
        this.controller.stopMotor();
        // and return an end message
        return true;
    }

    /**
     * Set raw speed controller value
     */
    public void set (double value){
        this.controller.set(value);
    }

    /**
     * Returns whether or not the forward switch is activated (in which case all attempting movement in the + direction is blocked)
     * @return - true if switch is activated and movement in the + direction is disabled
     */
    public boolean fowardSwitchActivated(){
        // Read from sensor and return the value
        return forwardSwitch.get();
    }

    /**
     * Returns whether or not the reverse switch is activated (in which case all attempting movement in the - direction is blocked)
     * @return - true if switch is activated and movement in the - direction is disabled
     */
    public boolean reverseSwitchActivated(){
        // Read from sensor and return the value
        return reverseSwitch.get();
    }

    /**
     * Returns the previously set controller value
     */
    public double get(){
        return this.controller.get();
    }

    /**
     * Essentially calls set(value) - implemented from SpeedController interface
     */
    public void pidWrite (double value){
        setControlled (value);
    }

    /**
     * Returns whether or not the controller is inverted 
     * If the controller is inverted, then this is *not* reflected in the limit switches
     */
    public boolean getInverted (){
        return controller.getInverted();
    }

    /**
     * Sets whether or not the controller is inverted
     * If the controller is inverted, then this is *not* reflected in the limit switches
     */
    public void setInverted (boolean inverted){
        this.controller.setInverted(inverted);
    }

    /**
     * Disables the controller
     */
    public void disable (){
        this.controller.disable();
    }

    /**
     * Halts the motor
     */
    public void stopMotor (){
        this.controller.stopMotor();
    }


}