/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.systems.Drive;

/**
 * Toggles the gear speed of the drive system
 */
public class ToggleGearSpeed extends InstantCommand {

  /**
   * Initializes the command
   */
  public ToggleGearSpeed() {

    // Call super
    super();
    // Requires the drive subsystem
    requires (Drive.getInstance());

  }

  // Called once when the command executes
  @Override
  protected void initialize() {
    // Toggles the gear speed
    Drive.getInstance().toggleGearSpeed();
  }

}
