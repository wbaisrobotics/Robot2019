/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;
import frc.robot.util.Logger;
import java.lang.instrument.Instrumentation;

/**
 * Do NOT add any static variables to this class, or any initialization at all.
 * Unless you know what you are doing, do not modify this file except to
 * change the parameter class to the startRobot call.
 */
public final class Main {
  private Main() {
  }

  /**
   * Main initialization function. Do not perform any initialization here.
   *
   * <p>If you change your main robot class, change the parameter type.
   */
  public static void main(String... args) {
    // RobotBase.startRobot(Robot::new);

    long start = System.currentTimeMillis();
    for (int i = 0; i < 7500; i++){
      Logger.logToFile(new Double[]{Math.random(), Math.random(), Math.random(), Math.random(), Math.random()
        , Math.random(), Math.random(), Math.random(), Math.random(), Math.random(), Math.random(), Math.random(),
        Math.random(), Math.random(), Math.random(), Math.random(), Math.random(), Math.random()});
    }
    try{
      Thread.sleep(10000);
    }
    catch (Exception e){
      
    }
    System.out.println(System.currentTimeMillis() - start);
    Logger.flushToFile();
    System.out.println(System.currentTimeMillis() - start);
  }

  
}