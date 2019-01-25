
package frc.robot.components.encoders;

import com.revrobotics.CANEncoder;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.CANSparkMax;

public class CANEncoderGroup{

    /**
     * The encoder group
     */
    private CANEncoder[] encoders;

    /**
     * Initializes from the encoders of the given spark max controllers
     * @param controllers
     */
    public CANEncoderGroup (CANSparkMax... controllers){
        // Create an empty array of encoders
        this.encoders = new CANEncoder [controllers.length];
        // Iterate through each controller
        for (int i = 0; i < controllers.length; i++){
            // Get the encoder for this controller
            this.encoders[i] = controllers[i].getEncoder();
        }
    }

    /**
     * Initializes the encoder group from multiple encoders
     * @param encoders
     */
    public CANEncoderGroup (CANEncoder... encoders){
        this.encoders = encoders;
    }

    /**
     * Return the average position amongst all the encoders
     * @return
     */
    public double getPosition(){

        /**
         * Calculate the average
         */
        int n = this.encoders.length;
        // Initialize a sum
        double sum = 0.0;
        // Iterate through each encoder
        for (CANEncoder e : this.encoders){
            double pos = e.getPosition();
            // Add each position to the sum
            sum += pos;
            if (pos == 0){
                n--;
            }
        }
        if (n == 0){
            return -1;
        }
        // Compute the average
        double average = sum / n;
        // Return the average
        return average;

    }

    /**
     * Return the average position amongst all the encoders
     * @return
     */
    public double getVelocity(){

        /**
         * Calculate the average
         */
        int n = this.encoders.length;
        // Initialize a sum
        double sum = 0.0;
        // Iterate through each encoder
        for (CANEncoder e : this.encoders){
            double pos = e.getVelocity();
            // Add each position to the sum
            sum += pos;
            if (pos == 0){
                n--;
            }
        }
        // Compute the average
        double average = sum / n;
        // Return the average
        return average;

    }

}