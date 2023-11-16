public class BST<T extends Comparable<T>> {

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

  private BSTNode root;
  private int size;

  public BST() {
    root = null;
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
    //change
    return d;
  }

  private void add(BSTNode n, BSTNode root) {}

  /* Implement a height method. */
  private int height(BSTNode r) {
    int h = -1;

    // TODO
    return h;
  }

  private void visit(BSTNode r) {
    if (r != null) System.out.println(r.getData());
  }

  /**
   * Return the number of nodes in the tree.
   */

  public int size() {
    return size;
  }

  /**
   * Return true if element d is present in the tree.
   */
  public T find(T d) {
    return find(d, root);
  }

  /**
   * Add element d to the tree.
   */
  public void add(T d) {
    BSTNode n = new BSTNode(d);
    if (root == null) {
      root = n;
    } else {
      add(root, n);
    }
    size++;
  }

  /**
   * Return the height of the tree.
   */
  public int height() {
    return height(root);
  }
}
