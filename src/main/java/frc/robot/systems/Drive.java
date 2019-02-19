
package frc.robot.systems;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.components.NetworkTableCommunicator;
import frc.robot.components.speed.SpeedControllers;
import frc.robot.constants.MotionProfilingConstants;
import frc.robot.constants.wiring.CANWiring;
import frc.robot.constants.wiring.DIOWiring;
import frc.robot.constants.wiring.PCMWiring;
import frc.robot.util.Logger;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.JoystickDrive;

/**
 * Represents the drive class of the robot
 */
public class Drive extends Subsystem{

    /**
     * The WPILib drive class
     */
    private DifferentialDrive drive;

    /**
     * Whether or not the left side should be reversed
     */
    public final static boolean LEFT_SIDE_REVERSE = false;

    /**
     * Whether or not the right side should be reversed
     */
    public final static boolean RIGHT_SIDE_REVERSE = true;

    /** The sole static instance of Drive */
    private static Drive instance;
    /**
     * Returns an instance of Drive, creating one if necessary
     * @return
     */
    public static Drive getInstance (){
        if (instance == null){

            // Initialize the Talon SRXs

            // Initialize the left master
            CANSparkMax left1 = SpeedControllers.getSparkMaxBrushless(CANWiring.DRIVE_LEFT_1);
            // Initialize the left slaves
            CANSparkMax left2 = SpeedControllers.getSparkMaxBrushless(CANWiring.DRIVE_LEFT_2);
            CANSparkMax left3 = SpeedControllers.getSparkMaxBrushless(CANWiring.DRIVE_LEFT_3);

            // Initialize the right master
            CANSparkMax right1 = SpeedControllers.getSparkMaxBrushless(CANWiring.DRIVE_RIGHT_1);
            // Initialize the left slaves
            CANSparkMax right2 = SpeedControllers.getSparkMaxBrushless(CANWiring.DRIVE_RIGHT_2);
            CANSparkMax right3 = SpeedControllers.getSparkMaxBrushless(CANWiring.DRIVE_RIGHT_3);

            // Initialize the gear shifter
            DoubleSolenoid gearShifter = new DoubleSolenoid (PCMWiring.G_A.getPort(), PCMWiring.G_B.getPort());

            // Initialize the gyro
            ADXRS450_Gyro gyro = new ADXRS450_Gyro();
            // Add gyro to IO Dashboard
            SmartDashboard.putData(gyro);
            //NetworkTableCommunicator.setIoDashboardValue("Gyro", gyro);

            // Initialize the reverse indicator light
            DigitalOutput indicatorLight = new DigitalOutput (DIOWiring.DRIVE_INDICATOR_LIGHT.getPort());

            // Create the instance
            instance = new Drive(left1, left2, left3, right1, right2, right3, gearShifter, gyro, indicatorLight);

        }
        return instance;
    }

    /**
     * The left master
     */
    private CANSparkMax left;
    /**
     * The right master
     */
    private CANSparkMax right;

    /**
     * The gear shifter
     */
    private DoubleSolenoid gearShifter;

    /**
     * The gyro
     */
    private Gyro gyro;

    /**
     * Whether or not drive is reversed
     */
    private boolean reverse = false;

    /**
     * The indicator light displaying which side is in reverse
     */
    private DigitalOutput indicatorLight;

    /**
     * Default constructor for just the motors and no encoders
     * @param left - the left motors
     * @param right - the right motors
     */
    public Drive (CANSparkMax left1, CANSparkMax left2, CANSparkMax left3, CANSparkMax right1, CANSparkMax right2, CANSparkMax right3, DoubleSolenoid gearShifter, Gyro gyro, DigitalOutput indicatorLight){

        // Call DifferentialDrive with the controllers
        drive = new DifferentialDrive (new SpeedControllerGroup(left1, left2, left3), new SpeedControllerGroup(right1, right2, right3));
        // Save the controllers
        this.left = left1;
        this.right = right1;

        // Save the gyro
        this.gyro = gyro;

        // Save the gear shifter
        this.gearShifter = gearShifter;

        // Save the indicator light
        this.indicatorLight = indicatorLight;

    }

    /**
     * Resets all sensors
     */
    public void reset (){

        // Reset the gyro
        gyro.reset();

        // Reset the encoders
        left.getEncoder().setPosition(0);
        right.getEncoder().setPosition(0);

        // Log the reset
        Logger.log("[Quadrature Encoders] All sensors are zeroed.");
        
    }

    /**
     * Immediately halts both sides
     */
    public void stop (){

        left.stopMotor();
        right.stopMotor();

    }

      /**
       * Returns the left master
       * @return the left master controller
       */
      public CANSparkMax getLeft(){
        return this.left;
      }

      /**
       * Returns the right master
       * @return the right master controller
       */
      public CANSparkMax getRight(){
        return this.right;
      }

      /**
       * Updatest the reverse setting and sends to talons
       * @param reverse - the new value for reverse
       */
      public void setReverse (boolean reverse){

        // Log the change
        Logger.log("Swapped face to: " + reverse);

        // Set the reverse variable
        this.reverse = reverse;

        // Update the indicator LEDs
        indicatorLight.set(reverse);

      }

      /**
       * Toggles whether or not driving in reverse
       */
      public void toggleReverse (){
        // Set the reverse variable to the opposite of what it currently is
        setReverse (!getReverse());
      }

      /**
       * Returns whether or not driving is reversed
       * @return - the current status of the reverse boolean
       */
      public boolean getReverse (){
          return this.reverse;
      }

      /**
       * Arcade drive (accounting for reverse)
       */
      public void arcadeDrive (double xSpeed, double zRotation){
          drive.arcadeDrive(getReverse()?-xSpeed:xSpeed, zRotation);
      }

      /**
       * Tank drive (accounting for reverse)
       */
      public void tankDrive (double left, double right){
        drive.tankDrive(getReverse()?-left:left, getReverse()?-right:right);
      }

      /**
       * Toggles the gear speed
       */
      public void toggleGearSpeed (){
          this.gearShifter.set(this.gearShifter.get() == Value.kForward?Value.kReverse:Value.kForward);
      }

      /**
       * Returns the gyro
       * @return gyro
       */
      public Gyro getGyro(){
          return this.gyro;
      }

      /**
       * Initializes the joystick command
       */
      public void initDefaultCommand (){
        // Set the default command to JoystickDrive
        super.setDefaultCommand(new JoystickDrive());
      }

}