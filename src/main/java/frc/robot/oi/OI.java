package frc.robot.oi;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import frc.robot.commands.ToggleGearSpeed;
import frc.robot.commands.ToggleReverse;
import frc.robot.util.Cantor;

import frc.robot.constants.OIConstants;

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
	
	// private static Button getButton (Controllers controller, POVDirection config){
	// 	int index = Cantor.getIndex(controller.getPort(), ButtonType.POV.ordinal(), config.getAngle());
	// 	if (!buttons.containsKey(index)) {
	// 		buttons.put(index, new POVButton (getController (controller), config));
	// 	}
	// 	return buttons.get(index);
	// }
	
	// private static Button getButton (Controllers controller, AxisConfiguration config, double low, double high){
	// 	int index = Cantor.getIndex(controller.getPort(), ButtonType.AXIS.ordinal(), config.getRawAxis());
	// 	if (!buttons.containsKey(index)) {
	// 		buttons.put(index, new AxisButton (getController (controller), config, low, high));
	// 	}
	// 	return buttons.get(index);
	// }
	
	// private static Button getButton (Button... buttons) {
	// 	return new ButtonCollection (buttons);
	// }

	public static XboxController getPilot(){
		return getController(Controllers.PILOT);
	}
	
	public static XboxController getCoPilot(){
		return getController(Controllers.COPILOT);
	}

	/**
	 * Initializes all the commands controlled by buttons and assigns the buttons to them
	 */
	public static void initButtons () {

		// Initializes the stick button for toggling gear speed
		getButton(Controllers.PILOT, OIConstants.TOGGLE_GEAR_SPEED).whenPressed(new ToggleGearSpeed());

		// Initializes the b button for toggling reverse
		getButton(Controllers.PILOT, OIConstants.TOGGLE_REVERSE).whenPressed(new ToggleReverse());
	
	}

}
