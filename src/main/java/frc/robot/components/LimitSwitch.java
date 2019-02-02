package frc.robot.components;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.constants.wiring.DIOWiring;

/** Class that manages limit switches, accounting for configurations */
public class LimitSwitch extends DigitalInput{

    /** The possible configurations for the connection on the limit switch side */
    public enum SwitchConfiguration { NO, NC }
    private SwitchConfiguration sConfig;

    /** The possible configurations for the connection on the DIO wiring side */
    public enum WiringConfiguration { S_GND, S_5V }
    private WiringConfiguration wConfig;

    /**
     * Initiales the limit switch
     * @param port - DIO port
     * @param sConfig - configuration for switch wiring
     * @param wConfig - configuration for DIO wiring
     */
    public LimitSwitch(DIOWiring port, SwitchConfiguration sConfig, WiringConfiguration wConfig){

        // Initializes the digital input
        super (port.getPort());

        // Saves the config preferences
        this.sConfig = sConfig;
        this.wConfig = wConfig;

    }

    /**
     * Returns the switch configuration
     * @return
     */
    public SwitchConfiguration getSwitchConfiguration(){
        return this.sConfig;
    }

    /**
     * Returns the wiring configuration
     * @return
     */
    public WiringConfiguration getWiringConfiguration(){
        return this.wConfig;
    }

    /**
     * Retreieves the value of the limit switch
     * @return - true if the limit switch is activated (pushed), false if not
     */
    public boolean get(){
        // Read from the input
        boolean reading = super.get();
        // Use the XOR operator (^) to determine whether or not to NOT the reading
        return ((getWiringConfiguration() == WiringConfiguration.S_GND) ^ (getSwitchConfiguration() == SwitchConfiguration.NC)) ? !reading : reading;
    }

}