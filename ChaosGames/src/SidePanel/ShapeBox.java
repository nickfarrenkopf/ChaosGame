package SidePanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import ChaosGameStart.Controller;
import Components.RadioGroup;
import Others.Point;

import static Others.Constants.*;

/**
 * ShapeBox is a JPanel that houses components for deciding shape and vertices.
 * A JComboBox houses possible polygons
 * JRadioButtons are linked to initial conditions, with one button allowing user
 * to manually select vertices.
 */
@SuppressWarnings("serial")
public class ShapeBox extends JPanel implements ActionListener, MouseListener{

	// Controller variable
	private Controller controller;

	// Shape combo box and size variable
	private JComboBox<String> shapeBox;
	private int shapeSize;
	
	// Array list that houses manual vertices
	private ArrayList<Point> manualVertices;

	// Radio button variables 
	private RadioGroup radioAll;
	private int manualRadioIndex;

	/**
	 * Initializes the shape box which has a combo box deciding base polygon size and shape size
	 * (number of vertices) and radio buttons that allows user to select between predetermined 
	 * vertices or manual vertices 
	 * @param controller
	 */
	public ShapeBox (Controller controller) 
	{
		// Controller variable
		this.controller = controller;
		
		// Initialize other variables
		manualVertices = new ArrayList<Point>();
		
		// Initialize shape label and combo box
		JLabel shapeLabel = new JLabel("Shape:");
		shapeBox = new JComboBox<String>(shapeNames);
		
		// Initialize radio buttons and radio group
		radioAll = new RadioGroup();
		for (int i=0; i<3; i++)
			radioAll.addButton(new JRadioButton());
		
		// Action listeners (radio buttons added later)
		shapeBox.addActionListener(this);

		// Sets grid bag constraints layout
		GridBagLayout grid = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(grid);

		// Add shape JLabel to panel
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = gridLabel;
		c.weighty = 1;
		c.ipadx = labelMinX;
		c.ipady = labelMinY;
		c.insets = insets;
		add(shapeLabel,c);

		// Add shape ComboBox to panel
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = gridOther;
		add(shapeBox, c);

		// Add radio buttons to panel and action listener to radio buttons
		for (int i=0; i<radioAll.getSize(); i++)
		{
			c.gridx = 1;
			c.gridy = 1 + i;
			add(radioAll.get(i), c);
			radioAll.get(i).addActionListener(this);
		}
	}

	/**
	 * Sets text for radio button, putting manual as last button
	 * Turns off buttons not being used
	 */
	public void setRadioLabels()
	{
		// Turns usable buttons on and sets text
		int i = 0;
		for (; i<=allVertices[shapeSize - 2].length; i++)
		{
			radioAll.get(i).setEnabled(true);
			radioAll.get(i).setText("Default " + (i + 1));
			if (i == allVertices[shapeSize - 2].length)
			{
				radioAll.get(i).setText("Manual");
				manualRadioIndex = i;
			}
		}
		
		// Turns unused buttons off and erases label
		for ( ; i<radioAll.getSize(); i++)
		{
			radioAll.get(i).setEnabled(false);
			radioAll.get(i).setText("");
		}
	}
	
	/**
	 * Grabs vertices from initial constants or manual vertices, then updates vertices and 
	 * shape size to controller.
	 */
	public void DrawVertices() 
	{	
		// Create array to hold vertices and double to iterate over
		ArrayList<Point> vertices = new ArrayList<Point>();
		double[] d;
		
		// If not manual, grab points from constants
		if (radioAll.getSelectedIndex() < allVertices[shapeSize - 2].length)
		{
			d = allVertices[shapeSize - 2][radioAll.getSelectedIndex()];
			for (int i=0; i<d.length / 2; i++)
				vertices.add(new Point(d[i * 2], d[i * 2 + 1]));
			if (controller.getScreen() != null)
				controller.getScreen().setMessage("");
		}
		else 
			vertices.addAll(manualVertices);
		
		// Sets vertices and shape size on controller
		controller.setVertices(vertices);
	}	

