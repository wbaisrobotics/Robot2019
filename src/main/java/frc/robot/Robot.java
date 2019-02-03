/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.oi.OI;
import frc.robot.systems.BackClimbers;
import frc.robot.systems.Drive;
import frc.robot.systems.FrontClimbers;
import frc.robot.util.Logger;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  /**
   * The drive system of the robot 
   */
  private Drive drive;

  /**
   * The system in control of the two front climbing legs
   */
  private FrontClimbers frontClimbers;
  /**
   * The system in control of the two back climbing legs
   */
  private BackClimbers backClimbers;
  
  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {

    /**
     * Initializes and stores the drive instance
     */
    this.drive = Drive.getInstance();

    /**
     * Initializes and stores the front climbers instance
     */
    this.frontClimbers = FrontClimbers.getInstance();

    /**
     * Initializes and stores the back climbers instance
     */
    this.backClimbers = BackClimbers.getInstance();

    /**
     * Initializes the OI
     */
    OI.initButtons();

  }

  @Override
  public void autonomousInit() {

    // Log the init
    Logger.log("Autonomous Begins");

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

  }

  @Override
  public void teleopPeriodic() {

    /**
     * Run the scheduler
     */
    Scheduler.getInstance().run();

  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
