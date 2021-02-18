package hr.fer.oprpp1.custom.scripting.nodes;

import java.util.Arrays;

import hr.fer.oprpp1.custom.scripting.elems.Element;
/**
 * Class represents node with elements inside tag that sparts with "="
 * @author Iva
 *
 */
public class EchoNode extends Node{
	private Element[] elements;

	public EchoNode(Element[] elements) {
		super();
		this.elements = elements;
	}

	public Element[] getElements() {
		return elements;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Element el : elements) {
			sb.append(el.asText()).append(' ');
		}
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(elements);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof EchoNode))
			return false;
		EchoNode other = (EchoNode) obj;
		if (!Arrays.equals(elements, other.elements))
			return false;
		return true;
	}

	
}
