import Roster.Roster;
import Roster.Student;

public class Main {

	public static void main(String[] args) {
		Roster roster = new Roster();
		
		BuildRoster(roster);
		
		Test(roster); 	
	}
	
	/**
	 * @param o
	 */
	private static void Test(Roster o)
	{
		System.out.println("**********     START TEST     **********");
		System.out.println("");
		
		o.print_all();
		
		System.out.println("");
		
		o.print_invalid_emails();
		
		System.out.println("");
		
		//loop through the ArrayList and for each element:
		System.out.println("***** Average Grades *****");
		for (Student current_loop_student : o)
		{
			o.print_average_grade(current_loop_student.getId());
		}
		
		System.out.println("");
		
		System.out.println("***** Remove *****");
		o.remove("3");
		o.remove("3");
		//expected: this should print a message saying such a student with this ID was not found.
		
		System.out.println("");
		System.out.println("**********     END TEST     **********");

	}
	
	private static void BuildRoster(Roster obj)
	{
		Student[] students = {
				new Student("1", "John", "Smith", "John1989@gmail.com", 20, new int[] {88, 79, 59}),
				new Student("2", "Suzan", "Erickson", "Erickson_1990@gmailcom  ", 19, new int[] {91, 72, 85}),
				new Student("3", "Jack", "Napoli", "The_lawyer99yahoo.com", 19, new int[] {85, 84, 87}),
				new Student("4", "Erin", "Black", "Erin.black@comcast.net", 22, new int[] {91, 98, 82}),
				new Student("5", "Jennifer", "Carlston", "jcarl34@wgu.edu", 32, new int[] {91, 98, 89})
		};
		
		obj.addNames(students);
	}

}
