/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.io.IOException;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.components.NetworkTableCommunicator;
import frc.robot.components.speed.EncoderFollower;
import frc.robot.constants.motionprofiling.MotionProfilingConstants;
import frc.robot.constants.network.SmartDashboardConstants;
import frc.robot.systems.Drive;
import frc.robot.util.Logger;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.PathfinderFRC;
import jaci.pathfinder.Trajectory;

/**
 * Drives a motion profile
 */
public class DriveMotionProfile extends Command {

  /**
   * The left trajectory
   */
  public Trajectory leftTrajectory;
  /**
   * The right trajectory
   */
  public Trajectory rightTrajectory;

  /**
   * The encoder follower for the left side
   */
  private EncoderFollower m_left_follower;
  /**
   * The encoder follower for the right side
   */
  private EncoderFollower m_right_follower;

  /**
   * The notifier
   */
  private Notifier m_follower_notifier;

  /**
   * Initialize the follower with a pathName
   * @param pathName
   */
  public DriveMotionProfile(String pathName) {
    
    // Requires the drive system
    requires (Drive.getInstance());

    // Read the trajectories
    try{
      leftTrajectory = PathfinderFRC.getTrajectory(pathName + ".right");
      rightTrajectory = PathfinderFRC.getTrajectory(pathName + ".left");
    }
    catch (IOException exception){
      Logger.log("IO Exception at loading motion profile: " + pathName);
      Logger.log(exception.getMessage());
    }

    // Initialize the encoder followers
    m_left_follower = new EncoderFollower(leftTrajectory);
    m_right_follower = new EncoderFollower(rightTrajectory);

  }

  /**
   * Initializes the trajectory followers
   */
  protected void initialize() {

    reset();

    // Configure the left encoder's starting position
    m_left_follower.configureEncoder((int)Drive.getInstance().getLeft().getEncoder().getPosition(), MotionProfilingConstants.kTicksPerMeterLeft);
    // // Configure the right encoder's starting position
    m_right_follower.configureEncoder((int)Drive.getInstance().getLeft().getEncoder().getPosition(), MotionProfilingConstants.kTicksPerMeterRight);

    // Update the PID values
    updatePID();

    // Initialize the follower
    m_follower_notifier = new Notifier(this::followPath);
    // Start the follower with a period given from leftTrajectory's first point time
    m_follower_notifier.startPeriodic(leftTrajectory.get(0).dt);

  }

  /**
   * Does nothing.
   */
  @Override
  protected void execute() {
  }

  /**
   * Returns true once finished following
   */
  @Override
  protected boolean isFinished() {
    // Check if either follower is finished
    return m_left_follower.isFinished() || m_right_follower.isFinished();
  }

  /**
   * Stop the notifier
   */
  @Override
  protected void end() {
    // Stop the motors
    Drive.getInstance().stop();
    if (m_follower_notifier != null){
      // End the notifier
      m_follower_notifier.stop();
    }
  }

  /**
   * Calls end()
   */
  @Override
  protected void interrupted() {
    end();
  }

  /**
   * Runs on a seperate thread for calculating the outputs
   */
  private void followPath() {

    if (m_left_follower.isFinished() || m_right_follower.isFinished()) {
      // Stop the motors
      Drive.getInstance().stop();
      // Wait for isFinished() to notice this
    } else {

      // Record the calculation start time
      long startTime = System.currentTimeMillis();

      // Calculate the speeds of each side based on the encoder locations
      double left_speed = m_left_follower.calculate((int)Drive.getInstance().getLeft().getEncoder().getPosition());
      double right_speed = m_right_follower.calculate((int)Drive.getInstance().getLeft().getEncoder().getPosition());
      // // Calculate the gyro angle's effect
      double heading = Drive.getInstance().getGyro().getAngle();
      double desired_heading = Pathfinder.r2d(m_left_follower.getHeading());
      double heading_difference = Pathfinder.boundHalfDegrees(desired_heading - heading);
      double turn =  -MotionProfilingConstants.kTurn  * (-1.0/80.0) * heading_difference;

      // // System.out.println("P: " + MotionProfilingConstants.kP + ", Turn: " + MotionProfilingConstants.kTurn);
      // // System.out.println("Left: " + (left_speed + turn) + ", Right: " + (right_speed - turn));

      SmartDashboard.putNumber("Left Encoder", Drive.getInstance().getLeft().getEncoder().getPosition());
      // SmartDashboard.putNumber("Left Setpoint", m_left_follower.getSegment().position);
      SmartDashboard.putNumber("Right Encoder", Drive.getInstance().getRight().getEncoder().getPosition());
      // SmartDashboard.putNumber("Right Setpoint", m_right_follower.getSegment().position);

      // // Set the speeds to the motors
      Drive.getInstance().tankDrive(-(left_speed + turn), -(right_speed - turn));

      // // Log the current status every n times
      Logger.logEvery("Drive Motion Profile Status: Left Error: " + m_left_follower.lastError
       + ", Right Error: " + m_right_follower.lastError + "-" + ", Gyro Error: " + heading_difference + " (" + heading + ")" + ", Total Calculation Time: " + (System.currentTimeMillis() - startTime), 1, this);

    }
  }

     /**
     * Updates the Talon PID values to those in the Smart Dashboard
     */
    public void updatePID(){

      // Pull from network tables
      MotionProfilingConstants.kP = NetworkTableCommunicator.get(SmartDashboardConstants.P_CONST).getDouble();
      MotionProfilingConstants.kI = NetworkTableCommunicator.get(SmartDashboardConstants.I_CONST).getDouble();
      MotionProfilingConstants.kD = NetworkTableCommunicator.get(SmartDashboardConstants.D_CONST).getDouble();
      MotionProfilingConstants.kV = NetworkTableCommunicator.get(SmartDashboardConstants.V_CONST).getDouble();
      MotionProfilingConstants.kA = NetworkTableCommunicator.get(SmartDashboardConstants.A_CONST).getDouble();
      MotionProfilingConstants.kTurn = NetworkTableCommunicator.get(SmartDashboardConstants.TURN_CONST).getDouble();

      System.out.println("P: " + MotionProfilingConstants.kP + ", Turn: " + MotionProfilingConstants.kTurn);

      // Update the followers
      m_left_follower.configurePDVA(MotionProfilingConstants.kP, MotionProfilingConstants.kD, MotionProfilingConstants.kV, MotionProfilingConstants.kA);
      m_right_follower.configurePDVA(MotionProfilingConstants.kP, MotionProfilingConstants.kD, MotionProfilingConstants.kV, MotionProfilingConstants.kA);
      
  }

  public void reset(){
    end();
    this.m_left_follower.reset();
    this.m_right_follower.reset();
  }
}
