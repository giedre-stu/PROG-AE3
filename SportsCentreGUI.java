import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

/**
 * Defines a GUI that displays details of a FitnessProgram object
 * and contains buttons enabling access to the required functionality.
 */
public class SportsCentreGUI extends JFrame implements ActionListener {

	/** GUI JButtons */
	private JButton closeButton, attendanceButton;
	private JButton addButton, deleteButton;

	/** GUI JTextFields */
	private JTextField idIn, classIn, tutorIn;

	/** Display of class timetable */
	private JTextArea display;

	/** Display of attendance information */
	private ReportFrame report;

	/** Names of input text files */
	private final String classesInFile = "ClassesIn.txt";
	private final String classesOutFile = "ClassesOut.txt";
	private final String attendancesFile = "AttendancesIn.txt";

	// instance variable - FitnesssProgram object
	private	FitnessProgram currentProgram;

	/**
	 * Constructor for AssEx3GUI class
	 */
	public SportsCentreGUI() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Boyd-Orr Sports Centre");
		setSize(700, 300);
		display = new JTextArea();
		display.setFont(new Font("Courier", Font.PLAIN, 14));
		add(display, BorderLayout.CENTER);
		layoutTop();
		layoutBottom();

		// initializes new FitnessProgram object, populates it, updates display
		currentProgram = new FitnessProgram();
		initLadiesDay();
		initAttendances();
		updateDisplay();
	}

	/**
	 * Creates the FitnessProgram list ordered by start time
	 * using data from the file ClassesIn.txt
	 */
	public void initLadiesDay() {

		try {
			FileReader reader = new FileReader(classesInFile);
			Scanner in = new Scanner (reader);

			// reads line by line
			String line;
			while (in.hasNextLine())	
			{
				line = in.nextLine();

				// splits the string using blank spaces
				// creates an array containing parameters for a new class
				String [] parameters = new String [4];
				parameters = line.split(" ");

				// currentProgram object stores a new class
				currentProgram.addClass(parameters);
			} 

			in.close();

		}


		catch (FileNotFoundException e) 
		{
			JOptionPane.showMessageDialog(null, "ClassesIn.txt file not found");
		}

	}

	/**
	 * Initialises the attendances using data
	 * from the file AttendancesIn.txt
	 */
	public void initAttendances() {

		try 
		{
			FileReader reader = new FileReader(attendancesFile);
			Scanner in = new Scanner (reader);

			// reads line by line
			String line;
			while (in.hasNextLine())	
			{
				line = in.nextLine();

				// splits the string using blank spaces
				// creates an array containing attendances for a new class
				String [] attendances = new String [6];
				attendances = line.split(" ");
				
				// currentProgram object stores attendances for a class
				currentProgram.addAttendances(attendances);
			}

			in.close();
		}

		catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "AttendancesIn.txt file not found");
		}
	}


	/**
	 * Instantiates timetable display and adds it to GUI
	 */
	public void updateDisplay() 
	{
		display.setText(currentProgram.getTimetable());
	}

	/**
	 * adds buttons to top of GUI
	 */
	public void layoutTop() {
		JPanel top = new JPanel();
		closeButton = new JButton("Save and Exit");
		closeButton.addActionListener(this);
		top.add(closeButton);
		attendanceButton = new JButton("View Attendances");
		attendanceButton.addActionListener(this);
		top.add(attendanceButton);
		add(top, BorderLayout.NORTH);
	}

	/**
	 * adds labels, text fields and buttons to bottom of GUI
	 */
	public void layoutBottom() {
		// instantiate panel for bottom of display
		JPanel bottom = new JPanel(new GridLayout(3, 3));

		// add upper label, text field and button
		JLabel idLabel = new JLabel("Enter Class Id");
		bottom.add(idLabel);
		idIn = new JTextField();
		bottom.add(idIn);
		JPanel panel1 = new JPanel();
		addButton = new JButton("Add");
		addButton.addActionListener(this);
		panel1.add(addButton);
		bottom.add(panel1);

		// add middle label, text field and button
		JLabel nmeLabel = new JLabel("Enter Class Name");
		bottom.add(nmeLabel);
		classIn = new JTextField();
		bottom.add(classIn);
		JPanel panel2 = new JPanel();
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		panel2.add(deleteButton);
		bottom.add(panel2);

		// add lower label text field and button
		JLabel tutLabel = new JLabel("Enter Tutor Name");
		bottom.add(tutLabel);
		tutorIn = new JTextField();
		bottom.add(tutorIn);

		add(bottom, BorderLayout.SOUTH);
	}

	/**
	 * Processes adding a class
	 */
	public void processAdding() {

		// creates an array for the class parameters
		String[] parameters = new String [4];

		boolean invalidData = false;

		// checks if valid id was entered
		parameters [0] = idIn.getText();
		if (parameters [0] == null || parameters [0].isEmpty() || parameters[0].length()>3)
		{
			invalidData = true;
			JOptionPane.showMessageDialog(null, "Please enter a valid class ID");
		} 

		// checks if id is not duplicate
		else if ( currentProgram.findClassIndex(parameters[0]) >= 0)
		{
			invalidData = true;
			JOptionPane.showMessageDialog(null, "Class ID already exists");
		}

		// if not duplicate
		if (!invalidData) 
		{
		// checks if class name was entered
		parameters [1] = classIn.getText();
		if (parameters [1] == null || parameters [1].isEmpty())
		{
			invalidData = true;
			JOptionPane.showMessageDialog(null, "Please enter the class name");
		} 

		// checks if tutor name was entered
		parameters [2] = tutorIn.getText();
		if (parameters [2] == null || parameters [2].isEmpty())
		{
			invalidData = true;
			JOptionPane.showMessageDialog(null, "Please enter the tutor name");
		} 
		}
		// proceeds to add class & updates display - only if valid data was entered
		if (!invalidData)
		{
			currentProgram.addClass(parameters);
			updateDisplay();
			
			// if the report window is open, updates it
			if (report!=null)
			report.updateDisplay(currentProgram);
		}

	}

	/**
	 * Processes deleting a class
	 */
	public void processDeletion() {

		String classId = idIn.getText();
		boolean invalidData = false;
		
		// checks if valid id was entered
		if (classId == null || classId.isEmpty() || classId.length()>3)
		{
			invalidData = true;
			JOptionPane.showMessageDialog(null, "Please enter a valid class ID");
		} 

		// checks if id exists
		int index = currentProgram.findClassIndex(classId);
		if ( index < 0)
		{ 
			invalidData = true;
			JOptionPane.showMessageDialog(null, "Class ID not found");
		}

		// proceeds to delete class & updates display - only if valid data was entered
		if (invalidData != true)
		{
			currentProgram.deleteClass(index);
			updateDisplay(); 
			
			// if the report window is open, updates it
			if (report!=null)
			report.updateDisplay(currentProgram);
		}

	}

	/**
	 * Instantiates a new window and displays the attendance report
	 */
	public void displayReport() {

		report = new ReportFrame();
		report.updateDisplay(currentProgram);
		report.setVisible(true);
	}

	/**
	 * Writes lines to file representing class name, 
	 * tutor and start time and then exits from the program
	 */
	public void processSaveAndClose() {

		try 
		{
			PrintWriter writer = new PrintWriter("ClassesOut.txt");

			// currentProgram object returns all classes as a String
			String fileOut = currentProgram.classesOut();
			writer.write(fileOut);
			writer.close();
			System.exit(0);	
		} 

		catch (FileNotFoundException e) 
		{ 
			JOptionPane.showMessageDialog(null, "File not found");
		}
	}

	/**
	 * Process button clicks.
	 * @param ae the ActionEvent
	 */
	public void actionPerformed(ActionEvent ae) {

		// addButton
		if (ae.getSource().equals(addButton))
		{
			processAdding();
			resetTextFields();
		}

		// deleteButton
		if (ae.getSource().equals(deleteButton))
		{
			processDeletion();
			resetTextFields();
		}

		//closeButton
		if (ae.getSource().equals(closeButton))
		{
			processSaveAndClose();
		}

		//closeButton
		if (ae.getSource().equals(attendanceButton))
		{
			displayReport();
		}
	}

	/**
	 * resets text fields
	 */
	public void resetTextFields()

	{
		idIn.setText("");
		classIn.setText("");
		tutorIn.setText("");
	}
}
