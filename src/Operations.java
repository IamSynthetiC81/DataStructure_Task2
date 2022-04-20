package src;

import src.BinarySearchTree.DynamicMemory.Node;

public interface Operations {

    /**
     * Searches for a node with the given info.
     * @param info The info of the node to search for.
     * @return The node with the given info;
     * @throws IllegalArgumentException If info is null or when the data
     * do not exist.
     */
    public Node Query(int info) throws IllegalArgumentException;

    /**
     * Inserts a new node with the given info.
     * @param info The info of the node to insert.
     * @throws IllegalArgumentException If info is null or when the data allready exist.
     */
    public void Append(int info) throws IllegalArgumentException;

    /**
     * Deletes a node with the given info.
     * @param info The info of the node to delete.
     * @throws IllegalArgumentException If info is null or when the data do not exist.
     */
    public void Delete(int info) throws IllegalArgumentException;
    
}
