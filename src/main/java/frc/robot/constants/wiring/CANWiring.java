package frc.robot.constants.wiring;

/**
 * Enumeration representing the CAN wiring of the robot
 */
public enum CANWiring {

    // Represents the different CAN devices and their cooresponding IDs
    DRIVE_LEFT_1 (20),
    DRIVE_LEFT_2 (21),
    DRIVE_LEFT_3 (22),
    DRIVE_RIGHT_1 (23),
    DRIVE_RIGHT_2 (24),
    DRIVE_RIGHT_3 (25),
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