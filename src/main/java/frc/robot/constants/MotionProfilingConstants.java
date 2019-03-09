package frc.robot.constants;

/**
 * Stores the motion profiling constants
 */
public class MotionProfilingConstants{

    /**
	 * Which PID slot to pull gains from. Starting 2018, you can choose from
	 * 0,1,2 or 3. Only the first two (0,1) are visible in web-based
	 * configuration.
	 */
	public static final int kSlotIdx = 0;

	/**
	 * Talon SRX/ Victor SPX will supported multiple (cascaded) PID loops. For
	 * now we just want the primary one.
	 */
	public static final int kPIDLoopIdx = 0;

    /**
	 * Set to zero to skip waiting for confirmation.
	 * Set to nonzero to wait and report to DS if action fails.
	 */
    public final static int kTimeoutMs = 30;

    /**
     * The numbers of ticks per meter for left
     */
    public static final double kTicksPerMeterLeft = -62569;

    /**
     * The numbers of ticks per meter for right
     */
    public static final double kTicksPerMeterRight = -61874; 

    /**
     * The max velocity in m/s
     */
    public static final double kMaxVelocity = 2.4;

    /**
     * The proportional constant for the controllers
     */
    public static double kP = 0;
    /**
     * The integral constant for the controllers
     */
    public static double kI = 0;
    /**
     * The derivative constant for the controllers
     */
    public static double kD = 0;
    /**
     * The velocity constant for the controllers
     */
    public static double kV = 1/kMaxVelocity;
    /**
     * The acceleration constant for the controllers
     */
    public static double kA = 0;
    /**
     * The turn constant for the controllers
     */
    public static double kTurn = 0;

    /**
     * Each profile lasts 20ms
     */
    public static final int kMotionProfileLength = 20;

}