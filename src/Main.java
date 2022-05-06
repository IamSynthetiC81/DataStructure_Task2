package src;

import src.BinarySearchTree.ArrayImplementation.BST_ArrayImplementation;
import src.BinarySearchTree.DynamicImplementation.BST_DynamicImpementation;
import src.Files.FileOps;
import src.PriorityQueue.ArrayImplementation.PQ_ArrayImplementation;
import src.PriorityQueue.DynamicImplementation.PQ_DynamicImplementation;
import src.Statistics.StatisticsInterface;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

public class Main {
    
    static int[] InsertionKeys;
    static int[] DeletionKeys;
    
    
    
    public static void main(String[] args) throws IOException {
        
        FileOps DataFile = new FileOps("src/Files/keys_1000000_BE.bin");
        FileOps DelFile  = new FileOps("src/Files/keys_del_100_BE.bin");
        
        InsertionKeys = DataFile.Read();
        DeletionKeys = DelFile.Read();
        
        PQ_ArrayImplementation PQ_Array = new PQ_ArrayImplementation(InsertionKeys.length);
        PQ_DynamicImplementation PQ_Dynamic = new PQ_DynamicImplementation();
        BST_ArrayImplementation BST_Array = new BST_ArrayImplementation(InsertionKeys.length);
        BST_DynamicImpementation BST_Dynamic = new BST_DynamicImpementation();
        
        TestBT(BST_Array, "Binary Search Tree Array Implementation");
        TestBT(BST_Dynamic, "Binary Search Tree Dynamic Implementation");
        
        TestHeap(PQ_Array," Priority Queue Array Implementation");
        TestHeap(PQ_Dynamic," Priority Queue Dynamic Implementation");
        
        
    }
    
    private static <T extends HeapOperations & StatisticsInterface> void TestHeap(T Heap, String HeapName) {
        Heap.Reset();
        
        /* Pushing one-by-one   */
        Instant start = Instant.now();
        for (int datum : InsertionKeys) {
            Heap.Push(datum);
        }
        
        Duration OneByOneAppendDuration = Duration.between(start, Instant.now());
        int AppendComparisons = Heap.getCounter();
        
        Heap.Reset();
        
        /* Pushing all at once */
        Heap.Push(InsertionKeys);
        
        Duration AllAtOnceAppendDuration = Heap.getDuration();
        int AllAtOnceAppendComparisons = Heap.getCounter();
        
        Heap.Reset();
        
        /* Deleting one-by-one */
        start = Instant.now();
        for (int i = 0 ; i < DeletionKeys.length ; i++) {
            Heap.Pull();
        }
        
        Duration OneByOneDeleteDuration = Duration.between(start, Instant.now());
        int DeleteComparisons = Heap.getCounter();
        
        Heap.Reset();
        
        System.out.println("====\t " + HeapName + " \t====");
        System.out.println("One-by-one append: " + OneByOneAppendDuration.toMillis() + " ms");
        System.out.println("\tComparisons: " + AppendComparisons/InsertionKeys.length);
        
        System.out.println("\nAll at once: " + AllAtOnceAppendDuration.toMillis() + " ms");
        System.out.println("\tComparisons: " + AllAtOnceAppendComparisons/InsertionKeys.length);
        
        System.out.println("\nOne-by-one delete: " + OneByOneDeleteDuration.toMillis() + " ms");
        System.out.println("\tComparisons: " + DeleteComparisons/DeletionKeys.length);
    }
    
    private static <T extends BSTOperations & StatisticsInterface> void TestBT(T BT, String BTName) {
        BT.Reset();
        
        /* Pushing one-by-one   */
        Instant start = Instant.now();
        for (int datum : InsertionKeys) {
            BT.Push(datum);
        }
        
        Duration OneByOneAppendDuration = Duration.between(start, Instant.now());
        int AppendComparisons = BT.getCounter();
        
        BT.Reset();
        
        /* Deleting one-by-one */
        start = Instant.now();
        for (int deletionKey : DeletionKeys)
            BT.Pull(deletionKey);
        
        Duration OneByOneDeleteDuration = Duration.between(start, Instant.now());
        int DeleteComparisons = BT.getCounter();
        
        BT.Reset();
        
        System.out.println("====\t " + BTName + " \t====");
        System.out.println("One-by-one append: " + OneByOneAppendDuration.toMillis() + " ms");
        System.out.println("\tComparisons: " + AppendComparisons/InsertionKeys.length);
        
        System.out.println("\nOne-by-one delete: " + OneByOneDeleteDuration.toMillis() + " ms");
        System.out.println("\tComparisons: " + DeleteComparisons/DeletionKeys.length);
    }
    
}

