package src.BinarySearchTree.ArrayImplementation;

import org.junit.jupiter.params.provider.ValueSource;
import src.BSTOperations;
import src.Operations;
import src.Statistics.Statistics;
import src.Statistics.StatisticsInterface;
/**
 * This class represents an Array implementation of a BST_DynamicImpementation.
 */
public class BST_ArrayImplementation implements StatisticsInterface, Operations , BSTOperations {
    /** The table of the BST_ArrayImplementation. */
    final Integer[][] table;
    /** The Root of the array. */
    int Root = 0;
    /** The next available node. */
    private static Integer Avail;
    /** The index for the left child. */
    private final static int Left = 1;
    /** The index for the right child. */
    private final static int Right = 2;
    
    private void IncrementAvail(){
        if(Avail != null){
            Integer temp = Avail;
            Avail = Right(Avail);
            Right(temp, null);
        }
        
    }
    /**
     * Setter for the left child of the <i>node</i> at the given index.
     * @param index the index of the node.
     * @param value the value to set the left child to.
     */
    private void Left(int index, Integer value) {
        table[index][Left] = value;
    }
    /**
     * Getter for the left child of the <i>node</i> at the given index.
     * @param index index of the node.
     * @return the index of the left <i>node</i> or -1 if there is no left child.
     */
    Integer Left(int index) {
        return table[index][Left];
    }
    /**
     * Getter for the right child of the <i>node</i> at the given index.
     * @param index the index of the node.
     * @param value the value to set the right child to.
     */
    private void Right(int index, Integer value) {
        table[index][Right] = value;
    }
    /**
     * Getter for the right child of the <i>node</i> at the given index.
     * @param index the index of the node.
     * @return the index of the right <i>node</i> or -1 if there is no left child.
     */
    Integer Right(int index) {
        return table[index][Right];
    }

    /**
     * Getter for the value of the <i>node</i> at the specified index.
     * @param index the index of the node to get the value of.
     * @return the value of the node at the specified index.
     * @implNote Increments the counter of the statistics object.
     */
    Integer Value(int index) {
        statistics.Increment();
        return table[index][0];
    }
    /**
     * Setter for the value of a <i>node</i> in the table.
     * @param index index of the node to set the value of
     * @param value value to set the node to
     */
    void Value(int index, Integer value) {
        table[index][0] = value;
    }
    /**
     * Constructs a new BST_ArrayImplementation.
     * @param Size The size of the table.
     * @implNote The table is initialized with null values except for the Right Column.
     *           The Right Column is initialized with negative values, each holding the
     *           index of the next available node, but negative.
     */
    public BST_ArrayImplementation(int Size) {
        table = new Integer[Size][3];                                                   // Create the table
        Root = -1;
        
        for(int i = 0 ; i < Size ; i++){                                                // Initialize the table.
            if(i == Size -1)                                                            // If we are at the last node
                table[i][Right] = null;                                                 // Set the right child to null
            else                                                                        // - If the index is the last one
                table[i][Right] = i + 1;                                                // - - Set the right child to null to indicate the end of the available nodes.
            
            Value(i, null);                                                       // - Set the information to null.
            table[i][Left] = null;                                                      // - Set the left child to null.
        }
        
        Avail = 0;                                                                      // Set the first available node to 0.
    }
    /**
     * Find the number of children of the given index.
     * @param index The index of the node.
     * @return The number of children of the node.
     */
    int getChildrenNum(int index){
        int num = 0;
        if(table[index][Left] != null){
            num++;
        }
        if(table[index][Right] != null){
            num++;
        }
        return num;
    }
    /**
     * Finds the parent of the given index.
     * @param index The index of the node.
     * @return The index of the parent or -1 if the the node has not parent.
     * @implNote This is a linear algorithm.
     * TODO: Implement a more efficient algorithm.
     */
    int getParent(int index) {
        int current = Root;
        int parent = -1;
        int value = Value(index);

        if(index == Root)
            return -1;

        while (true) {
            if(value == Value(current)){
                return parent;
            }
            if (value < Value(current)) {
                if (table[current][Left] == null)
                    return -1;
                else
                    current = table[current][Left];
            } else {
                if (table[current][Right] == null)
                    return -1;
                else
                    current = table[current][Right];
            }
        }
    }
    /**
     * Find the Inorder successor of the given index.
     * @param index The index of the node.
     * @return The index of the Inorder successor or -1 if the node has no successor.
     */
    int findInorderSuccessor(int index){
        
        if (Left(index) != null) {                                           // If the node has a left child  (has a successor)
            return minValue(Left(index));                                    // Return the index of the left child
        }

        // step 2 of the above algorithm
        int parent = getParent(index);                                              // Find the parent of the node (the node's successor)
        int current = index;                                                         // Set the current index to the node's index
        while (parent != -1 && current == Right(parent)) {                   // While the parent is not null and the current index is the right child of the parent
            current = parent;                                                        // - Set the current index to the parent index.
            parent = getParent(parent);                                             // - Find the parent of the parent.
        }                                                                            // - Repeat the process
        return parent;                                                               // Return the successor
    }
    /**
     * Get the minimum value of the <i>node</i>
     * @param index The index of the node.
     * @return The index of the minimum value.
     */
    int minValue(int index) {
        if(Left(index) == null)
            return index;
        else
            return minValue(Left(index));
    }
    
