/** Defines an object representing a single fitness class
 * Defines methods applicable to the FitnessClass object
 */

public class FitnessClass implements Comparable<FitnessClass> {

	// class constant = number of weeks over which attendance is recorded
	private final int weeks = 5; 

	// instance variables
	private String id; 
	private String name; 
	private String tutorsName; 
	private int time; 
	private int[] attendances = new int[5];
	private double averageAttendance;

	// constructor method
	public FitnessClass (String [] parameters)
	{
		id = parameters[0]; 
		name = parameters[1]; 
		tutorsName = parameters[2];
		time = Integer.valueOf(parameters[3]);
	}

	/**
	 * mutator method
	 * sets the number of attendances for a single FitnessClass object
	 * attendances are stored in an integer array
	 * @param String array containing attendances for each of the weeks
	 */
	public void setAttendances (String [] newAttendances)
	{
		for (int i=0; i < weeks; i++)
		{
			attendances[i] = Integer.parseInt(newAttendances[i+1]);
		}

	}

	/**
	 * accessor methods below
	 */

	// returns class id
	public String getID ()
	{
		return id;
	}

	// returns class name
	public String getName ()
	{
		return name;
	}

	// returns tutors name
	public String getTutorsName()
	{
		return tutorsName;
	}

	/**
	 * returns attendance report as a String
	 * loops through each of the FitnessClass objects
	 */
	public String getAttendances()
	{
		String aReport = "";

		// loop
		for (int i = 0; i<weeks; i++)
		{ 
			aReport = aReport + String.format(" %-3d", attendances[i]);
		}

		return aReport;
	}

	/**
	 * calculates average attendance for a single FitnessClass object
	 * returns average attendance as a double
	 */
	public double getAverage ()
	{
		double totalAttendances = 0; 

		// calculates total number of attendances
		for (int i=0; i < weeks; i++)
		{
			totalAttendances = totalAttendances + (double)(attendances[i]);
		}

		// calculates average
		averageAttendance = totalAttendances / weeks; 

		return averageAttendance;
	}

	/**
	 * compare method
	 * @override the default comparetTo() method
	 * defines the order of ordering objects in a class
	 * @param FitnessClass other
	 */
	public int compareTo(FitnessClass other) 
	{
		if (this.getAverage() < other.getAverage()) 
		{
			return 1;	
		}
		else if (this.getAverage() > other.getAverage())
		{
			return -1;
		}
		else if (this.getAverage() == other.getAverage())
		{
			return 0;
		}
		else 
			return -1;

	}
}