package frc.robot.oi;

import edu.wpi.first.wpilibj.GenericHID;

public class JoystickButton extends edu.wpi.first.wpilibj.buttons.JoystickButton {

	public JoystickButton(GenericHID joystick, ButtonConfiguration button) {
		super(joystick, button.getPort());
	}

}
