package GUI;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import ChaosGameStart.Controller;
import SidePanel.ButtonBox;
import SidePanel.ColorBox;
import SidePanel.IterationBox;
import SidePanel.RestrictionPage;
import SidePanel.ShapeBox;
import SidePanel.TutorialPage;

/**
 * SidePanel is a JTabbedPane that houses all pages that control Chaos game iteration.
 * Iteration Page (main pane) contains the following
 *  - ShapeBox controls shape choice and vertices
 *  - IterationBox controls variables for iteration and updates current iterations label
 *  - ColorBox controls color of points
 *  - ButtonBox controls run, reset, and exit buttons 
 *  Restriction page lets user select restrictions for iteration
 *  Tutorial Page houses tutorials
 */
@SuppressWarnings("serial")
public class SidePanel extends JTabbedPane {

	// Box variables
	private ShapeBox shapeBox;
	private IterationBox iterBox;
	private ColorBox colorBox;
	private ButtonBox buttonBox;
	
	// Creates iteration page, restriction page, and tutorial page
	private JPanel ip;
	private RestrictionPage rp;
	private TutorialPage tp;
	
	/**
	 * Initializes side panel full of boxes that control program iteration.
	 * @param controller
	 */
	public SidePanel(Controller controller)
	{
		// Initializes boxes
		shapeBox = new ShapeBox(controller);
		iterBox = new IterationBox(controller);
		colorBox = new ColorBox(controller);
		buttonBox = new ButtonBox(controller);

		// Adds to boxes to iteration page
		ip = new JPanel();
		ip.setLayout(new BoxLayout(ip, BoxLayout.Y_AXIS));
		ip.add(shapeBox);
		ip.add(iterBox);
		ip.add(colorBox);
		ip.add(buttonBox);

		// Initializes other pages
		rp = new RestrictionPage(controller);
		tp = new TutorialPage(controller);
		
		// Adds everything to panel
		add(ip, "Iterations");
		add(rp, "Restrictions");
		add(tp, "Tutorials");
		
		// Sets default values
		setDefault();
	}
	
	/**
	 * Returns shape box so tutorial can change initial conditions
	 * @return ShapeBox
	 */
	public ShapeBox getShapeBox()
	{
		return shapeBox;
	}

	/**
	 * Returns iteration box so controller can update current points label
	 * @return IterationBox
	 */
	public IterationBox getIterationBox()
	{
		return iterBox;
	}
	
	/**
	 * Gets buttons box so controller can change button text if done running
	 * @return ButtonBox
	 */
	public ButtonBox getButtonBox()
	{
		return buttonBox;
	}
	
	/**
	 * Gets restriction page so tutorial can set restrictions
	 * @return RestrictionPage
	 */
	public RestrictionPage getRestrictionPage()
	{
		return rp;
	}
	
	/**
	 * Gets tutorial page so controller can start tutorial
	 * @return RestrictionPage
	 */
	public TutorialPage getTutorialPage()
	{
		return tp;
	}
	
	/**
	 * Turns off other pages while tutorial is running
	 * @param boolean
	 */
	public void turnTutorialOnOff(boolean b)
	{
		setSelectedIndex(2);
		setEnabledAt(0, !b);
		setEnabledAt(1, !b);
	}
	
	/**
	 * Turns all boxes on/off.
	 * Input parameter is boolean.
	 * @param boolean
	 */
	public void turnOnOff(boolean b)
	{
		shapeBox.turnOnOff(b);
		iterBox.turnOnOff(b);
		colorBox.turnOnOff(b);
	}
	
	/**
	 * Sets defaults for all boxes in side panel.
	 */
	public void setDefault()
	{
		shapeBox.setDefault();
		iterBox.setDefault();
		colorBox.setDefault();
		buttonBox.setDefault();
		rp.setDefault();
	}
}