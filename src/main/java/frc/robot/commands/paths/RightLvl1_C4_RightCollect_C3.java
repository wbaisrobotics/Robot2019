/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.paths;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.*;

public class RightLvl1_C4_RightCollect_C3 extends CommandGroup {
  /**
   * Add your docs here.
   */
  public RightLvl1_C4_RightCollect_C3() {

    // Hatch 1
    addSequential(new DriveMotionProfile("RightLvl1-C4", false));
    addSequential(new Wait(0.3));
    addSequential(new DriveVision(this));
    addSequential(new HatchShooterOut());
    addSequential(new DriveTimed(0.5, 0.5));
    addSequential(new HatchShooterIn());

    // Hatch 2
    addSequential(new DriveMotionProfile("C4-TempRight", true));
    addSequential(new DriveMotionProfile("TempRight-RightCollect", false));
    addSequential(new Wait(0.3));
    addSequential(new DriveVision(this));
    addSequential(new ThunkerDown());
    addSequential(new DriveTimed(0.5, -0.5));
    addSequential(new ThunkerUp());
    addSequential(new DriveTimed(0.5, 0.5));

    addSequential(new DriveMotionProfile("RightCollect-TempRight", true));
    addSequential(new DriveMotionProfile("TempRight-C4", false));
    
    addSequential(new Wait(0.3));
    addSequential(new DriveVision(this));
    addSequential(new HatchShooterOut());
    addSequential(new DriveTimed(0.5, 0.5));
    addSequential(new HatchShooterIn());
    

  }
}
