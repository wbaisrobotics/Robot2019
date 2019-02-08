
package frc.robot.constants.wiring;

/**
 * Enumeration representing the PCM wiring of the robot
 */
public enum PCMWiring {

    // The different solenoids and their cooresponding PCM port(s)
    ;

    /** The stored port **/
    private int m_port;

    /**
     * Initializes the constant for the port
     * @param port
     */
    private PCMWiring (int port) {
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