package lab3;
import java.util.Arrays;

//Vaibhav Gogia
//400253615
//gogiav


public class BSTSet {
	private TNode root;
	
	public BSTSet() {
		root=null;
	}
	
	
	/*to be sure we have the minimum height BST, we have to sort the input array and then remove the duplicate
	 * After that we can implement the BST like operations
	 * Since I am using merge sort, the time complexity is O(nlogn) and the space comp isO(n)
	 */
	public BSTSet(int[] input) {
		if (input == null || input.length == 0) {
			root = null;
			return;
		}
		input = MergeSort(input);
		input = remDuplicates(input);
		int mid = input.length/2;
		
		root = new TNode(input[mid], null, null);
		leftNode(root, Arrays.copyOfRange(input, 0, mid));
		rightNode(root, Arrays.copyOfRange(input,mid+1,input.length));
	}
	
	//this method removes duplicate values from the sorted array
	// O(n) time and space complexity
    private int[] remDuplicates(int[] input) {
        int[] result = new int[input.length];
        int dup = result[0] = input[0];
        int Index = 1;

        for (int k = 1; k < input.length; k++) {
            if (dup != input[k]) {
                dup = result[Index++] = input[k];
            }
        }

        if (Index != input.length) {
            return Arrays.copyOfRange(result, 0, Index);
        }

        return result;
    }
    
 // Merge sort has a time comp of O(nlogn) and a space comp of  O(n)
    private int[] MergeSort(int[] data) {
        if (data.length <= 1) {
            return data;
        }
        int[] left, right;

        // Check if number of elements is even, if yes, we create two equal size arrays to store left and right hand sides respectively, else make
        // right 1 bigger than left
        if (data.length % 2 == 0) {
            left = new int[data.length / 2];
            right = new int[data.length / 2];
        } else {
            left = new int[data.length / 2];
            right = new int[data.length / 2 + 1];
        }

        // dividing the original array into left and right parts
        for (int i = 0; i < data.length; i++) {
            if (i < data.length / 2) {
                left[i] = data[i];
            } else {
                right[i - data.length / 2] = data[i];
            }
        }

        // Recursively split and then combine the arrays
        return combine(MergeSort(left), MergeSort(right));
    }
    
    private int[] combine(int[] left, int[] right) {
        int[] answer = new int[left.length + right.length];
        int m = 0, n = 0, index = 0;

        // Add elements to result in order
        while (m < left.length && n < right.length) {
        	if(left[m]<right[n]) {
        		answer[index++] = left[m++];
        	}
        	else {
        		answer[index++] = right[n++];
        	}
        }

        // Add any remaining elements
        while (m < left.length) {
            answer[index++] = left[m++];
        }

        while (n < right.length) {
            answer[index++] = right[n++];
        }

        return answer;
    }
    
    
 // leftNode() and rightNode() have O(n) time and space complexities because they have to go thru every node
    private void leftNode(TNode parent, int[] left) {
        if (left.length == 0) {
            return;
        }
        int mid = left.length / 2;
        parent.left = new TNode(left[mid], null, null);
        leftNode(parent.left, Arrays.copyOfRange(left, 0, mid));
        rightNode(parent.left, Arrays.copyOfRange(left, mid + 1, left.length));
    }

    private void rightNode(TNode parent, int[] right) {
        if (right.length == 0) {
            return;
        }
        int mid = right.length / 2;
        parent.right = new TNode(right[mid], null, null);
        leftNode(parent.right, Arrays.copyOfRange(right, 0, mid));
        rightNode(parent.right, Arrays.copyOfRange(right, mid + 1, right.length));
    }
    
    
    //time comp for this method is O(log n) avg, while the worst case is O(n) with a space comp of O(1) (BST)
    public boolean isIn(int v) {
    	if (root == null) { //set is empty
    		return false;
    	}
    	if (root.element == v ) {
    		return true;            //root is v
    	}
    	
    	TNode temp = root;

        while (temp != null) {
            if (v < temp.element) {
                temp = temp.left;
            } else if (v > temp.element) {
                temp = temp.right;
            } else {
                return true;
            }
        }

        return false;
    }
    
    //time complexity is O(log n) avg and O(n) worst, space comp is O(1)
    public void add(int v) {
    	if (root == null) {
    		root = new TNode(v, null, null);
    		return;
    	}
    	if (root.element == v) {
    		return;
    	}
    	TNode temp = root, prev = null;

        while (temp != null) {
            if (v < temp.element) {
                prev = temp;
                temp = temp.left;
            } else if (v > temp.element) {
                prev = temp;
                temp = temp.right;
            } else {
                return;
            }
        }

        if (v < prev.element) {
            prev.left = new TNode(v, null, null);
        } else {
            prev.right = new TNode(v, null, null);
        }
    }
    
    
    //time comp for remove method is O(logn) avg and O(n) worst case.
    //Space comp is O(logn)
    public boolean remove(int v) {
    	if (root == null) {
            return false;
        }

        if (isIn(v)) {
            root = remove(root, v);
            return true;
        }

        return false;
    }
    
    //this part of the code was taken from the lecture slides (topic 4)
    
    private TNode remove(TNode node, int val) {
        if (node == null) {
            return null;
        }

        if (val < node.element) {
            node.left = remove(node.left, val);
        } else if (val > node.element) {
            node.right = remove(node.right, val);
        } else if (node.left != null && node.right != null) {
            node.element = findMax(node.left).element;
            node.left = removeMax(node.left);
        } else {
            node = (node.left != null) ? node.left : node.right;
        }

        return node;
    }
 
