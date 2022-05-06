package src.PriorityQueue.ArrayImplementation;

import src.HeapOperations;
import src.Operations;
import src.Statistics.Statistics;
import src.Statistics.StatisticsInterface;

/** this is the implementation of the priority queue using an array
 */
public class PQ_ArrayImplementation implements StatisticsInterface, Operations, HeapOperations {

    private static final int LEFT = 1;
    private static final int RIGHT = 2;
    
    private final int[][] array;
    
    private int NumberOfElements;
    
    private final int Size;


    public PQ_ArrayImplementation(int size) {
        Size = size;
        array = new int[size][3];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < 3; j++) {
                array[i][j] = -1;
            }
        }

        NumberOfElements = 0;
    }
    
    /**
     * Calculates the correct index for the left child of the node.
     * @param pos the position of the node.
     * @return the index of the left child.
     * TODO: check if the index is within limit or throw.
     */
    private int LeftChildPos(int pos) {
        if (2 * pos + 1 < Size)
            return (2 * pos) + 1;
        else
            return -1;
    }
    
    /**
     * Calculates the correct index for the right child of the node.
     * @param pos the position of the node.
     * @return the index of the right child.
     * TODO: check if the index is within limit or throw.
     */
    private int RightChildPos(int pos) {
        if (2 * pos + 2 < Size)
            return (2 * pos) + 2;
        else
            return -1;
    }

    /**
     * Getter for the key of a node.
     * @param pos the position of the node.
     * @return the key of the node.
     * @implNote calling this increments the counter of the {@link Statistics} object.
     */
    private int getKey(int pos) {
        statistics.Increment();
        return array[pos][0];
    }

    /**
     * Setter for the key of a node.
     * @param pos the position of the node.
     * @param key the key to set.
     */
    private void setKey(int pos, int key) {
        array[pos][0] = key;
    }
    
    /**
     * Get the number of children of a <i>node</i>.
     * @param pos the node
     * @return the number of children.
     */
    private int ChildrenNum(int pos){
        int count = 0;
        if(array[pos][LEFT] != -1)
            count++;
        if(array[pos][RIGHT] != -1)
            count++;
        return count;
    }
    
    private int getMaxHeight(){
        return (int) (Math.log(Size) / Math.log(2));
    }
    
    private int getHeight(int pos) {
        for (int i = 0; i < getMaxHeight(); i++) {
            if (pos >= Math.pow(2, i) - 1 && pos < Math.pow(2, i + 1) - 1)
                return i;
        }
        return -1;
    }
    
    /**
     * Calculates the correct index for the next (right) cousin of the node.
     * @param pos the position of the node.
     * @return the index of the next cousin.
     * @implNote Cousin : is the node that is on the same level as the node but
     *          come from a diferent parent.
     */
    private int getNextSibling(int pos) {
        int height = getHeight(pos);
        pos++;
        if (pos >= Math.pow(2, height) - 1 && pos < Math.pow(2, height + 1) - 1)
            return pos;
        else
            return -1;
    }
    
    /**
     * Calculates the correct index for the in
     * @param pos
     * @return
     */
    private int getParentForNextRow(int pos){
        int height = getHeight(pos);
        return (int) (Math.pow(2,height) -1);
    }
    
    private void Insert(int pos, int data){
        if (NumberOfElements == 0) {
            setKey(0, data);
            NumberOfElements++;
            return;
        }
        
        int NodePos = getRightMostParentNode(NumberOfElements + 1);
        
        switch (ChildrenNum(NodePos)) {
            case 0:
                array[NodePos][LEFT] = LeftChildPos(NodePos);
                setKey(LeftChildPos(NodePos), data);
                bubbleUp(LeftChildPos(NodePos));
                break;
            case 1:
                array[NodePos][RIGHT] = RightChildPos(NodePos);
                setKey(RightChildPos(NodePos), data);
                bubbleUp(RightChildPos(NodePos));
                break;
            case 2:
                int sibling = getNextSibling(NodePos);
                if (sibling == -1) {
                    int parent = getParentForNextRow(NodePos);
                    int child = LeftChildPos(parent);
                    assert ChildrenNum(child) == 0;
                    int newNode = LeftChildPos(child);
                    array[child][LEFT] = newNode;
                    setKey(newNode, data);
                    bubbleUp(newNode);
                } else {
                    assert ChildrenNum(sibling) == 0;
                    array[sibling][LEFT] = LeftChildPos(sibling);
                    setKey(LeftChildPos(sibling), data);
                    bubbleUp(LeftChildPos(sibling));
                }

        }
        NumberOfElements++;
    }
    
    void bubbleUp(int pos){
        int parentIndex = (pos -1) / 2;
        int currentIndex = pos;
        int parentKey = getKey(parentIndex);
        int currentKey = getKey(currentIndex);

        while (currentIndex > 0 && parentKey < currentKey) {
            swap(currentIndex, parentIndex, currentKey, parentKey);
            currentIndex = parentIndex;
            parentIndex = (parentIndex - 1) / 2;
            parentKey = getKey(parentIndex);
            currentKey = getKey(currentIndex);
        }
    }
    
    void swap(int pos1, int pos2, int pos1Key, int pos2Key) {
        setKey(pos1, pos2Key);
        setKey(pos2, pos1Key);
    }
    
    /**
     * Sort the selected <i>node</i> and its children.
     * @param pos the <i>node</i> to sort
     */
    private void Sort(int pos) {
        int currentKey;
        int leftChildPos;
        int leftChildKey;
        int rightChildPos;
        int rightChildKey;
        switch (ChildrenNum(pos)) {
            case 0:                                                             // - if the node has no children
                break;                                                          // - - do nothing
            case 1:                                                             // - if the node has one child
                                                                        
                                                                                // Balance the tree
                if(array[pos][LEFT] == -1 && array[pos][RIGHT] != -1) {         // - if the left child is empty and the right child is not
                    array[pos][LEFT] = array[pos][RIGHT];                       // - - set the left child to the right child
                    array[pos][RIGHT] = -1;                                     // - - set the right child to -1
                    assert (false) : "The tree is not balanced";
                }
                
                currentKey = getKey(pos);
                leftChildPos = LeftChildPos(pos);
                leftChildKey = getKey(LeftChildPos(pos)); 

                if(currentKey < leftChildKey) {                                 // - - if the node is smaller than the left child
                    swap(pos, leftChildPos, currentKey, leftChildKey);          // - - - swap the node and the left child
                    Sort(array[pos][LEFT]);                                     // - - - sort the left child
                }
                break;
            case 2:                                                             // - if the node has two children
        
                currentKey = getKey(pos);
                leftChildPos = LeftChildPos(pos);
                leftChildKey = getKey(leftChildPos);
                rightChildPos = RightChildPos(pos);
                rightChildKey = getKey(rightChildPos);
                
                
                if(currentKey < leftChildKey) {                                 // - - if the data is smaller than the left child
                    swap(pos, leftChildPos, currentKey, leftChildKey);          // - - - Swap the data and the left child
                    Sort(array[pos][LEFT]);                                     // - - - Sort the left child
                    currentKey = leftChildKey;                                  // - - - Update the current key      
                    leftChildKey = currentKey;                                  // - - - Update the left child key        
                }
                if (currentKey < rightChildKey) {                               // - if the data is smaller than the right child
                    swap(pos, rightChildPos, currentKey, rightChildKey);        // - - swap the data and the right child
                    Sort(array[pos][RIGHT]);                                    // - - sort the right child
                }
                break;
        }
    }
    
    /**
     * Gets the parent of the rightmost <i>node</i> using the
     * method shown during the lessons.
     * @return the parent of the rightmost <i>node</i>
     */
    private int getRightMostParentNode(int numberOfElements) {
        int current = 0;
        // binary of numberOfElements without leading zeros
        String asBinary = Integer.toBinaryString(numberOfElements);
        // iterate from 2nd bit up to second to last bit
        for (int currentPos = 1; currentPos < asBinary.length() - 1; currentPos++) {
            if (asBinary.charAt(currentPos) == '0') {
                current = array[current][LEFT];
            } else {
                current = array[current][RIGHT];
            }
        }
        
        return current;
    }
    
    /**
     * Add a new value to the heap.
     * @param data the value to be added.
     */
    @Override
    public void Push(int data) {
        statistics.Start();
        
        Insert(0, data);
        
        statistics.End();
    }
    
    /**
     * Adds the specified values to the heap.
     * @param data the values to add.
     * @TODO: implement this method
     */
    @Override
    public void Push(int[] data) {
        return;
    }
    
    /**
     * Remove the max value from the heap
     * @return the max value
     */
    @Override
    public int Pull() {
        statistics.Start();
        
        assert NumberOfElements > 0 : "Heap is empty";
        int data = getKey(0); // get the data from the root
        
        int node = getRightMostParentNode(NumberOfElements); // get the rightmost parent node
        if (ChildrenNum(node) == 0) {
            node = getRightMostParentNode(NumberOfElements - 1);
        }
        
        switch (ChildrenNum(node)) { // check the number of children                                                       // - and return the data of the root
            case 1: // if the node has one child
                if (array[node][LEFT] != -1) { // - if the left child is not empty
                    swap(0, LeftChildPos(node), -1, getKey(LeftChildPos(node))); // - - swap the root and the left child
                    array[node][LEFT] = -1; // - - set the left child of the parent to -1
                } else { // - if the left child is empty
                    swap(0, RightChildPos(node), -1, getKey(RightChildPos(node))); // - - swap the root and the right child
                    array[node][RIGHT] = -1; // - - set the right child of the parent to -1
                }
                break;
            case 2:
                int RightChildKey = getKey(RightChildPos(node));
                
                swap(0, RightChildPos(node), -1, RightChildKey); // - if the right child is greater than the left child
                array[node][RIGHT] = -1; // - set the pointer of the parent to the child to -1
                
                break;
        }
        
        NumberOfElements--; // decrement the number of elements
        Sort(0); // sort the tree
        statistics.End(); // end the statistics
        return data; // return the data of the former root.
    }
    
    public boolean isPQ(int index) {
        if(index == -1)
            return true;
        if ( LeftChildPos(index) != -1 && getKey(LeftChildPos(index)) > getKey(index) )
            return false;
        if ( RightChildPos(index) != -1 && getKey(RightChildPos(index)) > getKey(index) )
            return false;
        return isPQ(LeftChildPos(index)) && isPQ(RightChildPos(index));
    }
}
