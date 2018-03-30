package BinarySearchTree;

import AddressBook.IKey;

public interface INode<T extends IKey> {	
	int getKey();
	T getObj();
	void setObj(T val);
	
	boolean hasLeft();
	INode<T> getLeft();
	void setLeft(INode<T> obj);	

	boolean hasRight();
	INode<T> getRight();
	void setRight(INode<T> obj);
	
	boolean isFull();
	boolean isLeaf();
	
	int getHeight();
	int getNodeCount();
	INode<T> getMinNode();
	INode<T> getMaxNode();
	
	INode<T> copy();
	void clear();
}
