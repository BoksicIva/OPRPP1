package hr.fer.oprpp1.custom.scripting.elems;
/**
 * Class representing function as element
 * @author Iva
 *
 */
public class ElementFunction extends Element{
	
	private String name;
	
	public ElementFunction(String name) {
		this.name=name;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public String asText() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ElementFunction))
			return false;
		ElementFunction other = (ElementFunction) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ElementFunction [name=" + name + "]";
	}
	
	
	
	

}
