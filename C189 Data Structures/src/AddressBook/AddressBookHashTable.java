package AddressBook;

import HashMapTable.HashTable;

public class AddressBookHashTable extends HashTable<Address> implements IAddressBook {
	
	public AddressBookHashTable(int size) {
		super(size);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void add(String firstName, String lastName, String email, String phoneNumber) {
		Address address = new Address(firstName, lastName, email, phoneNumber);
		this.insertObj(address);
	}

	@Override
	public void remove(String firstName, String lastName) {
		Address address = new Address(firstName, lastName);
		this.deleteObj(address);
	}

	@Override
	public boolean contains(String firstName, String lastName) {
		Address address = new Address(firstName, lastName);		
		return this.match(address) >= 0;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return 0;
	}

}
