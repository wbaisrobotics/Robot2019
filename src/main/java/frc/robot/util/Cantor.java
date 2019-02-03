package frc.robot.util;
/**
 * Cantor Paring Function
 * 
 * @author orianleitersdorf
 *
 */
public class Cantor {

	/**
	 * Returns the index for the two numbers
	 * Cantor Pairing Function
	 * @param a
	 * @param b
	 * @return
	 */
	private static int getIndex (int a, int b) {
		return (a+b+1)*(a+b)/2 + b;
	}
	
	/**
	 * Creates an index for an infinite amount of inputs, starting at the given index
	 * @param index
	 * @param inputs
	 * @return
	 */
	private static int getIndex (int index, int...inputs) {
		if (index == inputs.length) {
			return -1;
		}
		if (index == (inputs.length-1)) {
			return inputs[index];
		}
		return getIndex (inputs[index], getIndex (index + 1, inputs));
	}
	
	/**
	 * Creates an index for an infinite amount of inputs
	 * @param inputs
	 * @return
	 */
	public static int getIndex (int... inputs) {
		return getIndex (0, inputs);
	}
	
	/**
	 * Inverses the function solving for a
	 * @param index
	 * @return
	 */
	public static int getA (int index) {
		int w = (int)((Math.sqrt(8*index + 1)-1)/2);
		int t = (int)((Math.pow(w, 2) + w)/2);
		int y = index - t;
		return w - y;
	}
	
	/**
	 * Inverses the function solving for b
	 * @param index
	 * @return
	 */
	public static int getB (int index) {
		int w = (int)((Math.sqrt(8*index + 1)-1)/2);
		int t = (int)((Math.pow(w, 2) + w)/2);
		return index - t;
	}

}