	/**
	 * Action listeners for shape box. Things happen when shape box has changed value
	 * or when radio button is selected.
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// If shape box has changed, update size, radio buttons, and vertices
		if (e.getSource() == shapeBox)
		{
			// Change shape size
			shapeSize = shapeBox.getSelectedIndex() + 2;
			
			// Change labels of radio buttons
			radioAll.setSelected(0);
			setRadioLabels();
			
			// Update vertices
			DrawVertices();
		}
		
		// If radio button is clicked, update vertices accordingly
		if (e.getSource() instanceof JRadioButton)
		{
			radioAll.setSelected(radioAll.indexOf((JRadioButton) e.getSource()));
			if (radioAll.getSelected().getText().equals("Manual"))
			{
				// If screen does not have mouse listener, add one
				MouseListener[] mls = controller.getScreen().getMouseListeners();
				if (mls.length == 0)
					controller.getScreen().addMouseListener(this);
					
				// Clear manual vertices and prompt user for first vertex
				manualVertices.clear();
				controller.getScreen().setMessage("Select vertex 1 of " + shapeSize);
			}
			DrawVertices();
		}
	}
	
	/**
	 * Mouse listener for shape box. Prompts user to select certain vertex, then adds to manual vertices
	 * Checks that point is sufficiently far away from other points and not off screen.
	 */
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		if (radioAll.getSelected().getText().equals("Manual"))
		{
			// Grab minimum dimension so check points are on same scale
			double d = controller.getScreen().getMinDimension();
			
			// Checks if vertex is needed and that vertex is good vertex
			if (manualVertices.size() < shapeSize && checkPoint(e.getX() / d, e.getY() / d))
			{
				manualVertices.add(new Point(e.getX() / d, e.getY() / d));
				DrawVertices();
				
				// Update message to user
				controller.getScreen().setMessage("Select vertex " + (manualVertices.size() + 1) + " of " + shapeSize);
			}
			
			// If all vertices have been selected, output done
			if (manualVertices.size() == shapeSize)
				controller.getScreen().setMessage("Done");
		}
	}
	
	/**
	 * Checks that new vertex is sufficiently far away from other vertices and on screen.
	 * Input parameters are double.
	 * @param xNew
	 * @param yNew
	 * @return boolean
	 */
	public boolean checkPoint(double xNew, double yNew)
	{
		// Get width of point frame and side panel
		double frameWidth = controller.getFrame().getWidth();
		double sidePanelWidth = controller.getScreen().getSidePanel().getWidth();
		
		// Check that on point panel
		if (xNew > frameWidth - sidePanelWidth)
			return false;
		
		// Check if ok distance from previous points
		for (Point sp:manualVertices)
			if (sp.getDistance(xNew, yNew) < minPointDistance)
				return false;
		
		// No exception checks, so return true
		return true;
	}
	
	/**
	 * Method that allows tutorial to change selected shape.
	 * Input parameter is integer.
	 * @param i
	 */
	public void setBoxIndex(int i)
	{
		shapeBox.setSelectedIndex(i);
		DrawVertices();
	}
	
	/**
	 * Method that allows tutorial to change selected vertex combination.
	 * Input parameter is integer.
	 * @param i
	 */
	public void setRadioIndex(int i)
	{
		radioAll.setSelected(i);
		DrawVertices();
	}

	/**
	 * Turns certain components on/off when program is running.
	 * Takes boolean.
	 * @param b
	 */
	public void turnOnOff(boolean b)
	{
		shapeBox.setEnabled(b);
		for (int i=0; i<manualRadioIndex; i++)
			radioAll.get(i).setEnabled(b);
	}

	/**
	 * Sets default conditions of shape box. Turns everything on and selects initial values.
	 * Then updates vertices.
	 */
	public void setDefault()
	{
		turnOnOff(true);
		shapeBox.setSelectedIndex(startingShape);
		radioAll.setSelected(0);
		DrawVertices();
	}

	// Not needed mouse listener methods
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}