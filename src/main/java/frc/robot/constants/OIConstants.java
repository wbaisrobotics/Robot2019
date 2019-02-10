package frc.robot.constants;

import frc.robot.oi.AxisConfiguration;
import frc.robot.oi.ButtonConfiguration;

public class OIConstants {

	// /* Pilot */

	/**
	 * The control for toggling gear speed
	 */
	public static final ButtonConfiguration TOGGLE_GEAR_SPEED = ButtonConfiguration.kStickLeft;

	/**
	 * The control for turning the drive system
	 */
	public static final AxisConfiguration DRIVE_TURN = AxisConfiguration.RIGHT_X;

	/**
	 * The control for the drive system magnitude
	 */
	public static final AxisConfiguration DRIVE_MAGNITUDE = AxisConfiguration.LEFT_Y;
	
	// /* Copilot */

	public static final ButtonConfiguration TOGGLE_REVERSE = ButtonConfiguration.kB;
	
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
