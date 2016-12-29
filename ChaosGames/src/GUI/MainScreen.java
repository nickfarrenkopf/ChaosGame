package GUI;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import ChaosGameStart.Controller;
import Others.Point;
import static Others.Constants.*;

/**
 * MainScreen is JPanel that houses the main program screen for Chaos Game. Screen contains a point panel that 
 * shows points and message to user while the side panel houses implementation.
 */
@SuppressWarnings("serial")
public class MainScreen extends JPanel
{	
	// Controller variable
	private Controller controller;
	
	// Side panel object
	private SidePanel sidePanel;
	
	// String message to user
	private String message = "";
	
	/**
	 * Creates screen object that houses point panel and side panel. Adds border for esthetic appeal.
	 * Point panel houses points of chaos game and string message to user.
	 * Side panel houses various panels that control shape, iteration, color, and implementation.  
	 * @param controller
	 */
	public MainScreen(Controller controller)
	{
		// Sets controller
		this.controller = controller;

		// Initialize point panel (where points are viewed) and side panel
		JPanel pointPanel = new JPanel();
		sidePanel = new SidePanel(controller);

		// Sets border for panels
		Border lineBorder = BorderFactory.createStrokeBorder(new BasicStroke(2.0f));
		pointPanel.setBorder(lineBorder);
		sidePanel.setBorder(lineBorder);

		// Sets layout and grid variables
		GridBagLayout grid = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(grid);

		// Side Panel constraints
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1 - screenWeightX;
		c.weighty = 1;
		add(sidePanel, c);

		// Screen constraints
		c.gridx = 0;
		c.weightx = screenWeightX;
		add(pointPanel, c);
	}

	/**
	 * Allows controller to set string message to user.
	 * @param s
	 */
	public void setMessage(String s)
	{
		message = s;
	}

	/**
	 * Returns smallest dimension of point screen (height or width, ignoring side panel) so program knows  
	 * how to maximize size of points without hiding any.
	 * @return double
	 */
	public double getMinDimension()
	{
		// Calculates dimensions of screen width and height
		double screenHeight = controller.getFrame().getHeight();
		double screenWidth = controller.getFrame().getWidth() - sidePanel.getWidth();
		
		// Returns smaller
		if (screenHeight < screenWidth)
			return screenHeight;
		else
			return screenWidth;
	}
	
	/**
	 * Gets side panel object so controller can change certain components
	 * @return SidePanel
	 */
	public SidePanel getSidePanel()
	{
		return sidePanel;
	}

	/**
	 * Draws components to screen. Draws all points in point list and message to user.
	 * After drawing, tells controller to iterate if program is currently running.
	 * This implementation (sometimes) prevents ConcurrentEnrollmentException, which was annoying to get rid of.
	 * Other options were more costly and slowed down program significantly.
	 * ConcurrentEnrollmentException is not program breaking, so almost fix is good enough.
	 */
	@Override
	public void paint(Graphics g)
	{
		// Draw everything else
		super.paint(g);

		// Draw all points on point list
		for (Point points:controller.getPoints())
			points.draw(getMinDimension(), g);
		controller.Iterate();
		
		// Draw message to user
		g.setColor(Color.BLACK);
		g.setFont(smallFont);
		g.drawString(message, 20, 20);
	}
}