package Others;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.Random;
import javax.swing.JComponent;
import static Others.Constants.*;

/**
 * cPoint is a JComponent for a ellipse on screen. 
 * Each point has an x coordinate, y coordinate, size, and color.
 * I called it cPoint because Point is already taken, so calling it that adds
 * this awkward little plus sign on the class. Nothing else has that plus, so no plus point. 
 */
@SuppressWarnings("serial")
public class Point extends JComponent 
{
	// Variables
	private int size;
	private double x;
	private double y;
	private Color color;
	
	/**
	 * Initialize a point at given x and y position, with base size and color
	 * @param x
	 * @param y
	 */
	public Point(double x, double y)
	{
		this.x = x;
		this.y = y;
		size = basePointSize * 2;
		color = Color.BLACK;
	}
	
	/**
	 * Initialize a point given x and y position, size and color values
	 * @param x
	 * @param y
	 * @param size
	 * @param color
	 */
	public Point(double x, double y, int size, Color color)
	{
		this.size = size;
		this.x = x;
		this.y = y;
		this.color = color;
	}

	/**
	 * Return the x coordinate as a double instead of an integer
	 * @return double
	 */
	public double getXDouble()
	{
		return x;
	}
	
	/**
	 * Returns the y coordinate as a double instead of an integer
	 * @return double
	 */
	public double getYDouble()
	{
		return y;
	}

	/**
	 * Allows controller to access color of point for vertex things
	 * @return Color
	 */
	public Color getColor()
	{
		return color;
	}
	
	/**
	 * Allows controller to set color of this point
	 * @param color
	 */
	public void setColor(Color color)
	{
		this.color = color;
	}

	/**
	 * Returns distance from point given x and y value of new point
	 * @param xNew
	 * @param yNew
	 * @return
	 */
	public double getDistance(double xNew, double yNew)
	{
		return Math.sqrt(Math.pow(this.x - xNew, 2) + Math.pow(this.y - yNew, 2));
	}

	/**
	 * Draws point, fit to screen, at x, y, and with current color.
	 * If color is white, then goes with random Psychidelia option
	 * @param screenSize
	 * @param g
	 */
	public void draw(double screenSize, Graphics g)
	{
		// Sets point color
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(color);
		
		// If "Psychedelia" option, randomize color
		Random rand = new Random();
		if (Color.WHITE.equals(color))
			g.setColor(colorColors[rand.nextInt(maxNumColors)]);
		
		// Draw point with specified color relative to screen size
		g2.fill(new Ellipse2D.Double(x * screenSize, y * screenSize, size, size));
	}
}