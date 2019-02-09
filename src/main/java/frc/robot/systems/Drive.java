
package frc.robot.systems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.components.speed.SpeedControllers;
import frc.robot.constants.wiring.CANWiring;
import frc.robot.constants.wiring.PCMWiring;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.PathfinderFRC;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;

import edu.wpi.first.wpilibj.DoubleSolenoid;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Drive extends DifferentialDrive{

    public final static boolean LEFT_SIDE_REVERSE = false;
    public final static boolean RIGHT_SIDE_REVERSE = true;

    /**
	 * Set to zero to skip waiting for confirmation.
	 * Set to nonzero to wait and report to DS if action fails.
	 */
    public final static int kTimeoutMs = 30;
    
    /* We allow either a 0 or 1 when selecting a PID Index, where 0 is primary and 1 is auxiliary */
	public final static int PID_PRIMARY = 0;

      //private static final int k_ticks_per_rev = 1024;
    //private static final double k_wheel_diameter = 4.0 / 12.0;
    // private static final double k_max_velocity = 10;

    // private static final int k_left_channel = 0;
    // private static final int k_right_channel = 1;

    // private static final int k_gyro_port = 0;

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

            instance = new Drive(left1, left2, left3, right1, right2, right3, gearShifter, gyro);

        }
        return instance;
    }

    private WPI_TalonSRX left;
    private WPI_TalonSRX right;

    private DoubleSolenoid gearShifter;

    private Gyro gyro;

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

        // Factory default all the talons to prevent unexpected behavior
        left1.configFactoryDefault();
        left2.configFactoryDefault();
        left3.configFactoryDefault();
        right1.configFactoryDefault();
        right2.configFactoryDefault();
        right3.configFactoryDefault();

        // Set the inverted settings for the masters
        left1.setInverted(LEFT_SIDE_REVERSE);
        right1.setInverted(RIGHT_SIDE_REVERSE); 

        // Configure the other talons to follow
        left2.set(ControlMode.Follower, left1.getDeviceID());
        left3.set(ControlMode.Follower, left1.getDeviceID());
        right2.set(ControlMode.Follower, right1.getDeviceID());
        right3.set(ControlMode.Follower, right1.getDeviceID());

        // Configure the other talons to invert
        left2.setInverted(InvertType.FollowMaster);
        left3.setInverted(InvertType.FollowMaster);
        right2.setInverted(InvertType.FollowMaster);
        right3.setInverted(InvertType.FollowMaster);

        // Stop the WPILib class from inverting the right side
        super.setRightSideInverted(false);

        
        /* Configure PID Gains, to be used with Motion Profile */
		

		// /* Our profile uses 10ms timing */
        // left.configMotionProfileTrajectoryPeriod(10, kTimeoutMs); 
        
		
		// /* Status 10 provides the trajectory target for motion profile AND motion magic */
        // left.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, kTimeoutMs);
        
        // updatePID();

		// /* Our profile uses 10ms timing */
		// right.configMotionProfileTrajectoryPeriod(10, kTimeoutMs); 
		
		// /* Status 10 provides the trajectory target for motion profile AND motion magic */
		// right.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, kTimeoutMs);

    }

    /**
     * Resets all sensors
     */
    public void reset (){
        // Reset the gyro
        gyro.reset();
        // Reset the encoders
        left.getSensorCollection().setQuadraturePosition(0, kTimeoutMs);
		right.getSensorCollection().setQuadraturePosition(0, kTimeoutMs);
		System.out.println("[Quadrature Encoders] All sensors are zeroed.");
    }

    public void updatePID(){

        double p = SmartDashboard.getNumber("P", 0);
        double i = SmartDashboard.getNumber("I", 0);
        double d = SmartDashboard.getNumber("D", 0);
        double f = SmartDashboard.getNumber("F", 0);
        System.out.println(p +":"+ i +":"+d+":"+f);
        // Update the PID Values to what is in the dashboard
        left.config_kF(PID_PRIMARY, f, kTimeoutMs);
		left.config_kP(PID_PRIMARY, p, kTimeoutMs);
		left.config_kI(PID_PRIMARY, i, kTimeoutMs);
        left.config_kD(PID_PRIMARY, d, kTimeoutMs);
        right.config_kF(PID_PRIMARY, f, kTimeoutMs);
		right.config_kP(PID_PRIMARY, p, kTimeoutMs);
		right.config_kI(PID_PRIMARY, i, kTimeoutMs);
		right.config_kD(PID_PRIMARY, d, kTimeoutMs);
    }

    /**
     * Updates the PID Setpoint and starts the onboard pid controller
     * @param left
     * @param right
     */
    public void setPIDSetpoint(double leftSetpoint, double rightSetpoint){
        // Set the setpoint for left
        //left.getPIDController().setReference(leftSetpoint, ControlType.kPosition);
        // Set the setpoint for right
        //right.getPIDController().setReference(rightSetpoint, ControlType.kPosition);
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

        m_left_follower.configureEncoder((int)left.getSelectedSensorPosition(), -113, 0.15);
        // You must tune the PID values on the following line!
        //m_left_follower.configurePIDVA(SmartDashboard.getNumber("P Const", 0), SmartDashboard.getNumber("I Const", 0), SmartDashboard.getNumber("D Const", 0), 1 / k_max_velocity, 0);

        m_right_follower.configureEncoder((int)right.getSelectedSensorPosition(), 113, 0.15);
        // You must tune the PID values on the following line!
        //m_right_follower.configurePIDVA(SmartDashboard.getNumber("P Const", 0), SmartDashboard.getNumber("I Const", 0), SmartDashboard.getNumber("D Const", 0), 1 / k_max_velocity, 0);

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
          right.set(-(right_speed - turn));
        //   left.getPIDController().setReference(left_speed, ControlType.kPosition);
        //   right.getPIDController().setReference(right_speed, ControlType.kPosition);

          SmartDashboard.putNumber ("Left Speed", left_speed + turn);
          SmartDashboard.putNumber ("Right Speed", -(right_speed - turn));

          System.out.println((left_speed + turn) + ":" + -(right_speed - turn));
        }
      }

      public WPI_TalonSRX getLeft(){
        return this.left;
      }

      public WPI_TalonSRX getRight(){
        return this.right;
      }

      /**
       * Updatest the reverse setting and sends to talons
       * @param reverse - the new value for reverse
       */
      public void setReverse (boolean reverse){

        System.out.println("Swapped face to: " + reverse);

        this.reverse = reverse;

        this.left.setInverted(reverse?!LEFT_SIDE_REVERSE:LEFT_SIDE_REVERSE);

        this.right.setInverted(reverse?!RIGHT_SIDE_REVERSE:RIGHT_SIDE_REVERSE);

      }

      public void toggleReverse (){
        this.left.setInverted(!this.left.getInverted());

        this.right.setInverted(!this.right.getInverted());
      }

      /**
       * Returns whether or not driving is reversed
       * @return - the current status of the reverse boolean
       */
      public boolean getReverse (){
          return this.reverse;
      }

      public void toggleGearSpeed (){
          this.gearShifter.set(this.gearShifter.get() == Value.kForward?Value.kReverse:Value.kForward);
      }

}