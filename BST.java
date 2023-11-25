import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class BST<T extends Comparable<T>> implements Iterable<T> {

  class BSTNode implements Comparable<BSTNode> {

    private T data;
    private BSTNode left;
    private BSTNode right;

    public BSTNode(T d) {
      setLeft(null);
      setRight(null);
      setData(d);
    }

    public T getData() {
      return data;
    }

    public void setData(T d) {
      data = d;
    }

    public void setLeft(BSTNode l) {
      left = l;
    }

    public void setRight(BSTNode r) {
      right = r;
    }

    public BSTNode getLeft() {
      return left;
    }

    public BSTNode getRight() {
      return right;
    }

    /**
     * Checks if the current node is a leaf node.
     * A leaf node is defined as a node with no children, i.e., both its left and right children are null.
     *
     * @return true if the current node is a leaf node, false otherwise.
     */
    public boolean isLeaf() {
      return (getLeft() == null) && (getRight() == null);
    }

    /**
     * Compares this node with another node in terms of their data.
     * The comparison is based on the natural ordering of the data elements.
     *
     * @param o The other BSTNode to be compared with this node.
     * @return A negative integer, zero, or a positive integer as this node's data
     *         is less than, equal to, or greater than the specified node's data.
     */
    public int compareTo(BSTNode o) {
      return this.getData().compareTo(o.getData());
    }
  }

  /**
   * Iterator for a binary search tree (BST) that traverses the tree in an inorder manner.
   * This private class implements the Iterator interface.
   *
   * @param <T> The type of elements held in the BST.
   */
  private class BSTIteratorInorder implements Iterator<T> {

	/**
	 * Constructs a new BSTIteratorInorder.
	 * Initializes the traversal of the BST starting from the root in in-order sequence.
	 */
    public BSTIteratorInorder() {
      queue.clear();
      traverse(root, INORDER);
    }
    
    /**
     * Checks if the next element is available in the traversal.
     *
     * @return true if there are more elements to iterate over; false otherwise.
     */
    @Override
    public boolean hasNext() {
      return !queue.isEmpty();
    }

    /**
     * Returns the next element in the inorder traversal of the BST.
     *
     * @return the next element in the traversal.
     * @throws NoSuchElementException if the iteration has no more elements.
     */
    @Override
    public T next() {
      return queue.remove();
    }

  }

  private BSTNode root;
  private int size;
  private Comparator<T> comparator;
  private Queue<T> queue = new LinkedList<>();

  private final int INORDER = 0;
  private final int PREORDER = 1;
  private final int POSTORDER = 2;
  private final int LEVELORDER = 3;

  public BST() {
    root = null;
    comparator = null;
    size = 0;
  }

  /**
   * Constructs an empty BST with a specified comparator.
   * 
   * @param comparator The comparator to be used for ordering the BST.
   */
  public BST(Comparator<T> comparator) {
    root = null;
    this.comparator = comparator;
    size = 0;
  }

  /**
   * Add element d to the tree.
   */
  public void add(T d) {
    BSTNode n = new BSTNode(d);
    if (root == null) {
      root = n;
      size++;
    } else {
      add(root, n);
    }
  }

  /**
   * Internal method to add a node to the BST.
   * 
   * @param root The root node of the BST.
   * @param n    The node to be added.
   */
  private void add(BSTNode root, BSTNode n) {
    if (root == null) {
      return;
    }
    int result = compare(n.getData(), root.getData());

    // Check if the item already exists in the BST.
    if (result == 0) {
      return;
    }

    // Item isn't a match, so we recurse through the tree.
    if (result < 0) {
      if (root.getLeft() == null) {
        root.setLeft(n);
        size++;
      } else {
        add(root.getLeft(), n);
      }
    } else {
      if (root.getRight() == null) {
        root.setRight(n);
        size++;
      } else {
        add(root.getRight(), n);
      }
    }
  }

  /**
   * Deletes an element from the BST.
   * 
   * @param data The element to be deleted.
   */
  public void delete(T data) {
    BSTNode n = new BSTNode(data);
    delete(root, n);
    size--;
  }

  /**
   * Internal method to delete a node from the BST.
   * 
   * @param root The root node of the BST.
   * @param n    The node to be deleted.
   * @return     The root of the modified subtree.
   */
  private BSTNode delete(BSTNode root, BSTNode n) {
    T data = find(n.getData(), root);
    int c = n.compareTo(root);
    if (root == null) {
      return null;
    }
    if (data != null) {
      if (c < 0) {
        root.setLeft(delete(root.getLeft(), n));
      } else if (c > 0) {
        root.setRight(delete(root.getRight(), n));
      } else {
        if (root.getLeft() == null) {
          return root.getRight();
        } else if (root.getRight() == null) {
          return root.getLeft();
        }
        //in-order successor
        root.setData(findMin(root.getRight()));
        root.setRight(delete(root.getRight(), n));
      }
    }

    return root;
  }

  /**
   * Finds an element in the BST.
   * 
   * @param d The element to find.
   * @return  The element if found, null otherwise.
   */
  public T find(T d) {
    return find(d, root);
  }

  /**
   * Internal method to find an element in a subtree.
   * 
   * @param d    The element to find.
   * @param root The root of the subtree.
   * @return     The element if found, null otherwise.
   */
  private T find(T d, BSTNode root) {
    if (root == null) {
      return null;
    }
    int c = d.compareTo(root.getData());
    if (c == 0) {
      return root.getData();
    } else if (c < 0) {
      return find(d, root.getLeft());
    } else {
      return find(d, root.getRight());
    }
  }

  /**
   * Finds the minimum element in a subtree.
   * 
   * @param root The root of the subtree.
   * @return     The minimum element.
   */
  private T findMin(BSTNode root) {
    T minVal = root.getData();
    while (root.getLeft() != null) {
      minVal = root.getLeft().getData();
      root = root.getLeft();
    }
    return minVal;
  }

  /* Implement a height method. */

  /**
   * Returns the height of the BST.
   * 
   * @return The height of the BST.
   */
  public int height() {
    return height(root);
  }

  /**
   * Internal method to calculate the height of a subtree.
   * 
   * @param r The root of the subtree.
   * @return  The height of the subtree.
   */
  private int height(BSTNode r) {
    int h = -1;

    if (r == null) {
      return h;
    } else if (r.isLeaf()) {
      h = 0;
    } else {
      int leftHeight = 0;
      int rightHeight = 0;
      if (r.getLeft() != null) {
        leftHeight = height(r.getLeft());
      }
      if (r.getRight() != null) {
        rightHeight = height(r.getRight());
      }
      h = Math.max(leftHeight, rightHeight) + 1;
    }
    return h;
  }

  /**
   * Visits a node during traversal.
   * 
   * @param r The node to visit.
   */
  private void visit(BSTNode r) {
    if (r != null) {
      queue.add(r.getData());
    }
  }
  
  
  /**
   * Traverses the BST in a specified order.
   * 
   * @param traversalType The type of traversal (e.g., INORDER, PREORDER).
   */
  public void traverse(int traversalType) {
    traverse(root, traversalType);
  }

  /**
   * Internal method to traverses the BST in a specified traversal order.
   *
   * @param root           The root node of the BST.
   * @param traversalType  The type of traversal to perform (e.g., INORDER, PREORDER, POSTORDER, LEVELORDER).
   */
  private void traverse(BSTNode root, int traversalType) {
    if (root == null) {
      return;
    }
    switch (traversalType) {
      case INORDER:
        inOrderTraversal(root);
        break;
      case PREORDER:
        preOrderTraversal(root);
        break;
      case POSTORDER:
        postOrderTraversal(root);
        break;
      case LEVELORDER:
        levelOrderTraversal(root);
    }
  }

  /**
   * Performs an in-order traversal of the BST.
   * In this traversal, the left subtree is visited first, then the current node, and finally the right subtree.
   *
   * @param r The root node of the current subtree.
   */
  private void inOrderTraversal(BSTNode r) {
    if (r == null) {
      return;
    }
    else {
      inOrderTraversal(r.getLeft());
      visit(r);
      inOrderTraversal(r.getRight());
    }
  }

  /**
   * Performs a pre-order traversal of the BST.
   * In this traversal, the current node is visited first, then the left subtree, and finally the right subtree.
   *
   * @param r The root node of the current subtree.
   */
  private void preOrderTraversal(BSTNode r) {
    if (r == null) {
      return;
    }
    else {
      visit(r);
      preOrderTraversal(r.getLeft());
      preOrderTraversal(r.getRight());
    }
  }

  /**
   * Performs a post-order traversal of the BST.
   * In this traversal, the left subtree is visited first, then the right subtree, and finally the current node.
   *
   * @param r The root node of the current subtree.
   */
  private void postOrderTraversal(BSTNode r) {
    if (r == null) {
      return;
    }
    else {
      postOrderTraversal(r.getLeft());
      postOrderTraversal(r.getRight());
      visit(r);
    }
  }

  /**
   * Performs a level-order traversal of the BST.
   * In this traversal, nodes are visited level by level from top to bottom.
   *
   * @param r The root node of the BST.
   */
  private void levelOrderTraversal(BSTNode r) {
    Queue<BSTNode> queue = new LinkedList<BSTNode>();
    if (r == null) {
      return;
    }
    queue.add(r);

    while (!queue.isEmpty()) {
      BSTNode temp = queue.poll();
      System.out.println(r.getData());
      if (temp.getLeft() != null) {
        queue.add(temp.getLeft());
      }

      if (temp.getRight() != null) {
        queue.add(temp.getRight());
      }
    }
  }

  /**
   * Return the number of nodes in the tree.
   */

  public int size() {
    return size;
  }

  /**
   * Compares two elements using the BST's comparator or natural ordering.
   * 
   * @param t1 The first element to compare.
   * @param t2 The second element to compare.
   * @return   A negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second.
   */
  private int compare(T t1, T t2) {
    if (comparator != null) {
      return comparator.compare(t1, t2);
    } else {
      return t1.compareTo(t2);
    }
  }
  
  /**
   * Returns an iterator over elements of type {@code T}.
   * 
   * @return An Iterator.
   */
  @Override
  public Iterator<T> iterator() {
    return new BSTIteratorInorder();
  }
}
