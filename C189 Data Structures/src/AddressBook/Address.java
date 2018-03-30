package AddressBook;

public class Address implements Comparable<Address>, IKey {

	private String _firstName;
	private String _lastName;
	private String _email;
	private String _phone;

	private int _key = -1;
	private int _index = -1;
	
	public Address() {
		// Empty constructor
	}

	public Address(String firstName, String lastName)
	{
		_firstName = firstName.equals(null) ? "" : firstName.trim();
		_lastName = lastName.equals(null) ? "" : lastName.trim();
		
		//Set Key based on loaded name;
		_key = Math.abs(getName().hashCode());
		
		//System.out.println(getName()+ " has key of " + _key);
	}
	public Address(String firstName, String lastName, String phone, String email) {
		this(firstName, lastName);
		_email = email.equals(null) ? "" : email.trim().toLowerCase();
		_phone = phone.equals(null) ? "" : phone.trim().toUpperCase();
	}

	public int getKey()
	{	
		return _key;
	}
	
	public int getIndex()
	{
		return _index;
	}
	public void setIndex(int value)
	{
		_index = value;
	}
	
	public String getFirstName()
	{
		return _firstName.trim();
	}
	public String getLastName()
	{
		return _lastName.trim();
	}
	
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

	public String getEmail() {
		return _email.trim();
	}

	public String getPhone() {
		return _phone.trim();
	}
	

	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		//Hash
		if (_index >= 0)
		{
			sb.append("Index: \"");
			sb.append(_index);
			sb.append("\", ");
		}
		
		//Key
		if (_key > 0)
		{
			sb.append("Key: \"");
			sb.append(_key);
			sb.append("\", ");
		}
		
		//Name
		sb.append("Name: \"");
		sb.append(getName());
		sb.append("\"");

		//Phone
		if (_phone.length() > 0)
		{
			sb.append(", Phone: \"");
			sb.append(_phone);
			sb.append("\"");
		}
		
		//Email
		if (_email.length() > 0)
		{
			sb.append(", Email: ");
			sb.append(_email);
			sb.append("\"");
		}
		
		return sb.toString().trim();
	}

	@Override
	public int compareTo(Address other) {
		//JC: Compare First Names
		int result = this.getFirstName().compareTo(other.getFirstName());
		
		//JC: Compare Last Names
		if (result == 0)
			result = this.getLastName().compareTo(other.getLastName());
		
//		//JC: Compare Phones
//		if (result == 0)
//			result = this.getPhone().compareTo(other.getPhone());
//		//JC: Compare Emails
//		if (result == 0)
//			result = this.getEmail().compareTo(other.getEmail());
		
		//JC: Compare index only if index on other is set
		if (result == 0 
				&& other.getIndex() >= 0
				&& this.getIndex() == other.getIndex())
			result = 0;
		else if (result == 0 
				&& other.getIndex() >= 0
				&& this.getIndex() > other.getIndex())
			result = 1;
		else if (result == 0 
				&& other.getIndex() >= 0
				&& this.getIndex() < other.getIndex())
			result = -1;
		
		return result;
	}
}
