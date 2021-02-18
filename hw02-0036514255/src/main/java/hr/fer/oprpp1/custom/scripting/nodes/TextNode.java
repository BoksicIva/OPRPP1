package hr.fer.oprpp1.custom.scripting.nodes;
/**
 * Class TextNode represents node outside tags
 * @author Iva
 *
 */
public class TextNode extends Node{
	private final String text;

	
	public TextNode(String text) {
		super();
		this.text = text;
	}


	public String getText() {
		return text;
	}
	
	public String toString() {
		return getText();
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof TextNode))
			return false;
		TextNode other = (TextNode) obj;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	
	
}
