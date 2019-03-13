/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.DriveMotionProfile;
import frc.robot.components.NetworkTableCommunicator;
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

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  private Compressor comp;

  /**
   * The command to be run during auto
   */
  private Command autoCommand;

  /**
   * Initializes the robot and its systems
   */
  @Override
  public void robotInit() {

    comp = new Compressor();

    // Initialize the vision driving constants
    SmartDashboard.putNumber("Vision Forward Const", -0.6);
    SmartDashboard.putNumber("Vision P Const", -3);

    /**
     * Setup the chooser for autonomous
     */
    setupAutoChooser();

    /**
     * Initialize the network table communicator
     */
    NetworkTableCommunicator.init(this);
    
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

  }

  /**
   * Called the load the auto command from the dashboard & initialize the object
   */
  public void loadAutoCommand (){
    this.autoCommand = getAutoCommand();
  }

  /**
   * The chooser for the auto command
   */
  private SendableChooser<Command> autoChooser;

  /**
   * Sets up the smart dashbaord senable choosers
   */
  private void setupAutoChooser (){

    /**
     * Initiate the sendable chooser
     */
    autoChooser = new SendableChooser<Command>();

    /**
     * Add a no-auto option and have the object be null
     */
    autoChooser.addOption("None", null);


    /**
     * Send the chooser to the smart dashboard
     */
    SmartDashboard.putData("Autonomous Chooser", autoChooser);

  }

  public Command getAutoCommand (){

    /**
     * Retrieve the selected auto command
     */
    Command selectedAutoCommand = autoChooser.getSelected();

    // Return the selected auto command
    return selectedAutoCommand;

  }

  @Override
  public void autonomousInit() {

    /**
     * Reset the drive system
     */
    Drive.getInstance().reset();

    /**
     * Update the face of the robot
     */
    Drive.getInstance().setReverse(false);

    /**
     * Disable climbing mode
     */
    climbingMode = false;

    /**
     * Update to low gear
     */
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

    // /**
    //  * If there is no auto command, just run teleop
    //  */
    // if (autoCommand == null){
    //   teleopPeriodic();
    // }
    // /**
    //  * If there is an auto command
    //  */
    // else{

    //   /**
    //    * If the pilot desires to, cancel the auto command
    //    */
    //   if (OI.getPilot().getXButton()){
    //     autoCommand.cancel();
    //   }

    //   /**
    //    * Run the scheduler
    //    */
    //   Scheduler.getInstance().run();

    //   /**
    //    * Once the auto command is completed, run teleop
    //    */
    //   if (autoCommand.isCompleted()){
        teleopPeriodic();
    //   }

    // }

  }

  @Override
  public void teleopInit() {

    // Log the init
    Logger.log("Teleoperation Begins");

    // If auto command is not null
    if (autoCommand != null){
      // Cancel the auto command
      autoCommand.cancel();
    }

    /**
     * Disable climbing mode
     **/
    climbingMode = false;

  }

  /**
   * Whetehr or not the robot is in climbing mode
   */
  private boolean climbingMode = false;

  @Override
  public void teleopPeriodic() {

    if (OI.getCoPilot().getBackButtonPressed()){
      climbingMode = !climbingMode;
      System.out.println("Toggled Climb Mode");
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

      DeathCrawler.getInstance().setCrawlSpeed(Math.abs(OI.getPilot().getTriggerAxis(Hand.kRight)) > 0.1? -OI.getPilot().getTriggerAxis(Hand.kRight)*1.0:0);

      DeathCrawler.getInstance().setWormSpeed(Math.abs(OI.getPilot().getY(Hand.kRight)) > 0.1? OI.getPilot().getY(Hand.kRight):0);

  

    }

    // Not climbing mode
    else{

      if (OI.getCoPilot().getAButtonPressed()){
        NetworkTableCommunicator.toggleRunVision();
      }

      SmartDashboard.putBoolean("Is High Gear", Drive.getInstance().isHighGear());

      VisionTargetInfo targetInfo = NetworkTableCommunicator.getTargetInfo();

      SmartDashboard.putBoolean("Target in Sight", !targetInfo.isError());

      // If following vision
      if (!targetInfo.isError() && OI.getPilot().getBumper(Hand.kRight)){
        if (Drive.getInstance().isHighGear()){
          Drive.getInstance().toggleGearSpeed();
        }
        driveVision(targetInfo);
      }
      else if (Drive.getInstance().isHighGear()){
        double y = OI.getPilot().getY(Hand.kLeft);
        y *= 0.75;
        double x = -OI.getPilot().getX(Hand.kRight);
        x *= 0.75;
        Drive.getInstance().arcadeDrive(y, x);
      }
      // If grind mode
      else if (OI.getCoPilot().getTriggerAxis(Hand.kLeft) > 0.5){
        Drive.getInstance().arcadeDrive(OI.getPilot().getY(Hand.kLeft)*1.0, -OI.getPilot().getX(Hand.kRight)*0.6);
      }
      else{
        double y = OI.getPilot().getY(Hand.kLeft);
        y *= 0.9;
        y *= (y * Math.signum(y));
        double x = -OI.getPilot().getX(Hand.kRight);
        x *= 0.9;
        // x *= (x * Math.signum(x));
        x *= (x * x);
        Drive.getInstance().arcadeDrive(y, x);
      }

      if (OI.getCoPilot().getPOV() == POVDirection.NORTH.getAngle()){
        BallManipulator.getInstance().lowerBall();
      }
      if (OI.getCoPilot().getPOV() == POVDirection.SOUTH.getAngle()){
        DeathCrawler.getInstance().setCrawlSpeed(Math.abs(OI.getCoPilot().getTriggerAxis(Hand.kRight)) > 0.1? OI.getCoPilot().getTriggerAxis(Hand.kRight)*1.0:0);
      }
      else{
        DeathCrawler.getInstance().setCrawlSpeed(Math.abs(OI.getCoPilot().getTriggerAxis(Hand.kRight)) > 0.1? -OI.getCoPilot().getTriggerAxis(Hand.kRight)*1.0:0);
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

  /**
   * Periodically, log the state of the robot
   */
  public void robotPeriodic(){

    String driveStatus = Drive.getInstance().toString();
    String hatchStatus = HatchManipulator.getInstance().toString();
    String ballManipulatorStatus = BallManipulator.getInstance().toString();
    String deathCrawlerStatus = DeathCrawler.getInstance().toString();
    String frontClimbersStatus = FrontClimbers.getInstance().toString();
    String backClimbersStatus = BackClimbers.getInstance().toString();
    String compressorStatus = "Compressor: Switch - " + comp.getPressureSwitchValue() + ", Current - " + comp.getCompressorCurrent();

    String completeLog = driveStatus + ";\t" + hatchStatus + ";\t" + ballManipulatorStatus + ";\t" + deathCrawlerStatus + ";\t" + frontClimbersStatus + ";\t" + backClimbersStatus + ";\t" + compressorStatus;

    Logger.logEvery(completeLog, 25, this);

  }

}
