
package frc.robot.constants.wiring;

/**
 * Enumeration representing the DIO wiring of the robot
 */
public enum DIOWiring {

    // The different sensors/outputs and their cooresponding port(s)
    ELEVATOR_ENCODER_A(3),
    ELEVATOR_ENCODER_B(4),
    FORK_RETRACTED_SW(1),
    DRIVE_LEFT_B(5),
    DRIVE_LEFT_A(6),
    DRIVE_RIGHT_A(8),
    DRIVE_RIGHT_B(7),
    INTAKE_SW(9), 
    TEAMMATE_LIFTER_LEFT_SW(2),
    TEAMMATE_LIFTER_RIGHT_SW(20),
    ELEVATOR_BOTTOM_SW(18),
    FORK_EXTENDED_SW(0);

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