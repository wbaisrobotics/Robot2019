package frc.robot.oi;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Button;

public class AxisButton extends Button {

	private GenericHID m_controller;
	private AxisConfiguration m_axis;
	private double m_low;
	private double m_high;
	
	public AxisButton(GenericHID controller, AxisConfiguration axis, double low, double high) {
		this.m_controller = controller;
		this.m_low = low;
		this.m_high = high;
		this.m_axis = axis;
	}

	public boolean get() {
		double reading = this.m_controller.getRawAxis(m_axis.getRawAxis());
		return (reading >= this.m_low) && (reading <= this.m_high);
	}

}
