package SidePanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Hashtable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ChaosGameStart.Controller;

import static Others.Constants.*;

/**
 * IterationBox houses components that control the iteration of the Chaos Game.
 * Iteration field is a JTextField so user can input number of iterations. Throws exceptions.
 * Size slide is a JSlider that controls size of points beings drawn.
 * Speed slide is a JSlider that controls speed at which points are being drawn.
 * Travel field is a JTextField so user can specify distance points are traveling. Throws exceptions.
 */
@SuppressWarnings("serial")
public class IterationBox extends JPanel implements ChangeListener{

	// Controller variable
	private Controller controller;

	// JLabel containing current number of iterations
	private JLabel currentIterLabel;	
	
	// Text field for adding n iterations
	private JTextField iterField; 
	
	// Sliders to control point size and iteration speed
	private JSlider sizeSlide;	
	private JSlider speedSlide;	
	
	// Text Field for where new point gets added
	private JTextField travelField;

	/**
	 * Initializes iteration box that controls number of iterations via text field, 
	 * size of points being drawn, speed that points are being drawn,
	 * and distance the new point travels.
	 * @param controller
	 */
	public IterationBox(Controller controller)
	{
		// Controller variable
		this.controller = controller;

		// Label and text field dealing with max number of iterations
		JLabel maxLabel = new JLabel("Add points:");
		iterField = new JTextField(baseIter + "");

		// Label dealing with current number of iterations
		currentIterLabel = new JLabel("Current Iterations: 0");
		
		// Label and slider dealing with size of point drawn
		JLabel sizeLabel = new JLabel("Point Size:");
		sizeSlide = new JSlider(JSlider.HORIZONTAL, slideMin, slideMax, basePointSize);
		sizeSlide.setMajorTickSpacing(1);
		sizeSlide.setPaintTicks(true);
		sizeSlide.setPaintLabels(true);
		
		// Label and slider dealing with points per second, adding hash table to produce labels for slider
		JLabel speedLabel = new JLabel("Speed:");
		speedSlide = new JSlider(JSlider.HORIZONTAL, slideMin, slideMax, basePointSpeed);
		speedSlide.setMajorTickSpacing(1);
		speedSlide.setPaintTicks(true);
		
		// Add text to speed slider
		Hashtable<Integer, JLabel> label = new Hashtable<Integer, JLabel>();
		Integer[] ints = new Integer[] {1, 2, 3, 4};
		String[] values = new String[] {"slow", "medium", "fast", ":o"};
		for (int i=0; i<ints.length; i++)
			label.put(ints[i], new JLabel(values[i]));
		speedSlide.setLabelTable(label);
		speedSlide.setPaintLabels(true);
		
		// Label and text field for how far to travel
		JLabel travelLabel = new JLabel("Dot Distance:");
		travelField = new JTextField(baseTravel + "");
		
		// Adds action listeners for sliders
		sizeSlide.addChangeListener(this);
		speedSlide.addChangeListener(this);
		
		// Sets grid bag constraints and adds to panel (layout)
		GridBagLayout grid = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(grid);
		
		// Iteration JLabel
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = labelMinX;
		c.ipady = labelMinY;
		c.weightx = gridLabel;
		add(maxLabel,c);

		// Size JLabel
		c.gridy = 2;
		add(sizeLabel, c);

		// Speed JLabel
		c.gridy = 3;
		add(speedLabel, c);
		
		// Travel label
		c.gridy = 4;
		add(travelLabel, c);

		// Iteration JTextField
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = gridOther;
		add(iterField, c);
		
		// Size JSlider
		c.gridy = 2;
		add(sizeSlide, c);
		
		// Speed JSlider
		c.gridy = 3;
		add(speedSlide, c);
		
		// Travel text field
		c.gridy = 4;
		add(travelField, c);
		
		// Current JLabel
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.gridx = 1;
		c.gridy = 1;
		add(currentIterLabel,c);
	}
	
	/**
	 * State changes for JSliders. If size slide or speed slide changes, update in controller
	 * This allows change during iteration.
	 */
	@Override
	public void stateChanged(ChangeEvent e) 
	{
		// Size slider clicked
		if (e.getSource() == sizeSlide)
			controller.updatePointSize(sizeSlide.getValue());
		
		// Speed slider clicked
		if (e.getSource() == speedSlide)
			controller.updatePointSpeed(speedSlide.getValue());
	}
	
	/**
	 * Check that text field numbers are ok values. If ok, then allows iteration
	 * @return boolean
	 */
	public boolean checkValues()
	{
		// Check that max iteration value is positive integer
		if (getMaxValue() <= 0)
			return false;
		
		// Check that travel distance is in between 0 and 1
		double d = getTravelDistance();
		if (d <=0 || d >= 1)
			return false;
		
		// No issues, return true
		return true;
	}
	
	/**
	 * Gets value from iteration text field. Throws exception if number not positive integer
	 * @return
	 */
	public int getMaxValue()
	{
		// Try to take integer from max field
		int numIter = 0;
		try {
			numIter = Integer.parseInt(iterField.getText());
			if (numIter <= 0)
				throw new Exception();
			
		// If bad number format, let user know
		} catch (Exception e) {
			String message = "'Add points' box must be a positive integer. Value will be reset to default.";
			String header = "Error: Number format.";
			JOptionPane.showMessageDialog(null, message, header, JOptionPane.INFORMATION_MESSAGE);
			iterField.setText(baseIter + "");
		}
		
		// Returns value of numIter
		return numIter;
	}
	
	/**
	 * Gets value from travel text field. Throws exception if number not between 0 and 1
	 * @return
	 */
	public double getTravelDistance()
	{
		// Try to take double from travel field
		double d = 0;
		try {
			d = Double.parseDouble(travelField.getText());
			if (d <=0 || d >=1)
				throw new Exception();
			
		// If bad value, prompt user and reset to base value
		} catch (Exception e) {
			String message = "'Dot Distance' must be decimal between 0 and 1. Value will be reset to default.";
			String header = "Error: Number format.";
			JOptionPane.showMessageDialog(null, message, header, JOptionPane.INFORMATION_MESSAGE);
			travelField.setText(baseTravel + "");
		}
		
		// Returns value for d
		return d;
	}

	/**
	 * Updates label containing current number of iterations.
	 * Input parameter is integer
	 * @param numPoints
	 */
	public void updateCurrentLabel(int numPoints) 
	{
		currentIterLabel.setText("Current Iterations: " + numPoints);
	}
	
	/**
	 * Method to allow tutorial to change number of max iterations.
	 * Input is integer.
	 * @param i
	 */
	public void setMaxIteration(int i)
	{
		iterField.setText(i + "");
	}
	
	/**
	 * Method to allow tutorial to change distance point travels.
	 * Input is double
	 * @param d
	 */
	public void setPointTravelDistance(double d)
	{
		travelField.setText(d + "");
	}

	/**
	 *  Turns on manipulation of JTextfield and JSliders
	 *  Input parameter is boolean
	 * @param b
	 */
	public void turnOnOff(boolean b)
	{
		iterField.setEditable(b);
		travelField.setEditable(b);
	}

	/**
	 *  Set default conditions of iteration box, and updates controller
	 */
	public void setDefault()
	{
		// Base default values
		iterField.setText(baseIter + "");
		currentIterLabel.setText("Current Iterations: 0");
		sizeSlide.setValue(basePointSize);
		speedSlide.setValue(basePointSpeed);
		travelField.setText(baseTravel + "");
		
		// Changing value does not count as state change, so manually update
		controller.updatePointSize(sizeSlide.getValue());
		controller.updatePointSpeed(speedSlide.getValue());
	}
}