/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.components.encoders.CANEncoderGroup;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  private CANSparkMax left1;
  private CANSparkMax left2;
  private CANSparkMax left3;
  private SpeedControllerGroup leftMotors;
  private CANEncoder leftEncoder1;
  private CANEncoder leftEncoder2;
  private CANEncoder leftEncoder3;
  private CANEncoderGroup leftEncoderGroup;

  private CANSparkMax right1;
  private CANSparkMax right2;
  private CANSparkMax right3;
  private SpeedControllerGroup rightMotors;
  private CANEncoder rightEncoder1;
  private CANEncoder rightEncoder2;
  private CANEncoder rightEncoder3;
  private CANEncoderGroup rightEncoderGroup;

  private DoubleSolenoid d0;

  private DifferentialDrive drive;

  private XboxController pilot;

  private Compressor comp;

  //private CANSparkMax s20;

  
  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {

    left1 = new CANSparkMax(20, MotorType.kBrushless);
    left2 = new CANSparkMax(21, MotorType.kBrushless);
    left3 = new CANSparkMax(22, MotorType.kBrushless);
    leftMotors = new SpeedControllerGroup(left1, left2, left3);
    right1 = new CANSparkMax(23, MotorType.kBrushless);
    right2 = new CANSparkMax(24, MotorType.kBrushless);
    right3 = new CANSparkMax(25, MotorType.kBrushless);
    rightMotors = new SpeedControllerGroup(right1, right2, right3);

    leftEncoder1 = new CANEncoder (left1);
    leftEncoder2 = new CANEncoder (left2);
    leftEncoder3 = new CANEncoder (left3);
    leftEncoderGroup = new CANEncoderGroup(leftEncoder1, leftEncoder2, leftEncoder3);

    rightEncoder1 = new CANEncoder (right1);
    rightEncoder2 = new CANEncoder (right2);
    rightEncoder3 = new CANEncoder (right3);
    rightEncoderGroup = new CANEncoderGroup(rightEncoder1, rightEncoder2, rightEncoder3);

    drive = new DifferentialDrive(leftMotors, rightMotors);

    pilot = new XboxController(0);

    comp = new Compressor();
    comp.setClosedLoopControl(true);
    comp.start();

    d0 = new DoubleSolenoid(1, 3);
    //CameraServer.getInstance().startAutomaticCapture();

  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {

    long starttime = System.currentTimeMillis();

    System.out.println(comp.getPressureSwitchValue());

    drive.arcadeDrive(pilot.getY(Hand.kLeft), -pilot.getX(Hand.kRight));
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

    System.out.println(System.currentTimeMillis() - starttime);

  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
