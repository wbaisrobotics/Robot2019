
package frc.robot.components.encoders;

import com.revrobotics.CANEncoder;

public class CANEncoderGroup{

    /**
     * The encoder group
     */
    private CANEncoder[] encoders;

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
        // Initialize a sum
        double sum = 0.0;
        // Iterate through each encoder
        for (CANEncoder e : this.encoders){
            // Add each position to the sum
            sum += e.getPosition();
        }
        // Compute the average
        double average = sum / this.encoders.length;
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
        // Initialize a sum
        double sum = 0.0;
        // Iterate through each encoder
        for (CANEncoder e : this.encoders){
            // Add each velocity to the sum
            sum += e.getVelocity();
        }
        // Compute the average
        double average = sum / this.encoders.length;
        // Return the average
        return average;

    }

}