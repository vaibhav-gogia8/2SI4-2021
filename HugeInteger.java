import java.util.concurrent.ThreadLocalRandom;
import java.util.Arrays;
//Vaibhav Gogia
//400253615
//gogiav

public class HugeInteger {
	
	
	private int[] numerals; 
	private int signDig; // for -,0,+

    public HugeInteger(String val) {      //constructor 1
		if (val == null) {
			throw new IllegalArgumentException("string cannot be null");
			}
		final int lngt = val.length();
		int arrow = 0;
		
		if (lngt == 0) {
			throw new NumberFormatException("0 length"); 
		}
		
		//checking for a leading minus sign now (max allowed is 1)
		int sign = 1;
		int indOne = val.lastIndexOf("-");        //the index counter will start at 0, search for a minus sign and return its index
		if (indOne >= 0) {
			if (indOne != 0) {
				throw new NumberFormatException("Digit value not allowed");
			}
		 sign = -1;
		 arrow = 1;
		}
		
		if (arrow == lngt) { throw new NumberFormatException("0 length"); }     //the string just has a -
		
		//going to count the number of numerals here
		while(arrow<lngt && Character.digit(val.charAt(arrow),10)==0) {         //Character.digit(ch,radix) returns the numeric value of ch
			arrow++;       //skipping the insignificant zeros
		}
		
		//now if the input val is 0
		if (arrow==lngt) {
			signDig=0;
			numerals= new int[0];
			return;
		}
		signDig=sign;
		numerals = new int[lngt-arrow];
		
		//using parseInt() to parse each character/digit
		for (int j=0;(j+arrow)<lngt;j++) {
			String digit = val.substring(j+arrow, j + arrow + 1);
			numerals[j]=Integer.parseInt(digit);
			if ( numerals[j]<0) {
				throw new NumberFormatException("Illegal digit");
			}
		}
	} 
	
	public HugeInteger(int n) {           //constructor 2
		if (n<1) { throw new IllegalArgumentException("number must be larger or equal to 1"); }
		
		//generating numbers, keeping in mind the first digit cant be 0
		signDig = ThreadLocalRandom.current().nextBoolean() ? 1 : -1;   //generates random int sequence
		numerals = new int[n];
		numerals[0] = ThreadLocalRandom.current().nextInt(1,10);    // assigning random integer value to first place, excluding 0
	    
	    for (int k =1; k < n; k++) {       //assigning the rest of the numerals
	    	numerals[k]=ThreadLocalRandom.current().nextInt(0,10);
	    }
	}
	
	
	private HugeInteger(int[] numerals, int signDig) {  //to access the private variables
		this.numerals = numerals;
		this.signDig = signDig;
	}
	
	public HugeInteger add(HugeInteger h) {
		if (h.signDig == 0) {                    //if input is 0, this is returned because addition wouldn't change the result
			return this;
		}
		if (signDig == 0) {                      //if this HugeInteger is 0, return h with same logic as above
			return h;
		}
		
		//now since the numbers are not equal to 0:
		//case 1: they have the same sign
		if (signDig == h.signDig) {
			return new HugeInteger(add(numerals, h.numerals),signDig);
		}
		
		int comp = compareNumerals(h);        
		if (comp == 0) { return new HugeInteger(new int[0], 0); }

        int[] result = comp == 1 ? subtract(numerals, h.numerals) : subtract(h.numerals, numerals);
		//return new HugeInteger(add(numerals, h.numerals),signDig);
        return new HugeInteger(result, comp == signDig ? 1 : -1);
	}
	
		
	private static int[] add(int[] numerals, int[] hnumerals) {
		//the smaller number to be stored in "numerals" by using swap algo to avoid confusion later on
		if (numerals.length > hnumerals.length) {
            int[] temp = hnumerals;
            hnumerals = numerals;
            numerals = temp;
        }
		boolean forward = false;                                     //to hold and carry forward value (when a dig is >9)
        int smallInd = numerals.length, largeInd = hnumerals.length;
        int[] result = new int[largeInd];
    
        // Start from the end and sum each pair of elements with the optional carry, until small number is done
   
        
        while (smallInd > 0) {
        	int sum;
        	if(forward == true) {
        	    sum = numerals[--smallInd] + hnumerals[--largeInd]+1;
        	}
        	else{
        		sum = numerals[--smallInd] + hnumerals[--largeInd];
        	} 
        	
        	if (sum > 9) { // Can only have 1 digit per element so handle if the sum > 9
            	forward = true;
                sum -= 10;
            } else {
            	forward = false;
            }
            result[largeInd] = sum;
        } 

        // Sum remaining big number elements with optional carry
        while (largeInd > 0) {
            
        	int sum;
        	if(forward == true) {
        	    sum = hnumerals[--largeInd]+1;
        	}
        	else{
        		sum = hnumerals[--largeInd];
        	} 
            if (sum > 9) {
            	forward = true;
                sum -= 10;
            } else {
            	forward = false;
            }
            result[largeInd] = sum;
        }
        
        // Grow result depending on possible last carry
        if (forward) {
            int[] plusCarry = new int[result.length + 1];
            System.arraycopy(result, 0, plusCarry, 1, result.length);
            plusCarry[0] = 1;
            return plusCarry;
        }

        return result;
    }
	
