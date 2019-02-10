/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.components.NetworkTableCommunicator;
import frc.robot.oi.OI;
import frc.robot.systems.BackClimbers;
import frc.robot.systems.BallManipulator;
import frc.robot.systems.DeathCrawler;
import frc.robot.systems.Drive;
import frc.robot.systems.FrontClimbers;
import frc.robot.systems.HatchManipulator;
import frc.robot.util.Logger;

import edu.wpi.first.cameraserver.CameraServer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  /**
   * Initializes the robot and its systems
   */
  @Override
  public void robotInit() {

    /**
     * Initialize the network table communicator
     */
    NetworkTableCommunicator.init();

    /**
     *  Begin capturing and sending images for the driver camera
     */
    CameraServer.getInstance().startAutomaticCapture().setBrightness(40);
    
    /**
     * Initializes the drive instance
     */
    Drive.getInstance();

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
     * Initialize the Hatch Manipulator
     */
    HatchManipulator.getInstance();

    /**
     * Initializes the OI
     */
    //OI.initButtons();

  }

  @Override
  public void autonomousInit() {

    // Log the init
    Logger.log("Autonomous Begins");

    Drive.getInstance().reset();

    Drive.getInstance().drivePath("FromCollectRightToSide5");

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

    Drive.getInstance().reset();

  }

  @Override
  public void teleopPeriodic() {

    System.out.println(Drive.getInstance().getLeft().getSelectedSensorPosition() + "," + Drive.getInstance().getRight().getSelectedSensorPosition());

    /**
     * Run the scheduler
     */
    // Scheduler.getInstance().run();

    if (OI.getPilot().getStickButtonPressed(Hand.kLeft)){
      Drive.getInstance().toggleGearSpeed();
    }

    if (OI.getCoPilot().getBButtonPressed()){
      Drive.getInstance().toggleReverse();
      //Drive.getInstance().setReverse(Drive.getInstance().getReverse());
    }

    Drive.getInstance().arcadeDrive(OI.getPilot().getY(Hand.kLeft), -OI.getPilot().getX(Hand.kRight));

    SmartDashboard.putNumber("Left Encoder", Drive.getInstance().getLeft().getSelectedSensorPosition());
    SmartDashboard.putNumber("Right Encoder", Drive.getInstance().getRight().getSelectedSensorPosition());

    if (OI.getPilot().getAButton()){
      FrontClimbers.getInstance().extend();
      BackClimbers.getInstance().extend();
    }
    else if (OI.getPilot().getBButton()){
      FrontClimbers.getInstance().retract();
    }
    else if (OI.getPilot().getXButton()){
      BackClimbers.getInstance().retract();
    }
    else{

      // Inidividual control

      // Front left ( Left Bumper )
      if ((!OI.getPilot().getYButton()) && OI.getPilot().getBumper(Hand.kLeft)){
        FrontClimbers.getInstance().extendLeft();
      }
      else if ((OI.getPilot().getYButton()) && OI.getPilot().getBumper(Hand.kLeft)){
        FrontClimbers.getInstance().retractLeft();
      }
      else{
        FrontClimbers.getInstance().stopLeft();
      }

      // Front right ( Right Bumper )
      if ((!OI.getPilot().getYButton()) && OI.getPilot().getBumper(Hand.kRight)){
        FrontClimbers.getInstance().extendRight();
      }
      else if ((OI.getPilot().getYButton()) && OI.getPilot().getBumper(Hand.kRight)){
        FrontClimbers.getInstance().retractRight();
      }
      else{
        FrontClimbers.getInstance().stopRight();
      }

      // Back left (Left Trigger)
      if ((!OI.getPilot().getYButton()) && (OI.getPilot().getTriggerAxis(Hand.kLeft) > 0.2)){
        BackClimbers.getInstance().extendLeft();
      }
      else if ((OI.getPilot().getYButton()) && (OI.getPilot().getTriggerAxis(Hand.kLeft) > 0.2)){
        BackClimbers.getInstance().retractLeft();
      }
      else{
        BackClimbers.getInstance().stopLeft();
      }

      // Back right (Right Trigger)
      if ((!OI.getPilot().getYButton()) && (OI.getPilot().getTriggerAxis(Hand.kRight) > 0.2)){
        BackClimbers.getInstance().extendRight();
      }
      else if ((OI.getPilot().getYButton()) && (OI.getPilot().getTriggerAxis(Hand.kRight) > 0.2)){
        BackClimbers.getInstance().retractRight();
      }
      else{
        BackClimbers.getInstance().stopRight();
      }


    }

    if (OI.getCoPilot().getBumper(Hand.kLeft)){
      BallManipulator.getInstance().liftBall();
    }
    else{
      BallManipulator.getInstance().stopElevator();
    }

    if (OI.getCoPilot().getTriggerAxis(Hand.kLeft) > 0.3){
      BallManipulator.getInstance().shooterOut();
    }
    else{
      BallManipulator.getInstance().stopShooter();
    }

    if (OI.getCoPilot().getYButton()){
      HatchManipulator.getInstance().thunkerDown();
    }
    else{
      HatchManipulator.getInstance().thunkerUp();
    }

    if (OI.getCoPilot().getXButton()){
      HatchManipulator.getInstance().shooterOut();
    }
    else{
      HatchManipulator.getInstance().shooterIn();
    }

    DeathCrawler.getInstance().setCrawlSpeed(Math.abs(OI.getCoPilot().getTriggerAxis(Hand.kRight)) > 0.1? OI.getCoPilot().getTriggerAxis(Hand.kRight)*0.5:0);

    DeathCrawler.getInstance().setWormSpeed(Math.abs(OI.getCoPilot().getY(Hand.kRight)) > 0.1? OI.getCoPilot().getY(Hand.kRight):0);

  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
