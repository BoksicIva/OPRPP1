package hr.fer.oprpp1.hw04.db;

public class ComparisonOperators {
	
	/**
	 * Variable LESS of class ComparisonOperators is implementation of interface IComperisonOperator
	 * overrides method satisfied of interface
	 * compares given parameters and returns true if method compareTo called on first parameter returns value less of zero
	 */
	public static final IComparisonOperator LESS = (value1, value2)->{
		return value1.compareTo(value2) < 0;
	};
	
	
	
	
	/**
	 * Variable LESS_OR_EQUAL of class ComparisonOperators is implementation of interface IComperisonOperator
	 * overrides method satisfied of interface
	 * compares given parameters and returns true if method compareTo called on first parameter returns value less or equal of zero
	 */
	public static final IComparisonOperator LESS_OR_EQUAL = (value1, value2)->{
		return value1.compareTo(value2) <= 0;
	};
	
	
	
	
	/**
	 * Variable GREATER of class ComparisonOperators is implementation of interface IComperisonOperator
	 * overrides method satisfied of interface
	 * compares given parameters and returns true if method compareTo called on first parameter returns value greater then zero
	 */
	public static final IComparisonOperator GREATER = (value1, value2)->{
		return value1.compareTo(value2) > 0;
	};
	
	
	
	
	/**
	 * Variable GREATER_OR_EQUAL of class ComparisonOperators is implementation of interface IComperisonOperator
	 * overrides method satisfied of interface
	 * compares given parameters and returns true if method compareTo called on first parameter returns value greater then zero
	 */
	public static final IComparisonOperator GREATER_OR_EQUAL = (value1, value2)->{
		return value1.compareTo(value2) >= 0;
	};
	
	
	/**
	 * Variable EQUALS of class ComparisonOperators is implementation of interface IComperisonOperator
	 * overrides method satisfied of interface
	 * compares given parameters and returns true if method compareTo called on first parameter returns value equals zero
	 */
	public static final IComparisonOperator EQUALS = (value1, value2)->{
		return value1.compareTo(value2) == 0;
	};
	
	
	/**
	 * Variable NOT_EQUALS of class ComparisonOperators is implementation of interface IComperisonOperator
	 * overrides method satisfied of interface
	 * compares given parameters and returns true if method compareTo called on first parameter returns value not equals zero
	 */
	public static final IComparisonOperator NOT_EQUALS = (value1, value2)->{
		return value1.compareTo(value2) != 0;
	};
	
	
	
	/**
	 * Variable LIKE of class ComparisonOperators is implementation of interface IComperisonOperator
	 * overrides method satisfied of interface
	 * compares given parameters and returns true if first parameter is "regex" example of second parameter
	 * whildcard * represents any char and it can be used only once a beginning,in middle or at the and of second parameter
	 */
	public static final IComparisonOperator LIKE = (value1, value2)->{
		int firstIndex=value2.indexOf("*");
		int lastIndex=value2.lastIndexOf("*");
		boolean like=true;
		if(firstIndex!=lastIndex)
			throw new IllegalArgumentException("In like pattern only one wildcard * can be used");
		String[] pattern=value2.split("\\*");
		if(pattern.length==1 && firstIndex==0) { // first case wildcard * is at the beginning
			
			for(int i=pattern[0].length()-1;i>0;i--) {
				if(value1.charAt(value1.length()-i)!=(pattern[0].charAt(pattern[0].length()-i))) {
					like=false;
				}
			}
		}else if(pattern.length==2 && pattern[0]!="" && pattern[1]!="") { //second case wildcard is in the middle
			for(int i=0;i<pattern[0].length();i++) {
				if(value1.charAt(i)!=(pattern[0].charAt(i))) {
					like=false;
				}
			}
			for(int i=pattern[1].length()-1;i>0;i--) {
				if(value1.charAt(value1.length()-i)!=(pattern[1].charAt(pattern[1].length()-i))) {
					like=false;
				}
			}
		}else if(pattern.length==1 && firstIndex==value2.length()-1) { //last case when whildcard is at end of string
			for(int i=0;i<pattern[0].length();i++) {
				if(value1.charAt(i)!=(pattern[0].charAt(i))) {
					like=false;
				}
			}
		}
		return like;
	};
}
