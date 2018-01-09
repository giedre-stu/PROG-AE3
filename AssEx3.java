/**
 * The main class 
 */

/**
 * The program displays and updates the current Wednesday class schedule 
 * and stores five weeks of attendance details for Boyd-Orr Sports Centre.
 *  
 * 1. Upon opening, the program:  
 * 1.1. Reads current classes from the ClassesIn.txt file and stores them. 
 * 1.2. Reads current attendance details from the AttendancesIn.txt file and stores them.
 * 1.3. Displays the current timetable. 
 * 
 * 2. Upon pressing the View Attendances button, the program: 
 * 2.1. Displays the current attendance details for each of the classes in a new window
 * in order of average attendance. 
 * 2.2. Displays the overall average attendance. 
 * 
 * 3. Upon pressing the Add Class button, the program:
 * 3.1. Reads class parameters from the text fields. 
 * 3.2. All text fields must contain correct data, otherwise the program
 * displays relevant error messages.
 * 3.3. If there are available slots, it adds a new class to the timetable. 
 * 3.4. Updates the timetable and attendance report (if the window is open). 
 * 3.5. Empties text fields.
 * 
 * 4. Upon pressing the Delete Class button, the program:
 * 4.1. Reads the class ID from the text field. 
 * 4.2. Only the class ID is needed in order to delete a class.
 * If the ID is missing or longer than 3 characters, the program 
 * displays an error message.
 * 4.3. If the class ID exists in the system, the class is deleted.
 * 4.4. Updates the timetable and attendance report (if the window is open). 
 * 4.5. Empties text fields.
 * 
 * 5. Upon pressing the Save and Exit button, the program: 
 * 5.1. Writes all available classes to a new file ClassesOut.txt.
 * 5.2. Exits
 */

public class AssEx3 {
	/**
	 * The main method
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SportsCentreGUI display = new SportsCentreGUI();
		display.setVisible(true);
	}
}
