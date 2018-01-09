import java.util.*;

import javax.swing.JOptionPane;

/**
 * Maintains a list of Fitness Class objects
 * The list is initialised in order of start time
 * The methods allow objects to be added and deleted from the list
 * In addition an array can be returned in order of average attendance
 */
public class FitnessProgram {

	// class constant 
	private final int maxClasses = 7;

	// instance variables 
	private FitnessClass[] classes;
	private int currentClasses;

	/**
	 * constructor method
	 * creates a new FitnessProgram object
	 * FitnessProgram contains an array of FitnessClass objects
	 */
	public FitnessProgram () 	
	{
		classes = new FitnessClass[maxClasses];
		currentClasses = 0;
	}
	
	/**
	 * adds a new FitnessClass object to the array
	 * @param parameters - contains parameters for a new FitnessClass object
	 */
	public void addClass(String [] parameters)
	{
		// checks if the program is full
		if (currentClasses == 7)
		{
			JOptionPane.showMessageDialog(null, "The fitness program is already full");
		}
		
		// if there are empty slots
		else 
		{	
			// if no start time is inputed, finds the earliest available slot
			if (parameters[3] == null)
			{
				for (int i=maxClasses-1; i>0; i--)
				{
					if (classes[i] == null)
					{
						parameters[3] = String.valueOf(i+9); 
					}
				}
			}
			
			// adds an object to the array
			int index = Integer.valueOf(parameters[3])-9;
			classes[index] = new FitnessClass(parameters);
			// increases the number of currently offered classes
			currentClasses++;
		}
	}

	/**
	 * deletes object from the list
	 * @param index - index of the object to be deleted
	 */
	public void deleteClass(int index)
	{ 
		classes[index] = null;
		currentClasses--;
	}

	/**
	 * method to populate the attendance list
	 * @param newAttendances - array containing class id attendances
	 */
	public void addAttendances (String [] newAttendances)	
	{
		//finds class index based on its id
		int classIndex = findClassIndex(newAttendances[0]);
		classes[classIndex].setAttendances(newAttendances);
	}

	/**
	 * searches for a class index based on its id
	 * @param classID 
	 * @return index of the class in the FitnessProgram object
	 */
	public int findClassIndex (String classID)	
	{
		int index = -1; 

		for (int i=0; i<maxClasses && index<0; i++) 
		{	
			try
			{
				if (classID.equals(classes[i].getID())) 
				{ 
					index = i; 
				}
			}

			// catches exception
			catch (NullPointerException n) {}
		}	

		return index; 
	}

	/**
	 * creates a single String containing a class timetable
	 * default ordering (by start time)
	 * @return timetable
	 */
	public String getTimetable () 
	{
		StringBuilder t = new StringBuilder("");
		
		// loops through available slots
		for (int i = 0; i<maxClasses; i++)	
		{
			// calculates start and end times
			t.append(String.format("%d%s%d	", i+9, "-", i+10));

			try 
			{ 	// concatenates class name + tutors name
				t.append(String.format("%-20s	", classes[i].getName()));
				t.append(String.format("%-20s	\n", classes[i].getTutorsName()));
			}
			
			// if a null entry is found
			catch (NullPointerException n)
			{
				t.append(String.format("%s\n", "Available"));
			}
		}

		String timetable = t.toString();
		return timetable;
	}
	/**
	 * creates a Single string containing all the classes
	 * default ordering (by start time)
	 * @return fileOut
	 */
	
	public String classesOut()
	{ 
	StringBuilder fOut = new StringBuilder("");
	
	// loops through available slots
	for(int i = 0; i < maxClasses; i++) 
	{ 
		// if the slot is not empty, concatenates the String
		if (classes[i] != null) 
		{
			fOut.append(String.format(" %-5s", classes[i].getID()));
			fOut.append(String.format(" %-15s", classes[i].getName()));
			fOut.append(String.format(" %-15s", classes[i].getTutorsName())); 
			fOut.append(String.format(" %-5d \n", i+9));
		}
	}	
	
	String fileOut = fOut.toString();
	return fileOut;
	}

	/**
	 * creates a new array containing available classes
	 * sorts in in order of average attendance
	 * @return sorted array
	 */
	public FitnessClass[] sortClasses ()
	{	
		// creates a new empty array; length = current number of offered classes
		FitnessClass[] sortProgram = new FitnessClass[currentClasses];
		int index = 0;

		// loops through available slots
		// populates the array
		for (int i=0; i<maxClasses; i++)
		{
			// skips empty slots
			if (classes[i] != null)
			{
				sortProgram[index]=classes[i];
				index++;
			}
		}

		// sorts the array
		Arrays.sort(sortProgram); 
		return sortProgram;
	}

	/**
	 * calculates the overall average attendance count
	 * @return the overall average
	 */
	public double overallAverage ()
	{
		double totalAverages = 0; 
		
		// loops through available slots
		for (int i=0; i<maxClasses; i++)
		{
			// skips empty slots 
			if (classes[i] != null)
			{
				// sums averages for each of the classes
				totalAverages = totalAverages + classes[i].getAverage();
			}
		}

		// calculates overall average
		double average = totalAverages / currentClasses;
		return average;
	}

}
