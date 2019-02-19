
package frc.robot.constants.wiring;

/**
 * Enumeration representing the DIO wiring of the robot
 */
public enum DIOWiring {

    // The different sensors/outputs and their cooresponding port(s)
    FRONT_CLIMBER_LEFT_RETRACTED (6),
    FRONT_CLIMBER_LEFT_EXTENDED (12),
    FRONT_CLIMBER_RIGHT_RETRACTED (9),
    FRONT_CLIMBER_RIGHT_EXTENDED (14),
    BACK_CLIMBER_LEFT_RETRACTED (7),
    BACK_CLIMBER_LEFT_EXTENDED (16),
    BACK_CLIMBER_RIGHT_RETRACTED (8),
    BACK_CLIMBER_RIGHT_EXTENDED (18),

    DRIVE_INDICATOR_LIGHT (0),

    ;

    /** The stored port **/
    private int m_port;

    /**
     * Initializes the constant for the port
     * @param port
     */
    private DIOWiring (int port) {
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