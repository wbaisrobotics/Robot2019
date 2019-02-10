/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.constants.OIConstants;
import frc.robot.oi.OI;
import frc.robot.systems.Drive;

/**
 * Uses the joysticks to drive the robot
 */
public class JoystickDrive extends Command {

  /**
   * Initializes the joystick drive command 
   */
  public JoystickDrive() {
    requires (Drive.getInstance());
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // Arcade drive
    Drive.getInstance().arcadeDrive(OI.getPilot().getRawAxis(OIConstants.DRIVE_MAGNITUDE.getRawAxis()), -OI.getPilot().getRawAxis(OIConstants.DRIVE_TURN.getRawAxis()));
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  /**
   * Stops driving
   */
  @Override
  protected void end() {
    Drive.getInstance().stop();
  }

  /**
   * Calls end()
   */
  @Override
  protected void interrupted() {
    end();
  }
}
