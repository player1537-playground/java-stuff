import java.util.*;

class AList {
    public static void main(String[] args) {
	ArrayList arrayList = new ArrayList();
	arrayList.add(new Integer(1));
	arrayList.add(arrayList);
	arrayList.add(new String("Herro"));
	
	System.out.println(arrayList);
	System.out.println(((ArrayList)arrayList.get(1)).get(1));
    }
}