    /**
     * Adds a node with the given value to the specified index as a child.
     * @param value the value of the node to be added.
     * @throws IllegalArgumentException if the index is not found.
     *
     * @hidden This method is the Core function of the tree. The
     *         {@link #Push(int)} acts as a front for easier implementation
     *         of the {@link Statistics} functions.
     *
     */
    int Add(int index, int value) throws IllegalArgumentException {
        if(Avail == null)
            return -1;
        
        Integer current, parent;
        
        if (index == -1) {
            Value(Avail, value);
            Root = Avail;
            IncrementAvail();
            return index;
        } else {
            current = index;
        }
        
        while (true) {
            parent = current;
            if (value < Value(current)) {
                current = Left(current);
                if (current == null) {
                    Left(parent, Avail);
                    Value(Avail, value);
                    IncrementAvail();
                    return index;
                }
            } else if (value > Value(current)) {
                current = Right(current);
                if (current == null) {
                    Right(parent, Avail);
                    Value(Avail, value);
                    IncrementAvail();
                    return index;
                }
            }
        }
    
        
        
        
//
//        Integer current = Root;
//        Integer currentValue = Value(current);
//        int parent = -1;
//
//        Boolean isLeft = null;
//
//        while(currentValue != null){
//            if (value == currentValue) {
//                throw new IllegalArgumentException("The value is already in the tree.");
//            }
//            parent = current;
//
//            if(value < currentValue){
//                current = Left(current);
//                isLeft = true;
//            } else {
//                current = Right(current);
//                isLeft = false;
//            }
//            if(current == null){
//                break;
//            }
//            currentValue = Value(current);
//        }
//
//        if(parent == -1){
//            Value(Root, value);
//        } else if(isLeft) {
//            Value(Avail, value);
//            Left(parent, Avail);
//        } else {
//            Value(Avail, value);
//            Right(parent, Avail);
//        }
//        IncrementAvail();
    }
    
    /**
     * Removes the node with the given index from the tree.
     * @param index The index of the node to be removed.
     * @param key The key of the node to be removed.
     * 
     * @hidden This method is the Core function of the tree. The
     *          {@link #Pull(int)} acts as a front for easier implementation
     *          of the {@link Statistics} functions.
     */
    Integer RemoveNode(Integer index, int key){
        if(index == null)
            return null;
            
        if(key < Value(index)){
            Left(index, RemoveNode(Left(index), key));
        } else if(key > Value(index)){
            Right(index, RemoveNode(Right(index), key));
        } else {
            if (Left(index) == null ){
                return Right(index);
            } else if (Right(index) == null ){
                return Left(index);
            }
            int minValue = Value(minValue(Right(index)));
            Value(index, minValue);
            Right(index, RemoveNode(Right(index), Value(index)));
        }
        return index;
    }
    /**
     * Deletes the node with the given value from the tree.
     * @param value the value of the node to be deleted
     * @hidden Front for the {@link #RemoveNode(Integer, int)} function.
     */
    public void Pull(int value) {
        statistics.Start();
        
        RemoveNode(Root, value);                                           // Remove the node
        
        statistics.End();
    }
    /**
     * Adds a node with the given value to the tree
     * @param value the value of the node to be added
     * @hidden Front for the {@link #Add(int, int)} function.
     */
    public void Push(int value) {

        statistics.Start();

        Add(Root, value);                                                         // Add the node to the tree
        
        statistics.End();
    }
    
    /**
     * Searches for the node with the given value
     * @param value the value of the node to be searched
     * @return the index of the node with the given value
     */
    public Integer Search(int value) {
        statistics.Start();
        
        int current = Root;                                                 // Start at the root
        Integer val = Value(current);                                       // Get the value of the current node
        
        while(val != null) {                                              // While the current node is not the root
            if (val == value) {                                  // - If the current node has the value
                statistics.End();                                           // - - End the statistics
                return current;                                             // - - Return the index of the node
            } else if (val < value) {                            // - If the current node has a value less than the value
                current = Right(current);                                   // - - Go to the right child
            } else {                                                        // - If the current node has a value greater than the value
                current = Left(current);                                    // - - Go to the left child
            }
            val = Value(current);                                           // - Get the value of the current node
        }
        statistics.End();
        return -1;                                                          // If the value is not found
    }
    
    boolean isBST(Integer root) {
        if (root == null) {
            return true;
        }
        if (Left(root) != null && Value(Left(root)) >= Value(root)) {
            System.out.println("Left child of " + Value(root) + " is " + Value(Left(root)) + " greater than parent");
            return false;
        }
        if (Right(root) != null && Value(Right(root)) <= Value(root)) {
            System.out.println("Right child of " + Value(root) + " is " + Value(Right(root)) + " less than parent");
            return false;
        }
        return isBST(Left(root)) && isBST(Right(root));
    }
}
