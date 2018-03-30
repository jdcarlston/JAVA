package Roster;
import java.util.ArrayList;

/**
 * @author jcarlston
 */
@SuppressWarnings("serial")
public class Roster extends ArrayList<Student> implements IRoster  
{
	public void add(String studentId, String firstName, String lastName, String email, int age, int[] grades)
	{
		Student s = new Student(studentId, firstName, lastName, email, age, grades);
		this.add(s);
	}

	public void remove(String studentId) 
	{
		Student removed = null;
		
		for(Student s: this)
		{
			if (s.getId().equals(studentId))
			{
				removed = s;
				this.remove(s);
				System.out.println("Removed Student " + s.toString());
				break;
			}
		}
		
		if (removed == null)
			System.out.println("Student " + studentId + " was not found.");
	}

	public void print_all() 
	{
		System.out.println("***** Student Roster *****");
		
		for(Student s: this)
		{
			s.print();
		}
	}

	public void print_average_grade(String studentId) 
	{
		Student s = match(studentId);
		double gpa = 0.0;
		
		if (studentId.length() > 0
				&& s != null)
		{
			int[] grades = s.getGrades();
			
			for(int i : grades)
			{
				gpa += i;
			}
			
			gpa /= (grades.length);

			System.out.format(s.getName() + " GPA: %.2f\n", gpa);
		}
		else
			System.out.println("Student " + studentId + " does not exist.");
	}

	public void print_invalid_emails() {
		System.out.println("***** Invalid Emails *****");

		for(Student s: this)
		{
			if (!s.isValidEmail())
				System.out.println(s.getEmail());
		}
	}

	public boolean contains(String studentId) 
	{
		for(Student s: this)
		{
			if (s.getId().equals(studentId))
				return true;
		}
		return false;
	}
	
	private Student match(String studentId)
	{
		for(Student s: this)
		{
			if (s.getId().equals(studentId))
			{
				return s;
			}
		}
		
		return null;
	}

	public void addNames(Student[] arr) 
	{
		// TODO Auto-generated method stub
		for(Student s: arr)
		{
			this.add(s);
		}
	}
}
