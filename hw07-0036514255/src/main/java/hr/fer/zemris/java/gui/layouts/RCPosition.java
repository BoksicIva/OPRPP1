package hr.fer.zemris.java.gui.layouts;
/**
 * Class represents organization of components on layout
 * @author Iva
 *
 */
public class RCPosition {
	private int row;
	private int column;
	
	/**
	 * Constructor of class
	 * @param row int value  representing row
	 * @param column int value  representing column
	 */
	public RCPosition(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	/**
	 * Getter  of row
	 * @return row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Getter of column
	 * @return column
	 */
	public int getColumn() {
		return column;
	}
	
	/**
	 * Method parses given text into RCPosition object
	 * @param text string that needs to be parsed
	 * @return new RCPosition
	 */
	public static RCPosition parse(String text) {
		text=text.replaceAll("\\s","");
		String[] el=text.split("\\,");
		int row,column;
		try {
			if(el.length==2) {
			   row= RCPosition.isInt(el[0]);
			   column=RCPosition.isInt(el[1]);
			}else {
				throw new IllegalArgumentException("Constraints are not parseable to RCPosition");
			}
			
		}catch(NumberFormatException num) {
			throw new IllegalArgumentException("Constraints are not parseable to RCPosition");
		}
		return new RCPosition(row, column);
	}
	
	/**
	 * Checks if given object can be parsed to integer
	 * @param val
	 * @return parsed value
	 */
	private static int isInt(Object val) {
	    if (val == null) {
	    	throw new NumberFormatException();
	    }
	    try {
	        int number = Integer.parseInt((String) val);
	        return number;
	    } catch (NumberFormatException nfe) {
	        throw new NumberFormatException();
	    }   
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof RCPosition))
			return false;
		RCPosition other = (RCPosition) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
	
	
	
	

}
