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

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

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

  private CANSparkMax right1;
  private CANSparkMax right2;
  private CANSparkMax right3;
  private SpeedControllerGroup rightMotors;

  private DifferentialDrive drive;

  private XboxController pilot;

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

    drive = new DifferentialDrive(leftMotors, rightMotors);

    pilot = new XboxController(0);

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

    drive.arcadeDrive(pilot.getY(Hand.kLeft), -pilot.getX(Hand.kRight));

  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
