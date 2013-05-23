public class Test {
    static int quest = 102;
    public static void p(int n) {
	System.out.println("Question " + quest++ + ": " + n);
    }
    public static void main(String[] args) {
	p(quest0102(5));
	p(quest0103(6));
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
    static int quest0103(int n) {
	int temp = 0;
	for (int p=1; p<n; p++) {
	    for (int q=0; q<n-p; q++) {
		temp++;
	    }
	}
	return temp;
    }
}
