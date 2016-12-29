package SidePanel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ChaosGameStart.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * RestrictionPage allows user to set restrictions for iteration.
 * Restrictions limit what the next vertex can be (i.e. no repeating vertex).
 * Available vertices are decided using a linked list housing old vertex choices, 
 * sending it into a hash map, and returning all vertices that can be randomly chosen.
 * Page contains JComboBoxes for user to select what restrictions are possible.
 */
@SuppressWarnings("serial")
public class RestrictionPage extends JPanel {

	// Controller variable
	private Controller controller;
	
	// Array list to hold j combo boxes
	private ArrayList<JComboBox<String>> choiceBoxes;
	
	// Creates restriction page that houses iteration restrictions
	public RestrictionPage(Controller c)
	{
		// Sets controller 
		controller = c;
		
		// Initializes combo boxes
		choiceBoxes = new ArrayList<>();
		String[] res = new String[] {"No restriction"};
		for (int i=0; i<2; i++)
			choiceBoxes.add(new JComboBox<>(res));
		setComboBoxes(c.getShapeSize());
		choiceBoxes.get(1).setEnabled(false);
		
		// Initializes labels for text screen
		String[] labels = new String[] {"Last", "Second to last"};
		
		// Sets layout
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// Adds things to panel
		add(new JLabel("When choosing new vertex..."));
		add(new JLabel(" "));

		// Iterates through labels and boxes, adding to screen
		for (int i=0; i<choiceBoxes.size(); i++)
		{
			add(new JLabel(labels[i] + " vertex cannot be "));
			add(choiceBoxes.get(i));
			add(new JLabel("from new vertex"));
			add(new JLabel(" "));
		}
		add(Box.createVerticalGlue());
	}
	
	/**
	 * Sets choices for combo boxes given shape size
	 * @param shapeSize
	 */
	public void setComboBoxes(int shapeSize)
	{
		// Gets string of choices
		String[] choices = new String[shapeSize + 1];
		choices[0] = "No restriction";
		for (int i=0; i<shapeSize; i++)
			choices[i + 1] = i + "";

		// Adds choices to boxes
		for (int i=0; i<choiceBoxes.size(); i++)
		{
			choiceBoxes.get(i).removeAllItems();
			for (int j=0; j<choices.length; j++)
				choiceBoxes.get(i).addItem(choices[j]);
		}
	}

	/**
	 * Returns a hash map of possible vertex choices for specified vertex history.
	 * Takes parameter shape size to determine how vertices there are.
	 * Returns HashMap<LinkedList, int[]> that takes vertex history of linked list and gives
	 * array of integers that are ok vertex choices.
	 * @param shapeSize
	 * @return int[]
	 */
	public HashMap<LinkedList<Integer>, int[]> nextVertexIndex(int shapeSize)
	{
		// List of past vertex combinations with resulting possible next vertex combinations
		HashMap<LinkedList<Integer>, int[]> nextVertex = new HashMap<>();
		
		// If not restrictions, return empty hash map
		if (getNumRestrictions() == 0)
			return nextVertex;
		
		// Iterate through shape size, creating list of past vertices and finding restrictions
		for (int i=0; i<shapeSize; i++)
		{
			// Linked list that will simulate possible combinations of past vertices
			LinkedList<Integer> pastVertex = new LinkedList<>();
			
			// Add things to Linked list to simulate vertex combination
			pastVertex.add(i);

			// Find restrictions
			ArrayList<Integer> possibles = new ArrayList<>();
			for (int j=0; j<shapeSize; j++)
				possibles.add(j);
			for (int j=0; j<1; j++)
				if (!choiceBoxes.get(j).getSelectedItem().equals("No restriction"))
				{
					int intChoice = Integer.parseInt((String) choiceBoxes.get(j).getSelectedItem());
					int toRemove = pastVertex.get(pastVertex.size() - 1 - j) + intChoice;
				
					//  Size check
					if (toRemove > shapeSize - 1)
						toRemove -= shapeSize;
					
					// Remove ones I can't have
					if (possibles.contains(toRemove))
						possibles.remove(possibles.indexOf(toRemove));
				}
			
			// Convert array list to array
			int[] arrayPossibles;
			if (possibles.size() == 0)
			{
				arrayPossibles = new int[] {0};
				controller.getScreen().setMessage("No indexes to choose from. Default to zero.");

			// If possibles array is not empty
			} else {
				controller.getScreen().setMessage("");
				arrayPossibles = new int[possibles.size()];
				for (int j=0; j<possibles.size(); j++)
					arrayPossibles[j] = possibles.get(j);
			}

			// Add vertex combination and restrictions to hash map
			nextVertex.put(pastVertex, arrayPossibles);
		}
		// Return hash map of vertex index combinations
		return nextVertex;
	}
	
	/**
	 * Sets restriction for tutorial example.
	 */
	public void setFirstRestriction()
	{
		choiceBoxes.get(0).setSelectedIndex(1);
	}
	
	/**
	 * Method that return the number of restrictions currently chosen
	 * @return
	 */
	public int getNumRestrictions()
	{
		// Iterate backwards until restriction is found or none are found
		for (int i=choiceBoxes.size() - 1; i>=0; i--)
			if (!choiceBoxes.get(i).getSelectedItem().equals("No restriction"))
				return i + 1;
		return 0;
	}
	
	/**
	 * Sets default conditions for restriction page, setting all boxes to "No restriction"
	 */
	public void setDefault()
	{
		for (int i=0; i<choiceBoxes.size(); i++)
			choiceBoxes.get(i).setSelectedIndex(0);
	}
}