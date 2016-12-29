package SidePanel;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;

import ChaosGameStart.Controller;

import static Others.Constants.*;

/**
 * ColorBox is a JPanel housed in SidePanel. It controls the color scheme of iteration points.
 * colorBox is a JComboBox that houses color selection.
 * varyBox is a JComboBox that houses vary selection.
 * Vary selection choices are Constant, Random, Every n, Region, and Vertex
 */
@SuppressWarnings("serial")
public class ColorBox extends JPanel implements ActionListener{

	// Important variables
	private Controller controller;
	
	// Labels and boxes for color coding
	private JComboBox<String> colorBox;
	private JComboBox<String> varyBox;
	
	/**
	 * Initializes the color box for side panel. Contains a vary box that allows user to change color
	 * scheme and a color box that allows user to change color.
	 * @param controller
	 */
	public ColorBox(Controller controller)
	{
		// Initialize important variables
		this.controller = controller;
		
		// Sets color label and color choice box
		JLabel colorLabel = new JLabel("Color:");
		colorBox = new JComboBox<String>(colorStrings);

		// Sets text and adds items for vary box
		JLabel varyLabel = new JLabel("Vary:");
		varyBox = new JComboBox<String>(varyChoices);
		
		// Add action listeners to boxes
		colorBox.addActionListener(this);
		varyBox.addActionListener(this);

		// Sets layout
		GridBagLayout grid = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(grid);

		// Color JLabel
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = labelMinX;
		c.ipady = labelMinY;
		c.weightx = gridLabel;
		c.weighty = 1;
		c.insets = insets;
		add(colorLabel,c);

		// Vary JLabel
		c.gridy = 1;
		add(varyLabel,c);

		// Color JComboBox
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = gridOther;
		add(colorBox, c);

		// Vary ComboBox
		c.gridy = 1;
		add(varyBox, c);
	}

