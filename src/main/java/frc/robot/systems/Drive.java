
package frc.robot.systems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.components.speed.SpeedControllers;
import frc.robot.constants.wiring.CANWiring;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.PathfinderFRC;
import jaci.pathfinder.Trajectory;
//import jaci.pathfinder.followers.EncoderFollower;

import com.revrobotics.ControlType;
import frc.robot.components.encoders.EncoderFollower;

import frc.robot.components.speed.CANSparkMaxGroup;

import edu.wpi.first.wpilibj.Notifier;

public class Drive extends DifferentialDrive{

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

            // Initialize the spark maxes
            CANSparkMax left1 = SpeedControllers.getSpartMaxBrushless(CANWiring.DRIVE_LEFT_1);
            CANSparkMax left2 = SpeedControllers.getSpartMaxBrushless(CANWiring.DRIVE_LEFT_2);
            CANSparkMax left3 = SpeedControllers.getSpartMaxBrushless(CANWiring.DRIVE_LEFT_3);
            CANSparkMax right1 = SpeedControllers.getSpartMaxBrushless(CANWiring.DRIVE_RIGHT_1);
            CANSparkMax right2 = SpeedControllers.getSpartMaxBrushless(CANWiring.DRIVE_RIGHT_2);
            CANSparkMax right3 = SpeedControllers.getSpartMaxBrushless(CANWiring.DRIVE_RIGHT_3);

            SmartDashboard.putNumber("P", 0);
            SmartDashboard.putNumber("I", 0);
            SmartDashboard.putNumber("D", 0);


            SmartDashboard.putNumber("Turn Const", 0.8);

            instance = new Drive(new CANSparkMaxGroup (left1, left2, left3), new CANSparkMaxGroup (right1, right2, right3));

        }
        return instance;
    }

    private CANSparkMaxGroup left;
    private CANSparkMaxGroup right;

    private ADXRS450_Gyro gyro;

    /**
     * Default constructor for just the motors and no encoders
     * @param left - the left motors
     * @param right - the right motors
     */
    public Drive (CANSparkMaxGroup left, CANSparkMaxGroup right){
        // Call DifferentialDrive with the controllers
        super (left, right);
        // Save the groups
        this.left = left;
        this.right = right;

        gyro = new ADXRS450_Gyro();
    }

    /**
     * Resets all sensors
     */
    public void reset (){
        // Reset the gyro
        gyro.reset();
        // TBA for encoders once spark max update comes out
    }

    public void updatePID(){

        double p = SmartDashboard.getNumber("P", 0);
        double i = SmartDashboard.getNumber("I", 0);
        double d = SmartDashboard.getNumber("D", 0);
        System.out.println(p +":"+ i +":"+d);
        // Update the PID Values to what is in the dashboard
        left.getPIDController().setP(p);
        left.getPIDController().setI(i);
        left.getPIDController().setD(d);
        right.getPIDController().setP(p);
        right.getPIDController().setI(i);
        right.getPIDController().setD(d);
    }

    /**
     * Updates the PID Setpoint and starts the onboard pid controller
     * @param left
     * @param right
     */
    public void setPIDSetpoint(double leftSetpoint, double rightSetpoint){
        // Set the setpoint for left
        left.getPIDController().setReference(leftSetpoint, ControlType.kPosition);
        // Set the setpoint for right
        right.getPIDController().setReference(rightSetpoint, ControlType.kPosition);
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

        m_left_follower.configureEncoder((int)left.getEncoderPosition(), -113, 0.15);
        // You must tune the PID values on the following line!
        //m_left_follower.configurePIDVA(SmartDashboard.getNumber("P Const", 0), SmartDashboard.getNumber("I Const", 0), SmartDashboard.getNumber("D Const", 0), 1 / k_max_velocity, 0);

        m_right_follower.configureEncoder((int)right.getEncoderPosition(), 113, 0.15);
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

          double left_speed = m_left_follower.calculate((int)left.getEncoderPosition());
          double right_speed = m_right_follower.calculate((int)right.getEncoderPosition());
          double heading = gyro.getAngle();
          double desired_heading = Pathfinder.r2d(m_left_follower.getHeading());
          double heading_difference = Pathfinder.boundHalfDegrees(desired_heading - heading);
          double turn =  SmartDashboard.getNumber("Turn Const", 0.8) * (-1.0/80.0) * heading_difference;
          //left.set((left_speed + turn));
          //right.set(-(right_speed - turn));
          left.getPIDController().setReference(left_speed, ControlType.kPosition);
          right.getPIDController().setReference(right_speed, ControlType.kPosition);

          SmartDashboard.putNumber ("Left Speed", left_speed + turn);
          SmartDashboard.putNumber ("Right Speed", -(right_speed - turn));

          System.out.println((left_speed + turn) + ":" + -(right_speed - turn));
        }
      }

      public CANSparkMaxGroup getLeft(){
        return this.left;
      }

      public CANSparkMaxGroup getRight(){
        return this.right;
      }

}