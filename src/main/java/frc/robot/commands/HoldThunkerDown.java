/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.systems.HatchManipulator;

/**
 * Holds the thunker low while executing and lifts back when done
 */
public class HoldThunkerDown extends Command {

  /**
   * Initializes the command
   */
  public HoldThunkerDown() {
    requires (HatchManipulator.getInstance());
  }

  /**
   * Lowers the thunker
   */
  @Override
  protected void initialize() {
    // Send a signal to lower the thunker
    HatchManipulator.getInstance().thunkerDown();
  }

  /**
   * Do nothing
   */
  @Override
  protected void execute() {
  }

  /**
   * Never finishes on its own
   */
  @Override
  protected boolean isFinished() {
    // Return false to indicate it doesn't finish
    return false;
  }

  /**
   * Should never end on its own
   */
  @Override
  protected void end() {
  }

  /**
   * When interrupted, brings the thunker back up
   */
  @Override
  protected void interrupted() {
    // Send a signal to raise the thunker
    HatchManipulator.getInstance().thunkerUp();
  }

}