	/**
	 * Action listener for color box. Changes color scheme of points and updates controller.
	 * If vary box is changed, change how the points vary by manually selecting color scheme
	 * If random, random colored points
	 * If constant, constant colored points
	 * If color box is changed, then updates color scheme to set color
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// Color scheme variable to hold color changes
		Color[] colorScheme;
		
		// Vary box action listener
		if (e.getSource() == varyBox)
		{
			// If constant color, set to current color box value
			if (varyBox.getSelectedItem().equals("Constant"))
			{
				colorBox.setEnabled(true);
				colorScheme = new Color[] {colorColors[colorBox.getSelectedIndex()]};

			// If random, then disable colorBox and set to random color
			} else if (varyBox.getSelectedItem().equals("Random")){
				colorBox.setEnabled(false);
				Color randomColor = Color.BLACK;
				colorScheme = new Color[] {randomColor};
				
			// If neither, then give pop up box for user to manually choose colors
			} else {
				colorBox.setEnabled(false);
				colorScheme = manualBox();
			}
			
			// Regardless of choice, reset vertex colors
			controller.setColorScheme((String) varyBox.getSelectedItem(), colorScheme);
		}
		
		// If color box is changed and constant color
		if (e.getSource() == colorBox && varyBox.getSelectedItem().equals("Constant"))
		{
			colorScheme = new Color[] {colorColors[colorBox.getSelectedIndex()]};
			controller.setColorScheme((String) varyBox.getSelectedItem(), colorScheme);
		}
	}	

	
	/**
	 * A pop up box that allows user to manually select color choices. Choices are "Every n" 
	 * (which throws up an additional pop up box to determine number of colors and points per color),
	 * "Region" and "Vertex" take number of points equal to number of vertices and point per color of 1.
	 * Return an array of colors for color scheme, which is set to controller later
	 * @return Color[]
	 */
	public Color[] manualBox()
	{
		// Number of colors and number of points per color variables
		int numColors = controller.getShapeSize();
		int numPoints = 1;
		
		// If "every n" choice, grab number of colors and number of points
		if (varyBox.getSelectedItem().equals("Every n"))
		{
			// Number of colors label and slider
			JLabel numColorsLabel = new JLabel("How many different colors?");
			JSlider numColorsSlider = new JSlider(JSlider.HORIZONTAL, 1, maxNumColors, 3);
			numColorsSlider.setMajorTickSpacing(1);
			numColorsSlider.setPaintTicks(true);
			numColorsSlider.setPaintLabels(true);
			
			// Number of points label and slider
			JLabel numPointsLabel = new JLabel("How many points per color?");
			JSlider numPointsSlider = new JSlider(JSlider.HORIZONTAL, 1, 5, 3);
			// Set personal labels for slider
			Hashtable<Integer, JLabel> label = new Hashtable<Integer, JLabel>();
			Integer[] ints = new Integer[] {1, 2, 3, 4, 5};
			String[] values = new String[] {"1", "10", "100", "1000", "10000"};
			for (int i=0; i<ints.length; i++)
				label.put(ints[i], new JLabel(values[i]));
			numPointsSlider.setMajorTickSpacing(1);
			numPointsSlider.setLabelTable(label);
			numPointsSlider.setPaintTicks(true);
			numPointsSlider.setPaintLabels(true);
			
			// Add to components
			JComponent[] extraInputs = new JComponent[4];
			extraInputs[0] = numColorsLabel;
			extraInputs[1] = numColorsSlider;
			extraInputs[2] = numPointsLabel;
			extraInputs[3] = numPointsSlider;
			
			// Extra pop up box for extra input
			String header = "Set extra inputs";
			JOptionPane.showConfirmDialog(null, extraInputs, header, JOptionPane.PLAIN_MESSAGE);
			
			// Set values from pop up box
			numColors = numColorsSlider.getValue();
			numPoints = (int) Math.pow(10, numPointsSlider.getValue() - 1);
		} 
	
		// Creates labels and combo boxes so user can choose colors
		JLabel[] colorLabels = new JLabel[numColors];
		ArrayList<JComboBox<String>> colorBoxes = new ArrayList<JComboBox<String>>();
		
		// Initialize components to go into pop up box
		JComponent[] inputs = new JComponent[numColors * 2];
		
		// Iterates through number of colors to create things and add them to inputs
		for (int i=0; i<numColors; i++)
		{
			// Creates new labels and combo boxes
			colorLabels[i] = new JLabel("Color " + (i + 1) + ":");
			colorBoxes.add(new JComboBox<String>(colorStrings));
			colorBoxes.get(i).setSelectedIndex(i);
			
			// Adds them to inputs
			inputs[2 * i] = colorLabels[i];
			inputs[2 * i + 1] = colorBoxes.get(i);
		}

		// Pop up box to prompt user for action
		String header = "Set Color Scheme";
		JOptionPane.showConfirmDialog(null, inputs, header, JOptionPane.PLAIN_MESSAGE);
		
		// If selected ok, sets color scheme according to user preferences
		Color[] newColorScheme = new Color[] {Color.BLACK};
		newColorScheme = new Color[numColors * numPoints];
		for (int i=0; i<numColors; i++)
			for (int j=0; j<numPoints; j++)
				newColorScheme[i * numPoints + j] = colorColors[colorBoxes.get(i).getSelectedIndex()];

		// Sets color scheme
		return newColorScheme;
	}

	/**
	 * Turns color boxes on/off during iteration.
	 * @param b
	 */
	public void turnOnOff(boolean b)
	{
		if (varyBox.getSelectedItem().equals("Constant"))
			colorBox.setEnabled(b);
		varyBox.setEnabled(b);
	}

	/**
	 * Sets default choices of color box, making constant color and setting default scheme
	 */
	public void setDefault()
	{
		turnOnOff(true);
		colorBox.setSelectedIndex(0);
		varyBox.setSelectedIndex(0);
		controller.setColorScheme("Default", baseColorScheme);
	}
}