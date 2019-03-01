package frc.robot.constants.wiring;

/**
 * Enumeration representing the CAN wiring of the robot
 */
public enum CANWiring {

    // Represents the different CAN devices and their cooresponding IDs

    // Drive controllers (TALON SRX - 40A)
    DRIVE_LEFT_1 (10),
    DRIVE_LEFT_2 (11),
    DRIVE_LEFT_3 (12),
    DRIVE_RIGHT_1 (13),
    DRIVE_RIGHT_2 (14),
    DRIVE_RIGHT_3 (15),

    // Death Crawler controllers (SPARK MAX - 40A)
    DEATH_CRAWLER (27),
    // Death Crawler Arm (TALON SRX - 40A)
    DEATH_CRAWLER_ARM (6),

    // Climber ports (TALON SRX - 30A)
    CLIMBER_FRONT_RIGHT (5),
    CLIMBER_FRONT_LEFT (4),
    CLIMBER_BACK_RIGHT (3),
    CLIMBER_BACK_LEFT (2),

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