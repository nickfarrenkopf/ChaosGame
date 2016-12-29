package SidePanel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import ChaosGameStart.Controller;
import Components.RadioGroup;

import static Others.Constants.*;

/**
 * TutorialPage is a JPanel that houses all of the tutorial options.
 * The tutorials go through a variety of conditions that allow user to see what they can change.
 * This page has several JRadioButtons that select tutorials and a JButton to start and stop tutorials.
 * Tutorials are controlled via timer tasks and messages.
 */
@SuppressWarnings("serial")
public class TutorialPage extends JPanel implements ActionListener {

	// Controller variable
	private Controller controller;
	
	// Radio Group of tutorial buttons
	private RadioGroup allButtons;
	
	// Start stop button
	private JButton startButton;
	
	// Timer variable
	private Timer timer;
	
	/**
	 * Initializes the tutorial page, creating JRadioButtons and a JButton.
	 * Input parameter is a controller.
	 * @param c
	 */
	public TutorialPage(Controller c)
	{
		// Sets controller
		controller = c;
		
		// Sets text and initializes all buttons
		int numTutorials = 5;
		String[] buttonText = new String[numTutorials];
		buttonText[0] = "Tutorial 1 - Intro";
		buttonText[1] = "Tutorial 2 - Changing things up";
		buttonText[2] = "Tutorial 3 - Varying travel distance";
		buttonText[3] = "Tutorial 4 - New shapes";
		buttonText[4] = "Tutorial 5 - Psychedelia";
		allButtons = new RadioGroup(buttonText);
		
		// Start button
		startButton = new JButton("Start");
		startButton.addActionListener(this);
		
		// Sets layout of panel
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// Add to screen
		for (int i=0; i<allButtons.getSize(); i++)
			add(allButtons.get(i));
		add(startButton);
	}
	
	/**
	 * Houses code for all tutorials. Each tutorial is a collection of timers that 
	 * send messages to user and/or update iteration conditions at specified times
	 */
	public void runTutorial()
	{
		// Variables for ease of access
		ShapeBox sb = controller.getScreen().getSidePanel().getShapeBox();
		IterationBox ib = controller.getScreen().getSidePanel().getIterationBox();
		timer = new Timer();
		
		// Sets default conditions for chaos game
		controller.setDefaut();
		
		// Sets large max size so tutorial runs through all
		ib.setMaxIteration(baseIter * 10);
		
		// Sets conditions for first tutorial
		if (allButtons.getSelectedIndex() == 0)
		{
			controller.updatePointSpeed(2);
			timer.schedule(new SendMessage("A new point is added every iteration."), 10);
			timer.schedule(new SendMessage("Speed can be changed to hasten convergence."), 4000);
			timer.schedule(new UpdateSpeed(3), 6500);
			timer.schedule(new SendMessage("Very quickly, a structure begins to form."), 8000);
			timer.schedule(new SendMessage("This repeating structure within a structure is a fractal."), 12500);
			timer.schedule(new SendMessage("Next tutorial in 3..."), 17000);
			timer.schedule(new SendMessage("Next tutorial in 2..."), 18000);
			timer.schedule(new SendMessage("Next tutorial in 1..."), 19000);
			timer.schedule(new NextTutorial(), 19500);
			
		// Sets conditions for second tutorial
		} else if (allButtons.getSelectedIndex() == 1) {
			
			controller.updatePointSize(4);
			sb.setRadioIndex(1);
			Color[] colorScheme = new Color[] {Color.RED, Color.BLUE, Color.GREEN};
			controller.setColorScheme("Region", colorScheme);
			timer.schedule(new SendMessage("Things like the initial points, point size, and color scheme can be changed,"), 10);
			timer.schedule(new SendMessage("resulting in a plethora of distinct, colorful fractals."), 4000);
			timer.schedule(new SendMessage("Many initial conditions result in some form of fractal."), 8000);
			timer.schedule(new SendMessage("Next tutorial in 3..."), 13000);
			timer.schedule(new SendMessage("Next tutorial in 2..."), 14000);
			timer.schedule(new SendMessage("Next tutorial in 1..."), 15000);
			timer.schedule(new NextTutorial(), 15500);
			
		// Sets conditions for third tutorial
		} else if (allButtons.getSelectedIndex() == 2) {
			
			controller.updatePointSize(2);
			controller.updatePointSpeed(4);
			Color[] colorScheme = new Color[] {Color.RED, Color.BLUE, Color.GREEN};
			controller.setColorScheme("Region", colorScheme);
			ib.setPointTravelDistance(0.4);
			timer.schedule(new SendMessage("By changing the point's travel distance (i.e. not halfway), a new structure will form."), 10);
			timer.schedule(new SendMessage("This new structure may or may not be fractal in nature,"), 6000);
			timer.schedule(new SendMessage("but it will always result in something unique!"), 12000);
			timer.schedule(new SendMessage("Next tutorial in 3..."), 17000);
			timer.schedule(new SendMessage("Next tutorial in 2..."), 18000);
			timer.schedule(new SendMessage("Next tutorial in 1..."), 19000);
			timer.schedule(new NextTutorial(), 19500);
			
		// Sets conditions for fourth tutorial
		} else if (allButtons.getSelectedIndex() == 3) {
			
			sb.setBoxIndex(2);
			timer.schedule(new SendMessage("Here are the same conditions for a triangle, but with 4 vertices instead."), 10);
			timer.schedule(new SendMessage("Notice how adding one more vertex completely removes the fractal structure,"), 3500);
			timer.schedule(new SendMessage("resulting in a square of random points."), 7000);
			timer.schedule(new SendMessage("But if we add the restriction that the random vertex cannot be repeated,"), 9500);
			timer.schedule(new NextSquare(), 11500);
			timer.schedule(new SendMessage("we get a fractal!"), 14500);
			timer.schedule(new SendMessage("Similar restrictions for higher shapes result in unique fractals."), 18000);
			timer.schedule(new SendMessage("Next tutorial in 3..."), 22000);
			timer.schedule(new SendMessage("Next tutorial in 2..."), 23000);
			timer.schedule(new SendMessage("Next tutorial in 1..."), 24000);
			timer.schedule(new NextTutorial(), 24500);
		
		// Sets conditions for fifth tutorial
		} else if (allButtons.getSelectedIndex() == 4) {
		
			controller.updatePointSize(2);
			controller.updatePointSpeed(4);
			Color[] colorScheme = new Color[] {Color.WHITE};
			controller.setColorScheme("Constant", colorScheme);
			timer.schedule(new SendMessage("The 'Psychedelia' color option changes the color of every point every iteration."), 10);
			timer.schedule(new SendMessage("Allowing you to visualize how the program slows when more and more points are added."), 4000);
			timer.schedule(new SendMessage("Plus, it is quite mesmerizing."), 9000);
			timer.schedule(new SendMessage("That's the end of the tutorial. Go explore!"), 15000);
			timer.schedule(new StopIteration(), 18000);
		}
			
		// Run through iterations with given conditions		
		timer.schedule(new StartIteration(), 200);
	}