	 public HugeInteger subtract(HugeInteger h) {
	        if (h.signDig == 0) { 
	        	return this; 
	        }
	        if (signDig == 0) { 
	        	return new HugeInteger(h.numerals, -signDig); 
	        }
	        if (h.signDig != signDig) {           //if the signs are opposite then we can just use the add function to perform the subtration
	        	return new HugeInteger(add(numerals, h.numerals), signDig); 
	        }

	        // Same comparison as addition, because the signDigs are the same
	        int comp = compareNumerals(h);
	        if (comp == 0) { return new HugeInteger(new int[0], 0); }
	        int[] result;
	        if(comp == 1){
	        	 result = subtract(numerals, h.numerals);
	        }
	        else {
	        	 result = subtract(h.numerals, numerals);
	        }

	        return new HugeInteger(result, comp == signDig ? 1 : -1);
	    }

	    private static int[] subtract(int[] large, int[] small) {
	        boolean forward = false;                                 //for cases with a carry forward
	        int largeInd = large.length, smallInd = small.length;
	        int[] answer = new int[largeInd];                        //size of answer = largeInd

	        // Start from the end and subtract each pair of elements with the optional forward, until small number is done
	        while (smallInd > 0) {
	            int diff = large[--largeInd] - small[--smallInd] - (forward ? 1 : 0);
	            if (diff < 0) { // Can only have 1 digit per element 
	                forward = true;
	                diff += 10;
	            } else {
	                forward = false;
	            }
	            answer[largeInd] = diff;
	        }

	        // Subtract remaining big number elements with optional forward
	        while (largeInd > 0) {
	            int diff = large[--largeInd] - (forward ? 1 : 0);
	            if (diff < 0) {
	                forward = true;
	                diff += 10;
	            } else {
	                forward = false;
	            }
	            answer[largeInd] = diff;
	        }

	        return stripLeadZeroes(answer);
	    }


	    private int compareNumerals(HugeInteger h) {
	        // Compare length first then each digit pair
	        int lA = numerals.length;
	        int lB = h.numerals.length;

	        if (lA < lB) { 
	        	return -1; 
	        }
	        if (lA > lB) { 
	        	return 1; 
	        }

	        // Iterate through both numerals and compare them
	        for (int i = 0; i < lA; i++) {
	            int a = numerals[i], b = h.numerals[i];
	            if (a != b) {
	            	if(a<b) {
	            		return -1;
	            	}
	            	else {
	            		return 1;
	            	}
	            
	            }
	        }

	        return 0;
	    }
	    
	    private static int[] stripLeadZeroes(int[] num) {         
	        int non0Ind;
	        for (non0Ind = 0; non0Ind < num.length && num[non0Ind] == 0; non0Ind++) {}
	        return non0Ind != 0 ? Arrays.copyOfRange(num, non0Ind, num.length) : num;     //copying while leaving out the zeroes
	    }
	    
	    
	    //CompareTo Returns -1 if this HugeInteger is less than h, 1 if this HugeInteger is larger than h, and 0 if this HugeInteger is equal to h.
	    public int compareTo(HugeInteger h) {
	        // Compare signs first, then numerals
	        if (signDig == h.signDig) {
	            switch (signDig) {
	                case 1:
	                    return compareNumerals(h);
	                case -1:
	                    return h.compareNumerals(this);
	                default:
	                    return 0;
	            }
	        }
            if(signDig>h.signDig) {
            	return 1;
            }
            else {
            	return -1;
            }
	    }
	    
	    
	 public HugeInteger multiply(HugeInteger h) {
		 //I'll be implementing this function using the karatsuba algorithm
		 //brilliant.org/wiki/karatsuba-algorithm/ was used to study the algorithm
		 if (signDig == 0 || h.signDig == 0) {
	            return new HugeInteger(new int[0], 0);
	        }
            int NewSignDig;
            if (signDig==h.signDig) {       //checking what would the sign for the product be
                NewSignDig=1;
            }
            else {
            	NewSignDig=-1;
            }
	                  //if the two number have the same signs, the resultant sign is 1
	        int lngt = numerals.length, hlngt = h.numerals.length;
	        if (lngt == 1) {
	            return multiplyByDigit(h, numerals[0], NewSignDig);   //checking if either of the number have length 1
	        }
	        if (hlngt == 1) {
	            return multiplyByDigit(this, h.numerals[0], NewSignDig);
	        }

	        return karatsuba(this, h);
	 }
	 
