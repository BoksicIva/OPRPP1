package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;
/**
 * Interface for setting environment of shell after shell is started 
 * @author Iva
 *
 */
public interface Environment {
	
	/**
	 * Method for reading from user console 
	 * @return line that user wrote on console
	 * @throws ShellIOException if reading from console fails
	 */
	String readLine() throws ShellIOException;
	
	/**
	 * Method for writing on user console, does not go in new line after writing text
	 * @param text represents what should be written
	 * @throws ShellIOException if writing on console fails
	 */
	void write(String text) throws ShellIOException;
	
	/**
	 * Method for writing on user console, goes in new line after writing text
	 * @param text represents what should be written
	 * @throws ShellIOException if writing on console fails
	 */
	void writeln(String text) throws ShellIOException;
	
	/**
	 * Method for getting all supported command of shell
	 * @return unmodifiable map, so that the client can not delete 
	 * commands by clearing the map
	 */
	SortedMap<String, ShellCommand> commands();
	
	/**
	 * Getter method for multilineSymbol String
	 * @return multilineSymbol
	 */
	Character getMultilineSymbol();
	 
	/**
	 * Setter method  multilineSymbol
	 * @param symbol is set to String multilineSymbol
	 */
	void setMultilineSymbol(Character symbol);
	
	/**
	 * Getter method for promptSymbol
	 * @return promptSymbol
	 */
	Character getPromptSymbol();
	
	/**
	 * Setter method for promptSymbol
	 * @param symbol is set to String promptSymbol
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * Getter method for morelinesSymbol
	 * @return  morelinesSymbol
	 */
	Character getMorelinesSymbol();
	
	/**
	 * Setter method for  morelinesSymbol
	 * @param symbol is set for String  morelinesSymbol
	 */
	void setMorelinesSymbol(Character symbol);

}
