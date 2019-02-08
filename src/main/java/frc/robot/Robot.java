/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.components.speed.SpeedControllers;
import frc.robot.constants.wiring.CANWiring;
import frc.robot.oi.OI;
import frc.robot.systems.BackClimbers;
import frc.robot.systems.BallManipulator;
import frc.robot.systems.DeathCrawler;
import frc.robot.systems.Drive;
import frc.robot.systems.FrontClimbers;
import frc.robot.util.Logger;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  private WPI_TalonSRX left1;
  private WPI_TalonSRX left2;
  private WPI_TalonSRX left3;
  private WPI_TalonSRX right1;
  private WPI_TalonSRX right2;
  private WPI_TalonSRX right3;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {

    /**
     * Initializes the drive instance
     */
    //Drive.getInstance();

    // Initialize the left master
    left1 = SpeedControllers.getTalonSRX(CANWiring.DRIVE_LEFT_1);
    // Initialize the left slaves
    left2 = SpeedControllers.getTalonSRX(CANWiring.DRIVE_LEFT_2);
    left3 = SpeedControllers.getTalonSRX(CANWiring.DRIVE_LEFT_3);

    // Initialize the right master
    right1 = SpeedControllers.getTalonSRX(CANWiring.DRIVE_RIGHT_1);
    // Initialize the left slaves
    right2 = SpeedControllers.getTalonSRX(CANWiring.DRIVE_RIGHT_2);
    right3 = SpeedControllers.getTalonSRX(CANWiring.DRIVE_RIGHT_3);


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

    /**
     * Initializes the front climbers instance
     */
    FrontClimbers.getInstance();

    /**
     * Initializes the back climbers instance
     */
    BackClimbers.getInstance();

    /**
     * Initialize the Death Crawler
     */
    DeathCrawler.getInstance();
  
    /**
     * Initialize the Ball Manipulator
     */
    BallManipulator.getInstance();

    /**
     * Initializes the OI
     */
    //OI.initButtons();

  }

  @Override
  public void autonomousInit() {

    // Log the init
    Logger.log("Autonomous Begins");

  }

  @Override
  public void autonomousPeriodic() {

    /**
     * Run the scheduler
     */
    Scheduler.getInstance().run();
  
  }

  @Override
  public void teleopInit() {

    // Log the init
    Logger.log("Teleoperation Begins");

  }

  @Override
  public void teleopPeriodic() {

    /**
     * Run the scheduler
     */
    Scheduler.getInstance().run();

  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {

    left1.set (1.0);
    right1.set(1.0);

    //Drive.getInstance().arcadeDrive(OI.getPilot().getY(Hand.kLeft), OI.getPilot().getX(Hand.kLeft));
    
    if (OI.getPilot().getAButton()){
      FrontClimbers.getInstance().extend();
      BackClimbers.getInstance().extend();
    }
    else if (OI.getPilot().getBButton()){
      FrontClimbers.getInstance().retract();
      BackClimbers.getInstance().retract();
    }
    else{
      FrontClimbers.getInstance().stop();
      BackClimbers.getInstance().stop();
    }

    if (OI.getPilot().getBumper(Hand.kRight)){
      BallManipulator.getInstance().liftBall();
    }
    else{
      BallManipulator.getInstance().stopElevator();
    }

    if (OI.getPilot().getBumper(Hand.kLeft)){
      BallManipulator.getInstance().shooterOut();
    }
    else{
      BallManipulator.getInstance().stopShooter();
    }

    DeathCrawler.getInstance().setCrawlSpeed(Math.abs(OI.getPilot().getX(Hand.kRight)) > 0.2? OI.getPilot().getX(Hand.kRight):0);

    DeathCrawler.getInstance().setWormSpeed(Math.abs(OI.getPilot().getY(Hand.kRight)) > 0.2? OI.getPilot().getY(Hand.kRight):0);

  }

}
