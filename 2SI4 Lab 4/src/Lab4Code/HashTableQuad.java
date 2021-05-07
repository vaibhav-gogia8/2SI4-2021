package Lab4Code;

public class HashTableQuad {
	private Integer[] table;
    private int size;
    private int numKeys;
    private int maxNumKeys;
    private double maxLoadFactor;

    public HashTableQuad(int maxNum, double load) {
        this.numKeys = 0;
        this.maxNumKeys = maxNum;
        this.maxLoadFactor = load;
        this.size = NextPrimeNum((int) (maxNum / load + 1));
        this.table = new Integer[this.size];
    }

    /**
     * Finds the next smallest prime number greater or equal to n https://stackoverflow.com/a/2473188/6713362
     *
     * @param n the number to start at
     *
     * @return the next smallest prime number greater or equal to n
     */
    private int NextPrimeNum(int n) {
        while (true) {
            if (checkPrime(n)) {
                return n;
            }
            n++;
        }
    }

    // https://en.wikipedia.org/wiki/Primality_test#Pseudocode
    private boolean checkPrime(int n) {
        if (n <= 3) {
            return n > 1;
        } else if (n % 2 == 0 || n % 3 == 0) {
            return false;
        }

        int i = 5;
        while (i * i <= n) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
            i += 6;
        }

        return true;
    }

    private int HashLen(int key) {
        return key % table.length;
    }

    public void insert(int n) {
        if (isIn(n)) {
            return;
        }

        if ((numKeys + 1.0) / table.length > maxLoadFactor) {
            rehash();
        }

        int i = HashLen(n);
        int quadIters = 0;

        for (int j = 0; j < table.length; j++) {
            if (table[i] != null) {
                quadIters++;
                i = HashLen(i + quadIters * quadIters);
            } else {
                break;
            }
        }

        numKeys++;
        table[i] = n;
    }

    // Do in-place rehashing
    private void rehash() {
        HashTableQuad temp = new HashTableQuad(2 * maxNumKeys, maxLoadFactor);

        for (Integer integer : table) {
            if (integer != null) {
                temp.insert(integer);
            }
        }

        this.table = temp.table;
        this.size = temp.size;
    }

    public boolean isIn(int n) {
        int i = HashLen(n);
        int quadIters = 0;

        for (int j = 0; j < table.length; j++) {
            if (table[i] != null) {
                if (table[i] == n) {
                    return true;
                }

                quadIters++;
                i = HashLen(i + quadIters * quadIters);
            } else {
                break;
            }
        }

        return false;
    }

    public void printKeys() {
        System.out.print("Keys: ");

        for (Integer integer : table) {
            if (integer != null) {
                System.out.print(integer + " ");
            }
        }
    }

    public void printKeysAndIndexes() {
        System.out.print("Keys and Indices: ");

        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                System.out.print("(" + i + ", " + table[i] + ") ");
            }
        }
        System.out.println();
    }

    public int insertCount(int n) {
        if (isIn(n)) {
            return 0;
        }

        if ((numKeys + 1.0) / table.length > maxLoadFactor) {
            rehash();
        }

        int i = HashLen(n);
        int quadIters = 0;

        for (int j = 0; j < table.length; j++) {
            if (table[i] != null) {
                quadIters++;
                i = HashLen(i + quadIters * quadIters);
            } else {
                break;
            }
        }

        numKeys++;
        table[i] = n;
        return quadIters + 1;
    }

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
