/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.climbing;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.systems.FrontClimbers;
import frc.robot.util.Logger;

/**
 * Command that retracts the front climbers simoultaneously 
 * If interrupted, stops motor
 * Finishes once both legs have activated switches
 */
public class RetractFrontClimbers extends Command {

  // The climber system
  private FrontClimbers climbers;

  /**
   * Initializes the command 
   */
  public RetractFrontClimbers() {
    // Obtain the instance of the system
    climbers = FrontClimbers.getInstance();
    // Require the system for the command
    requires (climbers);
  }

  // Nothing to initialize, logs the start
  @Override
  protected void initialize() {
    // Log the start of the command
    Logger.log("RetractFrontClimbersCommand: Initialized");
  }

  // Sends the climbers a message to retract
  @Override
  protected void execute() {
    // Attempt to retract the climbers
    climbers.retract();
    // Log the execution of the command (every 50 runs)
    Logger.logEvery("RetractFrontClimbersCommand: Executing", 50, this);
  }

  // Queries the climbers as to whether they finished retracting
  @Override
  protected boolean isFinished() {
    // Query the climbers
    return climbers.isFullyRetracted();
  }

  // Stops the climbers
  @Override
  protected void end() {
    // Stop the climbers
    climbers.stop();
    // Log the start of the command
    Logger.log("ExtendFrontClimbersCommand: Ended");
  }

  // Stops the climbers
  @Override
  protected void interrupted() {
    // Initiate the end protocol
    end();
  }
}
