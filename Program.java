import java.lang.*;
import java.util.HashSet;

public class Program
{
    /**
     * This is the main entry point for the application
     */
    public static void main(String args[]) 
    {
	System.out.println(new RangeSet(new Number(10), new Number(20))
			   .restrict(new RangeSet(new Number(15), new Number(25))));
	System.out.println(new RangeSet(new Number(10), new Number(20))
			   .restrict(new RangeSet(new Number(5), new Number(25))));
	System.out.println(new RangeSet(new Number(10), new Number(20))
			   .restrict(new RangeSet(new Number(5), new Number(18))));
	System.out.println(new RangeSet(new Number(10), new Number(20))
			   .restrict(new RangeSet(new Number(25), new Number(30))));
	System.out.println(new RangeSet(new RangeSetCapInclusive(10), new RangeSetCapInclusive(20))
			   .restrict(new RangeSet(new RangeSetCapInclusive(10), new RangeSetCapInclusive(30))));
    
	System.out.println("\nExpand!");
    
	System.out.println(new RangeSet(new Number(10), new Number(20))
			   .expand(new RangeSet(new Number(15), new Number(25))));
	System.out.println(new RangeSet(new Number(10), new Number(20))
			   .expand(new RangeSet(new Number(5), new Number(25))));
	System.out.println(new RangeSet(new Number(10), new Number(20))
			   .expand(new RangeSet(new Number(5), new Number(18))));
	System.out.println(new RangeSet(new Number(10), new Number(20))
			   .expand(new RangeSet(new Number(25), new Number(30))));
    
	System.out.println(new EmptySet().expand(new RangeSet(new Number(10), new Number(15))));
    
	System.out.println(new ExplicitSet(new Number[] { new Number(1), new Number(2), new Number(3), new Number(4) }));
	System.out.println(new ExplicitSet(new Number[] { new Number(1), new Number(2), new Number(3), new Number(4) })
			   .restrict(new ExplicitSet(new Number[] { new Number(0), new Number(3), new Number(5), new Number(7) })));

	System.out.println("\nFunctions!");
	BinaryFunction min = new BinaryFunction() {
		public String call(Number a, Number b) {
		    return new FullSet()
			.restrict(new UpperBoundsSet(a))
			.restrict(new UpperBoundsSet(b))
			.restrict(new ExplicitSet(new Number[] { a, b }))
			.toString();
		}
	    };
	System.out.println(min.call(new Number(5), new Number(10)));
		 
    }
}

abstract class BinaryFunction {
    public abstract String call(Number a, Number b);
}

interface ISet {
    boolean memberOf(Number x);
    LogicSet restrict(LogicSet other);
    LogicSet expand(LogicSet other);
}

abstract class LogicSet implements ISet {
    public abstract boolean memberOf(Number x);
    public LogicSet restrict(LogicSet other) {
	return new AndSet(this, other);
    }
    public LogicSet expand(LogicSet other) {
	return new OrSet(this, other);
    }
}

class SingleSet extends ExplicitSet {
    public SingleSet(Number a) {
	super(new Number[] { a });
    }
}

class ExplicitSet extends LogicSet {
    HashSet<Number> numbers = new HashSet<Number>();
    public ExplicitSet(Number[] nums) {
	for (Number n : nums) {
	    numbers.add(n);
	}
    }
  
    public ExplicitSet(HashSet<Number> nums) {
	this.numbers.addAll(nums);
    }
  
    public boolean memberOf(Number x) {
	return numbers.contains(x);
    }
  
    public LogicSet expand(ExplicitSet other) {
	HashSet<Number> newSet = new HashSet<Number>();
	newSet.addAll(numbers);
	newSet.addAll(other.numbers);
	return new ExplicitSet(newSet);
    }
  
    public LogicSet restrict(ExplicitSet other) {
	HashSet<Number> newSet = new HashSet<Number>();
	newSet.addAll(numbers);
	newSet.retainAll(other.numbers);
	return new ExplicitSet(newSet);
    }
  
    public String toString() {
	return "{" + numbers.toString() + "}";
    }

    public Number[] getNumbers() {
	return (Number[])numbers.toArray();
    }
}
  
class AndSet extends LogicSet {
    LogicSet setA, setB;
    public AndSet(LogicSet a, LogicSet b) {
	this.setA = a;
	this.setB = b;
    }
  
    public boolean memberOf(Number x) {
	return this.setA.memberOf(x) && this.setB.memberOf(x);
    }
  
    public String toString() {
	return "(" + setA + " && " + setB + ")";
    }
}

class OrSet extends LogicSet {
    LogicSet setA, setB;
    public OrSet(LogicSet a, LogicSet b) {
	this.setA = a;
	this.setB = b;
    }
  
