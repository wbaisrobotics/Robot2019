package frc.robot.components.speed;

import jaci.pathfinder.Trajectory;

/**
 * Follows a trajectory and calculates the output to each motor
 */
public class EncoderFollower {

    /**
     * The initial value of the encoder
     */
    private double initialEncoderValue = 0.0;

    /**
     * The constants for the controller
     */
    private double kp, kd, kv, ka;

    /**
     * The latest error between the setpoint and the actual
     */
    public double lastError;
    /**
     * The latest heading
     */
    private double heading;

    private double ticksPerMeter;

    /**
     * The segment currently being processed
     */
    private int segment;
    /**
     * The trajectory that the follower is following
     */
    private Trajectory trajectory;

    /**
     * Initiate the follower with a trajectory
     */
    public EncoderFollower(Trajectory traj) {
        this.trajectory = traj;
    }

    /**
     * Configure the PID/VA Variables for the Follower
     * @param kp The proportional term. This is usually quite high (0.8 - 1.0 are common values)
     * @param kd The derivative term. Adjust this if you are unhappy with the tracking of the follower. 0.0 is the default
     * @param kv The velocity ratio. This should be 1 over your maximum velocity @ 100% throttle.
     *           This converts m/s given by the algorithm to a scale of -1..1 to be used by your
     *           motor controllers
     * @param ka The acceleration term. Adjust this if you want to reach higher or lower speeds faster. 0.0 is the default
     */
    public void configurePDVA(double kp, double kd, double kv, double ka) {
        this.kp = kp; this.kd = kd;
        this.kv = kv; this.ka = ka;
    }

    /**
     * Configure the Encoders being used in the follower.
     * @param initial_position      The initial 'offset' of your encoder. This should be set to the encoder value just
     *                              before you start to track
     */
    public void configureEncoder(int initial_position, double ticksPerMeter) {
        // Store the initial value
        initialEncoderValue = initial_position;
        this.ticksPerMeter = ticksPerMeter;
    }

    /**
     * Reset the follower to start again. Encoders must be reconfigured.
     */
    public void reset() {
        lastError = 0; segment = 0;
    }

    /**
     * Calculate the desired output for the motors, based on the amount of ticks the encoder has gone through.
     * This does not account for heading of the robot. To account for heading, add some extra terms in your control
     * loop for realignment based on gyroscope input and the desired heading given by this object.
     * @param encoderLoc The current location of the encoder
     * @return             The desired output for your motor controller
     */
    public double calculate(int encoderLoc) {
        // Number of Revolutions * Wheel Circumference
        double distance_covered = (encoderLoc - initialEncoderValue)/ticksPerMeter;
        // If done, return 0
        if (segment >= trajectory.length()) {
            return 0;
        }
        // If not, calculate the output
        // Get the current segment
        Trajectory.Segment seg = trajectory.get(segment);
        // Calculate the current error
        double error = seg.position - distance_covered;
        // Calculate the output value
        double calculated_value =
            kp * error +                                    // Proportional
            kd * ((error - lastError) / seg.dt) +          // Derivative
            (kv * seg.velocity + ka * seg.acceleration);    // V and A Terms
            lastError = error;
            heading = seg.heading;
            segment++;

        return calculated_value;
        
    }

    /**
     * @return the desired heading of the current point in the trajectory
     */
    public double getHeading() {
        return heading;
    }

    /**
     * @return the current segment being operated on
     */
    public Trajectory.Segment getSegment() {
        return trajectory.get(segment);
    }

    /**
     * @return whether we have finished tracking this trajectory or not.
     */
    public boolean isFinished() {
        return segment >= trajectory.length();
    }

}
