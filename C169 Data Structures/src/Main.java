import AddressBook.AddressBookBinaryTree;
import AddressBook.AddressBookHashTable;
import AddressBook.IAddressBook;

public class Main {

	public static void main(String[] args) {
		// JC: Bucket Search Test
		BucketSearchTest(); 
		
		//JC: Binary Search Tree Test 
		BinarySearchTest();
	}

	private static void BucketSearchTest()
	{
		System.out.println("**********Hash Bucket Search**********");
		System.out.println(" ");

		AddressBookHashTable t = new AddressBookHashTable(13);
		Test(t);
		System.out.println(" ");
		System.out.println("**********     END TEST     **********");
	}
	
	private static void BinarySearchTest()
	{
		System.out.println("**********Binary Search Tree**********");
		System.out.println(" ");

		AddressBookBinaryTree t = new AddressBookBinaryTree();
		Test(t);
		System.out.println("**********     END TEST     **********");
	}
	
	private static void Test(IAddressBook t)
	{
		t.add("Bob", "Smith", "555-235-1111", "bsmith@somewhere.com");
		t.add("Jane", "Williams", "555-235-1112", "jw@something.com");
		t.add("Mohammed", "al-Salam", "555-235-1113", "mas@someplace.com");
		t.add("Pat", "Jones", "555-235-1114", "pjones@homesweethome.com");
		t.add("Billy", "Kidd", "555-235-1115", "billy_the_kid@nowhere.com");
		t.add("H.", "Houdini", "555-235-1116", "houdini@noplace.com");
		t.add("Jack", "Jones", "555-235-1117", "jjones@hill.com");
		t.add("Jill", "Jones", "555-235-1118", "jillj@hill.com");
		t.add("John", "Doe", "555-235-1119", "jdoe@somedomain.com");
		t.add("Jane", "Doe", "555-235-1120", "jdoe@somedomain.com");
		t.contains("Pat", "Jones");
		t.contains("Billy", "Kidd");
		t.remove("John", "Doe");
		t.add("Test", "Case", "555-235-1121", "Test_Case@testcase.com");
		t.add("Nadezhda", "Kanachekhovskaya", "555-235-1122", "dr.nadezhda.kanacheckovskaya@somehospital.moscow.ci.ru");
		t.add("Millard", "Fillmore", "555-235-1124", "millard@theactualwhitehouse.us");	
		t.add("Bob", "vanDyke", "555-235-1125", "vandyke@nodomain.com");	
		t.contains("Jack", "Jones");
		t.contains("Nadezhda", "Kanachekhovskaya");
		t.remove("Jill", "Jones");
		t.remove("John", "Doe");
		t.contains("Jill", "Jones");
		t.contains("John", "Doe");
		t.contains("Millard", "Fillmore");
	}
}

