package hr.fer.zemris.lsystems.impl;

import hr.fer.oprpp1.custom.collections.ObjectStack;
/**
 * Class Context used for saving turtle states on implementation of ObjectStack stack
 * @author Iva
 *
 */
public class Context {
	private ObjectStack<TurtleState> stack;
	/**
	 * Constructor for Context which makes new instance of ObjectStack
	 */
	public Context() {
		stack = new ObjectStack<>();
	}
	
	/**
	 * Method gets current state of turtle calling peek method of ObjectStack class, does not pop state
	 * @return
	 */
	public TurtleState getCurrentState() {
		return stack.peek();
	}
	
	/**
	 * Method saves TuretleState on top of stack
	 * @param state
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
	}
	/**
	 * Method pops TurtleState from top of stack
	 */
	public void popState() {
		stack.pop();
	}
}
