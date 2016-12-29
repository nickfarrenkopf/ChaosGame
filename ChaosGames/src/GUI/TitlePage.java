package GUI;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ChaosGameStart.Controller;

import static Others.Constants.*;

/**
 * TitlePage is a JPanel that initialize a title page for Chaos Game program. Title page contains
 * title info, author info, a start buttons, instructions button, tutorial button, and exit button.
 */
@SuppressWarnings("serial")
class TitlePage extends JPanel implements ActionListener {
	
	// Controller variable
	private Controller controller;
	
	// All buttons
	private JButton startButton;
	private JButton iButton;
	private JButton tutorialButton;
	private JButton exitButton;

	/**
	 * Initializes title screen with title, author, start button, instructions button, and exit button.
	 * @param controller
	 */
	public TitlePage(Controller controller)
	{
		// Sets controller
		this.controller = controller;

		// Sets title and author label
		JLabel titleLabel = new JLabel("Chaos Game");
		JLabel nameLabel = new JLabel("by Nick Farrenkopf");
		
		// Sets font for labels
		titleLabel.setFont(largeFont);
		nameLabel.setFont(mediumFont);

		// Initialize buttons
		startButton = new JButton("Start");
		iButton = new JButton("Instructions");
		tutorialButton = new JButton("Tutorial");
		exitButton = new JButton("Exit");
		
		// Adds action listeners to buttons
		startButton.addActionListener(this);
		iButton.addActionListener(this);
		tutorialButton.addActionListener(this);
		exitButton.addActionListener(this);

		// Sets panel layout
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// Center all components on screen
		titleLabel.setAlignmentX(CENTER_ALIGNMENT);
		nameLabel.setAlignmentX(CENTER_ALIGNMENT);
		startButton.setAlignmentX(CENTER_ALIGNMENT);
		iButton.setAlignmentX(CENTER_ALIGNMENT);
		tutorialButton.setAlignmentX(CENTER_ALIGNMENT);
		exitButton.setAlignmentX(CENTER_ALIGNMENT);

		// Adds to panel, adding separation for esthetic appeal
		add(Box.createVerticalGlue());
		add(titleLabel);
		add(nameLabel);
		add(Box.createRigidArea(new Dimension(0, vertSpace * 2)));
		add(startButton);
		add(Box.createRigidArea(new Dimension(0, vertSpace)));
		add(iButton);
		add(Box.createRigidArea(new Dimension(0, vertSpace)));
		add(tutorialButton);
		add(Box.createRigidArea(new Dimension(0, vertSpace)));
		add(exitButton);
		add(Box.createVerticalGlue());
	}

	/**
	 * Code for when buttons are clicked. Start button initializes program, instructions button
	 * brings up instructions page, and exit button exits program
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// Remove title page from screen
		controller.getFrame().remove(this);
		
		// If start button clicked, remove title screen and initialize program screen
		if (e.getSource() == startButton)
			controller.InitializeScreen();
		
		// If instructions button clicked, remove title screen and initialize instructions panel
		if (e.getSource() == iButton)
			controller.InstructionsPage();

		// If tutorial button clicked, run tutorial
		if (e.getSource() == tutorialButton)
			controller.startTutorial();

		// If exit button clicked, exit program
		if (e.getSource() == exitButton)
			controller.getFrame().Exit();
	}
}