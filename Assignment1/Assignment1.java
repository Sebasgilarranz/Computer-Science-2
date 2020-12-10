// Sebastian Gilarranz
// COP 3503, Spring 2019
// Se171788
// Assignement 2 GenericBST

// ====================
// GenericBST: BST.java
// ====================
// Basic binary search tree (BST) implementation that supports insert() and
// delete() operations. This framework is provide for you to modify as part of
// Programming Assignment #2.


import java.io.*;
import java.util.*;


// This class called Node holds information of a 
// node including the data, left node, and right node.
class Node<T extends Comparable<T>>
{
	T data; 
	Node<T> left, right;

	// Class constructor which sets the data for a new node object.
	Node(T data)
	{
		this.data = data;
	}
}

public class GenericBST<T extends Comparable <T>>
{
	private Node<T> root;

	// Insert helper method.
	public void insert(T data)
	{
		root = insert(root, data);
	}

	// Actual insert method.
	private Node<T> insert(Node<T> root, T data)
	{
		// Base Case, if we find a null we will insert data here.
		if (root == null)
		{
			return new Node<T>(data);
		}
		// We are using compareTo. If data is less than root.data, it will return 
		// something less than 0.
		else if (data.compareTo(root.data) < 0)
		{
			// Recursevely call the left child of the current root.
			root.left = insert(root.left, data);
		}
		// We are using compareTo. If data is greater than root.data, it will return 
		// something greater than 0.
		else if (data.compareTo(root.data) > 0)
		{
			// Recursevely call the right child of the current root.
			root.right = insert(root.right, data);
		}
		else
		{
			// Stylistically, I have this here to explicitly state that we are
			// disallowing insertion of duplicate values. This is unconventional
			// and a bit cheeky.
			;
		}

		return root;
	}

	// Delete helper method.
	public void delete(T data)
	{
		root = delete(root, data);
	}

	// Actual delete method.
	private Node<T> delete(Node<T> root, T data)
	{
		// Base case, once we hit a null root, we return null.
		if (root == null)
		{
			return null;
		}
		// If data is smaller than root.data, recursevely call the root.left.
		else if (data.compareTo(root.data) < 0)
		{
			// Recursevely call to the left child of current root.
			root.left = delete(root.left, data);
		}
		// If data is larger than 
		else if (data.compareTo(root.data) > 0)
		{
			// Recursevely call to the right child of current root.
			root.right = delete(root.right, data);
		}
		// If a match is found...
		else
		{
			// If the target root has no children...
			if (root.left == null && root.right == null)
			{
				return null;
			}
			// If the target only had a right child...
			else if (root.left == null)
			{
				return root.right;
			}
			// If the target root only had a left child...
			else if (root.right == null)
			{
				return root.left;
			}
			// If the target root had two children, we will find the max of the left child
			// and replace so we can delete it.
			else
			{
				root.data = findMax(root.left);
				root.left = delete(root.left, root.data);
			}
		}

		return root;
	}

	// This method assumes root is non-null, since this is only called by
	// delete() on the left subtree, and only when that subtree is not empty.
	private T findMax(Node<T> root)
	{
		// We continue down the right side of a root to find its max node.
		while (root.right != null)
		{
			root = root.right;
		}

		return root.data;
	}

	// Contain helper method.
	public boolean contains(T data)
	{
		return contains(root, data);
	}

	// Actual Contain method.
	private boolean contains(Node<T> root, T data)
	{
		// If we arrive to base case, we didnt find a match, we return false.
		if (root == null)
		{
			return false;
		}
		// If data is smaller than root.data then we recursvely go down
		// left child.
		else if (data.compareTo(root.data) < 0)
		{
			return contains(root.left, data);
		}
		// If data is bigger than root.data then we recursvely go down 
		// right child.
		else if (data.compareTo(root.data) > 0)
		{
			return contains(root.right, data);
		}
		// If nothing happened in other if's, we found a match and return true.
		else
		{
			return true;
		}
	}

	// Inorder helper method.
	public void inorder()
	{
		System.out.print("In-order Traversal:");
		inorder(root);
		System.out.println();
	}

	// Actual inorder method.
	private void inorder(Node<T> root)
	{
		// Base case.
		if (root == null)
			return;

		// Prints the left, middle, then right, recursevely.
		inorder(root.left);
		System.out.print(" " + root.data);
		inorder(root.right);
	}

	// Preorder helper method.
	public void preorder()
	{
		System.out.print("Pre-order Traversal:");
		preorder(root);
		System.out.println();
	}

	// Actual Preorder method.
	private void preorder(Node<T> root)
	{
		// Base case.
		if (root == null)
			return;

		// Prints out the middle, left, then right, recursevely.
		System.out.print(" " + root.data);
		preorder(root.left);
		preorder(root.right);
	}

	// Postorder helper method.
	public void postorder()
	{
		System.out.print("Post-order Traversal:");
		postorder(root);
		System.out.println();
	}

	// Postorder helper method.
	private void postorder(Node<T> root)
	{
		// Base case.
		if (root == null)
			return;

		// Prints the left, right, then middle, recursevely.
		postorder(root.left);
		postorder(root.right);
		System.out.print(" " + root.data);
	}

	public static double difficultyRating()
	{
		return 3.0;
	}

	public static double hoursSpent()
	{
		return 5.0;
	}
}
