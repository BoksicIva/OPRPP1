package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;
/**
 * Class ForLoopnode represents node with elements inside tag that starts with "for"
 * @author Iva
 *
 */
public class ForLoopNode extends Node{
	private ElementVariable variable;
	private Element startExpression;
	private Element endExpression;
	private Element stepExpression; 
	
	
	
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		super();
		if(startExpression==null || endExpression==null || variable==null)
			throw new SmartScriptParserException();
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	public ElementVariable getVariable() {
		return variable;
	}
	
	public Element getStartExpression() {
		return startExpression;
	}
	
	public Element getEndExpression() {
		return endExpression;
	}
	
	public Element getStepExpression() {
		return stepExpression;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(variable.asText()).append(' ')
		  .append(startExpression.asText()).append(' ')
		  .append(endExpression.asText());

		if (stepExpression != null) {
			sb.append(' ').append(stepExpression.asText());
		}
		int children = numberOfChildren();

		for (int i = 0; i < children; i++) {
			Node child = getChild(i);

			sb.append(child.toString());
		}
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endExpression == null) ? 0 : endExpression.hashCode());
		result = prime * result + ((startExpression == null) ? 0 : startExpression.hashCode());
		result = prime * result + ((stepExpression == null) ? 0 : stepExpression.hashCode());
		result = prime * result + ((variable == null) ? 0 : variable.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ForLoopNode))
			return false;
		ForLoopNode other = (ForLoopNode) obj;
		if (endExpression == null) {
			if (other.endExpression != null)
				return false;
		} else if (!endExpression.equals(other.endExpression))
			return false;
		if (startExpression == null) {
			if (other.startExpression != null)
				return false;
		} else if (!startExpression.equals(other.startExpression))
			return false;
		if (stepExpression == null) {
			if (other.stepExpression != null)
				return false;
		} else if (!stepExpression.equals(other.stepExpression))
			return false;
		if (variable == null) {
			if (other.variable != null)
				return false;
		} else if (!variable.equals(other.variable))
			return false;
		return true;
	}
	
	

}
