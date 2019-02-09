
package frc.robot.constants.wiring;

/**
 * Enumeration representing the DIO wiring of the robot
 */
public enum DIOWiring {

    // The different sensors/outputs and their cooresponding port(s)
    FRONT_CLIMBER_LEFT_RETRACTED (4),
    FRONT_CLIMBER_LEFT_EXTENDED (2),
    FRONT_CLIMBER_RIGHT_RETRACTED (5),
    FRONT_CLIMBER_RIGHT_EXTENDED (1),
    BACK_CLIMBER_LEFT_RETRACTED (6),
    BACK_CLIMBER_LEFT_EXTENDED (0),
    BACK_CLIMBER_RIGHT_RETRACTED (7),
    BACK_CLIMBER_RIGHT_EXTENDED (3),

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