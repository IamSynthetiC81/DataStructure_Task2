package src.PriorityQueue.DynamicImplementation;


import src.HeapOperations;
import src.Operations;
import src.Statistics.StatisticsInterface;

/**
 * This class implements a priority queue using a Max Heap.
 */
public class PQ_DynamicImplementation implements StatisticsInterface, Operations , HeapOperations {
    Node Root;

    private int NumberOfNodes;

    public PQ_DynamicImplementation() {
        Root = null;
        NumberOfNodes = 0;
    }

    boolean isPQ(Node node){
        if (node == null) {
            return true;
        }
        if(node.getLeft() != null && node.getLeft().getKey() > node.getKey()){
            return false;
        }
        if(node.getRight() != null && node.getRight().getKey() > node.getKey()){
            return false;
        }
        return isPQ(node.getLeft()) && isPQ(node.getRight());
    }
    
    private void Insert(int data) {
        if (NumberOfNodes == 0) {
            Root = new Node(data);
            NumberOfNodes++;
            return;
        }

        Node parentNode = getRightMostParentNode(NumberOfNodes + 1);
        Node newNode = new Node(data, parentNode);

        switch (parentNode.getChildCount()) {
            case 0:
                parentNode.setLeft(newNode);
                NumberOfNodes++;
                break;
            case 1:
                parentNode.setRight(newNode);
                NumberOfNodes++;
                break;
            case 2:
                assert (false);
                break;
            
        }       
        Sort(newNode);
    }

    private void Sort(Node node) {
        if (node == null) {
            return;
        }

        Node parent = node.getParent();
        if (parent != null) {

            int ParentKey = parent.getKey();
            int NodeKey = node.getKey();

            Increment(2);
            if (ParentKey < NodeKey) {
                Swap(parent, node, ParentKey, NodeKey);
                Sort(parent);
            }
        }
    }

    private void SortDown(Node node) {
        if (node == null) {
            return;
        }

        Node left = node.getLeft();
        Node right = node.getRight();
        int NodeKey = node.getKey();
        int LeftKey = -1;
        int RightKey = -1;
        
        if(left != null){
            Increment();
            LeftKey = left.getKey();
        }
        
        if(right != null) {
            Increment();
            RightKey = right.getKey();
        }
        
        if (left != null && LeftKey > NodeKey) {
            Swap(left, node, LeftKey, NodeKey);
            NodeKey = LeftKey;
            SortDown(left);
        }
        if (right != null && RightKey > NodeKey) {
            Swap(right, node, RightKey, NodeKey);
            SortDown(right);
        }
    }

    private void Swap(Node node1, Node node2, int key1, int key2) {
        node1.setKey(key2);
        node2.setKey(key1);
    }
    
    private Node getRightMostParentNode(int numberOfNodes) {
        if (numberOfNodes == 0 || numberOfNodes < 0) {
            assert (false) : "Heap is empty";
            return null;
        }
        Node current = Root;
        // binary of numberOfElements without leading zeros
        String asBinary = Integer.toBinaryString(numberOfNodes);
        // iterate from 2nd bit up to second to last bit
        for (int currentPos = 1; currentPos < asBinary.length() - 1; currentPos++) {
            if (asBinary.charAt(currentPos) == '0') {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
        }

        return current;
    }
    
    /**
     * Adds the specified element to the queue.
     * @param key the value to add.
     */
    public void Push(int key) {
        statistics.Start();
        
        Insert(key);
        
        statistics.End();
    }
    
    /**
     * Adds the specified elemetns to the queue.
     * @param keys the values to add.
     */
    public void Push(int[] keys) {
        statistics.Start();
        
        for (int key : keys) {
            Insert(key);
        }
        
        statistics.End();
    }
    
    /**
     * Returns the element at the head of the queue.
     * @return the element at the head of the queue.
     */
    public int Pull() {
        assert NumberOfNodes > 0 : "Heap is empty";
        int data = Root.getKey();

        if(NumberOfNodes == 1){
            Root = null;
            NumberOfNodes = 0;
            return data;
        }
        
        Node lastNode = getRightMostParentNode(NumberOfNodes);
        switch (lastNode.getChildCount()) {
            case 0:
                Root.setKey(lastNode.getKey());
                lastNode = null;
                break;
            case 1:
                Root.setKey(lastNode.getLeft().getKey());
                lastNode.setLeft(null);
                break;
            case 2:
                Root.setKey(lastNode.getRight().getKey());
                lastNode.setRight(null);
                break;
        }
        SortDown(Root);
        NumberOfNodes--;
        return data;
    }
    
}
