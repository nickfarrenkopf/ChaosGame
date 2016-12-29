package SidePanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ChaosGameStart.Controller;

import static Others.Constants.*;

/**
 * ButtonBox is a JPanel that houses implementation of Chaos Game buttons.
 * Run button can start or stop iteration (as controlled by controller), 
 * Clear Button clears current point list
 * Reset button sets default conditions of program
 * Exit button exits program.
 */
@SuppressWarnings("serial")
public class ButtonBox extends JPanel implements ActionListener{

	// Controller variables
	private Controller controller;
	
	// Button variables
	private JButton runButton;
	private JButton clearButton;
	private JButton resetButton;
	private JButton exitButton;
	
	/**
	 * Initializes Button Box for side panel. Button box contains a run button that can start/stop iteration,
	 * a clear button that clears current point list, a reset button that sets default conditions, 
	 * and an exit button that exits program. 
	 * @param controller
	 */
	public ButtonBox(Controller controller)
	{	
		// Initializes variables
		this.controller = controller;
		
		// Initialize buttons
		runButton = new JButton("Start");
		clearButton = new JButton("Clear");
		resetButton = new JButton("Reset");
		exitButton = new JButton("Exit");
		
		// Add action listeners
		runButton.addActionListener(this);
		clearButton.addActionListener(this);
		resetButton.addActionListener(this);
		exitButton.addActionListener(this);

		// Sets layout of panel
		GridBagLayout grid = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(grid);
		
		// Start Button
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		c.insets = insets;
		add(runButton, c);

		// Clear Button
		c.gridy = 1;
		add(clearButton, c);

		// Reset Button
		c.gridy = 2;
		add(resetButton, c);

		// Exit Button
		c.gridy = 3;
		add(exitButton, c);
	}

	/**
	 * Action listener for button box. Run button can start or stop program (controller by controller),
	 * clear button clears current points, reset button sets default conditions, 
	 * and exit button exits program.
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// If run button clicked, can start or stop iteration
		if (e.getSource() == runButton)
		{
			// Code if need to start running program, changing button text and alerting controller
			if (runButton.getText().equals("Start"))
			{
				// Check for valid inputs from user
				if (controller.getScreen().getSidePanel().getIterationBox().checkValues())
				{
					runButton.setText("Stop");
					controller.startIteration();
				}
				
			// Code if already running and need to stop, changing button text and alerting controller
			} else if (runButton.getText().equals("Stop")) 
			{
				runButton.setText("Start");
				controller.stopIteration();
			}
		}
		
		// If clear button pressed, clear all iterations
		if (e.getSource() == clearButton)
		{
			if (runButton.getText().equals("Stop"))
				runButton.doClick();
			controller.clearPointList();
		}
		
		// If reset button is clicked
		if (e.getSource() == resetButton)
		{
			// Stops program if already running
			if (runButton.getText().equals("Stop"))
				runButton.doClick();
			
			// Prompts user if want to reset program
			String header = "Warning";
			String message = "Reset to default conditions?";
			int result = JOptionPane.showConfirmDialog (null, message, header, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			// If yes, sets program default values
			if (result == JOptionPane.YES_OPTION)
				controller.setDefaut();
		}
		
		// If exit button is clicked
		if (e.getSource() == exitButton)
		{
			// Prompts user if want to exit program
			String header = "Warning";
			String message = "Are you sure you want to exit program?";
			int result = JOptionPane.showConfirmDialog (null, message, header, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			// If yes, exits
			if (result == JOptionPane.YES_OPTION)
			controller.getFrame().Exit();
		}
	}
	
	/**
	 * Sets defaults of button box
	 */
	public void setDefault()
	{
		runButton.setText("Start");
	}
}