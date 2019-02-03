package frc.robot.oi;

import edu.wpi.first.wpilibj.buttons.Button;

/**
 * Represents a collection of buttons connected using an AND operator
 * @author orianleitersdorf
 *
 */
public class ButtonCollection extends Button{
	
	/**
	 * The buttons whose source is used
	 */
	private Button[] m_buttons;

	/**
	 * Initializes the button that only returns true when get() is called when all of these buttons return true on get()
	 * @param buttons
	 */
	public ButtonCollection(Button...buttons) {
		this.m_buttons = buttons;
	}

	/**
	 * Returns true only if all of the buttons return true when their get() is called
	 */
	@Override
	public boolean get() {
		for (Button button : this.m_buttons) {
			if (!button.get()) {
				return false;
			}
		}
		return true;
	}

}
