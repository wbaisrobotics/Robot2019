package frc.robot.constants;

import frc.robot.oi.AxisConfiguration;
import frc.robot.oi.POVDirection;

public class OIConstants {

	// /* Pilot */

	/**
	 * When pressed, allows for NORTH/SOUTH to control the front climbers 
	 */
	public static final POVDirection FRONT_CLIMBERS_A = POVDirection.WEST;

	/**
	 * When pressed, allows for NORTH/SOUTH to control the back climbers 
	 */
	public static final POVDirection BACK_CLIMBERS_A = POVDirection.EAST;

	/**
	 * When pressed, retracts the climbers elected using FRONT_CLIMBERS_A or BACK_CLIMBERS_A
	 */
	public static final POVDirection RETRACT_CLIMBERS_B = POVDirection.NORTH;

	/**
	 * When pressed, extends the climbers elected using FRONT_CLIMBERS_A or BACK_CLIMBERS_A
	 */
	public static final POVDirection EXTEND_CLIMBERS_B = POVDirection.SOUTH;
	
	/**
	 * The control for turning the drive system
	 */
	public static final AxisConfiguration DRIVE_TURN = AxisConfiguration.RIGHT_X;

	/**
	 * The control for the drive system magnitude
	 */
	public static final AxisConfiguration DRIVE_MAGNITUDE = AxisConfiguration.LEFT_Y;
	
	// /* Copilot */
	
	// public static final ButtonConfiguration RETRACT_FORK = ButtonConfiguration.kB;
	// public static final ButtonConfiguration EXTEND_FORK = ButtonConfiguration.kA;
	// public static final ButtonConfiguration TOGGLE_GRIPPER = ButtonConfiguration.kBumperRight;
	
	// // Both must be pressed together
	// public static final ButtonConfiguration RELEASE_FORK_A = ButtonConfiguration.kBack;
	// public static final ButtonConfiguration RELEASE_FORK_B = ButtonConfiguration.kStart;
	
	// public static final AxisConfiguration LOWER_RAMP = AxisConfiguration.LEFT_TRIGGER;
	// public static final AxisConfiguration LIFT_RAMP = AxisConfiguration.RIGHT_TRIGGER;
	
	// public static final AxisConfiguration ELEVATOR_Y = AxisConfiguration.LEFT_Y;
	

}
