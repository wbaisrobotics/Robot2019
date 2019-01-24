package frc.robot.components.speed;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Victor;
import frc.robot.constants.wiring.CANWiring;
import frc.robot.constants.wiring.PWMWiring;

/**
 * Class with static methods that create and manipulate speed controllers
 */
public class SpeedControllers{

    /**
     * Creates and returns a spark max at the given port
     * @param port - a port not used so far (or else HAL Allocation Error thrown)
     * @return
     */
    public static CANSparkMax getSpartMaxBrushless (CANWiring port){
        // Initialize the controller
        CANSparkMax controller = new CANSparkMax(port.getPort(), MotorType.kBrushless);
        // Return the controller
        return controller;
    }

    /**
     * Creates and returns a talon srx at the given port
     * @param port - a port not used so far (or else HAL Allocation Error thrown)
     * @return
     */
    public static WPI_TalonSRX getTalonSRX (CANWiring port){
        // Initialize the controller
        WPI_TalonSRX controller = new WPI_TalonSRX(port.getPort());
        // Return the controller
        return controller;
    }

    /**
     * Creates and returns a victor spx at the given port
     * @param port - a port not used so far (or else HAL Allocation Error thrown)
     * @return
     */
    public static WPI_VictorSPX getVictorSPX (CANWiring port){
        // Initialize the controller
        WPI_VictorSPX controller = new WPI_VictorSPX(port.getPort());
        // Return the controller
        return controller;
    }

    /**
     * Creates and returns a victor (depricated controller) at the given port
     * @param port - a port not used so far (or else HAL Allocation Error thrown)
     * @return
     */
    public static Victor getVictor (PWMWiring port){
        // Initialize the controller
        Victor controller = new Victor(port.getPort());
        // Return the controller
        return controller;
    }

}