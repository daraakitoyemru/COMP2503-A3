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

    public boolean isLeaf() {
      return (getLeft() == null) && (getRight() == null);
    }

    //go over this later
    public int compareTo(BSTNode o) {
      return this.getData().compareTo(o.getData());
    }
  }

  private class BSTIteratorInorder implements Iterator {

    public BSTIteratorInorder() {
      queue.clear();
      traverse(root, INORDER);
    }

    @Override
    public boolean hasNext() {
      return !queue.isEmpty();
    }

    @Override
    public Object next() {
      return queue.remove();
    }
  }

  private BSTNode root;
  private int size;
  private Comparator<T> comparator;
  private Queue<T> queue = new LinkedList<T>();

  private final int INORDER = 0;
  private final int PREORDER = 1;
  private final int POSTORDER = 2;
  private final int LEVELORDER = 3;

  public BST() {
    root = null;
    comparator = null;
    size = 0;
  }

  public BST(Comparator<T> comparator) {
    root = null;
    this.comparator = comparator;
    size = 0;
  }

  /*
   * TODO:
   *    add copy constructors
   *    add traversal methods
   *    add any help methods
   *    make sure BST is iterable
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
   * Return true if element d is present in the tree.
   */
  public T find(T d) {
    return find(d, root);
  }

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

  private int compare(T t1, T t2) {
    if (comparator != null) {
      return comparator.compare(t1, t2);
    } else {
      return t1.compareTo(t2);
    }
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

  public void delete(T data) {
    BSTNode n = new BSTNode(data);
    delete(root, n);
    size--;
  }

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

  private T findMin(BSTNode root) {
    T minVal = root.getData();
    while (root.getLeft() != null) {
      minVal = root.getLeft().getData();
      root = root.getLeft();
    }
    return minVal;
  }

  /* Implement a height method. */
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
   * Return the height of the tree.
   */
  public int height() {
    return height(root);
  }

  private void visit(BSTNode r) {
    if (r != null) {
      queue.add(r.getData());
    }
  }

  public void traverse(int traversalType) {
    traverse(root, traversalType);
  }

  private void traverse(BSTNode root, int traversalType) {
    if (root == null) {
      return;
    }
    switch (traversalType) {
      case INORDER:
        inOrder(root);
        break;
      case PREORDER:
        preOrder(root);
        break;
      case POSTORDER:
        postOrder(root);
        break;
      case LEVELORDER:
        levelOrderTraversal(root);
    }
  }

  public void traverseRight() {
    traverseRight(root);
  }

  private void traverseRight(BSTNode root) {
    if (root != null) {
      traverseRight(root.getRight());
      System.out.println(root.getData());
    }
  }

  private void inOrder(BSTNode r) {
    if (r == null) {
      return;
    }
    else {
      inOrder(r.getLeft());
      visit(r);
      inOrder(r.getRight());
    }
  }

  private void preOrder(BSTNode r) {
    if (r == null) {
      return;
    }
    else {
      visit(r);
      preOrder(r.getLeft());
      preOrder(r.getRight());
    }
  }


  private void postOrder(BSTNode r) {
    if (r == null) {
      return;
    }
    else {
      postOrder(r.getLeft());
      postOrder(r.getRight());
      visit(r);
    }
  }

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

  @Override
  public Iterator<T> iterator() {
    return new BSTIteratorInorder();
  }
}
