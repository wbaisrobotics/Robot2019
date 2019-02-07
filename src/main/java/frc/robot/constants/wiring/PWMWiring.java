package frc.robot.constants.wiring;

/**
 * Enumeration representing the PWM wiring of the robot
 */
public enum PWMWiring {

    // Represents the different PWM devices and their cooresponding IDs
    VIC_0 (0),
    VIC_1 (1),
    VIC_2 (2),
    VIC_3 (3),

    DEATH_CRAWLER_ARM (6);

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