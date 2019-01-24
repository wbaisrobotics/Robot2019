package frc.robot.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Use for logging output
 * @author orianleitersdorf
 *
 */
public class Logger {
	
	/**
	 * Logs a message to console
	 * @param message
	 */
	public synchronized static void log (Object message) {
		System.out.println(message);
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