package frc.robot.oi;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.util.Cantor;

/**
 * Connection between controllers and commands on the robot
 * @author orianleitersdorf
 *
 */
public class OI {
	
	private static enum ButtonType {
		BUTTON, AXIS, POV;
	}
	
	public static enum Controllers {
		
		PILOT (0), COPILOT (1);
		
		private int m_port;
		private Controllers (int port) {
			this.m_port = port;
		}
		
		private int getPort () {
			return this.m_port;
		}
	}

	/** The XboxController representing the pilot **/
	private static Map <Integer, XboxController> controllers = new HashMap <Integer, XboxController>();

	
	/**
	 * Returns the pilot controller
	 * @return
	 */
	public static XboxController getController (Controllers controller) {
		if (!controllers.containsKey(controller.getPort())) {
			controllers.put(controller.getPort(), new XboxController (controller.getPort()));
		}
		return controllers.get(controller.getPort());
	}
	
	private static Map <Integer, Button> buttons = new HashMap <Integer, Button>();
	
	private static Button getButton (Controllers controller, ButtonConfiguration config){
		int index = Cantor.getIndex(controller.getPort(), ButtonType.BUTTON.ordinal(), config.getPort());
		if (!buttons.containsKey(index)) {
			buttons.put(index, new JoystickButton (getController (controller), config));
		}
		return buttons.get(index);
	}
	
	private static Button getButton (Controllers controller, POVDirection config){
		int index = Cantor.getIndex(controller.getPort(), ButtonType.POV.ordinal(), config.getAngle());
		if (!buttons.containsKey(index)) {
			buttons.put(index, new POVButton (getController (controller), config));
		}
		return buttons.get(index);
	}
	
	private static Button getButton (Controllers controller, AxisConfiguration config, double low, double high){
		int index = Cantor.getIndex(controller.getPort(), ButtonType.AXIS.ordinal(), config.getRawAxis());
		if (!buttons.containsKey(index)) {
			buttons.put(index, new AxisButton (getController (controller), config, low, high));
		}
		return buttons.get(index);
	}
	
	private static Button getButton (Button... buttons) {
		return new ButtonCollection (buttons);
	}
	
	public static void initButtons () {
		
		// Command toggleGearSpeed = new ToggleGearSpeed(drive);
		// Command invertDrive = new InvertDrive(drive);
		// Command startIntake = new StartIntake (intake);
		// Command reverseIntake = new ReverseIntake (intake);
		// Command toggleRetractIntake = new ToggleRetractIntake(intake);
		
		// getButton (Controllers.PILOT, OIConstants.TOGGLE_GEAR_SPEED).whenPressed(toggleGearSpeed);
		// getButton (Controllers.PILOT, OIConstants.TOGGLE_INVERSE).whenPressed(invertDrive);
		// getButton (Controllers.PILOT, OIConstants.TOGGLE_INTAKE_IN).toggleWhenPressed(startIntake);
		// getButton (Controllers.PILOT, OIConstants.TOGGLE_INTAKE_REVERSE).toggleWhenPressed(reverseIntake);
		// getButton (Controllers.PILOT, OIConstants.TOGGLE_RETRACT_INTAKE).whenPressed(toggleRetractIntake);
		
		// SmartDashboard.putData(toggleGearSpeed);
		// SmartDashboard.putData(toggleGearSpeed);
		// SmartDashboard.putData(startIntake);
		// SmartDashboard.putData(reverseIntake);
		// SmartDashboard.putData(toggleRetractIntake);
		
		
		// Command extendFork = new ExtendFork (fork);
		// Command retractFork = new RetractFork (fork);
		// Command toggleGripCube = new ToggleGripCube (fork);
		// Command releaseFork = new ReleaseFork(fork);
		// Command lowerRamp = new LowerRamp(ramp);
		// Command liftRamp = new LiftRamp(ramp);
		
		// Command revertLowerRamp = new ReverseLowerRamp(ramp);
		// Command revertLiftRamp = new ReverseLiftRamp(ramp);
		
		// getButton (Controllers.COPILOT, OIConstants.EXTEND_FORK).whileHeld(extendFork);
		// getButton (Controllers.COPILOT, OIConstants.RETRACT_FORK).whileHeld(retractFork);
		// getButton (Controllers.COPILOT, OIConstants.TOGGLE_GRIPPER).whenPressed(toggleGripCube);
		// getButton (getButton (Controllers.COPILOT, OIConstants.RELEASE_FORK_A), getButton (Controllers.COPILOT, OIConstants.RELEASE_FORK_B)).whenPressed(releaseFork);
		// getButton (Controllers.COPILOT, OIConstants.LOWER_RAMP, 0.5, 1).whileHeld(lowerRamp);
		// getButton (Controllers.COPILOT, OIConstants.LIFT_RAMP, 0.5, 1).whileHeld(liftRamp);
		
		// SmartDashboard.putData(extendFork);
		// SmartDashboard.putData(retractFork);
		// SmartDashboard.putData(toggleGripCube);
		// SmartDashboard.putData(releaseFork);
		// SmartDashboard.putData(lowerRamp);
		// SmartDashboard.putData(liftRamp);
		// SmartDashboard.putData(extendFork);
		
		// SmartDashboard.putData(revertLowerRamp);
		// SmartDashboard.putData(revertLiftRamp);
	
		
	}

}
