package frc.robot.oi;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;

public class POVButton extends Button {
	
	private XboxController m_controller;
	private POVDirection m_direction;

	public POVButton(XboxController controller, POVDirection direction) {
		this.m_controller = controller;
		this.m_direction = direction;
	}

	@Override
	public boolean get() {
		return m_controller.getPOV() == m_direction.getAngle();
	}

}
