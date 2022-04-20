package src.BinarySearchTree.DynamicMemory;

import src.Operations;
import src.Statistics.Statistics;

/**
 * This class represents a binary search tree.
 */
public class BST implements Operations{

    private Node Root;                                  // The root of the tree.
    private Statistics stats;                           // The statistics of the tree.

    /**
     * Constructs a new binary search tree.
     */
    public BST() {
        Root = null;
    }

    /**
     * Adds a new node to the tree.
     * @param node The node to add.
     * @throws IllegalArgumentException If node is null or data of node allready exist.
     */
    private void addNewNode(Node node) throws IllegalArgumentException {
        if(node == null) {
            throw new IllegalArgumentException("Node is null");
        }
        if (Root == null) {
            Root = node;
            return;
        }
        
        Node current = Root;
        Node parent = null;

        while (current != null) {
            parent = current;
            if(node.getInfo() == current.getInfo()) {
                throw new IllegalArgumentException("Node already exists");
            }
            if (node.getInfo() < current.getInfo()) {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
        }

        if (node.getInfo() < parent.getInfo()) {
            parent.setLeft(node);
        } else {
            parent.setRight(node);
        }
        
    }

    /**
     * Deletes a node from the tree.
     * @param node The node to delete.
     * @throws IllegalArgumentException If node is null or if node is root.
     */
    private void deleteNode(Node node) throws IllegalArgumentException{
        if (node == null) {
            throw new IllegalArgumentException("Node is null");
        }
        Node parent = node.getParent();
        if (parent == null) {
            throw new IllegalArgumentException("Node is root");
        }

        switch(node.getChildrenNum()){
            case 0 :                                        // node has no children
                if (node.getInfo() < parent.getInfo()) {    // If node is left child of its parent
                    parent.setLeft(null);                   // - replace node with null
                } else {                                    // If node is right child of its parent
                    parent.setRight(null);                  // - replace node with null
                }
            case 1:                                         // node has one child
                if (node.getInfo() < parent.getInfo()) {    // If node is left child of its parent
                    if (node.getLeft() != null) {           // - If node has left child
                        parent.setLeft(node.getLeft());     // - - replace node with its left child
                    } else {                                // - node has right child
                        parent.setLeft(node.getRight());    // - - replace node with its right child
                    }
                } else {                                    // If node is right child of its parent
                    if (node.getLeft() != null) {           // - If node has left child
                        parent.setRight(node.getLeft());    // - - replace node with its left child
                    } else {                                // - node has right child
                        parent.setRight(node.getRight());   // - - replace node with its right child
                    }
                }
                    
            }
    }


    @Override
    public Node Query(int info) throws IllegalArgumentException {
        stats.Start();                                     // Start measuring time

        Node current = Root;
        while (current != null) {
            if (current.getInfo() == info) {
                stats.End();                               // Stop measuring time
                return current;
            } else if (current.getInfo() < info) {
                current = current.getRight();
            } else {
                current = current.getLeft();
            }
        }
        stats.End();                                        // Stop measuring time
        return null;                                        // If node is not found return null
    }

    @Override
    public void Append(int info) throws IllegalArgumentException {
        stats.Start();                                      // Start timer
        Node node = new Node(info, 0, null);   // Create new node
        addNewNode(node);                                   // Add new node to tree

        stats.End();                                        // End of Append
    }

    @Override
    public void Delete(int info) throws IllegalArgumentException{
        stats.Start();                                      // Start timer

        Node node = Query(info);
        if (node != null) {
            deleteNode(node);
        }

        stats.End();                                        // End of Delete
    }

    Statistics getStats() {
        return stats;
    }


    
}
