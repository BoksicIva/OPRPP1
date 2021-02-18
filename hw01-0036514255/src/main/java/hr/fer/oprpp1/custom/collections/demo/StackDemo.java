package hr.fer.oprpp1.custom.collections.demo;

import javax.naming.directory.InvalidAttributesException;

import hr.fer.oprpp1.custom.collections.ObjectStack;

public class StackDemo {
	
	/**
	 * Method checks if given value can be modified into Integer
	 * @param val value that needs to be checked
	 * @return true if value can be parse to Integer,otherwise returns false
	 */
	public static boolean isInteger(Object val) {
	    if (val == null) {
	        return false;
	    }
	    try {
	        Integer number = Integer.parseInt((String) val);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
	/**
	 * Example of usage ObjectStack for math implementation 
	 * @param args representation of postfix e
	 * @throws InvalidAttributesException
	 */
	public static void main(String[] args) throws InvalidAttributesException {
		Object[] elements ;
		if(args.length==1) {
			elements=args[0].split(" ");
		Integer firstNum,secondNum,result = null;
		ObjectStack stack=new ObjectStack();
		for(Object el: elements) {
			if(isInteger(el)) {
				stack.push(Integer.parseInt((String)el));
			}else {
				secondNum=(Integer) stack.pop();
				firstNum=(Integer) stack.pop();
				switch((String)el) {
				case "/" : {
					if(secondNum==0) throw new InvalidAttributesException("Program cannot divide by zero!!");
				   result=firstNum/secondNum;
					break;
				}
				case "-" : {
				   result=firstNum-secondNum;
					break;
				}
				case "+" : {
				   result=firstNum+secondNum;
					break;
				}
				case "%" : {
					if(secondNum==0)
				   result=firstNum%secondNum;
					break;
				}
				case "*" : {
				   result=firstNum*secondNum;
					break;
				}
				default:{
					throw new InvalidAttributesException();
				}
			    }
				stack.push(result);
			}
		}
		if(stack.size() != 1) System.out.println("Error!! Something went wrong. Try again");
		else
			System.out.println("Result of expression is "+stack.pop());
	}else {
		throw new IllegalArgumentException("Only one parametar should be given to program.");
	}
	}
}
