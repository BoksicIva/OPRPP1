package hr.fer.oprpp1.custom.scripting.lexer;
/**
 * Class SmartScriptToken is used when generating tokens
 * @author Iva
 *
 */
public class SmartScriptToken {

	private SmartScriptTokenType type;
	private Object value;
	
	public SmartScriptToken(SmartScriptTokenType type, Object value) {
		this.type=type;
		this.value=value;
	} 
	
	public Object getValue() {
		return value;
	}
	
	
	public SmartScriptTokenType getType() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof SmartScriptToken))
			return false;
		SmartScriptToken other = (SmartScriptToken) obj;
		if (!type.equals( other.type))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SmartScriptToken [type=" + type + ", value=" + value + "]";
	}
	
	
	

}
