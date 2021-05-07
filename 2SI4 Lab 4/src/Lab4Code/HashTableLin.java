package Lab4Code;

public class HashTableLin {
	private Integer[] table;
	private int size;
	private int numKeys;
	private int maxNumKeys;
	private double maxLoadFactor;
	
	public HashTableLin(int maxNum, double load) {
		this.numKeys=0;
		this.maxNumKeys= maxNum;
		this.maxLoadFactor = load;
		this.size = NextPrimeNum((int) (maxNum / load +1));
	}
	
	//this function returns the next smallest prime no. > or = to x
	//has a runtime of max x operations
	
	private int NextPrimeNum(int x) {
		while(true) {
			if(checkPrime(x)) {
				return x;
			}
			x++;
		}
	}
	
	private boolean checkPrime(int n) {
		if (n <= 3) {
            return n > 1;
        } else if (n % 2 == 0 || n % 3 == 0) {
            return false;
        }

        int k = 5;
        while (k * k <= n) {
            if (n % k == 0 || n % (k + 2) == 0) {
                return false;
            }
            k += 6;
        }

        return true;
	}
	
	//time complexity: O(1) avg
	public void insert(int n) {
        if ((numKeys + 1.0) / table.length > maxLoadFactor) {
            rehash();
        }

        int i = HashLen(n);

        for (int j = 0; j < table.length; j++) {
            if (table[i] != null && table[i] != n) {
                i = Increment(i);
            } else {
                break;
            }
        }

        numKeys++;
        table[i] = n;
    }
	
	// in place rehashing
	//time comp: O(n)
    private void rehash() {
        HashTableLin temp = new HashTableLin(2 * maxNumKeys, maxLoadFactor);

        for (Integer integer : table) {
            if (integer != null) {
                temp.insert(integer);
            }
        }

        this.table = temp.table;
        this.size = temp.size;
    }
	
 // time complexity: O(1) avg
    public boolean isIn(int n) {
        int i = HashLen(n);

        for (int j = 0; j < table.length; j++) {
            if (table[i] != null) {
                if (table[i] == n) {
                    return true;
                }

                i = Increment(i);
            } else {
                break;
            }
        }

        return false;
    }
    
    //time complexity: O(n)
    public void printKeys() {
        System.out.print("Keys: ");

        for (Integer integer : table) {
            if (integer != null) {
                System.out.print(integer + " ");
            }
        }
    }
    
    //time complexity: O(n)
    public void printKeysAndIndexes() {
        System.out.print("Keys and Indices: ");

        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                System.out.print("(" + i + ", " + table[i] + ") ");
            }
        }
        System.out.println();
    }
    
    //inserts and returns the number of probes required
    //time complexity: O(1)
    public int insertCount(int n) {
        if ((numKeys + 1.0) / table.length > maxLoadFactor) {
            rehash();
        }

        int i = HashLen(n);
        int count = 0;

        for (int j = 0; j < table.length; j++) {
            if (table[i] != null) {
                if (table[i] == n) {
                    return 0;
                }
                count++;
                i = Increment(i);
            } else {
                break;
            }
        }

        numKeys++;
        table[i] = n;
        return count + 1;
    }
    
    //Additional methods used in the implementation:
    
    //time complexity: O(1)
  	private int HashLen(int key) {
  		return key % table.length;
  	}
  	
  	//time complexity: O(1)
  	private int Increment(int k) {
          return (k == table.length - 1) ? 0 : k + 1;
      }
  	
    //all the public get methods:
    public int getTableSize() {
        return size;
    }

    public int getNumKeys() {
        return numKeys;
    }

    public double getMaxLoadFactor() {
        return maxLoadFactor;
    }

    public double getLoadFactor() {
        return (double) numKeys / table.length;
    }
	

}
