package frc.robot.components.encoders;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

/**
 * Represents a group of CAN PID Controllers 
 */
public class CANPIDControllerGroup{

    /**
     * The stored pid controllers 
     */
    private CANPIDController[] pidControllers;

    /**
     * Initializes with an array of CAN PID Controllers
     * @param pidControllers
     */
    public CANPIDControllerGroup (CANSparkMax... controllers){
        // Create an empty array of encoders
        this.pidControllers = new CANPIDController [controllers.length];
        // Iterate through each controller
        for (int i = 0; i < controllers.length; i++){
            // Get the encoder for this controller
            this.pidControllers[i] = controllers[i].getPIDController();
            this.pidControllers[i].setOutputRange(-1, 1);
        }
    }

    /**
     * Initializes with an array of CAN PID Controllers
     * @param pidControllers
     */
    public CANPIDControllerGroup (CANPIDController... pidControllers){
        // Save the controller array
        this.pidControllers = pidControllers;
    }

    /**
     * Immediately halts all the motors
     */
    public void stop(){
        // Iterate through each controller
        for (CANPIDController controller : this.pidControllers){
            controller.setReference(0, ControlType.kDutyCycle);
        }
    }

    /**
     * Sets the reference point of the controller and enables it
     * @param setPoint
     */
    public void setReference (double setpoint){
         // Iterate through each controller
        for (CANPIDController controller : this.pidControllers){
            controller.setReference(setpoint, ControlType.kPosition);
        }
    }

    /**
     * Sets the proportional value of the controller
     * @param setPoint
     */
    public void setP (double p){
        // Iterate through each controller
       for (CANPIDController controller : this.pidControllers){
           controller.setP(p);
       }
   }

    /**
     * Sets the integral value of the controller
     * @param setPoint
     */
    public void setI (double i){
        // Iterate through each controller
       for (CANPIDController controller : this.pidControllers){
           controller.setI(i);
       }
   }

    /**
     * Sets the derivative value of the controller
     * @param setPoint
     */
    public void setD (double d){
        // Iterate through each controller
       for (CANPIDController controller : this.pidControllers){
           controller.setD(d);
       }
   }

}