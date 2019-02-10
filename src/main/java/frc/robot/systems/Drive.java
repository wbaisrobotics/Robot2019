
package frc.robot.systems;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.components.NetworkTableCommunicator;
import frc.robot.components.speed.EncoderFollower;
import frc.robot.components.speed.SpeedControllers;
import frc.robot.constants.MotionProfilingConstants;
import frc.robot.constants.network.SmartDashboardConstants;
import frc.robot.constants.wiring.CANWiring;
import frc.robot.constants.wiring.PCMWiring;
import frc.robot.util.Logger;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.PathfinderFRC;
import jaci.pathfinder.Trajectory;

import edu.wpi.first.wpilibj.DoubleSolenoid;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * Represents the drive class of the robot
 */
public class Drive extends DifferentialDrive{

    /**
     * Whether or not the left side should be reversed
     */
    public final static boolean LEFT_SIDE_REVERSE = false;

    /**
     * Whether or not the right side should be reversed
     */
    public final static boolean RIGHT_SIDE_REVERSE = true;

    private EncoderFollower m_left_follower;
    private EncoderFollower m_right_follower;

    private Notifier m_follower_notifier;

    /** The sole static instance of Drive */
    private static Drive instance;
    /**
     * Returns an instance of Drive, creating one if necessary
     * @return
     */
    public static Drive getInstance (){
        if (instance == null){

            // Initialize the Talon SRXs

            // Initialize the left master
            WPI_TalonSRX left1 = SpeedControllers.getTalonSRX(CANWiring.DRIVE_LEFT_1);
            // Initialize the left slaves
            WPI_TalonSRX left2 = SpeedControllers.getTalonSRX(CANWiring.DRIVE_LEFT_2);
            WPI_TalonSRX left3 = SpeedControllers.getTalonSRX(CANWiring.DRIVE_LEFT_3);

            // Initialize the right master
            WPI_TalonSRX right1 = SpeedControllers.getTalonSRX(CANWiring.DRIVE_RIGHT_1);
            // Initialize the left slaves
            WPI_TalonSRX right2 = SpeedControllers.getTalonSRX(CANWiring.DRIVE_RIGHT_2);
            WPI_TalonSRX right3 = SpeedControllers.getTalonSRX(CANWiring.DRIVE_RIGHT_3);

            // Initialize the gear shifter
            DoubleSolenoid gearShifter = new DoubleSolenoid (PCMWiring.G_A.getPort(), PCMWiring.G_B.getPort());

            // Initialize the gyro
            ADXRS450_Gyro gyro = new ADXRS450_Gyro();
            // Add gyro to IO Dashboard
            NetworkTableCommunicator.setIoDashboardValue("Gyro", gyro);

            // Create the instance
            instance = new Drive(left1, left2, left3, right1, right2, right3, gearShifter, gyro);

        }
        return instance;
    }

    /**
     * The left master
     */
    private WPI_TalonSRX left;
    /**
     * The right master
     */
    private WPI_TalonSRX right;

    /**
     * The gear shifter
     */
    private DoubleSolenoid gearShifter;

    /**
     * The gyro
     */
    private Gyro gyro;

    /**
     * Whether or not drive is reversed
     */
    private boolean reverse = false;