	 private static HugeInteger multiplyByDigit(HugeInteger m, int digit, int signDig) {
	        int[] result = new int[m.numerals.length + 1];
	        int forward = 0;
	        int ans;
	        int rInd = result.length - 1;

	        for (int i = m.numerals.length - 1; i >= 0; i--) {
	            ans = m.numerals[i] * digit + forward;
	            if (ans> 9) {
	                result[rInd--] = ans % 10;
	                forward = ans / 10;
	            } else {
	                result[rInd--] = ans;
	                forward = 0;
	            }
	        }

	        // removing empty spaces from array
	        if (forward == 0) {
	            result = Arrays.copyOfRange(result, 1, result.length);
	        } else {
	            result[rInd] = forward;
	        }

	        return new HugeInteger(result, signDig);
	    }
	 
	 
	 private HugeInteger karatsuba(HugeInteger x, HugeInteger y) {
	        int xLen = x.numerals.length, yLen = y.numerals.length;

	        int mid = (Math.max(xLen, yLen) + 1) / 2;      //finding midpoint of the larger number

	        // Dividing the numbers into upper and lower halves
	        HugeInteger xUp = x.getUp(mid), xLow = x.getLow(mid), yUp = y.getUp(mid), yLow = y.getLow(mid);

	        //the three results required to find the final answer
	        HugeInteger z0 = xLow.multiply(yLow);   
	        HugeInteger z1 = xUp.multiply(yUp);
	        HugeInteger z2 = xLow.add(xUp).multiply(yLow.add(yUp));
            
	        //computing the final answer
	        HugeInteger answer = z1.shiftL(mid).add(z2.subtract(z1).subtract(z0)).shiftL(mid).add(z0);

	        if (x.signDig != y.signDig) {
	            return new HugeInteger(answer.numerals, -answer.signDig);
	        } else {
	            return answer;
	        }
	    }
	 
	 private HugeInteger shiftL(int j) {
	        if (signDig == 0) {
	            return new HugeInteger(new int[0], 0);
	        }
	        if (j > 0) {
	            // Copy the numerals array to a bigger one, starting at 0 to process the shift
	            int[] result = new int[numerals.length + j];
	            System.arraycopy(numerals, 0, result, 0, numerals.length);
	            return new HugeInteger(result, signDig);
	        } else if (j == 0) {
	            return this;
	        } else {
	            throw new IllegalArgumentException("n cannot be negative");
	        }
	    }
	 
	 private HugeInteger getLow(int k) {
	        int lngt = numerals.length;

	        // Return the absolute value of this HugeInteger if the length is <= the number of numerals in the lower half
	        if (lngt <= k) {
	            return signDig >= 0 ? this : new HugeInteger(this.numerals, -this.signDig);
	        }

	        int[] lowernumerals = new int[k];
	        System.arraycopy(numerals, lngt - k, lowernumerals, 0, k);
	        lowernumerals = stripLeadZeroes(lowernumerals);
	        return new HugeInteger(lowernumerals, lowernumerals.length == 0 ? 0 : 1); // Check if the lower half is 0
	    }
	 
	 
	 private HugeInteger getUp(int n) {
	        int length = numerals.length;

	        // Return 0 if the length is <= the number of numerals in the upper half
	        if (length <= n) {
	            return new HugeInteger(new int[0], 0);
	        }

	        int upperLength = length - n;
	        int[] uppernumerals = new int[upperLength];
	        System.arraycopy(numerals, 0, uppernumerals, 0, upperLength);

	        return new HugeInteger(stripLeadZeroes(uppernumerals), 1);
	    }
	 
	 
	
	 @Override
	    public String toString() {
	        if (signDig == 0) { return "0"; }

	        StringBuilder build = new StringBuilder(numerals.length + 1);

	        if (signDig == -1) { build.append("-"); }

	        for (int digit : numerals) {
	            build.append(digit);
	        }

	        return build.toString();
	    } 
}
	 

	
	