	/**
	 * Action listeners for tutorial page.
	 * Start button starts iterations, changes button text, and turns other pages off
	 * Stop button stops iterations, changes button text, and turns other pages on
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{	
		// If start button clicked
		if (e.getSource() == startButton)
		{
			// Button that turns tutorial on
			if (startButton.getText().equals("Start"))
			{
				startButton.setText("Stop");
				controller.getScreen().getSidePanel().turnTutorialOnOff(true);
				runTutorial();

			// Button that turns tutorial off
			} else {
				startButton.setText("Start");
				controller.getScreen().getSidePanel().turnTutorialOnOff(false);
				controller.stopIteration();
				timer.cancel();
			}
		}

		// Repaint screen
		controller.getScreen().repaint();
	}
	
	/**
	 * Timer task that allows tutorial to change speed of iteration
	 * Input parameter is integer
	 */
	class UpdateSpeed extends TimerTask {

		int speed = 2;
		public UpdateSpeed(int i)
		{
			speed = i;
		}
		
		@Override
		public void run()
		{
			controller.updatePointSpeed(speed);
		}
	}
	
	/**
	 * Timer task that starts current iteration that should prevent ConcurrentModificationException
	 */
	class StartIteration extends TimerTask {
		@Override
		public void run()
		{
			controller.startIteration();
		}
	}
	
	/**
	 * Timer task that ends the tutorial when over
	 */
	class StopIteration extends TimerTask {
		@Override
		public void run()
		{
			startButton.setText("Start");
			controller.getScreen().getSidePanel().turnTutorialOnOff(false);
			controller.stopIteration();
			timer.cancel();
			controller.setDefaut();
		}
	}
	
	/**
	 * Timer task that starts next tutorial if there is one
	 */
	class NextTutorial extends TimerTask {
		@Override
		public void run()
		{
			if (!(allButtons.getSelectedIndex() == allButtons.getSize() - 1))
			{
				allButtons.setSelected(allButtons.getSelectedIndex() + 1);
				runTutorial();
			}
		}
	}
	
	/**
	 * Timer task that allows tutorial to have two different iterations.
	 * Resets point list and adds new ones.
	 */
	class NextSquare extends TimerTask {
		@Override
		public void run()
		{
			controller.stopIteration();
			controller.clearPointList();
			controller.updatePointSpeed(4);
			controller.getScreen().getSidePanel().getRestrictionPage().setFirstRestriction();
			timer.schedule(new StartIteration(), 1000);
		}
	}
	
	/**
	 * Timer task that sets message to screen.
	 * Input parameter is String message.
	 */
	class SendMessage extends TimerTask {
		
		String message = "";
		public SendMessage(String s)
		{
			message = s;
		}
		
		@Override
		public void run() 
		{
			controller.getScreen().setMessage(message);
		}
	}
}