    //finds the largest node in the tree and returns it
    private TNode findMax(TNode N) {
        TNode temp = N;

        while (temp.right != null) {
            temp = temp.right;
        }

        return temp;
    }
    
    //finds the largest node is the tree and removes it, returns the next largest
    private TNode removeMax(TNode N) {
        if (N.right == null) {
            return N.left;
        }

        N.right = removeMax(N.right);
        return N;
    }

    
 // Time complexity is O(x log x) where x = n + m because of the constructor, space complexity is O(x)
    public BSTSet union(BSTSet s) {
        if (root == null) {
            return s;
        } else if (s.root == null) {
            return this;
        }

        int[] m = new int[size()], n = new int[s.size()];
        list(root, m, 0);
        list(s.root, n, 0);

        int[] union = new int[m.length + n.length];
        System.arraycopy(m, 0, union, 0, m.length);
        System.arraycopy(n, 0, union, m.length, n.length);

        return new BSTSet(union);
    }
    
 // Time complexity is O(n) because it goes to every node, space complexity is O(log n)
    private int list(TNode t, int[] arrayList, int index) {
        if (t == null) {
            return index;
        }

        index = list(t.left, arrayList, index);
        arrayList[index++] = t.element;
        index = list(t.right, arrayList, index);

        return index;
    }
    
 // Time complexity is O(x) where x = n * m because of the nested for-each loops, space complexity is O(n + m)
    public BSTSet intersection(BSTSet s) {
        if (root == null || s.root == null) {
            return new BSTSet();
        }

        int[] m = new int[size()], n = new int[s.size()];
        list(root, m, 0);
        list(s.root, n, 0);

        int lngt = m.length, sLngt = n.length, index = 0;
        int[] temp = new int[Math.min(lngt, sLngt)];

        for (int obj : m) {
            for (int value : n) {
                if (obj == value) {
                    temp[index++] = obj;
                    break;
                }
            }
        }

        if (index != temp.length) {
            return new BSTSet(Arrays.copyOfRange(temp, 0, index));
        }

        return new BSTSet(temp);
    }

    // Time complexity is O(x) where x = n * m because of the nested for-each loops, space complexity is O(n + m)
    public BSTSet difference(BSTSet s) {
        if (root == null) {
            return new BSTSet();
        } else if (s.root == null) {
            return this;
        }

        int[] m = new int[size()], n = new int[s.size()];
        list(root, m, 0);
        list(s.root, n, 0);

        int index = 0;
        int[] temp = new int[m.length];

        nestLoop:
        for (int item : m) {
            for (int value : n) {
                if (item == value) {
                    continue nestLoop;
                }
            }

            temp[index++] = item;
        }

        if (index != temp.length) {
            return new BSTSet(Arrays.copyOfRange(temp, 0, index));
        }

        return new BSTSet(temp);
    }
    
 // Time complexity is O(n), space complexity is O(log n)
    public int size() {
        return size(root);
    }

    private int size(TNode t) {
        if (t == null) {
            return 0;
        }

        return size(t.left) + 1 + size(t.right);
    }
	
 // Time complexity is O(log n), space complexity is O(log n)
    public int height() {
        if (root == null) {
            return -1;
        }

        return height(root);
    }

    private int height(TNode N) {
        if (N == null) {
            return -1; // Subtract 1 to offset adding one in the caller function
        }

        return 1 + Math.max(height(N.left), height(N.right));
    }
    
 // Time complexity is O(n), space complexity is O(log n)
    public void printBSTSet() {
        if (root == null) {
            System.out.println("The set is empty");
        } else {
            System.out.print("The set elements are: ");
            printBSTSet(root);
            System.out.print("\n");
        }
    }

    private void printBSTSet(TNode t) {
        if (t != null) {
            printBSTSet(t.left);
            System.out.print(" " + t.element + ", ");
            printBSTSet(t.right);
        }
    }
    
 // Time complexity is O(n) because you have to visit every node, space complexity is O(log n)
    public void printNonRec() {
        if (root == null) {
            System.out.println("The set is empty");
            return;
        }

        MyStack<TNode> s = new MyStack<>();
        TNode temp = root;

        System.out.print("The set elements are: ");

        while (temp != null || !s.isEmpty()) {
            // Iterate through all the left nodes first, then go to the next right node
            while (temp != null) {
                s.push(temp);
                temp = temp.left;
            }

            temp = s.pop();
            System.out.print(" " + temp.element + ", ");
            temp = temp.right;
        }

        System.out.println();
    }
    
    // Time and space: O(n) in O(log n) space
    public void printLevelOrder(){
        if (root == null){
            System.out.println("The set is empty");
            return;
        }

        Queue<TNode> q = new Queue<>();
        TNode tmp = root;
        System.out.println("[queue] The set elements are: ");

        while(tmp != null || !q.isEmpty()){
            while (tmp != null){
                q.enqueue(tmp);
                tmp = tmp.left;
            }
            tmp = q.dequeue();
            System.out.println(" "+tmp.element+", ");
            tmp = tmp.right;
        }
        System.out.println();
    }
    
	/*public class TNode {
        int element;
        TNode left;
        TNode right;

        TNode(int i, TNode l, TNode r) {
            element = i;
            left = l;
            right = r;
        }
 } */
    public TNode getRoot() {
    	return root;
    }
}


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
