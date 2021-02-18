package hr.fer.oprpp1.custom.scripting.nodes;
/**
 * Class represents main node when document is parsed
 * @author Iva
 *
 */
public class DocumentNode extends Node{
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder();

		int children = numberOfChildren();

		for (int i = 0; i < children; i++) {
			Node child = getChild(i);

			sb.append(child.toString());
		}

		return sb.toString();
	}
}
