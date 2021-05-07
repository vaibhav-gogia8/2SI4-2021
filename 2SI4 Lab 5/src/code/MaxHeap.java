package code;

import java.util.Arrays;
import java.util.Objects;

public class MaxHeap{
	private Integer[] heap;
	private int sizeH;  //size of the heap
	private int sizeA;  //size of the array
	
	//creates an empty heap with the given array size
	//O(1) time and O(n) space complexity, n = size
	
	public MaxHeap(int sizeH) {
		if (sizeH<=0) {
			throw new IllegalArgumentException("size cant be less than 1");
		}
		this.heap = new Integer[sizeH];
		this.sizeH=0;
		this.sizeA=heap.length;
	} 
	
	//creates a maxHeap to store the data from the input array
	//worst case time: O(nlogn) 
	//avg case time: O(n)
	
	public MaxHeap(Integer[] someArray) {
		if (someArray == null) {
			throw new IllegalArgumentException("argument cannot be null");
		}
		if (someArray.length == 0) {
			throw new IllegalArgumentException("array cant be empty");
		}
		
		this.heap = new Integer[someArray.length];
		this.sizeH = 0;
		this.sizeA =  heap.length;
		
		//removing the null elements from the input array and inserting the rest
		Arrays.stream(someArray).filter(Objects::nonNull).forEach(this::insert);
	}
    
	
	//inserts the value n in this maxHeap at the correct spot
	//worst case time: O(logn) time comp
	//avg case: O(1) time comp
	public void insert(int n) {
        if (sizeH == sizeA) {
            Integer[] grow = new Integer[sizeA *= 2];
            System.arraycopy(heap, 0, grow, 0, heap.length);
            heap = grow;
        }

        // Insert new element
        heap[sizeH] = n;

        // Move element to correct spot
        int currentPos = sizeH++, parentPos;
        while (heap[currentPos] > heap[parentPos = getParentIdx(currentPos)]) {
            swap(currentPos, parentPos);
            currentPos = parentPos;
        }
    }
	
	//deletes the item with the largest value and return that value
	//O(log n) time and O(1) space complexity
	private int deleteMax() {
        int num = heap[0];
        heap[0] = heap[--sizeH];

        // Move root back down
        int pos = 0;
        while (pos < sizeH / 2) {
            int left = getLeftIdx(pos), right = getRightIdx(pos);

            // comparing the left, right childern to perform swapping
            if (heap[pos] < heap[left] || heap[pos] < heap[right]) {
                if (heap[left] > heap[right]) {
                    swap(pos, left);
                    pos = left;
                } else {
                    swap(pos, right);
                    pos = right;
                }
            } else {
                break;
            }
        }

        return num;
    }
	
	@Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        for (int i = 0; i < sizeH; i++) {
            s.append(heap[i]).append(",");
        }

        return s.toString();
    }
	
	//O(nlogn) time and O(n) space complexity
	public static void heapsort(Integer[] arrayToSort) {
        MaxHeap sorted = new MaxHeap(arrayToSort.length);	//initiate a heap array to sort
		MaxHeap tosort = new MaxHeap(arrayToSort);			//initiate a heap array to store the Integer array
		for(int i=0; i<arrayToSort.length; i++) {			//a for loop delete the node and sort it
			sorted.heap[i] = tosort.deleteMax();			
		}
		
		for(int i=0; i<arrayToSort.length; i++) {			//copying the values from the sorted array into the original array
			arrayToSort[i] = sorted.heap[i];
		}
    }
	
    public Integer[] getHeap() {
        return heap;
    }

    public int getSizeArr() {
        return sizeA;
    }

    public int getSizeHeap() {
        return sizeH;
    }
	//extra methods used:
	// O(1) time and space complexity
    private int getParentIdx(int pos) {
        return (pos - 1) / 2;
    }

    // O(1) time and space complexity
    private int getLeftIdx(int pos) {
        return 2 * pos + 1;
    }

    // O(1) time and space complexity
    private int getRightIdx(int pos) {
        return 2 * pos + 2;
    }

    // O(1) time and space complexity
    private void swap(int idx1, int idx2) {
        int temp = heap[idx1];
        heap[idx1] = heap[idx2];
        heap[idx2] = temp;
    }
}