    public boolean memberOf(Number x) {
	return this.setA.memberOf(x) || this.setB.memberOf(x);
    }
  
    public String toString() {
	return "(" + setA + " || " + setB + ")";
    }
}

abstract class ExpressionSet extends LogicSet {
    public abstract boolean memberOf(Number x);
    public LogicSet restrict(LogicSet other) {
	return new AndSet(this, other);
    }
  
    public LogicSet expand(LogicSet other) {
	return new OrSet(this, other);
    }
}

class Number {
    double num;
    public Number(double x) {
	num = x;
    }
    public boolean greater(Number other) {
	return num > other.num;
    }
  
    public boolean less(Number other) {
	return num < other.num;
    }
  
    public boolean equal(Number other) {
	double diff = other.num - num;
	double tolerance = 1e-5;
	diff = diff < 0 ? -diff : diff;
	return diff < tolerance;
    }
  
    public double getNum() {
	return this.num;
    }
  
    public String toString() {
	return "" + num;
    }
}

class RangeSetCap extends Number {
    public RangeSetCap(double x) {
	super(x);
    }
    public RangeSetCap(Number x) {
	super(x.getNum());
    }
}

class RangeSetCapInclusive extends RangeSetCap {
    public RangeSetCapInclusive(double x) {
	super(x);
    }
    public RangeSetCapInclusive(Number x) {
	super(x);
    }
    public boolean less(Number x) {
	return super.less(x) || super.equals(x);
    }
    public boolean greater(Number x) {
	return super.greater(x) || super.equals(x);
    }
}

class UpperBoundsSet extends RangeSet {
    public UpperBoundsSet(Number max) {
	super(new Number(Double.NEGATIVE_INFINITY), max);
    }
}

class LowerBoundsSet extends RangeSet {
    public LowerBoundsSet(Number min) {
	super(min, new Number(Double.POSITIVE_INFINITY));
    }
}

class EmptySet extends RangeSet {
    public EmptySet() {
    
    }
    public boolean memberOf(Number x) {
	return false;
    }
    public LogicSet restrict(LogicSet other) {
	return new EmptySet();
    }
    public LogicSet expand(RangeSet other) {
	return new RangeSet(other);
    }
  
    public String toString() {
	return "()";
    }
}

class FullSet extends RangeSet {
    public FullSet() {
    }
    
    public boolean memberOf(Number x) {
	return true;
    }

    public LogicSet restrict(RangeSet other) {
	return new RangeSet(other);
    }

    public LogicSet expand(LogicSet other) {
	return new FullSet();
    }
    
    public String toString() {
	return "(-inf, inf)";
    }
}

class RangeSet extends LogicSet {
    RangeSetCap bottom, top;
    public RangeSet() {
    }

    public RangeSet(RangeSetCap bottom, RangeSetCap top) {
	this.bottom = bottom;
	this.top = top;
    }
    
    public RangeSet(Number bottom, Number top) {
	this(new RangeSetCap(bottom),
	     new RangeSetCap(top));
    }
  
    public RangeSet(RangeSet other) {
	this(other.bottom, other.top);
    }
  
    public boolean memberOf(Number x) {
	return (bottom.greater(x) && top.less(x))
	    || (bottom.equals(x) || top.equals(x));
    }
  
    public LogicSet restrict(RangeSet other) {
	if (other.memberOf(bottom) && other.memberOf(top)) {
	    return new RangeSet(other);
	} else if (this.memberOf(other.bottom) && this.memberOf(other.top)) {
	    return new RangeSet(this);
	} else if (other.top.less(this.bottom) || other.bottom.greater(this.top)
		   || this.top.less(other.bottom) || this.bottom.greater(other.top)) {
	    return new EmptySet();
	} else {
	    return new RangeSet(other.bottom.greater(bottom) ? other.bottom : bottom,
				other.top.less(top) ? other.top : top);
	}
    }
  
    public LogicSet expand(RangeSet other) {
	if (this.memberOf(other.bottom) && this.memberOf(other.top)) {
	    // if this contains that
	    return new RangeSet(this);
	} else if (other.memberOf(this.bottom) && other.memberOf(this.top)) {
	    // if that contains this
	    return new RangeSet(other);
	} else if (other.top.less(this.bottom) || other.bottom.greater(this.top)
		   || this.top.less(other.bottom) || this.bottom.greater(other.top)) {
	    // other.top < this.bottom || other.bottom > this.top
	    // this.top < other.bottom || this.bottom > other.top
	    // [other.bottom, other.top] < [this.bottom, this.top]
	    return new OrSet(this, other);
	} else {
	    return new RangeSet(other.bottom.greater(this.bottom) ? this.bottom : other.bottom,
				other.top.less(this.top) ? this.top : other.top);
	}
    }
  
    public String toString() {
	return "[" + bottom + ", " + top + "]";
    }
}











