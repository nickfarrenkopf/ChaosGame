package GUI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ChaosGameStart.Controller;
import Others.Point;
import static Others.Constants.*;

/**
 * InstructionsPage is a JPanel that houses instructions for what the Chaos Game program does.
 * It has a few what-to-do message and an example iterating fractal, sized to fit this screen.
 */
@SuppressWarnings("serial")
public class InstructionsPage extends JPanel implements ActionListener {

	// Controller variable
	private Controller controller;
	
	// Button that starts program or goes to tutorial
	private JButton beginButton;
	private JButton tutorialButton;

	/**
	 * Houses instructions in messages printed to screen. Also houses iterating fractal with
	 * tweaked initial conditions for smaller size 
	 * @param controller
	 */
	public InstructionsPage(Controller controller)
	{
		// Controller variable
		this.controller = controller;
		
		// String header
		JLabel header = new JLabel("Hello! Welcome to the Chaos Game!");
		
		// Message after header and before example fractal
		ArrayList<String> openingMessage = new ArrayList<String>();
		openingMessage.add("Chaos Game is one mathematical method of fractal creation.");
		openingMessage.add("Start with the vertices of a polygon (example triangle below).");
		openingMessage.add("Choose a random point as the starting point.");
		openingMessage.add("For each iteration, we add a new point halfway between the last point and a randomly chosen vertex.");
		
		// Instructions page initial condition 
		ArrayList<Point> vertices = new ArrayList<Point>();
		for (int i=0; i<iPageVertices.length / 2; i++)
			vertices.add(new Point(iPageVertices[2 * i], iPageVertices[2 * i + 1], iPageSize + 2, Color.BLACK));
		controller.setVertices(vertices);
		
		// Message after fractal
		ArrayList<String> closingMessage = new ArrayList<String>();
		closingMessage.add("Under many conditions, a fractal will form!");
		closingMessage.add("This program allows you to tweak certain conditions of the fractal creation.");
		closingMessage.add("You are able to change things like the initial polygon, iteration speed, or new point placement.");
		closingMessage.add("Head to the 'Tutorial' if you want to see examples in action,");
		closingMessage.add("or click 'Begin' to jump straight into the program.");
		closingMessage.add("Have fun!");
		
		// Intiailizes buttons
		beginButton = new JButton("Begin");
		tutorialButton = new JButton("Tutorial");
		
		// ADds action listeners
		beginButton.addActionListener(this);
		tutorialButton.addActionListener(this);
		
		// Sets layout of panel
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// Sets alignment
		header.setAlignmentX(CENTER_ALIGNMENT);
		beginButton.setAlignmentX(CENTER_ALIGNMENT);
		tutorialButton.setAlignmentX(CENTER_ALIGNMENT);
		
		// Add header to panel
		header.setFont(largeFont);
		add(header);
		add(Box.createRigidArea(new Dimension(0, vertSpace * 3)));
		
		// Opening messages
		for(int i=0; i<openingMessage.size(); i++)
		{
			JLabel label = new JLabel(openingMessage.get(i));
			label.setAlignmentX(CENTER_ALIGNMENT);
			label.setFont(mediumFont);
			add(label);
		}
		add(Box.createRigidArea(new Dimension(0, vertSpace * 20)));
		
		// Closing messages
		for(int i=0; i<closingMessage.size(); i++)
		{
			JLabel label = new JLabel(closingMessage.get(i));
			label.setAlignmentX(CENTER_ALIGNMENT);
			label.setFont(mediumFont);
			add(label);
		}
		add(Box.createRigidArea(new Dimension(0, vertSpace)));
		
		// Add buttons to panel
		add(beginButton);
		add(Box.createRigidArea(new Dimension(0, vertSpace)));
		add(tutorialButton);
	}

	/**
	 * Action listeners for button. If button is clicked, then main program gets initialized.
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// Remove this from screen
		controller.getFrame().remove(this);
		
		// If begin button is pressed, initialize program
		if (e.getSource() == beginButton)
			controller.InitializeScreen();
		
		// If tutorial button clicked
		if (e.getSource() == tutorialButton)
			controller.startTutorial();
	}
	
	/**
	 * Code for drawing iteration points to instructions page
	 */
	public void paint(Graphics g)
	{
		// Draw everything else
		super.paint(g);

		// Draw all points on point list
		for (Point points:controller.getPoints())
			points.draw(500, g);
	}	
}