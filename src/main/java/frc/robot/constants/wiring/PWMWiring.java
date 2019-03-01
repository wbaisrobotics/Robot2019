package frc.robot.constants.wiring;

/**
 * Enumeration representing the PWM wiring of the robot
 */
public enum PWMWiring {

    // Represents the different PWM devices and their cooresponding IDs

    // Ball Elevator/Shooter ports (VICTOR SP - 30A) 
    BALL_ELEVATOR (0),
    BALL_SHOOTER (1),
    
    ;

    /** The stored port */
    private int m_port;

    /**
     * Initializes the constant for the port
     * @param port
     */
    private PWMWiring (int port) {
        this.m_port = port;
    }

    /**
     * Returns the port for the specific constant
     * @return
     */
    public int getPort (){
        return this.m_port;
    }

}