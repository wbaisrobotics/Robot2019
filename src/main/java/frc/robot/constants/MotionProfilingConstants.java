package frc.robot.constants;

/**
 * Stores the motion profiling constants
 */
public class MotionProfilingConstants{

    /**
	 * Set to zero to skip waiting for confirmation.
	 * Set to nonzero to wait and report to DS if action fails.
	 */
    public final static int kTimeoutMs = 30;
    
    /* We allow either a 0 or 1 when selecting a PID Index, where 0 is primary and 1 is auxiliary */
    public final static int PID_PRIMARY = 0;
    
    public static final int k_ticks_per_rev = 1024;
    public static final double k_wheel_diameter = 4.0 / 12.0;
    public static final double k_max_velocity = 10;

    public static final int k_left_channel = 0;
    public static final int k_right_channel = 1;


}