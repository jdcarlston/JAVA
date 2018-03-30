package Roster;


/**
 * @author jcarlston
 *
 */
public interface IRoster {
	void add(String studentId, String firstName, String lastName, String email, int age, int[] grades);
	void remove(String studentId);
	
	void print_all();
	void print_average_grade(String studentId);
	void print_invalid_emails();
	
	boolean contains(String studentId);
}
