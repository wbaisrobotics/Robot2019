package frc.robot.constants.wiring;

/**
 * Enumeration representing the PWM wiring of the robot
 */
public enum PWMWiring {

    // Represents the different PWM devices and their cooresponding IDs

    // Climber ports (VICTOR SP - 30A)
    CLIMBER_FRONT_RIGHT (0),
    CLIMBER_FRONT_LEFT (1),
    CLIMBER_BACK_RIGHT (2),
    CLIMBER_BACK_LEFT (3),

    // Ball Elevator/Shooter ports (VICTOR SP - 30A) 
    BALL_ELEVATOR (4),
    BALL_SHOOTER (4),

    // Death Crawler Arm (VICTOR SP - 40A)
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