
public class MyTest {
	 public static void main(String[] args) {
		   HugeInteger A = new HugeInteger("99");
		   HugeInteger B = new HugeInteger("1");
		   System.out.println("addition test:");
		   System.out.println(A.add(B));
		   
		   HugeInteger C = new HugeInteger("0");
		   HugeInteger D = new HugeInteger("2");
		   System.out.println("subtraction test:");
		   System.out.println(C.subtract(D));
		   
		   HugeInteger E = new HugeInteger("99");
		   HugeInteger F = new HugeInteger("99");
		   System.out.println("multiplication test:");
		   System.out.println(E.multiply(F));
		   
		   HugeInteger G = new HugeInteger("99");
		   HugeInteger H = new HugeInteger("100");
		   System.out.println("compareTo test:");
		   System.out.println(G.compareTo(H));
	   }
}
