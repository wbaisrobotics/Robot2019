package frc.robot.oi;

public enum AxisConfiguration {
	
	LEFT_X (0),
	LEFT_Y (1),
	RIGHT_X (4),
	RIGHT_Y (5),
	LEFT_TRIGGER (2),
	RIGHT_TRIGGER (3);

	private int m_rawAxis;
	private AxisConfiguration(int axis) {
		m_rawAxis = axis;
	}
	public int getRawAxis() {
		return m_rawAxis;
	}

}
