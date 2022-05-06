package src;

public interface HeapOperations extends Operations {
    
    /**
     * Adds the specified values into the tree.
     * @param values the values to add.
     */
    public void Push(int[] values);
    
    /**
     * Pulls a value from the heap.
     * @return the value pulled.
     */
    public int Pull();
    
}
