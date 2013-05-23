import java.io.*;

public class Lab_B {
    float array[] = { 81.4, 75.3, 9-, 96.9, 85.6 };
    public void Lab_B_1() {
	int i;
	for(i=0; i<this.array.length; i++)
	    System.out.println(array[i] + ", ");
    }
    public void Lab_B_2() {
	int i;
	float average = 0,
	    maximum = 0;
	for(i=0; i<this.array.length; i++) {
	    average += array[i];
	    if( this.array[i] > maximum )
		maximum = this.array[i];
	}
	System.out.println("Average score: " + average + " Maximum score: " + maximum);
    }
    public int withinBounds(float value, float high, float low) {
	return (value <= high) && (value > low);
    }
    public void Lab_B_3() {
	System.out.print("Letter grades: ");
	int i;
	String grade;
	for(i=0; i<this.array.length; i++) {
	    if( withinBounds(i, 100, 90) )
		grade = "A";
	    else if( withinBounds(i, 90, 80) )
		grade = "B";
	    else if( withinBounds(i, 80, 70) )
		grade = "C";
	    else if( withinBounds(i, 70, 60) )
		grade = "D";
	    else
		grade = "F";
	    
	    System.out.print(grade + " ");
	}
    }
    public void Lab_B_4() {
	int i;
	for(i=0; i<this.array.length; i++) {
	    this.array[i] *= 1.05;
	}
	this.Lab_B_3();
    }
    public String prompt(String msg) {
	System.out.print(msg);
	String s;
	try {
	    InputStreamReader isr = new InputStreamReader(System.in);
	    BufferedReader br = new BufferedReader(isr);
	    s = br.readLine();
	} catch (IOException e) {
	    s = "";
	}
	return s;
    }
    public void Lab_B_5() {
	Candidate candidates[5];
	int i;
	for(i=0; i<candidates.length; i++) {
	    candidates[i] = new Candidate(prompt("Name: "),
					  prompt("Votes: "));
	}
	for(i=0; i<candidates.length; i++) {
	    System.out.println("Candidate: " + candidates[i].name);
	    System.out.println("  Votes: " + candidates[i].votes);
	    System.out.println("  Percentage of total votes: " + (candidates[i].votes / (float)Candidate.total));
	    System.out.println();
	}
    }
}

private class Candidate {
    public String name;
    public int votes;
    public static int total;
    public Candidate(String name, int votes) {
	this.name = name;
	this.votes = votes;
	this.total += votes;
    }
}