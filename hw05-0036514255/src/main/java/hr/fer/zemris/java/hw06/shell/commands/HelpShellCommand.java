package hr.fer.zemris.java.hw06.shell.commands;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
/**
 * Implementation of shell command help
 * @author Iva
 *
 */
public class HelpShellCommand implements ShellCommand {
	private String commandName="help";
	private List<String> commandDescription=Arrays.asList(
			"Displays a list of the available commands or detailed help information on a specified command.",
			"If used without parameters, help lists every shell command.");


	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] arrayOfArgs = null;
		if(arguments!=null)
			arrayOfArgs=arguments.split(" ");
		SortedMap<String,ShellCommand> commands=env.commands();
		
		if(arguments==null) {
			for(String command :commands.keySet()) {
				env.writeln(command);
			}
		} else if(arrayOfArgs.length==1 && commands.containsKey(arrayOfArgs[0])) {
			var description=commands.get(arrayOfArgs[0]).getCommandDescription();
			for(String line : description)
				env.writeln(line);
		}else {
			throw new ShellIOException("To many arguments for help command");
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return commandName;
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(commandDescription);
	}

}
