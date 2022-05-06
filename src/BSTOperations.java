package src;

public interface BSTOperations extends Operations{
    /**
     * Searches for the specified value in the tree.
     * @param value the value to search for.
     */
    public Object Search(int value);
    
    /**
     * Pulls the specified value from the tree.
     * @param value the value to pull.
     * @throws UnsupportedOperationException if the function is not supported.
     */
    public void Pull(int value);
}
