
package frc.robot.constants.wiring;

/**
 * Enumeration representing the PCM wiring of the robot
 */
public enum PCMWiring {

    // The different solenoids and their cooresponding PCM port(s)
    DRIVE_B (0), DRIVE_A (7),
    INTAKE_A(1), INTAKE_B(6),
    RELEASE_A (2), GRIPPER_B(5),
    RELEASE_B(3), GRIPPER_A(4);

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
        return m_port;
    }
    
}