package src.BinarySearchTree.ArrayImplementation;


/**
 * This class represents an Array implementation of a BST.
 */
public class Table{
    final Integer[][] table;
    int Root = -1;

    enum Index{
        Info(0),
        Left(1),
        Right(2);

        private final int value;

        Index(int value){
            this.value = value;
        }
    }

    public Table(int Size){
        table = new Integer[Size][3];

        for(int i = 0 ; i < Size ; i++){
            if(i < Size - 1)
                table[i][Index.Right.value] = -(i + 1);
            else
                table[i][Index.Right.value] = null;
            table[i][Index.Info.value] = null;
            table[i][Index.Left.value] = null;
        }
    }

    /**
     * Find the number of children of the given index.
     * @param index The index of the node.
     * @return The number of children of the node.
     */
    int getChildrenNum(int index){
        int num = 0;
        if(table[index][Index.Left.value] != null){
            num++;
        }
        if(table[index][Index.Right.value] != null){
            num++;
        }
        return num;
    }

    /**
     * Finds the parent of the given index.
     * @param index The index of the node.
     * @return The index of the parent or -1 if the the node has not parent.
     */
    int findParent(int index){
        for(int i = 0 ; i < table.length; i++){                                           // For each line
            Integer right = getRightChild(i);                                             // Get the right child
            Integer left = getLeftChild(i);                                               // Get the left child

            if(right != null && right == index){                                          // If the right child is the index
                return i;                                                                 // Return the index of the line
            }
            if(left != null && left == index)                                              // If the left child is the index
                return i;                                                                 // Return the index of the line
        }
        return -1;                                                                        // Return -1 if the node has no parent
    }

    /**
     * Finds the line of available cells and follows it to the end.
     * @return The index of the next available cell.
     */
    int findVacancy(){
        int VacancyLine = -1;
        int previousVacancyLine = -1;

        for(int i = 0 ; i < table.length ; i++) {
            if (table[i][Index.Right.value] == null)                                      // If the pointer is null
                continue;                                                                 // Continue to the next line

            if (table[i][Index.Right.value] < 0){                                         // If the pointer is negative
                VacancyLine = i;                                                          // Set the line to the current line
                break;                                                                    // Break the loop
            }
            return -1;                                                                    // Return -1 if the tree is full  (no vacancy)
        }

        while(table[VacancyLine][Index.Right.value] != null){                             // Follow the line to the end
            assert table[VacancyLine][Index.Right.value] < 0;                             // Assert that the pointer is negative

            previousVacancyLine = VacancyLine;                                            // - Update the previous cell
            VacancyLine = (table[VacancyLine][Index.Right.value]) * (-1);                 // - Set the index to the next cell
        }
        if(table[VacancyLine][Index.Info.value] != null)                                  // If the cell is not empty
            return previousVacancyLine;                                                   // - Return the previously available cell

        return VacancyLine;                                                               // Return the index of the next available cell
    }

    /**
     * Find the Inorder successor of the given index.
     * @param index The index of the node.
     * @return The index of the Inorder successor or -1 if the node has no successor.
     */
    int findInorderSuccessor(int index){
        // step 1 of the above algorithm
        if (getLeftChild(index) != null) {
            return minValue(getLeftChild(index));
        }

        // step 2 of the above algorithm
        int parent = findParent(index);
        int current = index;
        while (parent != -1 && current == getRightChild(parent)) {
            current = parent;
            parent = findParent(parent);
        }
        return parent;                                                               // Return the successor
    }

    /**
     * Find the minimum value of the tree.
     * @param index The index of the node.
     * @return The index of the minimum value.
     */
    int minValue(int index) {
        int current = index;

        /* loop down to find the leftmost leaf */
        while (table[current][Index.Left.value] != null) {
            current = table[current][Index.Left.value];
        }
        return current;
    }

    /**
     * Traverse the tree to the right node for the value.
     * @param value The value to search.
     * @return The index of the node.
     */
    int Traverse(int value){
        int root = Root;
        if(root == -1)
            return findVacancy();
        while(true) {
            if (value < table[root][Index.Info.value]) {
                if (table[root][Index.Left.value] == null)
                    return root;
                else
                    root = table[root][Index.Left.value];
            } else {
                if (table[root][Index.Right.value] == null)
                    return root;
                else
                    root = table[root][Index.Right.value];
            }
        }
    }


    /**
     * Adds a node with the given value to the specified index as a child.
     * @param value the value of the node to be added
     * @param index the index of the parent node.
     * @throws IllegalArgumentException if the index is not found.
     */
    void Add(int value, int index) throws IllegalArgumentException{
        if(index < 0 || index >= table.length)                                           // If the index is out of bounds
            throw new IllegalArgumentException("Index " + index + " is not valid");      // - Throw an exception

        Integer ParentLeftNode  = table[index][Index.Left.value];                        // Get the parents left node
        Integer ParentRightNode = table[index][Index.Right.value];                       // Get the parents right node
        Integer ParentValue     = table[index][Index.Info.value];                        // Get the parents value
        int ChildIndex          = findVacancy();                                         // Find the index of the next available cell


        if(ParentValue == null) {                                                        // If the parent is empty (root)
            table[index][Index.Info.value] = value;                                      // - Set the parent to the value
            Root = index;                                                                // - Set the root to the index
            return;
        }

        if(value < ParentValue){                                                         // If the value is less than the parent value
            assert ParentLeftNode == null;                                               // - Assert that the parent has no left child
            table[index][Index.Left.value] = ChildIndex;                                 // - Set the left child of the parent to the index of the child
        } else {                                                                         // If the value is greater than the parent value
            assert ParentRightNode == null;                                              // - Assert that the parent has no right child
            table[index][Index.Right.value] = ChildIndex;                                // - Set the right child of the parent to the index of the child
        }

            table[ChildIndex][Index.Info.value] = value;                                 // - Set the value of the index
            table[ChildIndex][Index.Left.value] = null;                                  // - Set the left child of the index to null
            table[ChildIndex][Index.Right.value] = null;                                 // - Set the right child of the index to null
    }

