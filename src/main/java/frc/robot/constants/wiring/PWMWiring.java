package frc.robot.constants.wiring;

/**
 * Enumeration representing the PWM wiring of the robot
 */
public enum PWMWiring {

    // Represents the different PWM devices and their cooresponding IDs
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
        return m_port;
    }

}