import java.awt.*;
import javax.swing.*;

/**
 * Class to define window in which attendance report is displayed.
 */
public class ReportFrame extends JFrame {

	private JTextArea display; 

	/**
	 * constructor for ReportFrame class
	 */
	public ReportFrame() {

		setTitle("Attendance Report");
		setSize(700, 300);
		display = new JTextArea();
		display.setFont(new Font("Courier", Font.PLAIN, 14));
		add(display, BorderLayout.CENTER); 

	}
	
	/**
	 * updates display
	 * @param program - current FitnessProgram object
	 */
	public void updateDisplay (FitnessProgram program)
	{
		// sorts classes by avg attendances
		FitnessClass [] sClasses = program.sortClasses();
		StringBuilder r = new StringBuilder("");

		// report column names
		String columns = String.format(" %-5s %-15s %-15s %-25s%-15s \n \n", 
				"ID", "Class", "Tutor", "Attendances", "Average Attendances");
		r.append(columns);

		// report body 
		for(int i = 0; i < sClasses.length; i++) 
		{
			// loops through each of the classes
			try 
			{
				r.append(String.format(" %-5s", sClasses[i].getID()));
				r.append(String.format(" %-15s", sClasses[i].getName()));
				r.append(String.format(" %-15s", sClasses[i].getTutorsName()));
				r.append(String.format("%-25s", sClasses[i].getAttendances()));
				r.append(String.format(" %-15.2f \n", sClasses[i].getAverage()));
			}

			// skips empty slots
			catch (NullPointerException n) {} 
		}
		
		// appends overall average
		r.append(String.format("\n %s %.2f", "Overal average: ", program.overallAverage()));

		String report = r.toString();
		
		// updates display
		display.setText(report);
	}
}
