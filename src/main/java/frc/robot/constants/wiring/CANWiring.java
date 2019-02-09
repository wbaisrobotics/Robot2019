package frc.robot.constants.wiring;

/**
 * Enumeration representing the CAN wiring of the robot
 */
public enum CANWiring {

    // Represents the different CAN devices and their cooresponding IDs

    // Drive controllers (TALON SRX - 40A)
    DRIVE_LEFT_1 (1),
    DRIVE_LEFT_2 (2),
    DRIVE_LEFT_3 (3),
    DRIVE_RIGHT_1 (4),
    DRIVE_RIGHT_2 (5),
    DRIVE_RIGHT_3 (6),

    // Death Crawler controllers (SPARK MAX - 40A)
    DEATH_CRAWLER (27),
    ;

    /** The stored port */
    private int m_port;

    /**
     * Initializes the constant for the port
     * @param port
     */
    private CANWiring (int port) {
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