    /**
     * Default constructor for just the motors and no encoders
     * @param left - the left motors
     * @param right - the right motors
     */
    public Drive (WPI_TalonSRX left1, WPI_TalonSRX left2, WPI_TalonSRX left3, WPI_TalonSRX right1, WPI_TalonSRX right2, WPI_TalonSRX right3, DoubleSolenoid gearShifter, Gyro gyro){

        // Call DifferentialDrive with the controllers
        super (left1, right1);
        // Save the controllers
        this.left = left1;
        this.right = right1;

        // Save the gyro
        this.gyro = gyro;

        // Save the gear shifter
        this.gearShifter = gearShifter;

        // Set the inverted settings for the masters
        left1.setInverted(LEFT_SIDE_REVERSE);
        right1.setInverted(RIGHT_SIDE_REVERSE); 

        // Configure the other talons to follow
        left2.follow(left1);
        left3.follow(left1);
        right2.follow(right1);
        right3.follow(right1);

        // Configure the other talons to invert
        left2.setInverted(InvertType.FollowMaster);
        left3.setInverted(InvertType.FollowMaster);
        right2.setInverted(InvertType.FollowMaster);
        right3.setInverted(InvertType.FollowMaster);

        // Set frame update to every 1ms for left
        left.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 1);
        // Set frame update to every 1ms for right
        right.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 1);

        // Config the sensor coefficient for left
        left.configSelectedFeedbackCoefficient(1);
        // Invert the left sensor
        left.setSensorPhase(true);
        // Config the sensor coefficient for right
        right.configSelectedFeedbackCoefficient(1);
        // Invert the right sensor
        right.setSensorPhase(true);

        // Stop the WPILib class from inverting the right side
        super.setRightSideInverted(false);

        /* Config the peak and nominal outputs, 12V means full */
		left.configNominalOutputForward(0, MotionProfilingConstants.kTimeoutMs);
		left.configNominalOutputReverse(0, MotionProfilingConstants.kTimeoutMs);
		left.configPeakOutputForward(1, MotionProfilingConstants.kTimeoutMs);
		left.configPeakOutputReverse(-1, MotionProfilingConstants.kTimeoutMs);

		/**
		 * Config the allowable closed-loop error, Closed-Loop output will be
		 * neutral within this range. See Table in Section 17.2.1 for native
		 * units per rotation.
		 */
        left.configAllowableClosedloopError(0, MotionProfilingConstants.kPIDLoopIdx, MotionProfilingConstants.kTimeoutMs);
        
        /* Config the peak and nominal outputs, 12V means full */
		right.configNominalOutputForward(0, MotionProfilingConstants.kTimeoutMs);
		right.configNominalOutputReverse(0, MotionProfilingConstants.kTimeoutMs);
		right.configPeakOutputForward(1, MotionProfilingConstants.kTimeoutMs);
		right.configPeakOutputReverse(-1, MotionProfilingConstants.kTimeoutMs);

		/**
		 * Config the allowable closed-loop error, Closed-Loop output will be
		 * neutral within this range. See Table in Section 17.2.1 for native
		 * units per rotation.
		 */
		right.configAllowableClosedloopError(0, MotionProfilingConstants.kPIDLoopIdx, MotionProfilingConstants.kTimeoutMs);
        
        // Update the PID values
        updatePID();

    }

    /**
     * Resets all sensors
     */
    public void reset (){

        // Reset the gyro
        gyro.reset();
        
        // Reset the encoders
        left.getSensorCollection().setQuadraturePosition(0, 30);
        right.getSensorCollection().setQuadraturePosition(0, 30);

        // Log the reset
        Logger.log("[Quadrature Encoders] All sensors are zeroed.");
        
    }

    public void updatePID(){

        // Pull from network tables
        MotionProfilingConstants.kP = NetworkTableCommunicator.get(SmartDashboardConstants.P_CONST).getDouble();
        MotionProfilingConstants.kI = NetworkTableCommunicator.get(SmartDashboardConstants.I_CONST).getDouble();
        MotionProfilingConstants.kD = NetworkTableCommunicator.get(SmartDashboardConstants.D_CONST).getDouble();
        MotionProfilingConstants.kF = NetworkTableCommunicator.get(SmartDashboardConstants.F_CONST).getDouble();

        /* Config Position Closed Loop gains for left */
		left.config_kP(MotionProfilingConstants.kPIDLoopIdx, MotionProfilingConstants.kP, MotionProfilingConstants.kTimeoutMs);
		left.config_kI(MotionProfilingConstants.kPIDLoopIdx, MotionProfilingConstants.kI, MotionProfilingConstants.kTimeoutMs);
        left.config_kD(MotionProfilingConstants.kPIDLoopIdx, MotionProfilingConstants.kD, MotionProfilingConstants.kTimeoutMs);
		left.config_kF(MotionProfilingConstants.kPIDLoopIdx, MotionProfilingConstants.kF, MotionProfilingConstants.kTimeoutMs);
        
        /* Config Position Closed Loop gains for right */
		right.config_kP(MotionProfilingConstants.kPIDLoopIdx, MotionProfilingConstants.kP, MotionProfilingConstants.kTimeoutMs);
		right.config_kI(MotionProfilingConstants.kPIDLoopIdx, MotionProfilingConstants.kI, MotionProfilingConstants.kTimeoutMs);
        right.config_kD(MotionProfilingConstants.kPIDLoopIdx, MotionProfilingConstants.kD, MotionProfilingConstants.kTimeoutMs);
        right.config_kF(MotionProfilingConstants.kPIDLoopIdx, MotionProfilingConstants.kF, MotionProfilingConstants.kTimeoutMs);

    }

    /**
     * Immediately halts both sides
     */
    public void stop (){

        if (m_follower_notifier != null){
            m_follower_notifier.stop();
        }

        left.stopMotor();
        right.stopMotor();

    }

    /**
     * Starts following the path with name
     * @param pathName
     */
    public void drivePath (String pathName){

        Trajectory left_trajectory = PathfinderFRC.getTrajectory(pathName + ".left");
        Trajectory right_trajectory = PathfinderFRC.getTrajectory(pathName + ".right");

        m_left_follower = new EncoderFollower(left_trajectory);
        m_right_follower = new EncoderFollower(right_trajectory);

        m_left_follower.configureEncoder((int)left.getSelectedSensorPosition(), 62569);
        // You must tune the PID values on the following line!
        m_left_follower.configurePIDVA(SmartDashboard.getNumber("P Const", 0), SmartDashboard.getNumber("I Const", 0), SmartDashboard.getNumber("D Const", 0), 1 / k_max_velocity, 0);

        m_right_follower.configureEncoder((int)right.getSelectedSensorPosition(), 61874);
        // You must tune the PID values on the following line!
        m_right_follower.configurePIDVA(SmartDashboard.getNumber("P Const", 0), SmartDashboard.getNumber("I Const", 0), SmartDashboard.getNumber("D Const", 0), 1 / k_max_velocity, 0);

        m_follower_notifier = new Notifier(this::followPath);
        m_follower_notifier.startPeriodic(left_trajectory.get(0).dt);

    }

    private void followPath() {
        if (m_left_follower.isFinished() || m_right_follower.isFinished()) {
          m_follower_notifier.stop();
          left.stopMotor();
          right.stopMotor();
        } else {

            updatePID();

          double left_speed = m_left_follower.calculate((int)left.getSelectedSensorPosition());
          double right_speed = m_right_follower.calculate((int)right.getSelectedSensorPosition());
          double heading = gyro.getAngle();
          double desired_heading = Pathfinder.r2d(m_left_follower.getHeading());
          double heading_difference = Pathfinder.boundHalfDegrees(desired_heading - heading);
          double turn =  SmartDashboard.getNumber("Turn Const", 0.8) * (-1.0/80.0) * heading_difference;
          left.set((left_speed + turn));
          right.set((right_speed - turn));

          SmartDashboard.putNumber ("Left Speed", left_speed + turn);
          SmartDashboard.putNumber ("Right Speed", -(right_speed - turn));

          System.out.println((left_speed + turn) + ":" + -(right_speed - turn));
        }
      }

      /**
       * Returns the left master
       * @return the left master controller
       */
      public WPI_TalonSRX getLeft(){
        return this.left;
      }

      /**
       * Returns the right master
       * @return the right master controller
       */
      public WPI_TalonSRX getRight(){
        return this.right;
      }

      /**
       * Updatest the reverse setting and sends to talons
       * @param reverse - the new value for reverse
       */
      public void setReverse (boolean reverse){

        // Log the change
        Logger.log("Swapped face to: " + reverse);

        // Set the reverse variable
        this.reverse = reverse;

        // Update the indicator LEDs
        // TBD

      }

      /**
       * Toggles whether or not driving in reverse
       */
      public void toggleReverse (){
        // Set the reverse variable to the opposite of what it currently is
        setReverse (!getReverse());
      }

      /**
       * Returns whether or not driving is reversed
       * @return - the current status of the reverse boolean
       */
      public boolean getReverse (){
          return this.reverse;
      }

      /**
       * Arcade drive (accounting for reverse)
       */
      public void arcadeDrive (double xSpeed, double zRotation){
          super.arcadeDrive(getReverse()?-xSpeed:xSpeed, zRotation);
      }

      /**
       * Toggles the gear speed
       */
      public void toggleGearSpeed (){
          this.gearShifter.set(this.gearShifter.get() == Value.kForward?Value.kReverse:Value.kForward);
      }

      /**
       * Returns the gyro
       * @return gyro
       */
      public Gyro getGyro(){
          return this.gyro;
      }

}