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
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.DriveMotionProfile;
import frc.robot.components.NetworkTableCommunicator;
import frc.robot.constants.motionprofiling.ProfilingGroups;
import frc.robot.constants.network.VisionTargetInfo;
import frc.robot.oi.OI;
import frc.robot.oi.POVDirection;
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
   * The command to be run during auto
   */
  private DriveMotionProfile autoCommand;

  /**
   * Initializes the robot and its systems
   */
  @Override
  public void robotInit() {

    SmartDashboard.putNumber("Vision Forward Const", -0.6);
    SmartDashboard.putNumber("Vision P Const", -3);

    setupAutoChooser();

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
     * Initialize the auto command
     */
    autoCommand = getAutoCommand();
    /**
     * Initializes the OI
     */
    //OI.initButtons();

  }

  private SendableChooser<ProfilingGroups> autoChooser;

  /**
   * Sets up the smart dashbaord senable choosers
   */
  private void setupAutoChooser (){

    /**
     * Initiate the sendable chooser
     */
    autoChooser = new SendableChooser<ProfilingGroups>();

    /**
     * Add a no-auto option and have the object be null
     */
    autoChooser.addOption("None", null);

    /**
     * For each path group in ProfilingGroups, add a object to the chooser
     */
    for (ProfilingGroups group : ProfilingGroups.values()){
      autoChooser.addOption(group.toString(), group);
    }

    /**
     * Send the chooser to the smart dashboard
     */
    SmartDashboard.putData("Autonomous Chooser", autoChooser);

  }

  public DriveMotionProfile getAutoCommand (){

    /**
     * Retrieve the selected path group
     */
    ProfilingGroups selectedPathGroup = autoChooser.getSelected();

    /**
     * If none was chosen, return null
     */
    if (selectedPathGroup == null){
      return null;
    }

    // TO DO: Create the command using the profiling group

    return new DriveMotionProfile("FromSide5ToCollectRight");
  }

  @Override
  public void autonomousInit() {


    Drive.getInstance().setReverse(false);

    climbingMode = false;

    if (Drive.getInstance().isHighGear()){
      Drive.getInstance().toggleGearSpeed();
    }

    // Log the init
    Logger.log("Autonomous Begins");

    /**
     * Reset the drive encoders
     */
    Drive.getInstance().reset();

    // If auto command is not null
    if (autoCommand != null){
      // Start the auto command
      autoCommand.start();
    }

  }

  @Override
  public void autonomousPeriodic() {

    /**
     * If the pilot desires to, cancel the auto command
     */
    if (OI.getPilot().getBackButton()){
      autoCommand.cancel();
    }

    /**
     * Run the scheduler
     */
    Scheduler.getInstance().run();

    /**
     * Once the auto command is completed, run teleop
     */
    if (autoCommand.isCompleted()){
      teleopPeriodic();
    }
  }

  @Override
  public void teleopInit() {

    // Log the init
    Logger.log("Teleoperation Begins");

    // If auto command is not null
    if (autoCommand != null){
      // Start the auto command
      autoCommand.cancel();
    }

    Drive.getInstance().setReverse(false);

    climbingMode = false;

  }

  private boolean climbingMode = false;

  @Override
  public void teleopPeriodic() {

    // System.out.println(Drive.getInstance().getLeft().getEncoder().getPosition() + "," + Drive.getInstance().getRight().getEncoder().getPosition());

    if (OI.getCoPilot().getBackButton() && OI.getCoPilot().getStartButton()){
      climbingMode = true;
      System.out.println("Entered Climb Mode");
    }

    

    SmartDashboard.putNumber("Left Encoder", Drive.getInstance().getLeft().getEncoder().getPosition());
    SmartDashboard.putNumber("Right Encoder", Drive.getInstance().getRight().getEncoder().getPosition());

    if (climbingMode){

      Drive.getInstance().arcadeDrive(OI.getPilot().getY(Hand.kLeft), 0);

      if (OI.getCoPilot().getAButton()){
        FrontClimbers.getInstance().extend();
        BackClimbers.getInstance().extend();
      }
      else if (OI.getCoPilot().getXButton() || OI.getCoPilot().getBButton()){
        if (OI.getCoPilot().getXButton()){
          FrontClimbers.getInstance().retract();
        }
        if (OI.getCoPilot().getBButton()){
          BackClimbers.getInstance().retract();
        }
      } 
      else{
  
        // Inidividual control
  
        // Front left ( Left Bumper )
        if ((!OI.getCoPilot().getYButton()) && OI.getCoPilot().getBumper(Hand.kLeft)){
          BackClimbers.getInstance().extendLeft();
        }
        else if ((OI.getCoPilot().getYButton()) && OI.getCoPilot().getBumper(Hand.kLeft)){
          BackClimbers.getInstance().retractLeft();
        }
        else{
          BackClimbers.getInstance().stopLeft();
        }
  
        // Front right ( Right Bumper )
        if ((!OI.getCoPilot().getYButton()) && OI.getCoPilot().getBumper(Hand.kRight)){
          BackClimbers.getInstance().extendRight();
        }
        else if ((OI.getCoPilot().getYButton()) && OI.getCoPilot().getBumper(Hand.kRight)){
          BackClimbers.getInstance().retractRight();
        }
        else{
          BackClimbers.getInstance().stopRight();
        }
  
        // Back left (Left Trigger)
        if ((!OI.getCoPilot().getYButton()) && (OI.getCoPilot().getTriggerAxis(Hand.kLeft) > 0.2)){
          FrontClimbers.getInstance().extendLeft();
        }
        else if ((OI.getCoPilot().getYButton()) && (OI.getCoPilot().getTriggerAxis(Hand.kLeft) > 0.2)){
          FrontClimbers.getInstance().retractLeft();
        }
        else{
          FrontClimbers.getInstance().stopLeft();
        }
  
        // Back right (Right Trigger)
        if ((!OI.getCoPilot().getYButton()) && (OI.getCoPilot().getTriggerAxis(Hand.kRight) > 0.2)){
          FrontClimbers.getInstance().extendRight();
        }
        else if ((OI.getCoPilot().getYButton()) && (OI.getCoPilot().getTriggerAxis(Hand.kRight) > 0.2)){
          FrontClimbers.getInstance().retractRight();
        }
        else{
          FrontClimbers.getInstance().stopRight();
        }

    
  
      }

      DeathCrawler.getInstance().setCrawlSpeed(Math.abs(OI.getPilot().getTriggerAxis(Hand.kRight)) > 0.1? -OI.getPilot().getTriggerAxis(Hand.kRight)*0.4:0);

      DeathCrawler.getInstance().setWormSpeed(Math.abs(OI.getPilot().getY(Hand.kRight)) > 0.1? OI.getPilot().getY(Hand.kRight):0);

  

    }

    // Not climbing mode
    else{

      if (OI.getPilot().getYButtonPressed()){
        NetworkTableCommunicator.toggleRunVision();
      }

      VisionTargetInfo targetInfo = NetworkTableCommunicator.getTargetInfo();

      SmartDashboard.putBoolean("Target in Sight", !targetInfo.isError());

      // If following vision
      if (!targetInfo.isError() && OI.getPilot().getBumper(Hand.kRight)){
        driveVision(targetInfo);
      }
      else if (Drive.getInstance().isHighGear()){
        Drive.getInstance().arcadeDrive(OI.getPilot().getY(Hand.kLeft)*0.75, -OI.getPilot().getX(Hand.kRight)*0.75);
      }
      else{
        Drive.getInstance().arcadeDrive(OI.getPilot().getY(Hand.kLeft)*0.85, -OI.getPilot().getX(Hand.kRight)*0.85);
      }

      if (OI.getCoPilot().getPOV() == POVDirection.NORTH.getAngle()){
        BallManipulator.getInstance().lowerBall();
      }
      if (OI.getCoPilot().getPOV() == POVDirection.SOUTH.getAngle()){
        DeathCrawler.getInstance().setCrawlSpeed(Math.abs(OI.getCoPilot().getTriggerAxis(Hand.kRight)) > 0.1? OI.getCoPilot().getTriggerAxis(Hand.kRight)*0.4:0);
      }
      else{
        DeathCrawler.getInstance().setCrawlSpeed(Math.abs(OI.getCoPilot().getTriggerAxis(Hand.kRight)) > 0.1? -OI.getCoPilot().getTriggerAxis(Hand.kRight)*0.4:0);
      }
      
      if (OI.getPilot().getBumperPressed(Hand.kLeft)){
        Drive.getInstance().toggleGearSpeed();
      }
  
      if (OI.getCoPilot().getBButtonPressed()){
        Drive.getInstance().toggleReverse();
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

      
      DeathCrawler.getInstance().setWormSpeed(Math.abs(OI.getCoPilot().getY(Hand.kRight)) > 0.1? OI.getCoPilot().getY(Hand.kRight):0);

    } 

  }

  public void disabledInit (){

    Drive.getInstance().reset();
      /**
     * Initialize the auto command
     */
    // autoCommand = getAutoCommand();

    // Reset the auto command, if it exists
    if (autoCommand != null){
      autoCommand.reset();
    }

    // Stop all the systems
    Drive.getInstance().stop();
    FrontClimbers.getInstance().stop();
    BackClimbers.getInstance().stop();
    BallManipulator.getInstance().stop();
    DeathCrawler.getInstance().stop();

  }

  /**
   * Drive according to commands from vision
   */
  private void driveVision(VisionTargetInfo targetInfo){

    // Unwrap the target info
    double xDiff = targetInfo.getXDiff();

    // double yDiff = targetInfo.getYDiff();
    // double heightRatio = targetInfo.getHeightRatio();
    // double ttsr = targetInfo.getTTSR();

    double pConst = SmartDashboard.getNumber("Vision P Const", 0);

    double turn = xDiff * pConst;
    turn = Math.abs(turn)>0.5?Math.signum(turn)*0.5:turn;

    double forward = SmartDashboard.getNumber("Vision Forward Const", 0);

    Drive.getInstance().arcadeDrive(forward, turn);

  }

}
