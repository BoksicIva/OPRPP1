package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class CharsetsShellCommand implements ShellCommand {
	private String commandName="charsets";
	private List<String> commandDescription=Arrays.asList(
			"Command charsets takes no arguments and lists names of supported charsets for your Java platform.",
			"A single charset name is written per line.");
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments == null) {
			Map<String,Charset> charset=Charset.availableCharsets();
			for(String name:charset.keySet()) {
				env.writeln(name);
			}
		}else {
			throw new ShellIOException("Charsets command does not have arguments.");
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
