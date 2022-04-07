package src;
public class TaskA {

    Table table;

    int root;
    int Size;

    public TaskA(int size) {
        Size = size;
        table = new Table(size);
    }
    
    /**
     * Adds a new node into the specified node of the tree.
     * @param info The info of the node.
     * @param parentNode The index of the parent node.
     * @throws IndexOutOfBoundsException If the parentNode is larger than the 
     * size of the tree.
     */
    private void addNewNode(int info, int parentNode) throws IndexOutOfBoundsException {
        if (parentNode > Size)
            throw new IndexOutOfBoundsException("Parent Node is out of bounds");

        Integer[] node = table.getNode(parentNode);

        if (info < node[0]) {
            table.setNode(node[1], info, -1, -1);
        } else {
            table.setNode(node[2], info, -1, -1);
        }
    }

    public void Append(int info) {
        Integer[] target = table.getNode(root);
        int index = root;


        if (target[1] == -1 & target[2] == -1) {
            // If the target node has no children, then add the new node as a child.
            if (info < target[0]) {         // If the new node is less than the target node.
                addNewNode(info,target[1]); // Add the new node as a left child.
            } else {                        // If the new node is greater than the target node.
                addNewNode(info,target[2]); // Add the new node as a right child.
            }
        } else {
            if (info < target[0]) {
                target = table.getLeft(index);
            }
        }
        
    
        

    }


}
