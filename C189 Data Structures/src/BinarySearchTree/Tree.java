package BinarySearchTree;

import AddressBook.Address;

public abstract class Tree<T extends Address> {

	public Node<T> root;

	public Tree() {
		root = null;
	}

	protected void insertNode(Node<T> node) {
		// JC: Check if there is no root set and set it if not
		if (root == null) {
			root = node;
			System.out.println("Added " + node.getName() + " with key " + node.getKey() + " to root.");
		} else {
			// JC: keep track of the parent node and the current working node
			Node<T> current = root;
			Node<T> parent;

			while (true) {
				parent = current;

				// JC: check if the node to be inserted should go on left or right
				if (node.getKey() < current.getKey()) {
					current = current.getLeft();

					// JC: if the node is null then set it when the node's key is less than current,
					// recursively with while loop
					if (current == null) {
						parent.setLeft(node);

						System.out.println("Added " + node.getName() + " with key " + node.getKey() + " to the left of "
								+ parent.getName() + " with key " + parent.getKey());
						return;
					}
				} else {
					current = current.getRight();

					// JC: if the node is null then set it when the node's key is greater than
					// current, recursively with while loop
					if (current == null) {
						parent.setRight(node);

						System.out.println("Added " + node.getName() + " with key " + node.getKey()
								+ " to the right of " + parent.getName() + " with key " + parent.getKey());

						return;
					}
				}
			}
		}
	}

	// JC: Print out different forms of tree traversal
	@Override
	public String toString() {
		return traverse(Traversals.InOrder);
	}

	public String traverse(Traversals type) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("\n---------------------------------------------------------------------------------------------");	
		sb.append(traverseNode(root, type));
		sb.append("\n---------------------------------------------------------------------------------------------\n");
		
		return sb.toString();
	}

	private String traverseNode(Node<T> node, Traversals type) {
		StringBuilder sb = new StringBuilder();
		// JC: understanding traversal for class
		if (node != null) {
			switch (type) {
			case PreOrder:
				sb.append(node.getObj().toString());
				sb.append(traverseNode(node.getLeft(), type));
				sb.append(traverseNode(node.getRight(), type));
				break;
			case PostOrder:
				sb.append(traverseNode(node.getLeft(), type));
				sb.append(traverseNode(node.getRight(), type));
				sb.append(node.getObj().toString());
				break;
			default:
				sb.append(traverseNode(node.getLeft(), type));
				sb.append("\n");
				sb.append(node.getObj().toString());
				sb.append(traverseNode(node.getRight(), type));
				break;
			}
		}
		
		return sb.toString();
	}

	protected Node<T> findNodeByKey(int searchKey) {
		Node<T> current = root;

		while (current.getKey() != searchKey) {
			if (searchKey < current.getKey()) {
				current = current.getLeft();
			} else {
				current = current.getRight();
			}

			if (current == null)
				return null;
		}

		return current;
	}

	protected boolean deleteNodeByKey(int delKey) {
		//JC: Start at the top of the tree
		Node<T> current = root;
		Node<T> parent = root;

		//JC: know whether you're on the left or right
		boolean isLeft = true;

		// JC: search while the key has not been found
		while (current.getKey() != delKey) {
			//JC: set parent equal to current working node and 
			//JC: figure out which side you're on 
			//JC: set current working node
			
			parent = current;
			if (delKey < current.getKey()) {
				isLeft = true;
				current = current.getLeft();
			} else {
				isLeft = false;
				current = current.getRight();
			}

			if (current == null)
				return false;
		}

		// JC: there are no children of this node, just delete
		if (current.isLeaf()) {
			if (current == root)
				root = null;
			//JC: Delete current from parent 
			else if (isLeft)
				parent.setLeft(null);
			else
				parent.setRight(null);
		} 
		//JC: if there is no right child, move left child into place
		else if (current.getRight() == null) {
			if (current == root)
				root = current.getLeft();
			else if (isLeft)
				parent.setLeft(current.getLeft());
			else
				parent.setRight(current.getLeft());
		}
		//JC: if there is no left child, move right child into place
		else if (current.getLeft() == null) 
		{
			if (current == root)
				root = current.getRight();
			else if (isLeft)
				parent.setLeft(current.getRight());
			else
				parent.setRight(current.getLeft());
		}
		//JC: two children involved, figure out which side should be replaced in root, left & right
		else 
		{
			Node<T> replacer = replaceNode(current);

			if (current == root)
				root = replacer;
			else if (isLeft)
				parent.setLeft(replacer);
			else
				parent.setRight(replacer);

			//JC: replaced child should have current left 
			replacer.setLeft(current.getLeft());
		}

		return true;
	}

	protected Node<T> replaceNode(Node<T> node) {
		Node<T> parent = node;
		Node<T> replacer = node;
		
		//JC: move right child up and replace, then assign left
		//JC: always replace with right child
		Node<T> current = node.getRight();

		//JC: set the recursive replacements for parent and current

		while (current != null) {
			parent = replacer;
			replacer = current;
			current = current.getLeft();
		}

		//JC: Move replacer's right into parent's left child, move node's right into replacer's right
		if (replacer != node.getRight()) {
			parent.setLeft(replacer.getRight());
			replacer.setRight(node.getRight());
		}

		return replacer;
	}
	
	public int getCount()
	{
		return root.getNodeCount();
	}
	public int getMin()
	{
		return root.getMinNode().getKey();
	}
	public int getMax()
	{
		return root.getMaxNode().getKey();
	}
}
