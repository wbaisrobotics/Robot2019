/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motion.TrajectoryPoint;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.components.encoders.CANEncoderGroup;
import frc.robot.systems.Drive;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.TimedRobot;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.PathfinderFRC;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  // private CANSparkMax left1;
  // private CANSparkMax left2;
  // private CANSparkMax left3;
  private Victor v0;
  private Victor v1;
  private SpeedControllerGroup leftMotors;
  // private CANEncoder leftEncoder1;
  // private CANEncoder leftEncoder2;
  // private CANEncoder leftEncoder3;
  // private CANEncoderGroup leftEncoderGroup;

  // private CANSparkMax right1;
  // private CANSparkMax right2;
  // private CANSparkMax right3;
  private Victor v2;
  private Victor v3;
  private SpeedControllerGroup rightMotors;

  private ADXRS450_Gyro gyro;
  // private CANEncoder rightEncoder1;
  // private CANEncoder rightEncoder2;
  // private CANEncoder rightEncoder3;
  // private CANEncoderGroup rightEncoderGroup;

  // private DoubleSolenoid d0;

  private DifferentialDrive drive;

  private XboxController pilot;

  private Encoder leftEncoder;
  private Encoder rightEncoder;

  // private Compressor comp;

  //private static final int k_ticks_per_rev = 1024;
  //private static final double k_wheel_diameter = 4.0 / 12.0;
  private static final double k_max_velocity = 10;

  private static final int k_left_channel = 0;
  private static final int k_right_channel = 1;

  private static final int k_gyro_port = 0;

  private static final String k_path_name = "FromCollectRightToSide5";

  private EncoderFollower m_left_follower;
  private EncoderFollower m_right_follower;

  private Notifier m_follower_notifier;

  
  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {

    // left1 = new CANSparkMax(20, MotorType.kBrushless);
    // left2 = new CANSparkMax(21, MotorType.kBrushless);
    // left3 = new CANSparkMax(22, MotorType.kBrushless);
    // leftMotors = new SpeedControllerGroup(left1, left2, left3);
    // right1 = new CANSparkMax(23, MotorType.kBrushless);
    // right2 = new CANSparkMax(24, MotorType.kBrushless);
    // right3 = new CANSparkMax(25, MotorType.kBrushless);
    // rightMotors = new SpeedControllerGroup(right1, right2, right3);

    // leftEncoder1 = new CANEncoder (left1);
    // leftEncoder2 = new CANEncoder (left2);
    // leftEncoder3 = new CANEncoder (left3);
    // leftEncoderGroup = new CANEncoderGroup(leftEncoder1, leftEncoder2, leftEncoder3);

    // rightEncoder1 = new CANEncoder (right1);
    // rightEncoder2 = new CANEncoder (right2);
    // rightEncoder3 = new CANEncoder (right3);
    // rightEncoderGroup = new CANEncoderGroup(rightEncoder1, rightEncoder2, rightEncoder3);

    v0 = new Victor (0);
    v1 = new Victor (1);
    v2 = new Victor (2);
    v3 = new Victor (3);

    leftMotors = new SpeedControllerGroup(v0, v1);
    rightMotors = new SpeedControllerGroup(v2, v3);

    leftEncoder = new Encoder (0, 1);
    rightEncoder = new Encoder (2, 3);
    SmartDashboard.putData(leftEncoder);
    SmartDashboard.putData(rightEncoder);

    drive = new DifferentialDrive(leftMotors, rightMotors);

    pilot = new XboxController(0);

    gyro = new ADXRS450_Gyro();

    SmartDashboard.putNumber("P Const", 1.0);
    SmartDashboard.putNumber("I Const", 0);
    SmartDashboard.putNumber("D Const", 0);

    SmartDashboard.putNumber("Turn Const", 0.8);

    SmartDashboard.putData(gyro);



    // comp = new Compressor();
    // comp.setClosedLoopControl(true);
    // comp.start();

    // d0 = new DoubleSolenoid(1, 3);
    // //CameraServer.getInstance().startAutomaticCapture();

  }

  @Override
  public void autonomousInit() {
  
    gyro.reset();
    leftEncoder.reset();
    rightEncoder.reset();

    Trajectory left_trajectory = PathfinderFRC.getTrajectory(k_path_name + ".left");
    Trajectory right_trajectory = PathfinderFRC.getTrajectory(k_path_name + ".right");

    m_left_follower = new EncoderFollower(left_trajectory);
    m_right_follower = new EncoderFollower(right_trajectory);

    m_left_follower.configureEncoder(leftEncoder.get(), 1619, 0.15);
    // You must tune the PID values on the following line!
    m_left_follower.configurePIDVA(SmartDashboard.getNumber("P Const", 0), SmartDashboard.getNumber("I Const", 0), SmartDashboard.getNumber("D Const", 0), 1 / k_max_velocity, 0);

    m_right_follower.configureEncoder(rightEncoder.get(), 1619, 0.15);
    // You must tune the PID values on the following line!
    m_right_follower.configurePIDVA(SmartDashboard.getNumber("P Const", 0), SmartDashboard.getNumber("I Const", 0), SmartDashboard.getNumber("D Const", 0), 1 / k_max_velocity, 0);

    m_follower_notifier = new Notifier(this::followPath);
    m_follower_notifier.startPeriodic(left_trajectory.get(0).dt);

  }

  private void followPath() {
    if (m_left_follower.isFinished() || m_right_follower.isFinished()) {
      m_follower_notifier.stop();
      leftMotors.set(0);
      rightMotors.set(0);
    } else {
      double left_speed = m_left_follower.calculate(leftEncoder.get());
      double right_speed = m_right_follower.calculate(rightEncoder.get());
      double heading = gyro.getAngle();
      double desired_heading = Pathfinder.r2d(m_left_follower.getHeading());
      double heading_difference = Pathfinder.boundHalfDegrees(desired_heading - heading);
      double turn =  SmartDashboard.getNumber("Turn Const", 0.8) * (-1.0/80.0) * heading_difference;
      leftMotors.set((left_speed + turn));
      rightMotors.set(-(right_speed - turn));
      System.out.println((left_speed + turn) + ":" + -(right_speed - turn));
    }
  }

  @Override
  public void autonomousPeriodic() {
    // drive.updatePID();
    // drive.driveAuto();
  }

  @Override
  public void teleopInit() {
    if (m_follower_notifier != null){
      m_follower_notifier.stop();
      leftMotors.set(0);
      rightMotors.set(0);
    }
  }

  @Override
  public void teleopPeriodic() {

    long starttime = System.currentTimeMillis();

    //leftMotors.set(1.0);
    //rightMotors.set(-1);

    System.out.println(leftEncoder.getDistance() + ":" + rightEncoder.getDistance());

    drive.arcadeDrive(-pilot.getY(Hand.kLeft), -pilot.getX(Hand.kRight));
/*
    System.out.println(leftEncoder1.getPosition() + ":" + leftEncoder2.getPosition() + ":" + leftEncoder3.getPosition() + ":" + rightEncoder1.getPosition() + ":" + rightEncoder2.getPosition() + ":" + rightEncoder3.getPosition() + ":" + leftEncoderGroup.getPosition() + ":" + rightEncoderGroup.getPosition());

    SmartDashboard.putNumber("Left Encoder 1 Value", leftEncoder1.getPosition());
    SmartDashboard.putNumber("Left Encoder 2 Value", leftEncoder2.getPosition());
    SmartDashboard.putNumber("Left Encoder 3 Value", leftEncoder3.getPosition());
    SmartDashboard.putNumber("Right Encoder 1 Value", rightEncoder1.getPosition());
    SmartDashboard.putNumber("Right Encoder 2 Value", rightEncoder2.getPosition());
    SmartDashboard.putNumber("Right Encoder 3 Value", rightEncoder3.getPosition());
    SmartDashboard.putNumber("Left Encoder Average Value", leftEncoderGroup.getPosition());
    SmartDashboard.putNumber("Right Encoder Average Value", rightEncoderGroup.getPosition());
    */

    //System.out.println(System.currentTimeMillis() - starttime);

  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
