package GUI;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

import ChaosGameStart.Controller;

import static Others.Constants.*;

/**
 * Frame is a JFrame that houses the Chaos Game. Initializes frame given controller, adding title page 
 * as first visible page. Centers and size to screen.
 */
@SuppressWarnings("serial")
public class Frame extends JFrame
{	
	/**
	 * Initializes the frame for GUI program. Adds Title Page as first control panel.
	 * @param Controller
	 */
	public Frame(Controller controller)
	{
		// Initializes properties of frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(frameTitle);
		setVisible(true);

		// ToolMethods to make and set GUI half of computer screen size
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension dim = kit.getScreenSize();
		setSize(dim.width / 2, dim.height * 3 / 5);
		setLocation(dim.width / 4, dim.height / 4);
		
		// Initialize first page in GUI
		add(new TitlePage(controller));
	}

	/**
	 * Exit GUI if exit button is ever pressed
	 */
	public void Exit()
	{
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
}