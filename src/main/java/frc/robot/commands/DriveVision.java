/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.components.NetworkTableCommunicator;
import frc.robot.constants.network.VisionTargetInfo;
import frc.robot.systems.Drive;

public class DriveVision extends Command {
  public DriveVision() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  /**
   * Cancels this command if target is not in sight upon init
   */
  private CommandGroup failureCallback;

  public DriveVision (CommandGroup callback){
    this.failureCallback = callback;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if (this.failureCallback != null && NetworkTableCommunicator.getTargetInfo().isError()){
      this.failureCallback.cancel();
    }
  }

  private VisionTargetInfo targetInfo;

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    targetInfo = NetworkTableCommunicator.getTargetInfo();

    if (targetInfo.isError()){
      super.cancel();
      return;
    }

    // Unwrap the target info
    double xDiff = targetInfo.getXDiff();
    // double yDiff = targetInfo.getYDiff();
    // double heightRatio = targetInfo.getHeightRatio();
    // double ttsr = targetInfo.getTTSR();

    double pConst = SmartDashboard.getNumber("Vision P Const", 0);

    double turn = xDiff * pConst;
    turn = Math.abs(turn)>0.5?Math.signum(turn)*0.5:turn;

    double forward = SmartDashboard.getNumber("Vision Forward Const", 0);
    System.out.println(turn);
    Drive.getInstance().arcadeDrive(forward, turn);

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return targetInfo.isError();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Drive.getInstance().stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Drive.getInstance().stop();
  }
}
