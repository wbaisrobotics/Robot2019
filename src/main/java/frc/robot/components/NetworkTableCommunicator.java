
package frc.robot.components;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableValue;
import frc.robot.constants.network.SmartDashboardConstants;

/**
 * Class for communicating with the network tables
 */
public class NetworkTableCommunicator{

    /**
     * The global network table instance
     */
    private static NetworkTableInstance tableInstance;

    /**
     * Initializes all the network tables
     */
    public static void init (){
        // Retrieve a network table instance
        tableInstance = NetworkTableInstance.getDefault();
        // Init the smart dashboard
        initSmartDashboard();
        // Init the motor dashboard
        initMotorDashboard();
        // Init the solenoid dashboard
        initSolenoidDashboard();
        // Init the IO dashboard
        initIoDashboard();
    }

    /** ---- Smart Dashboard ---- */

    /**
     * The name for the smart dashboard table
     */
    public static final String SMART_DASHBOARD_NAME = "SmartDashboard";

    /**
     * The smart dashboard network table
     */
    private static NetworkTable smartDashboard;

    /**
     * Initializes the entries in the Smart Dashboard
     */
    private static void initSmartDashboard (){
        // Retrieve the smart dashboard table
        smartDashboard = tableInstance.getTable(SMART_DASHBOARD_NAME);
        // Iterate through each constant in SmartDashboardConstants
        for (SmartDashboardConstants constant : SmartDashboardConstants.values()){
            // Get the entry from the table for the constant and set to the default value
            smartDashboard.getEntry(constant.getKey()).setValue(constant.getDefaultValue());
        }
    }

    /**
     * Sets the value of the entry given by loc to value
     * @param loc - the referenced entry
     * @param value - the value to set it to
     */
    public static void set (SmartDashboardConstants loc, NetworkTableValue value){
        // Get the entry at loc and set the value
        smartDashboard.getEntry(loc.getKey()).setValue(value);
    }

    /**
     * Retrieves the value for the loc at the table
     * @param loc - the referenced entry
     * @return - the current value at that entry
     */
    public static NetworkTableValue get (SmartDashboardConstants loc){
        return smartDashboard.getEntry(loc.getKey()).getValue();
    }

    /** ---- Motor Dashboard ---- */

    /**
     * The name for the motor dashboard table
     */
    public static final String MOTOR_DASHBOARD_NAME = "MotorDashboard";

    /**
     * The motor dashboard network table
     */
    private static NetworkTable motorDashboard;

    /**
     * Initializes the motor dashboard
     */
    private static void initMotorDashboard (){
        // Retrieve the motor dashboard table
        motorDashboard = tableInstance.getTable(SMART_DASHBOARD_NAME);
    }

    /**
     * Sets the value of the entry given by loc to value
     * @param loc - the referenced entry
     * @param value - the value to set it to
     */
    public static void setMotorDashboardValue (String loc, NetworkTableValue value){
        // Get the entry at loc and set the value
        motorDashboard.getEntry(loc).setValue(value);
    }

    /**
     * Retrieves the value for the loc at the table
     * @param loc - the referenced entry
     * @return - the current value at that entry
     */
    public static NetworkTableValue getMotorDashboardValue (String loc){
        return motorDashboard.getEntry(loc).getValue();
    }

    /** ---- Solenoid Dashboard ---- */

    /**
     * The name for the solenoid dashboard table
     */
    public static final String SOLENOID_DASHBOARD_NAME = "SolenoidDashboard";

    /**
     * The solenoid dashboard network table
     */
    private static NetworkTable solenoidDashboard;

    /**
     * Initializes the solenoid dashboard
     */
    private static void initSolenoidDashboard (){
        // Retrieve the solenoid dashboard table
        solenoidDashboard = tableInstance.getTable(SOLENOID_DASHBOARD_NAME);
    }

    /**
     * Sets the value of the entry given by loc to value
     * @param loc - the referenced entry
     * @param value - the value to set it to
     */
    public static void setSolenoidDashboardValue (String loc, NetworkTableValue value){
        // Get the entry at loc and set the value
        solenoidDashboard.getEntry(loc).setValue(value);
    }

    /**
     * Retrieves the value for the loc at the table
     * @param loc - the referenced entry
     * @return - the current value at that entry
     */
    public static NetworkTableValue getSolenoidDashboardValue (String loc){
        return solenoidDashboard.getEntry(loc).getValue();
    }
    
    /** ---- I/O Dashboard ---- */

    /**
     * The name for the solenoid dashboard table
     */
    public static final String IO_DASHBOARD_NAME = "IODashboard";

    /**
     * The io dashboard network table
     */
    private static NetworkTable ioDashboard;

    /**
     * Initializes the io dashboard
     */
    private static void initIoDashboard (){
        // Retrieve the io dashboard table
        ioDashboard = tableInstance.getTable(IO_DASHBOARD_NAME);
    }

    /**
     * Sets the value of the entry given by loc to value
     * @param loc - the referenced entry
     * @param value - the value to set it to
     */
    public static void setIoDashboardValue (String loc, NetworkTableValue value){
        // Get the entry at loc and set the value
        ioDashboard.getEntry(loc).setValue(value);
    }

    /**
     * Retrieves the value for the loc at the table
     * @param loc - the referenced entry
     * @return - the current value at that entry
     */
    public static NetworkTableValue getIoDashboardValue (String loc){
        return ioDashboard.getEntry(loc).getValue();
    }


}