/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.components.encoders.CANEncoderGroup;
import frc.robot.systems.Drive;

import edu.wpi.first.wpilibj.Notifier;

import com.revrobotics.ControlType;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  private Drive drive;

  // private int n = 1;

  // private CANSparkMax c0;
  // private CANPIDController p0;

  private XboxController pilot;

  private CANSparkMax masterLeft;
  private CANSparkMax secondLeft;
  
  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {

    masterLeft = new CANSparkMax (20, MotorType.kBrushless);
    System.out.println(masterLeft.getFirmwareString());
    secondLeft = new CANSparkMax (21, MotorType.kBrushless);
    secondLeft.follow(masterLeft);

    // this.drive = Drive.getInstance();

    pilot = new XboxController(0);

    // c0 = new CANSparkMax(23, MotorType.kBrushless);
    // p0 = c0.getPIDController();

    // p0.setOutputRange(-1, 1);

    // SmartDashboard.putNumber("P3", 0);
    // SmartDashboard.putNumber("I3", 0);
    // SmartDashboard.putNumber("D3", 0);


  }

  @Override
  public void autonomousInit() {
  
    //drive.reset();

    // p0.setP(SmartDashboard.getNumber("P1", 0));
    System.out.println("P0" + SmartDashboard.getNumber("P1", 0));
    // p0.setI(SmartDashboard.getNumber("I1", 0));
    // p0.setD(SmartDashboard.getNumber("D1", 0));

    // System.out.println(p0.setReference(c0.getEncoder().getPosition()+500, ControlType.kPosition));

    // System.out.println(c0.getEncoder().getPosition());

    //drive.drivePath ("FromCollectRightToSide5");

    // double setPoint = n * 10;
    // n++;

    // System.out.println("SETPOINT: "  + setPoint);

    // double p = SmartDashboard.getNumber("P3", 0);
    // double i = SmartDashboard.getNumber("I3", 0);
    // double d = SmartDashboard.getNumber("D3", 0);
    // System.out.println(p +":"+ i +":"+d);
    // // Update the PID Values to what is in the dashboard
    // masterLeft.getPIDController().setP(p);
    // masterLeft.getPIDController().setI(i);
    // masterLeft.getPIDController().setD(d);

    // //drive.updatePID();

    // masterLeft.getPIDController().setReference(setPoint, ControlType.kPosition);

    //drive.setPIDSetpoint(setPoint, setPoint);

  }

  @Override
  public void autonomousPeriodic() {

    // System.out.println(masterLeft.getPIDController().getP());

    // //System.out.println(c0.getEncoder().getPosition());
    // // drive.updatePID();
    // // drive.driveAuto()
    // // System.out.println(drive.getLeft().getEncoderPosition());
    // SmartDashboard.putNumber("L1", masterLeft.getEncoder().getPosition());
    // SmartDashboard.putNumber("R1", drive.getRight().getEncoderPosition());
  
    // SmartDashboard.putNumber("AAA", drive.getLeft().getPIDController().getP());
  
  }

  @Override
  public void teleopInit() {
    //drive.stop();
    
  }

  @Override
  public void teleopPeriodic() {

    long starttime = System.currentTimeMillis();

    this.masterLeft.set(0.2);

    //this.secondLeft.set(0.2);
    // this.masterLeft.set(-0.5);

    //leftMotors.set(1.0);
    //rightMotors.set(-1);

    // System.out.println (drive.getLeft().getEncoderPosition() +":" + drive.getRight().getEncoderPosition());
    // drive.arcadeDrive(pilot.getY(Hand.kLeft), -pilot.getX(Hand.kRight));

    System.out.println(drive.getLeft().getEncoderPosition() + ":"+ drive.getRight().getEncoderPosition());

    // System.out.println(leftEncoder1.getPosition() + ":" + leftEncoder2.getPosition() + ":" + leftEncoder3.getPosition() + ":" + rightEncoder1.getPosition() + ":" + rightEncoder2.getPosition() + ":" + rightEncoder3.getPosition() + ":" + leftEncoderGroup.getPosition() + ":" + rightEncoderGroup.getPosition());

    // SmartDashboard.putNumber("Left Encoder 1 Value", leftEncoder1.getPosition());
    // SmartDashboard.putNumber("Left Encoder 2 Value", leftEncoder2.getPosition());
    // SmartDashboard.putNumber("Left Encoder 3 Value", leftEncoder3.getPosition());
    // SmartDashboard.putNumber("Right Encoder 1 Value", rightEncoder1.getPosition());
    // SmartDashboard.putNumber("Right Encoder 2 Value", rightEncoder2.getPosition());
    // SmartDashboard.putNumber("Right Encoder 3 Value", rightEncoder3.getPosition());
    // SmartDashboard.putNumber("Left Encoder Average Value", leftEncoderGroup.getPosition());
    // SmartDashboard.putNumber("Right Encoder Average Value", rightEncoderGroup.getPosition());
    

    System.out.println(System.currentTimeMillis() - starttime);

  }

  @Override
  public void testInit() {
    //c0.stopMotor();
  }

  @Override
  public void testPeriodic() {
  }

}
