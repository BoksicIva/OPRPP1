package hr.fer.oprpp1.custom.scripting.elems;
/**
 * Class representing double element
 * @author Iva
 *
 */
public class ElementConstantDouble extends Element{
	private double value;
	
	public ElementConstantDouble(double value) {
		this.value=value;
	}
	
	public double getValue() {
		return value;
	}

	@Override
	public String asText() {
		return Double.toString(value);
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(value);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ElementConstantDouble))
			return false;
		ElementConstantDouble other = (ElementConstantDouble) obj;
		if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ElementConstantDouble [value=" + value + "]";
	}

}
