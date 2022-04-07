package src;
public class Table{
    private final int Size;

    public Integer[][] table;

    Table(int size) {
        Size = size;
        table = new Integer[size][3];
        init();
    }
    
    public void init() {
        for (int i = 0; i < Size; i++) {
            table[i][0] = -1;
            table[i][1] = -1;
            table[i][2] = i + 1; /* This node marks the next available node.
                                    To implement this, we mark each nodee, to point the 
                                    next one as available. The rest are handled through
                                    the function ReplaceNextNodePointer                 */

        }
    }

    public Integer[] getNode(int index) {
        return table[index];
    }

    private void ReplaceNextNodePointer(int index) {
        int pointer = table[index][2]; // points to the next available node.

        for (int i = index - 1 ; i >= 0; i--) {
            if (table[i][0] == -1) 
                table[i][2] = pointer;  /* replace the nodes pointer with the pointer from the
                                            Node we are replacing*/
        }
    }

    public void setNode(int index, Integer[] node) {
        ReplaceNextNodePointer(index);

        table[index] = node;
    }
    
    public void setNode(int index, Integer info, Integer left, Integer right) {
        ReplaceNextNodePointer(index);

        table[index] = new Integer[]{info, left, right};
    }

    /**
     * This function tries to find the root of the tree
     * @implNote This function tries to find the root of the tree
     * by selecting a Node, and then checking all other nodes for
     * an upward link. If a link is found, then that proccess is 
     * repeated for that node until no upward link is found.
     * @return {@code int} The index of the root node.
     * @throws IndexOutOfBoundsException If the tree has no root.<br>
     * <li> Either there is a problem with the program. 
     //TODO: Test the functionality of {@link #findRoot()}.
     * <li> Or there is a problem with the data.
     //TODO: Test the vunerability to incorect data.
     */
    public int findRoot() throws IndexOutOfBoundsException {

        int target = 0;

        /*  if cell is empty    */
        if (table[target][0] == -1)
            target++;

        if (target >= Size)
            throw new IndexOutOfBoundsException("No Root Found");

        for (int j = 0; j < Size; j++) {
            if (target == j)
                continue;

            if (table[j][1] == target || table[j][2] == target) {
                target = j;
                j = -1; // reset j                  
            }

        }

        return target;
    }

    public Integer[] getLeft(int index) {
        return table[table[index][1]];
    }

    public Integer[] getRight(int index) {
        return table[table[index][2]];
    }

    
}
