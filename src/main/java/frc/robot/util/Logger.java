package frc.robot.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * Use for logging output
 * @author orianleitersdorf
 *
 */
public class Logger {

	private static String[] csvHeader = new String[]{

		"Time",
		"DriveXSpeed", "DriveZRotation", "DriveHighGear", "DriveReverse", "DriveVision",
		"HatchShooterIn", "HatchThunkerUp",
		"BallShooter", "BallElevator",
		"DCCrawler", "DCWorm",
		"FrontClimberLeft", "FrontClimberRight",
		"BackClimberLeft", "BackClimberRight",
		"CompSwitch", "CompCurrent"

	};
	private static ArrayList <Double[]> logs = new ArrayList <Double[]>();
	
	/**
	 * Logs a message to console
	 * @param message
	 */
	public synchronized static void log (Object message) {
		System.out.println(message);
	}

	/**
	 * Adds a message that would be saved to the csv at match end
	 * @param values
	 */
	public synchronized static void logToFile (Double[] values){
		logs.add(values);
	}

	/**
	 * Flushes the stored values to the csv
	 */
	public synchronized static void flushToFile(){

		// If anything was logged yet
		if (logs.size() > 0){

			// Open a directory named Logs (iff doesn't already exist)
			File file = new File("/home/lvuser/Logs");
			if (!file.exists()) {
				if (file.mkdir()) {
					System.out.println("Log Directory is created!");
				} else {
					System.out.println("Failed to create Log directory!");
				}
			}
			// Create name for this log
			Date date = new Date() ;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
			// For Detroit time zone
			dateFormat.setTimeZone(TimeZone.getTimeZone("GMT-4:00"));
			// Define the file location
			String fileName = "/home/lvuser/Logs/" + dateFormat.format(date) + ";" + DriverStation.getInstance().getMatchType() + DriverStation.getInstance().getMatchNumber() + ".csv";

			// Try to open the file at the location
			try (PrintWriter writer = new PrintWriter(new File(fileName))) {

				// Start building the file
				StringBuilder sb = new StringBuilder();

				// Add the header to the string
				for (String c : csvHeader){
					// Add a value and comma (csv)
					sb.append(c);
					sb.append(',');
				}

				// New line
				sb.append('\n');

				// Iterate through each log
				for (Double[] values : logs){

					// Iterate through each value in the log
					for (Double value : values){
						// Add a value and comma (csv)
						sb.append(value);
						sb.append(',');
					}

					// New line
					sb.append('\n');

				}

				// Write the string to the file		  
				writer.write(sb.toString());

				log ("Saved " + logs.size() + " logs to " + fileName);
		  
			  } catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
			  }
		}

	}
	
	/* Log every x */
	
	/** Represents the logs that a given object has sent through logEvery() **/
	private static Map <Object, Integer> logsSent = new HashMap <Object, Integer>();
	
	/**
	 * Logs the given message for every n messages sent (using logEvery()) from the given sender
	 * @param message
	 * @param n
	 * @param sender
	 */
	public static synchronized void logEvery (Object message, int n, Object sender) {
		
		int logsSent = logsSentForSender (sender);
		
		if (logsSent % n == 0) {
			log (message);
		}
		
		incrimentLogsSent (sender);
		
	}
	
	/**
	 * Returns the number of logs that the sender has sent
	 * @param sender
	 * @return
	 */
	private static synchronized int logsSentForSender (Object sender) {
		if (!logsSent.containsKey(sender)) {
			logsSent.put(sender, 0);
		}
		return logsSent.get(sender);
	}
	
	/**
	 * Incriments the number of logs a sender has sent
	 * @param sender
	 */
	private static synchronized void incrimentLogsSent (Object sender) {
		logsSent.put(sender, logsSentForSender(sender) + 1);
	}

}