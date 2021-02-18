package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.SplitLineIntoArguments;

public class MkdirShellCommand implements ShellCommand {
	private String commandName="mkdir";
	private List<String> commandDescription=Arrays.asList(
			"The mkdir command takes a single argument: directory name, and creates the appropriate directory structure.");

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] arrayOfArgs=SplitLineIntoArguments.split(arguments);
		
		if(arrayOfArgs.length==1) {
			try {
			Files.createDirectories(Paths.get(arrayOfArgs[0]));
			}catch(Exception e) {
				throw new ShellIOException();
			}
		}else {
			throw new ShellIOException("Mkdir command must have one argument. ");
		}
		env.writeln("Directory" +arrayOfArgs[0]+" is created. ");
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
