
package frc.robot.constants.wiring;

/**
 * Enumeration representing the DIO wiring of the robot
 */
public enum DIOWiring {

    // The different sensors/outputs and their cooresponding port(s)
    FRONT_CLIMBER_LEFT_RETRACTED (0),
    FRONT_CLIMBER_LEFT_EXTENDED (1),
    FRONT_CLIMBER_RIGHT_RETRACTED (2),
    FRONT_CLIMBER_RIGHT_EXTENDED (3),
    BACK_CLIMBER_LEFT_RETRACTED (4),
    BACK_CLIMBER_LEFT_EXTENDED (5),
    BACK_CLIMBER_RIGHT_RETRACTED (6),
    BACK_CLIMBER_RIGHT_EXTENDED (7),

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