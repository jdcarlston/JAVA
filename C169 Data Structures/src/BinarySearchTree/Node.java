package BinarySearchTree;

import AddressBook.Address;

public class Node<T extends Address> implements INode<T>
{
    private T _obj;
    private Node<T> _left, _right;
	private int _count = 0;

    public Node()
    {
    	//Empty Node
    	this(null);
    }
    public Node(T obj)
    {
    	//Leaf Object
    	this(obj, null, null);
    }
    public Node(T obj, Node<T> left, Node<T> right)
    {
    	//Full Node
    	_obj = obj;
        _left = left;
        _right = right;
    }

    public T getObj()
    {
         return _obj;
    }
    public void setObj(T node)
    {
    	_obj = node;
    }
    
    public int getKey()
    {
         return _obj.getKey();
    }
    public String getName()
    {
         return _obj.getName();
    }
    
    public Node<T> getLeft()
    {
         return hasLeft() ? (Node<T>)_left : null;
    }
    public void setLeft(INode<T> left)
    {
    	_left = (Node<T>)left;
    }
    public boolean hasLeft()
    {
         return _left != null;
    }
    
    public Node<T> getRight()
    {
         return hasRight() ? (Node<T>)_right : null;
    }
    public void setRight(INode<T> right)
    {
    	_right = (Node<T>)right;
    }
    public boolean hasRight()
    {
         return _right != null;
    }
    
    public int getHeight()
    {
    	return getHeight(this);
    }
    private int getHeight(Node<T> node)
    {
	    int height = 0;
	    if (node != null)
	       height = 1 + Math.max(getHeight(node.getLeft()),getHeight(node.getRight()));
	    return height;
    }
    
    //JC: one possible node count
	public int getNodeCount() {
		_count = 0;
		getNodeCount(this);
		return _count;
	}
	protected void getNodeCount(Node<T> node) {
		if (node != null) {
			_count++;
			getNodeCount(node.getLeft());
			getNodeCount(node.getRight());
		}
	}
	
    //JC: other possible node count
//    public int getNodeCount()
//    {
//	    return getNodeCount(this);
//    }
//    private int getNodeCount(Node<T> node)
//    {
//	    int leftNumber = 0;
//	    int rightNumber = 0;
//	    if (node.getLeft() != null)
//	       leftNumber = node.getLeft().getNodeCount();
//	    if (node.getRight() != null)
//	       rightNumber = node.getRight().getNodeCount();
//	    return 1 + leftNumber + rightNumber;
//    }

    public Node<T> getMinNode() {
		return getMinNode(this);
	}
	protected Node<T> getMinNode(Node<T> node) {
		Node<T> current = node;

		while (current.getLeft() != null) {
			current = current.getLeft();
		}

		return current;
	}

	public Node<T> getMaxNode() {
		return getMaxNode(this);
	}
	protected Node<T> getMaxNode(Node<T> node) {
		Node<T> current = node;

		while (current.getRight() != null) {
			current = current.getRight();
		}

		return current;
	}

    public Node<T> copy()
    {
    	Node<T> newRoot = new Node<T>(_obj);
	    if (_left != null)
	         newRoot.setLeft((Node<T>)_left.copy());
	    if (_right != null)
	         newRoot.setRight((Node<T>)_right.copy());
	    return newRoot;
    }

	public boolean isFull()
	{
		return (_left != null) && (_right != null);
	}
	public boolean isLeaf()
	{
		return (_left == null) && (_right == null);
	}

	public void clear()
	{
		_obj = null;
		_left = null;
		_right = null;
	}
}
