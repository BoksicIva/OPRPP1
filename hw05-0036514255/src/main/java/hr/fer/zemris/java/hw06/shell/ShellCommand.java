package hr.fer.zemris.java.hw06.shell;

import java.util.List;
/**
 * Interface for all supported shell commands
 * @author Iva
 *
 */
public interface ShellCommand {
	
	/**
	 * The second argument of method executeCommand is a single string which represents 
	 * everything that user entered AFTER the command name. It is expected that in case 
	 * of multiline input, the shell has already concatenated all lines into a single 
	 * line and removed MORELINES symbol from line endings (before concatenation). 
	 * This way, the command will always get a single line with arguments
	 * @param env
	 * @param arguments single string which represents everything that user entered AFTER the command name
	 * @return ShellStatus which can be CONTINUE or  TERMINATE
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * Method for getting name of command
	 * @return the name of the command
	 */
	String getCommandName();
	
	/**
	 * Method for description of command
	 * @return description -> usage instructions
	 */
	List<String> getCommandDescription();

}
