package hr.fer.oprpp1.custom.scripting.elems;
/**
 * Class representing integer element
 * @author Iva
 *
 */
public class ElementConstantInteger extends Element{
	
	private int value;
	
	public ElementConstantInteger(int value) {
		this.value=value;
	}
	
	
	public int getValue() {
		return value;
	}

	@Override
	public String asText() {
		return Integer.toString(value);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + value;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ElementConstantInteger))
			return false;
		ElementConstantInteger other = (ElementConstantInteger) obj;
		if (value != other.value)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "ElementConstantInteger [value=" + value + "]";
	}
	
	

}
