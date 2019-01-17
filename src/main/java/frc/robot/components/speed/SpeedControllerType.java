package frc.robot.components.speed;

/**
 * Describes the different speed controllers supported by SpeedControllers.java
 */
public enum SpeedControllerType{

    // The various types of supported controllers
    Spark (SpeedControllerConnectionType.PWM), // The Rev Robotics Spark motor controller for brushed motors - through PWM
    SparkMAXPWM (SpeedControllerConnectionType.PWM), // The Rev Robotics Spark Max motor controller for brushless motors - through PWM
    SparkMAXCAN (SpeedControllerConnectionType.CAN), // The Rev Robotics Spark Max motor controller for brushless motors - through CAN
    VictorSP (SpeedControllerConnectionType.PWM), // The traditional Victor SP for brushed motors - through PWM
    VictorSPXPWM (SpeedControllerConnectionType.PWM), // The CTRE renewed version of the Victor SP for brushed motors - through PWM
    VictorSPXCAN (SpeedControllerConnectionType.CAN), // The CTRE renewed version of the Victor SP for brushed motors - through CAN
    TalonSRXPWM (SpeedControllerConnectionType.PWM), // The CTRE Talon SRX for brushed motors - through PWM
    TalonSRXCAN (SpeedControllerConnectionType.CAN); // The CTRE Talon SRX for brushed motors - through CAN

    /**
     * Stores the connection type
     */
    private SpeedControllerConnectionType connectionType;

    /**
     * Initializes the instance with a given connection type
     * @param connectionType
     */
    private SpeedControllerType (SpeedControllerConnectionType connectionType){
        this.connectionType = connectionType;
    }

    /**
     * Returns the type of connection for the instance
     * @return
     */
    public SpeedControllerConnectionType getConnectionType(){
        return this.connectionType;
    } 

}