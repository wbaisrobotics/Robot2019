
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
     * The name for the smart dashboard table
     */
    public static final String SMART_DASHBOARD_NAME = "SmartDashboard";

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
    }

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


}