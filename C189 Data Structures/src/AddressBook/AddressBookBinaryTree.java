package AddressBook;

import BinarySearchTree.Tree;
import BinarySearchTree.Node;

public class AddressBookBinaryTree extends Tree<Address> implements IAddressBook 
{

	@Override
	public void add(String firstName, String lastName, String email, String phoneNumber) 
	{
		//JC: Create new node from address to insert into the tree
		Address obj = new Address(firstName, lastName, email, phoneNumber);
		this.insertNode(new Node<Address>(obj));
		System.out.println(this.toString());
	}

	@Override
	public void remove(String firstName, String lastName) 
	{
		//JC: Create Address object to format key in the same way as the node
		Address obj = new Address(firstName, lastName);
		boolean success = this.deleteNodeByKey(obj.getKey());
		
		if (success)
			System.out.println("Removed " + obj.getName() + " in tree with key " + obj.getKey());
		else
			System.out.println("Could not find " + obj.getName() + " in tree.");
		
		System.out.println(this.toString());
	}

	@Override
	public boolean contains(String firstName, String lastName) 
	{
		//JC: Create Address object to format key in the same way as the node
		Address obj = new Address(firstName, lastName);
		//JC: Use the exists function to find out if the key exists in the tree
		Node<Address> node = this.findNodeByKey(obj.getKey());

		boolean success = (node != null);
		
		if (success)
			System.out.println("Found " + node.getName() + " in tree with key " + node.getKey());
		else
			System.out.println("Could not find " + obj.getName() + " in tree.");
		
		System.out.println(this.toString());
		
		return success;
	}

	@Override
	public int count() {
		
		return 0;
	}

}
