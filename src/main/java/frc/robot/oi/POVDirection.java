package frc.robot.oi;

public enum POVDirection {
	NORTH (0),
	NORTHEAST (45),
	EAST (90),
	SOUTHEAST (135),
	SOUTH (180),
	SOUTHWEST (225),
	WEST (270),
	NORTHWEST (315),
	NOSELECTION (-1),
	ERROR (-1000);
	
	private int angle;
	private POVDirection (int angle) {
		this.angle = angle;
	}
	public int getAngle() {
		return angle;
	}
}
