package Roster;
import java.util.Arrays;

/**
 * @author jcarlston
 *
 */
public class Student implements Comparable<Student> {

	private String _id;

	private String _firstName;
	private String _lastName;
	private String _email;
	private int _age = 0;
	private int[] _grades;

	private int _key = -1;
	
	public Student() {
		// Empty constructor
	}

	public Student(String ID, String firstName, String lastName, String email, int age, int[] grades) {
		_id = ID;
		_firstName = firstName.equals(null) ? "" : firstName;
		_lastName = lastName.equals(null) ? "" : lastName;	
		_email = email.equals(null) ? "" : email;
		_age = age;
		_grades = grades;

		//Set Key based on StudentId;
		_key = Math.abs(getId().hashCode());
	}

	//Hash Key from Student Id
	public int getKey()
	{	
		return _key;
	}
	
	//Student Id
	public String getId()
	{
		return _id;
	}
	public void setId(String value)
	{
		_id = value.trim();
	}
	
	//First Name
	public String getFirstName()
	{
		return _firstName;
	}
	public void setFirstName(String value)
	{
		_firstName = value.trim();
	}
	
	//Last Name
	public String getLastName()
	{
		return _lastName;
	}
	public void setLastName(String value)
	{
		_lastName = value.trim();
	}

	//Email
	public String getEmail()
	{
		return _email.trim();
	}
	public void setEmail(String value)
	{
		_email = value.trim().toLowerCase();
	}
	public boolean isValidEmail()
	{	
		if (getEmail().indexOf("@") > -1
				&& getEmail().indexOf(".") > -1
				&& getEmail().indexOf(" ") == -1)
			return true;
		else
			return false;
	}

	//Age
	public int getAge() 
	{
		return _age;
	}
	public void setAge(int value)
	{
		_age = value;
	}
	
	//Grades
	public int[] getGrades() 
	{
		return _grades;
	}	
	public void setGrades(int[] value)
	{
		_grades = value;
	}
	public void addGrade(int value)
	{
		_grades[_grades.length] = value;
	}
	public void removeGrade(int value)
	{
	   for(int i = value; i < _grades.length -1; i++)
	   {
		   _grades[i] = _grades[i + 1];
	   }
	}
	
	//Name String
	public String getName() 
	{		
		return getName(_firstName, _lastName);
	}
	public String getName(String firstName, String lastName)
	{
		StringBuilder sb = new StringBuilder();
		if (firstName.length() > 0)
		{
			sb.append(firstName);
			sb.append(" ");
		}
		
		if (lastName.length() > 0)
		{
			sb.append(lastName);
		}
		
		return sb.toString().trim();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		//ID
		if (getId().length() >= 0)
		{
			sb.append("ID: \"");
			sb.append(getId());
			sb.append("\"\t");
		}
		
//		//Key
//		if (_key > 0)
//		{
//			sb.append("Key: \"");
//			sb.append(getKey());
//			sb.append("\"\t ");
//		}
		
		//Name
		sb.append("Name: \"");
		sb.append(getName());
		sb.append("\"");

//		//Email
//		if (_email.length() > 0)
//		{
//			sb.append("\t Email: ");
//			sb.append(getEmail());
//			sb.append("\"");
//		}

//		//Age
//		if (_age > 0)
//		{
//			sb.append(", Age: \"");
//			sb.append(getAge());
//			sb.append("\"");
//		}
	
		//Grades
		if (getGrades().length > 0)
		{
			sb.append("\tGrades: \"");
			sb.append(Arrays.toString(getGrades()));
			sb.append("\"");
		}
		
		return sb.toString().trim();
	}
	
	//Print
	public void print()
	{
		System.out.println(this.toString());
	}

	//Comparable Implementation
	public int compareTo(Student other) {
		int result = this.getId().compareToIgnoreCase(other.getId());
				
//		//JC: Compare First Names
//		if (result == 0)
//			result = this.getFirstName().compareTo(other.getFirstName());
//		
//		//JC: Compare Last Names
//		if (result == 0)
//			result = this.getLastName().compareTo(other.getLastName());
		
//		//JC: Compare Phones
//		if (result == 0)
//			result = this.getPhone().compareTo(other.getPhone());
		
//		//JC: Compare Emails
//		if (result == 0)
//			result = this.getEmail().compareTo(other.getEmail());
		
//		//JC: Compare index only if index on other is set
//		if (result == 0 
//				&& other.getIndex() >= 0
//				&& this.getIndex() == other.getIndex())
//			result = 0;
//		else if (result == 0 
//				&& other.getIndex() >= 0
//				&& this.getIndex() > other.getIndex())
//			result = 1;
//		else if (result == 0 
//				&& other.getIndex() >= 0
//				&& this.getIndex() < other.getIndex())
//			result = -1;
		
		return result;
	}
}