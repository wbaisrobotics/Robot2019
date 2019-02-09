/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.systems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.constants.wiring.PCMWiring;

/**
 * The hatch panel manipulator
 */
public class HatchManipulator extends Subsystem {

  /**
  * The instance of the system
  */
  private static HatchManipulator instance;

  /**
  * Returns (and possibly creates) the system instance
  * @return the instance 
  */
  public static HatchManipulator getInstance (){

    // If not initialized yet,

    if (instance == null){
      // then initialize:

      // Initialize the solenoids
      DoubleSolenoid thunker = new DoubleSolenoid (PCMWiring.HIN_A.getPort(), PCMWiring.HIN_B.getPort());
      DoubleSolenoid shooter = new DoubleSolenoid (PCMWiring.HOT_A.getPort(), PCMWiring.HOT_B.getPort());
            
      // Initialize the instance
      instance = new HatchManipulator(thunker, shooter);

    }

    // Return the instance
    return instance;

  }

  /**
   * The solenoid value for thunker down
   */
  public final DoubleSolenoid.Value THUNKER_DOWN_VALUE = DoubleSolenoid.Value.kForward;

  /**
   * The solenoid value for thunker up
   */
  public final DoubleSolenoid.Value THUNKER_UP_VALUE = DoubleSolenoid.Value.kReverse;

    /**
   * The solenoid value for shooter out
   */
  public final DoubleSolenoid.Value SHOOTER_OUT_VALUE = DoubleSolenoid.Value.kForward;

    /**
   * The solenoid value for thunker down
   */
  public final DoubleSolenoid.Value SHOOTER_IN_VALUE = DoubleSolenoid.Value.kReverse;

  /**
   * The piston for picking up the hatch panel from the floor
   */
  private DoubleSolenoid thunker;

  /**
   * The pistons for shooting out the hatch panel
   */
  private DoubleSolenoid shooter;

  /**
   * Initiailzes the hatch manipulator
   * @param thunker
   * @param shooter
   */
  public HatchManipulator (DoubleSolenoid thunker, DoubleSolenoid shooter){
    // Save the solenoids
    this.thunker = thunker;
    this.shooter = shooter;
  }

  /**
   * Lowers the thunker
   */
  public void thunkerDown (){
    thunker.set(THUNKER_DOWN_VALUE);
  }

  /**
   * Lowers the thunker
   */
  public void thunkerUp (){
    thunker.set(THUNKER_UP_VALUE);
  }

  /**
   * Shoots out the panel
   */
  public void shooterOut (){
    shooter.set(SHOOTER_OUT_VALUE);
  }

  /**
   * Retracts the shooter
   */
  public void shooterIn (){
    shooter.set(SHOOTER_IN_VALUE);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
