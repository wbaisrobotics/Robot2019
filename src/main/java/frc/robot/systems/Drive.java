
package frc.robot.systems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.components.speed.SpeedControllers;
import frc.robot.constants.wiring.CANWiring;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.PathfinderFRC;
import jaci.pathfinder.Trajectory;

import com.revrobotics.ControlType;
import frc.robot.components.encoders.EncoderFollower;

import frc.robot.components.speed.CANSparkMaxGroup;

import edu.wpi.first.wpilibj.Notifier;

public class Drive extends DifferentialDrive{

    /**
	 * Set to zero to skip waiting for confirmation.
	 * Set to nonzero to wait and report to DS if action fails.
	 */
    public final static int kTimeoutMs = 30;
    
    /* We allow either a 0 or 1 when selecting a PID Index, where 0 is primary and 1 is auxiliary */
	public final static int PID_PRIMARY = 0;

      //private static final int k_ticks_per_rev = 1024;
    //private static final double k_wheel_diameter = 4.0 / 12.0;
    private static final double k_max_velocity = 10;

    private static final int k_left_channel = 0;
    private static final int k_right_channel = 1;

    private static final int k_gyro_port = 0;

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

            instance = new Drive(left1, left2, left3, right1, right2, right3);

        }
        return instance;
    }

    private WPI_TalonSRX left;
    private WPI_TalonSRX right;

    private ADXRS450_Gyro gyro;

    /**
     * Default constructor for just the motors and no encoders
     * @param left - the left motors
     * @param right - the right motors
     */
    public Drive (WPI_TalonSRX left1, WPI_TalonSRX left2, WPI_TalonSRX left3, WPI_TalonSRX right1, WPI_TalonSRX right2, WPI_TalonSRX right3){
        // Call DifferentialDrive with the controllers
        super (left1, right1);
        // Save the groups
        this.left = left1;
        this.right = right1;

        /* Factory Default all hardware to prevent unexpected behaviour */
        left1.configFactoryDefault();
        left2.configFactoryDefault();
        left3.configFactoryDefault();
        
        left2.follow(left1);
        left3.follow(left1);

        left2.setInverted(InvertType.FollowMaster);
        left3.setInverted(InvertType.FollowMaster);

        /* Factory Default all hardware to prevent unexpected behaviour */
        right1.configFactoryDefault();
        right2.configFactoryDefault();
        right3.configFactoryDefault();
                    
        right2.follow(right1);
        right3.follow(right1);

        right2.setInverted(InvertType.FollowMaster);
        right3.setInverted(InvertType.FollowMaster);

        /* [3] flip values so robot moves forward when stick-forward/LEDs-green */
        right1.setInverted(true); 
        left1.setInverted(false);

        /*
         * WPI drivetrain classes defaultly assume left and right are opposite. call
         * this so we can apply + to both sides when moving forward. DO NOT CHANGE
         */
        super.setRightSideInverted(false);

        // Initialize the gyro
        gyro = new ADXRS450_Gyro();

        // Further init
        //init();
        
    }

    /**
     * Further initializes the controllers
     */
    private void init(){

        /* Disable all motor controllers */
		left.set(ControlMode.PercentOutput, 0);
		right.set(ControlMode.PercentOutput, 0);
		
		/* Set Neutral Mode */
		left.setNeutralMode(NeutralMode.Brake);
		right.setNeutralMode(NeutralMode.Brake);
		
		/** Feedback Sensor Configuration */
		
		/* Configure Selected Sensor for Motion Profile */
        left.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, PID_PRIMARY, kTimeoutMs);
        /* Keep sensor and motor in phase, postive sensor values when MC LEDs are green */
		left.setSensorPhase(true);
                                            
        /* Configure Selected Sensor for Motion Profile */
        right.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, PID_PRIMARY, kTimeoutMs);
        /* Keep sensor and motor in phase, postive sensor values when MC LEDs are green */
        right.setSensorPhase(true);
        
        /* Configure PID Gains, to be used with Motion Profile */
		

		/* Our profile uses 10ms timing */
        left.configMotionProfileTrajectoryPeriod(10, kTimeoutMs); 
        
		
		/* Status 10 provides the trajectory target for motion profile AND motion magic */
        left.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, kTimeoutMs);
        
        updatePID();

		/* Our profile uses 10ms timing */
		right.configMotionProfileTrajectoryPeriod(10, kTimeoutMs); 
		
		/* Status 10 provides the trajectory target for motion profile AND motion magic */
		right.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, kTimeoutMs);

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

        // Trajectory left_trajectory = PathfinderFRC.getTrajectory(pathName + ".left");
        // Trajectory right_trajectory = PathfinderFRC.getTrajectory(pathName + ".right");

        // m_left_follower = new EncoderFollower(left_trajectory);
        // m_right_follower = new EncoderFollower(right_trajectory);

        // m_left_follower.configureEncoder((int)left.getEncoderPosition(), -113, 0.15);
        // // You must tune the PID values on the following line!
        // //m_left_follower.configurePIDVA(SmartDashboard.getNumber("P Const", 0), SmartDashboard.getNumber("I Const", 0), SmartDashboard.getNumber("D Const", 0), 1 / k_max_velocity, 0);

        // m_right_follower.configureEncoder((int)right.getEncoderPosition(), 113, 0.15);
        // // You must tune the PID values on the following line!
        // //m_right_follower.configurePIDVA(SmartDashboard.getNumber("P Const", 0), SmartDashboard.getNumber("I Const", 0), SmartDashboard.getNumber("D Const", 0), 1 / k_max_velocity, 0);

        // m_follower_notifier = new Notifier(this::followPath);
        // m_follower_notifier.startPeriodic(left_trajectory.get(0).dt);

    }

    private void followPath() {
        // if (m_left_follower.isFinished() || m_right_follower.isFinished()) {
        //   m_follower_notifier.stop();
        //   left.stopMotor();
        //   right.stopMotor();
        // } else {

        //     updatePID();

        //   double left_speed = m_left_follower.calculate((int)left.getEncoderPosition());
        //   double right_speed = m_right_follower.calculate((int)right.getEncoderPosition());
        //   double heading = gyro.getAngle();
        //   double desired_heading = Pathfinder.r2d(m_left_follower.getHeading());
        //   double heading_difference = Pathfinder.boundHalfDegrees(desired_heading - heading);
        //   double turn =  SmartDashboard.getNumber("Turn Const", 0.8) * (-1.0/80.0) * heading_difference;
        //   //left.set((left_speed + turn));
        //   //right.set(-(right_speed - turn));
        //   left.getPIDController().setReference(left_speed, ControlType.kPosition);
        //   right.getPIDController().setReference(right_speed, ControlType.kPosition);

        //   SmartDashboard.putNumber ("Left Speed", left_speed + turn);
        //   SmartDashboard.putNumber ("Right Speed", -(right_speed - turn));

        //   System.out.println((left_speed + turn) + ":" + -(right_speed - turn));
        // }
      }

      public WPI_TalonSRX getLeft(){
        return this.left;
      }

      public WPI_TalonSRX getRight(){
        return this.right;
      }

}