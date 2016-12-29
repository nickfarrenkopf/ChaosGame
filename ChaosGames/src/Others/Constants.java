package Others;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

public class Constants {

	// Frame constants
	public static final String frameTitle = "Chaos Game";
	
	// Title page constants
	public static final Font largeFont = new Font(Font.SANS_SERIF, Font.BOLD, 25);
	public static final Font mediumFont = new Font(Font.SANS_SERIF, Font.PLAIN, 18);
	public static final Font smallFont = new Font(Font.SANS_SERIF, Font.PLAIN, 15);
	
	// Instructions page constants
	public static final int iPageSize = 6;
	public static final int iPageSpeed = 900;
	public static final int iPageMaxPoints = 15;
	public static final double[] iPageVertices = {0.7, 0.64, 0.9, 0.36, 1.1, 0.64};
	
	// Screen constants
	public static final double screenWeightX = 0.99;
	
	// GUI constants
	public static final int vertSpace = 10;
	public static final int labelMinX = 10;
	public static final int labelMinY = 10;
	public static final double gridLabel = 0.2;
	public static final double gridOther = 1 - gridLabel;
	public static final int insetSize = 4;
	public static final Insets insets = new Insets(insetSize / 2, insetSize, insetSize / 2, insetSize);

	// Shape box constants
	public static final String[] shapeNames = {"Line", "Triangle", "Quadrilateral", "Pentagon"};
	public static final int startingShape = 1;
	public static final int maxShapeSize = 3;
	public static final double[][] lineVertices = {{0.06, 0.5, 0.94, 0.5},
			 									 {0.06, 0.84, 0.94, 0.06}};
	public static final double[][] triangleVertices = {{0.1, 0.84, 0.5, 0.06, 0.9, 0.84},
													 {0.1, 0.1, 0.1, 0.84, 0.8, 0.84}};
	public static final double[][] quadVertices = {{0.15, 0.15, 0.15, 0.8, 0.8, 0.8, 0.8, 0.15}};
	public static final double[][] quintVertices = {{.5, .05, .9, .40, .8, .85, .2, .85, .1, .40}};													
	public static final double[][][] allVertices = {lineVertices, triangleVertices, quadVertices, quintVertices};

	// Point constants
	public static final int basePointSize = 3;
	public static final int pointsPerSecond = 256;
	public static final double minPointDistance = 0.25;
	
	// Iteration Box constants
	public static final int baseIter = 10000;
	public static final int basePointSpeed = 3;
	public static final double baseTravel = 1./2.;
	public static final int slideMin = 1;
	public static final int slideMax = 4;
	
	// Color Box constants
	public static final Color[] baseColorScheme = {Color.BLACK};
	public static final String[] colorStrings = {"Black", "Red", "Blue", "Green",  
		    "Orange", "Magenta", "Pink", "Psychadelia"};
	public static final Color[] colorColors = {Color.BLACK, Color.RED, Color.BLUE, Color.GREEN,  
			Color.ORANGE, Color.MAGENTA, Color.PINK, Color.WHITE};
	public static final int maxNumColors = colorColors.length - 1;
	public static final String[] varyChoices = {"Constant", "Random", "Every n", "Region", "Vertex"};
	
	
	
}