
package frc.robot.systems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.SpeedController;
import frc.robot.components.speed.SpeedControllers;
import frc.robot.constants.wiring.CANWiring;

/**
 * Class representing the death crawler (both worm arm and the crawler)
 */
public class DeathCrawler{

    /**
     * The instance of the system
     */
    private static DeathCrawler instance;

    /**
     * Returns (and possibly creates) the system instance
     * @return
     */
    public static DeathCrawler getInstance (){
        // If not initialized yet,
        if (instance == null){
            // then initialize:

            // Initialize the worm arm motor
            SpeedController armMotor = SpeedControllers.getTalonSRX(CANWiring.DEATH_CRAWLER_ARM);

            // Initialize the death crawler motor
            CANSparkMax deathCrawlMotor = SpeedControllers.getSparkMaxBrushless(CANWiring.DEATH_CRAWLER);
            
            instance = new DeathCrawler(deathCrawlMotor, armMotor);

        }
        // Return the instance
        return instance;
    }

    private CANSparkMax deathCrawlMotor;

    private SpeedController wormArmMotor;

    public DeathCrawler (CANSparkMax deathCrawlMotor, SpeedController wormArmMotor){

        this.deathCrawlMotor = deathCrawlMotor;
        this.wormArmMotor = wormArmMotor;

    }

    public void stop (){

        this.deathCrawlMotor.set(0.0);
        this.wormArmMotor.set(0.0);

    }

    public void setCrawlSpeed (double speed){
        this.deathCrawlMotor.set(speed);
    }

    public void setWormSpeed (double speed){
        // Going up
        if (speed < 0){
            this.wormArmMotor.set(speed * 1.0);
        }
        // Going down
        else{
            this.wormArmMotor.set(speed * 0.75);
        }
        
    }

}