    /**
     * Removes the node with the given index from the tree.
     * @param index The index of the node to be removed.
     * @throws IllegalArgumentException if the index is not valid.
     * TODO : When root is altered, must change the {@link #Root} variable.
     */
    void RemoveNode(int index) throws IllegalArgumentException{
        if(index < 0 || index >= table.length){                                         // Check if the index is valid
            throw new IllegalArgumentException("Index is not valid");                // If not, throw an exception
        }

        int parent = findParent(index);                                                 // Find the parent of the node

        switch (getChildrenNum(index)) {
            case 0 :
                assert (getChildrenNum(index) == 0);                                    // Assert that the node has no children

                int Vacancy = findVacancy();                                            // Find the next available vacancy
                if(Vacancy != -1)                                                       // If there is a vacancy
                    table[Vacancy][Index.Right.value] = index;                          // Set the right child of the vacancy to the node


                if (table[index][Index.Info.value] < table[parent][Index.Info.value]) { // If the node is smaller than its parent
                    table[parent][Index.Left.value] = null;                             // - Nullify the left node
                } else {                                                                // If the node is bigger than its parent
                    table[parent][Index.Right.value] = null;                            // - Nullify the right node
                }

                table[index][Index.Info.value] = null;                                  // nullify the node
                table[index][Index.Left.value] = null;                                  // nullify the node
                table[index][Index.Right.value] = null;                                 // Set as the last available vacancy

                break;
            case 1 :
                assert(getChildrenNum(index) == 1);                                     // Assert that the node has only one child

                int childIndex;

                if (table[index][Index.Left.value] != null) {                           // If the child is on the left
                    childIndex = table[index][Index.Left.value];                        // - Get the child index
                } else {                                                                // If the child is on the right
                    childIndex = table[index][Index.Right.value];                       // - Get the child index
                }

                if(parent != -1) {                                                      // If the node has a parent
                    if (table[index][Index.Info.value] < table[parent][Index.Info.value]) { // If the node is on the left
                        table[parent][Index.Left.value] = childIndex;                       // - Set the parent left child to the nodes child
                    } else {                                                                // If the node is on the right
                        table[parent][Index.Right.value] = childIndex;                      // - Set the parent right child to the nodes child
                    }
                }

                table[index][Index.Info.value] = null;                                  // Nullify the node
                table[index][Index.Left.value] = null;                                  // Nullify the node
                table[index][Index.Right.value] = null;                                 // Nullify the node

                int Vacancy2 = findVacancy();                                           // Find the next available vacancy
                if(Vacancy2 != -1)                                                      // If there is a vacancy
                    table[findVacancy()][Index.Right.value] = index;                    // - Set the vacancy to the node

                break;
            case 2 :
                assert (getChildrenNum(index) == 2);                                    // Assert that the node has two children

                int successor = findInorderSuccessor(index);                            // Find the inorder successor

                assert (successor != -1);                                               // If the successor is not found, throw an exception
                assert (getChildrenNum(successor) == 0);                                // If the successor has children, throw an exception

                Integer SuccessorValue = getInfo(successor);                            // Get the successor value
                RemoveNode(successor);                                                  // Remove the successor

                table[index][Index.Info.value] = SuccessorValue;                        // Copy the value of the successor to the node
                break;
        }
    }

    Integer getLeftChild(int index) {
        return table[index][Index.Left.value];
    }

    Integer getRightChild(int index) {
        return table[index][Index.Right.value];
    }

    Integer getInfo(int index) {
        return table[index][Index.Info.value];
    }

    /**
     * Deletes the node with the given value
     * @param value the value of the node to be deleted
     */
    public void Delete(int value){
        int index = Search(value);                                                      // Find the index of the value
        if(index == -1){                                                                // If the value is not found
            throw new IllegalArgumentException("Value is not found");                   // Throw an exception
        }
        RemoveNode(index);                                                              // Remove the node
    }

    /**
     * Adds a node with the given value to the tree
     * @param value the value of the node to be added
     */
    public void Append(int value){
        Add(value, Traverse(value));                                                    // Add the node to the tree
    }

    /**
     * Searches for the node with the given value
     * @param value the value of the node to be searched
     * @return the index of the node with the given value
     */
    public int Search(int value){
        for(int i = 0 ; i < table.length ; i++){                                        // For each node
            if(getInfo(i) != null && getInfo(i) == value){                              // If the value is found
                return i;                                                               // Return the index
            }
        }
        return -1;                                                                      // If the value is not found
    }

}
