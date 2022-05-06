package src.PriorityQueue.DynamicImplementation;

public class Node{
    
    private int Key;
    private Node Left;
    private Node Right;
    
    private Node Parent;
    
    public Node(int Data) {
        this.Key = Data;
        this.Left = null;
        this.Right = null;
        this.Parent = null;
    }
    
    public Node(int Data, Node parent) {
        this.Key = Data;
        this.Left = null;
        this.Right = null;
        this.Parent = parent;
    }
    
    public int getKey() {
        return this.Key;
    }
    
    
    public void setKey(int Data) {
        this.Key = Data;
    }
    
    public Node getLeft() {
        return this.Left;
    }
    
    public void setLeft(Node Left) {
        this.Left = Left;
    }
    
    public Node getRight() {
        return this.Right;
    }
    
    public void setRight(Node Right) {
        this.Right = Right;
    }
    
    public Node getParent() {
        return this.Parent;
    }
    
    public void setParent(Node Parent) {
        this.Parent = Parent;
    }
    
    public int getChildCount() {
        int count = 0;
        if (this.Left != null) {
            count++;
        }
        if (this.Right != null) {
            count++;
        }
        return count;
    }
    
    public Node getMinChild() {
        int min = 0;
        if(this.Left != null){
            min = this.Left.getKey();
        }
        if(this.Right != null){
            if(this.Right.getKey() < min){
                return this.Right;
            }
        }
        return this.Left;
    }
    
    public Node getMaxChild() {
        int max = 0;
        if (this.Right != null) {
            max = this.Right.getKey();
        }
        if (this.Left != null) {
            if (this.Left.getKey() > max) {
                return this.Left;
            }
        }
        return this.Right;
    }
    
    public Node[] getChildren() {
        Node[] children = new Node[2];
        children[0] = this.Left;
        children[1] = this.Right;
        return children;
    }
}
