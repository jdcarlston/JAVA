package HashMapTable;

import AddressBook.Address;

public abstract class HashTable<T extends Address> {
	private Object[] _array;
	private int _arraySize = 0;
	private int _count = 0;
	
	public HashTable(int size)
	{	
		//JC: Use a prime number for the array size to reduce collisions
		_arraySize = getNextPrime(size);
		_array = new Object[_arraySize];
	}
	
	protected int insertObj(T obj)
	{
		return insertObjByHash(-1, obj);
	}
	private int insertObjByHash(int hashKey, T obj)
	{
		//JC: Set hashKey equal to default if it is not set
		if (hashKey < 0)
			hashKey = getHash(obj);
			
		//JC: return invalid index if there is an issue inserting
		int index = -1;
		//System.out.println("hashKey: " + hashKey + ", _arraySize: " + _arraySize);
		
		//JC: There's an empty slot available at the hashKey
		if (hashKey < _arraySize
				&& _array[hashKey] == null)
		{
			index = hashKey;
			_array[index] = obj;
			
			//JC: store hash in the object to speed up match and delete
			obj.setIndex(index);
			
			//JC: Maintain a count in the table
			_count++;
			
			System.out.println(obj.getName() + 
					" added to table at index " + index + ".");
			System.out.println(this.toString());
		}
		//JC: Slot isn't empty, try next slot
		else if (hashKey < _arraySize
				&& _array[hashKey] != null)
		{
			//JC: iterate hashKey
			hashKey++;

			//JC: Recursive insert at the next empty slot
			//System.out.println("index of " + (hashKey-1) + " exists, Iterated to " + hashKey);
			index = insertObjByHash(hashKey, obj);
		}
		//JC: made it to the end of the array, check if full and start at beginning if not
		else if (!isFull() && hashKey >= _arraySize)
		{
			//System.out.println("Start at beginning of hash to insert into empty spaces.");
			
			//JC: Start at the beginning of the array to try to insert
			index = insertObjByHash(0, obj);
		}
		//JC: made it to the end of the array, increase size, better to have more space
		else if (isFull() && hashKey >= _arraySize)
		{
			System.out.println("Table is too small to add " + obj.getName() + ", increase size.");
			increaseArraySize(_arraySize * 2);
			
			//JC: Get new hashkey and insert into resized array
			hashKey = getHash(obj);
			index = insertObjByHash(hashKey, obj);
		}
		
		return index;
	}
	
	protected void deleteObj(T obj)
	{
		deleteObjByHash(-1, obj);
	}
	private void deleteObjByHash(int hashKey, T obj)
	{
		//JC: Set hashKey equal to default if it is not set
		if (hashKey < 0)
			hashKey = getHash(obj);
		
		
		//JC: try to match the object to one in the table
		int index = match(hashKey, obj);

		if (index >= 0 && index < _arraySize)
		{
			System.out.println("Removed " + ((Address)_array[hashKey]).getName() + 
					" from table at index " + hashKey + ".");

			//JC: set index to null
			_array[hashKey] = null;
			
			//JC: decrement count
			_count--;			

			System.out.println(this.toString());
		}
		else
		{
			System.out.println(obj.getName() + " could not be removed because they do not exist in table.");
			System.out.println(this.toString());
		}
	}
	protected int match(T obj)
	{
		return match(-1, obj);
	}
	@SuppressWarnings("unchecked")
	private int match(int hashKey, T obj)
	{			
		//JC: Set hashKey equal to default if it is not set
		if (hashKey < 0 && obj.getIndex() >= 0)
			hashKey = obj.getIndex();
		else if (hashKey < 0)
			hashKey = getHash(obj);
		
		//JC: Start at the first most likely place to find the object
		//JC: set potential match to an address object
		T aobj = (T)_array[hashKey];

		//JC: make a comparison with potential match 
		if (!isIndexNull(hashKey)
				&& aobj.compareTo((T)obj) == 0)
		{
			System.out.println("Found " + aobj.getName() + 
					" in table with index " + hashKey);
			System.out.println(this.toString());
			return hashKey;
		}
		//JC: If its not null but doesn't match, iterate the index to see if it was added somewhere down the line
		else if (!isIndexNull(hashKey)
				&& aobj.compareTo((T)obj) != 0
				&& hashKey != getHash(obj) - 1)
		{			
			hashKey++;
			
			return match(hashKey, obj);
		}
		//JC: We're at the end of the array looped from the hash point
		else if (hashKey == getHash(obj) - 1
				&& !isIndexNull(hashKey)
				&& aobj.compareTo((T)obj) != 0)
		{
			System.out.println("Could not find " + obj.getName() + " in table.");
			System.out.println(this.toString());
			return -1;
		}
		//JC: Check next index to see if there's a match
		else if (isIndexNull(hashKey)
				&& hashKey < _arraySize - 1)
		{
			hashKey++;
			return match(hashKey, obj);
		}
		//JC: if we're at the end of the array, loop back around
		else if (isIndexNull(hashKey)
				&& hashKey == _arraySize - 1)
		{
			hashKey = 0;
			return match(hashKey, obj);
		}
		else
		{
			return -1;
		}
	}
	private void increaseArraySize(int newMinSize)
	{
		//JC: Use a prime number for the array size
		Object[] oldArray = _array;
		int oldArraySize = _arraySize;
				
		_arraySize = getNextPrime(newMinSize);
		_array = new Object[_arraySize];
		
		//JC: Add old array to new with new hashes
		for(int i = 0; i < oldArraySize; i++)
		{
			@SuppressWarnings("unchecked")
			T old = (T)oldArray[i];
			
			if (old != null)
			{
				System.out.println(old.getName() + " had index of " + i + " reassigned.");
				insertObj(old);
			}
		}
		
		System.out.println("Increased table size from " + oldArraySize + " to " + _arraySize);
	}
	
	private boolean isPrime(int number)
	{
		//JC: Not prime if even
		if (number % 2 == 0)
			return false;
		
		//JC: iterate by uneven numbers to number squared starting at 3
		for (int i = 3; i * i <= number; i+=2)
		{
			//JC: isn't prime if there's no remainder for mod i
			if (number % i == 0)
				return false;
		}
		
		return true;
	}
	protected int getNextPrime(int minNumber)
	{
		//JC: find next prime after the min
		for (int i = minNumber; true; i++)
		{
			if (isPrime(i)) 
				return i;
		}
	}
	
	private int getHash(T obj)
	{
		int hash = obj.getKey() % _arraySize;
		//System.out.println(obj.getName() + " attempts index at " + hash);
		return hash;
	}

	
	private boolean isIndexNull(int index)
	{
		return (_array[index] == null) ? true : false;
	}

	protected Object[] getArray()
	{
		return _array;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String toString()
	{
		int i = 0;
		StringBuilder sb = new StringBuilder();
		sb.append("\n---------------------------------------------------------------------------------------------\n");		
		for (Object o : _array)
		{
			if (o != null)
			{
				sb.append((T)o);
				sb.append("\n");
			}
			
			i++;
		}
		sb.append("---------------------------------------------------------------------------------------------\n");	
		
		return sb.toString();
	}
	public boolean isFull()
	{
		return (_count == _arraySize) ? true : false;
	}
	public int count()
	{
		return _count;
	}
}
