package AddressBook;

public interface IAddressBook {
	//JC: insert function
	void add(String firstName, String lastName, String email, String phoneNumber);
	//JC: delete function
	void remove(String firstName, String lastName);
	//JC: look-up function
	boolean contains(String firstName, String lastName);
	//JC: count the number of items in the book
	int count();
}
