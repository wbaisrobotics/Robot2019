package frc.robot.constants.wiring;

/**
 * Enumeration representing the CAN wiring of the robot
 */
public enum CANWiring {

    // Represents the different CAN devices and their cooresponding IDs

    // Drive controllers (SPARK Max - 40A)
    DRIVE_LEFT_1 (25),
    DRIVE_LEFT_2 (24),
    DRIVE_LEFT_3 (23),
    DRIVE_RIGHT_1 (20),
    DRIVE_RIGHT_2 (21),
    DRIVE_RIGHT_3 (22),

    // Death Crawler controller (SPARK MAX - 40A)
    DEATH_CRAWLER (27),
    // Death Crawler Arm (TALON SRX - 40A)
    DEATH_CRAWLER_ARM (1),

    // Climber ports (TALON SRX - 30A)
    CLIMBER_FRONT_RIGHT (3),
    CLIMBER_FRONT_LEFT (2),
    CLIMBER_BACK_RIGHT (5),
    CLIMBER_BACK_LEFT (4),

    // Ball Elevator/Shooter ports (VICTOR SP - 30A) 
    BALL_ELEVATOR (6),
    BALL_SHOOTER (7),


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