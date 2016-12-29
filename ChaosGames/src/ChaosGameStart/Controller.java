package ChaosGameStart;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import GUI.Frame;
import GUI.InstructionsPage;
import GUI.MainScreen;
import Others.Point;
import static Others.Constants.*;

/**
 * Controller houses main method and controller for program controlling.
 * Main method is initialization needed to start any java program.
 * Controller object houses all methods used to work program.
 * This section is split into sections that group types of functions. 
 * Initialization - contains constructor and initialization methods 
 * Point - adding point methods
 * Iteration - iteration start and stop methods
 * SidePanel - side panel control
 * IterationBox - get/set some iteration box values
 * ButtonBox - for button clicking
 * Getters - allows other classes to access certain controller variables
 * @author nickf
 */
public class Controller {

	/**
	 * 
	 * Main method that starts program. 
	 * 
	 */
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Controller controller = new Controller();	
	}
	/**
	 * 
	 * Main method that starts program.
	 * 
	 */
	
	// GUI variables
	private Frame frame;
	private InstructionsPage iPage;
	private MainScreen screen;

	// Point variables
	private ArrayList<Point> pointList;

	// Shape size variable that tells certain methods when to stop looping
	private int shapeSize;
	
	// Point creation variables
	private int pointSize;
	private int pointSpeed;
	private double travelDistance;
	
	// Variables to hold vertex history, possible vertices, and number of restrictions
	private LinkedList<Integer> vertexHistory = new LinkedList<>();
	private HashMap<LinkedList<Integer>, int[]> possibles = new HashMap<>();
	private int numRes = 0;
	
	// Variables that tell when to stop iterating
	private int counter;
	private int maxIteration;
	
	// Timer for iterating
	private Timer timer = null;
	
	// String to hold scheme type and color array to hold color scheme
	private String colorType;
	private Color[] colorScheme;
	private int colorCount;
	
	// Boolean variable to check if program should be running
	private boolean isRunning;
	
	///// Initialization /////
	/** 
	 * Initialization of controller variable, which initializes frame variable
	 */
	public Controller()
	{
		frame = new Frame(this);
	}
		
	/**
	 * Initializes the instructions page, adding to frame.
	 */
	public void InstructionsPage() 
	{
		// Set initial run conditions for instructions page
		isRunning = true;
		travelDistance = baseTravel;
		pointSize = iPageSize;
		pointSpeed = iPageSpeed;
		counter = 0;
		maxIteration = iPageMaxPoints * 10;
		colorType = "";
		colorScheme = new Color[] {Color.BLACK};
		
		// Create new page and add it to frame
		iPage = new InstructionsPage(this);
		frame.add(iPage);
		frame.revalidate();
		
		// Set timer to iterate at fixed speed for instructions page
		timer = new Timer();
		timer.schedule(new Iterate(), 0, pointSpeed);
	}
	
	/**
	 * Initializes main screen variable and adds to frame.
	 */
	public void InitializeScreen()
	{
		// Turns instructions page iterations and old timer off
		isRunning = false; 
		if (timer != null)
			timer.cancel();
		
		// Create Screen to hold components
		screen = new MainScreen(this);
		
		// Sets restrictions
		screen.getSidePanel().getRestrictionPage().setComboBoxes(shapeSize);

		// Add to frame and update 
		frame.add(screen);
		frame.revalidate();
	}
	
	/**
	 * Initializes main screen and sets tutorial page first.
	 */
	public void startTutorial()
	{
		// Initialize screen
		InitializeScreen();
		screen.getSidePanel().setSelectedIndex(2);
		screen.setMessage("Inital vertices are a triangle. Click start to begin!");
	}
	
	
	///// POINT METHODS /////
	/**
	 * Creates a new point according to specifications.
	 * If pointList contains only vertex, adds random point on screen.
	 * If pointList has points, adds new point at a specified distance between
	 * last point and randomly chosen vertex
	 */
	public void newPoint()
	{
		// Variables to hold x, y, and color of new point
		double xNew, yNew;
		Random rand = new Random();
		
		// If first point, add random point
		if (pointList.size() == shapeSize)
		{
			xNew = rand.nextDouble();
			yNew = rand.nextDouble();
		}

		// If not first point, go halfway in between one of the vertices
		else
		{

			// Keep vertex history small
			if (screen != null)
			if (vertexHistory.size() > screen.getSidePanel().getRestrictionPage().getNumRestrictions())
				vertexHistory.removeFirst();

			// If no restrictions, add random, otherwise follow restrictions
			if (possibles.size() * vertexHistory.size() == 0 || vertexHistory.size() < numRes)
				vertexHistory.add(rand.nextInt(shapeSize));
			else
			{
				int[] choices = possibles.get(vertexHistory);
				vertexHistory.add(choices[rand.nextInt(choices.length)]);
			}
			
			// Easy read for new points
			Point p1 = pointList.get(pointList.size() - 1);
			Point v1 = pointList.get(vertexHistory.getLast());
			
			// Calculate new x and y values, then add new point
			xNew = p1.getXDouble() * (1 - travelDistance) + v1.getXDouble() * travelDistance;
			yNew = p1.getYDouble() * (1 - travelDistance) + v1.getYDouble() * travelDistance;
		}

		// Adds new point at specified x, y, and color
		pointList.add(new Point(xNew, yNew, pointSize, getNextColor(xNew, yNew)));
		
		// Iterate until pass max iteration, then reset button box
		counter++;
		if (counter >= maxIteration)
		{
			stopIteration();
			if (screen.getSidePanel().getSelectedIndex() == 2)
				screen.setMessage("Finished! Select new tutorial or explore the program yourself!");
			else
				screen.setMessage("Finished!");
		}
	}
	
	/**
	 * Gets color of next new point, dependent on type color wanted. 
	 * Options are closest vertex, closest region, random, constant, or every n.
	 * Vertex, region, and random all have specific color schemes
	 * While constant and every n have default color schemes.
	 * @param xNew
	 * @param yNew
	 * @return
	 */
	public Color getNextColor(double xNew, double yNew)
	{
		Color color = Color.BLACK;

		// Grabs color if type is nearest vertex
		if (colorType.equals("Vertex"))
			color = closetVertexColor(xNew, yNew);
		
		// Grabs color if type is vertex region
		else if (colorType.equals("Region")) {
			if (pointList.size() != shapeSize)
				color = pointList.get(vertexHistory.getLast()).getColor();
			
		// Random color choice
		} else if (colorType.equals("Random"))
			color = colorColors[new Random().nextInt(maxNumColors)];
		
		// Default method of creating color scheme
		else {
			if (colorCount >= colorScheme.length)
				colorCount = 0;
			color = colorScheme[colorCount];
			colorCount++;
		}
		return color;
	}
	
	/**
	 * Returns the color of the closet vertex, given x and y of point and list of vertices
	 * @param x
	 * @param y
	 * @param vertices
	 * @return Color
	 */
	public Color closetVertexColor(double x, double y)
	{
		// Grabs vertices to determine closest vertex
		ArrayList<Point> vertices = new ArrayList<Point>();
		for (int i=0; i<shapeSize; i++)
			vertices.add(pointList.get(i));

		// Iterate over vertices until minimum is found
		int min = 0;
		for (int i=1; i<vertices.size(); i++)
			if (vertices.get(i).getDistance(x, y) < vertices.get(min).getDistance(x, y))
			{
				min = i;
				i = 1;
			}
		return vertices.get(min).getColor();
	}

	/**
	 * Sets the vertices and shape size for current iteration, controlled by shape box.
	 * Input parameters are ArrayList<Point> and integer
	 * @param vertices
	 * @param shapeSize
	 */
	public void setVertices(ArrayList<Point> vertices)
	{
		// Updates point list
		pointList = new ArrayList<Point>();
		for (Point sp:vertices)
			pointList.add(sp);

		// Updates shape size
		shapeSize = vertices.size();
		
		// Screen is null when program first starts, so need a check
		if (screen != null)
		{
			screen.getSidePanel().getRestrictionPage().setComboBoxes(shapeSize);
			setColorScheme(colorType, colorScheme);
			screen.repaint();
		}
	}
	

	///// ITERATION METHODS /////
	/**
	 * What other panels access to iterate points after drawing original points.
	 * This type of implementation prevents ConcurrentEnrollmentException without being too taxing
	 */
	public void Iterate()
	{
		if (isRunning)
			timer.schedule(new Iterate(), pointSpeed);
	}
	
	/**
	 * Start iterating current set of points. 
	 * On main screen, sets a counter for staying under max number of iterations,
	 * grabs travel distance, turns some side panel functions off, and repaints screen
	 * (thereby calling iterate timer task) 
	 */
	public void startIteration() 
	{	
		// Turn iterations on
		isRunning = true;
		
		// Initialize timer
		timer = new Timer();

		// Sets value for max iteration counter so know when to stop
		counter = 0;
		maxIteration = screen.getSidePanel().getIterationBox().getMaxValue();

		// Sets travel distance to determine where new point is placed
		travelDistance = screen.getSidePanel().getIterationBox().getTravelDistance();
		
		// Grab restrictions
		possibles = screen.getSidePanel().getRestrictionPage().nextVertexIndex(shapeSize);
		numRes = screen.getSidePanel().getRestrictionPage().getNumRestrictions();

		// Disable some features while iterating
		screen.getSidePanel().turnOnOff(false);

		// Calls screen repaint which starts iterations
		screen.repaint();
	}

	/**
	 * Stops current iterations and turns side panel functions on
	 */
	public void stopIteration() 
	{
		// Turn iterations off
		isRunning = false;
		
		// Enable features that were turned off during iteration
		screen.getSidePanel().turnOnOff(true);
		screen.getSidePanel().getButtonBox().setDefault();
	}

	/** 
	 * Timer task for iterations, repeating at specified speed interval.
	 * Creates 1 - many new points to increase speed, then updates current iterations label
	 * Repaints screen to call new iteration timer task
	 * If not on main screen, clears point list after maximum points reached
	 */
	class Iterate extends TimerTask {
		public void run() {

			// Add point
			for (int i=0; i<Math.floor(100/pointSpeed) + 1; i++)
				if (isRunning)
					newPoint();

			// Extra functions for main screen
			if (screen != null)
			{
				// Update current iterations point label
				screen.getSidePanel().getIterationBox().updateCurrentLabel(pointList.size() - shapeSize);

				// Update screen
				screen.repaint();
				
			// Extra functions for instructions page
			} else {
				if (pointList.size() >= iPageMaxPoints)
					clearPointList();
				
				// Update page
				iPage.repaint();
			}
		}
	}
	
	
	///// SIDE PANEL //////
	/**
	 * Sets default for side panel boxes and controller
	 */
	public void setDefaut()
	{
		stopIteration();
		screen.getSidePanel().setDefault();
	}

	
	///// Iteration Box /////
	/**
	 * Updates point size from iteration box that controls how big points are
	 * This allows change during iteration
	 * Input parameter is integer
	 * @param i
	 */
	public void updatePointSize(int i)
	{
		pointSize = i;
	}
	
	/**
	 * Updates point speed from iteration box that controls how fast points added to screen
	 * This allows change during iteration
	 * Input parameter is integer
	 * @param i
	 */
	public void updatePointSpeed(int i)
	{
		pointSpeed = (int) (pointsPerSecond / Math.pow(i, i));
	}
	
	
	///// Color Box /////
	public void setColorScheme(String type, Color[] scheme)
	{
		// Sets variables
		colorType = type;
		colorScheme = scheme;

		// If there are less that shape size vertices
		int size = shapeSize;
		if (pointList.size() <= shapeSize)
			size = pointList.size();
		
		// Resets vertex colors
		for (int i=0; i<size; i++)
			if (colorScheme.length == 1)
				pointList.get(i).setColor(colorScheme[0]);
			else if (colorScheme.length == shapeSize)
				pointList.get(i).setColor(colorScheme[i]);
			else
				pointList.get(i).setColor(Color.BLACK);
		
		// Repaint screen
		if (screen != null)
			screen.repaint();
	}
	
	
	///// Button Box /////
	/**
	 * Clears current point list and resets to vertices
	 */
	public void clearPointList() 
	{
		// New array list to hold vertices
		ArrayList<Point> newPoints = new ArrayList<Point>();
		for (int i=0; i<shapeSize; i++)
			newPoints.add(pointList.get(i));
		
		// Clear old and add vertices
		pointList.clear();
		pointList.addAll(newPoints);
		
		// Refresh screen and current point list
		if (screen != null)
		{
			screen.repaint();
			screen.getSidePanel().getIterationBox().updateCurrentLabel(pointList.size() - shapeSize);
		}
	}

	
	///// GETTERS /////
	/**
	 * Returns frame for lots of uses
	 * @return Frame
	 */
	public Frame getFrame()
	{
		return frame;
	}
	
	/**
	 * Returns screen so can access size to output points
	 * @return Screen
	 */
	public MainScreen getScreen()
	{
		return screen;
	}
	
	/** 
	 * Returns point list to panel so it can draw all of the points
	 * @return ArrayList<Point> 
	 */
	public ArrayList<Point> getPoints()
	{
		return pointList;
	}
	
	/**
	 * Return shape size so color box knows how many vertices
	 * @return int
	 */
	public int getShapeSize()
	{
		return shapeSize;
	}
}