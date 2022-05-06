package src.BinarySearchTree.DynamicImplementation;

import src.BSTOperations;
import src.Operations;
import src.Statistics.StatisticsInterface;

/**
 * This class represents a binary search tree.
 */
public class BST_DynamicImpementation implements StatisticsInterface, Operations , BSTOperations {

    Node Root;                                  // The root of the tree.

    /**
     * Constructs a new binary search tree.
     */
    public BST_DynamicImpementation() {
        Root = null;
    }
    
    /**
     * Finds the inorder successor of a node.
     * @param node The node to find the successor of.
     * @return The inorder successor of the node.
     */
    Node findInorderSuccessor(Node node) {
        Node current = node;
        Node successor = null;
        boolean found = false;
        while (!found) {
            if (current.getLeft() != null) {
                current = current.getLeft();
            } else {
                successor = current;
                found = true;
            }
        }
        return successor;
    }
    
    /**
     * Tests whether the tree is constructed correctly.
     * @param node The node to start from.
     * @return True if the tree is correct, false otherwise.
     */
    boolean isBST(Node node){
        if (node == null) {
            return true;
        }
        if (node.getLeft() != null && node.getLeft().getInfo() > node.getInfo()) {
            return false;
        }
        if (node.getRight() != null && node.getRight().getInfo() < node.getInfo()) {
            return false;
        }
        return isBST(node.getLeft()) && isBST(node.getRight());
    }
    
    /**
     * Inserts a node into the tree.
     *
     * @implNote This method is recursive. In extreme cases it can cause stack overflow
     *              unless the tree is balanced or the stack is increased significantly.
     *
     * @param node The node to insert.
     * @param value The value to insert.
     * @return The new root of the tree.
     */
    private Node Insert(Node node, int value){
        if (node == null) {
            return new Node(value, null);
        }
        Increment();
        
        int nodeInfo = node.getInfo();
        
        if (value < nodeInfo) {
            node.setLeft(Insert(node.getLeft(), value));
            node.getLeft().setParent(node);
        } else if (value > nodeInfo) {
            node.setRight(Insert(node.getRight(), value));
            node.getRight().setParent(node);
        }
        return node;
    }
    
    /**
     * Return the minimum value in the tree.
     * @param node The node to start from.
     * @return The minimum value in the tree.
     */
    private Node getMin(Node node) {
        if (node.getLeft() == null) {
            return node;
        }
        return getMin(node.getLeft());
    }
    
    /**
     * Deletes the minimum value in the tree.
     * @param node The node to start from.
     * @return The new root of the tree.
     */
    private Node deleteMin(Node node) {
        if (node.getLeft() == null) {
            return node.getRight();
        }
        node.setLeft(deleteMin(node.getLeft()));
        return node;
    }
    
    /**
     * Removes the specified node from the tree.
     * @param node The node to remove.
     * @return The new root of the tree.
     */
    private Node RemoveNode(Node node, int value) {
        if (node == null)
            return null;
        
        Increment();
        if(value < node.getInfo()) {
            node.setLeft(RemoveNode(node.getLeft(), value));
        } else if(value > node.getInfo()) {
            Increment();
            node.setRight(RemoveNode(node.getRight(), value));
        } else {
            if(node.getLeft() == null)
                return node.getRight();
            else if(node.getRight() == null)
                return node.getLeft();
            
            node.setInfo(getMin(node.getRight()).getInfo());
            node.setRight(RemoveNode(node.getRight(), node.getInfo()));
        }
        return node;
    }
    
    /**
     * Searches for the specified value in the tree.
     * @param value the value to search for.
     * @return Returns the {@link Node} containing the value if found, otherwise null.
     */
    public Node Search(int value) {
        Node current = Root;
        while (current != null) {
            if (current.getInfo() == value) {
                return current;
            } else if (current.getInfo() < value) {
                current = current.getRight();
            } else {
                current = current.getLeft();
            }
        }
        return null;
    }
    
    /**
     * A <i>Front</i> for the {@link #Insert(Node, int)} method. This helps the implementation
     * of the {@link src.Statistics.Statistics} functions.
     * @param value The value to insert.
     */
    public void Push(int value) {
        statistics.Start();                                      // Start timer
        
        Root = Insert(Root, value);
        
        statistics.End();                                        // End of Append
    }
    
    /**
     * Pulls the specified value from the tree.
     * @param value the value to pull.
     */
    public void Pull(int value){
        statistics.Start();                                      // Start timer
        
        Root = RemoveNode(Root, value);
        
        statistics.End();                                        // End of Delete
    }
    
}
