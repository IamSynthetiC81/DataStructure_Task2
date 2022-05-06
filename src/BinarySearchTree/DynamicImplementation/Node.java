package src.BinarySearchTree.DynamicImplementation;

/**
 * This class represents a node in a binary search tree.
 */
public class Node {

    protected Node LeftChild;
    protected Node Right;
    protected Node Parent;
    private int Info;


    Node(Integer info, Node parent) {
        Info = info;
        LeftChild = null;
        Right = null;

        Parent = parent;
    }
    
    /**
     * Returns the number of children of this node.
     * @return the number of children of this node.
     */
    public int getChildrenNumber() {
        int number = 0;
        if (LeftChild != null) {
            number++;
        }
        if (Right != null) {
            number++;
        }
        return number;
    }
    
    public Node getLeft() {
        return LeftChild;
    }

    public void setLeft(Node left) {
        LeftChild = left;
    }

    public Node getRight() {
        return Right;
    }
    
    public void setRight(Node right) {
        Right = right;  
    }

    public int getInfo() {
        return Info;
    }
    
    public void setInfo(int info) {
        Info = info;
    }

    public Node getParent() {
        return Parent;
    }

    public void setParent(Node parent) {
        Parent = parent;
    }

    public int getChildrenNum() {
        int num = 0;
        if (LeftChild != null)
            num++;
        if (Right != null)
            num++;
        return num;
    }

    public boolean isLeaf() {
        return (LeftChild == null && Right == null);
    }

    public void attach(Node node) {
        if (node.getInfo() < Info) {
            if (LeftChild == null) {
                LeftChild = node;
                node.setParent(this);
            } else {
                LeftChild.attach(node);
            }
        } else {
            if (Right == null) {
                Right = node;
                node.setParent(this);
            } else {
                Right.attach(node);
            }
        }
    }

}
