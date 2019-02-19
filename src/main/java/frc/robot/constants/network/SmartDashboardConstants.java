
package frc.robot.constants.network;

import edu.wpi.first.networktables.NetworkTableValue;

import frc.robot.constants.MotionProfilingConstants;

/**
 * The key constants for Smart Dashboard communication
 * All values here will be have their default values sent to the dashboard upon program start
 */
public enum SmartDashboardConstants{

    /**
     * Motion profiling PID constants
     */
    P_CONST ("P Const", NetworkTableValue.makeDouble (MotionProfilingConstants.kP)),
    I_CONST ("I Const", NetworkTableValue.makeDouble (MotionProfilingConstants.kI)),
    D_CONST ("D Const", NetworkTableValue.makeDouble (MotionProfilingConstants.kD)),
    V_CONST ("V Const", NetworkTableValue.makeDouble (MotionProfilingConstants.kV)),
    A_CONST ("A Const", NetworkTableValue.makeDouble (MotionProfilingConstants.kA)),
    TURN_CONST ("Turn Const", NetworkTableValue.makeDouble (MotionProfilingConstants.kTurn)),
    
    /**
     * Climber constants
     */
    LEFT_BACK_CLIMBER_CONSTANT ("Left Back Climber Constant", NetworkTableValue.makeDouble (1.0)),
    RIGHT_BACK_CLIMBER_CONSTANT ("Right Back Climber Constant", NetworkTableValue.makeDouble (1.0)),
    LEFT_FRONT_CLIMBER_CONSTANT ("Left Front Climber Constant", NetworkTableValue.makeDouble (1.0)),
    RIGHT_FRONT_CLIMBER_CONSTANT ("Right Front Climber Constant", NetworkTableValue.makeDouble (1.0)),

    ;

    /**
     * The key for this value
     */
    private String key;
    /**
     * The default value (used with get___(defaultValue) and is inputed to the dashboard upon program start)
     */
    private NetworkTableValue defaultValue;

    /**
     * Initializes the enumeration with a key and a default value
     * @param key - the key in the table
     * @param defaultValue - the default value
     */
    private SmartDashboardConstants (String key, NetworkTableValue defaultValue){
        // Save the key
        this.key = key;
        // Save the default value
        this.defaultValue = defaultValue;
    }

    /**
     * Returns the key
     * @return - the key for this entry
     */
    public String getKey (){
        return this.key;
    }

    /**
     * Returns the default value
     * @return - the default value for this entry
     */
    public NetworkTableValue getDefaultValue (){
        return this.defaultValue;
    }

}