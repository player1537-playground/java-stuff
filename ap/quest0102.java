public class Test {
    public static void main(String[] args) {
	System.out.println("Returns: " + quest0102(5));
    }
    static int quest0102(int n) {
	int temp = 0;
	for (int p=1; p<=n; p++) {
	    for (int q=0; q<= n/2; q++) {
		temp++;
	    }
	}
	return temp;
    }
}
