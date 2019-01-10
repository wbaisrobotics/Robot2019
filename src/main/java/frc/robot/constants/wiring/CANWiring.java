package frc.robot.constants.wiring;

/**
 * Enumeration representing the CAN wiring of the robot
 */
public enum CANWiring {

    // Represents the different CAN devices and their cooresponding IDs
    DRIVE_LEFT (2),
    INTAKE_LEFT (3), 
    ELEVATOR (4),
    TEAMMATE_LIFTER_LEFT (5),
    FORK (6),
    TEAMMATE_LIFTER_RIGHT (7),
    INTAKE_RIGHT (8),
    RAMP_LEFT (9),
    RAMP_RIGHT (10),
    DRIVE_RIGHT (11);

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