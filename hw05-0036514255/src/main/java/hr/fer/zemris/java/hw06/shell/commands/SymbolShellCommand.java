package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class SymbolShellCommand implements ShellCommand {
	private String commandName="symbol";
	private List<String> commandDescription=Arrays.asList(
			"Command symbol works on two ways depending on numbers of arguments.",
			"If it is given one argument naming accepted symbols it returns current used symbol for named one.",
			"If it is tree arguments given command changes current used symbol for named one.");


	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] arrayOfArgs=arguments.split(" ");
		
		if(arrayOfArgs.length==1) {
			switch(arrayOfArgs[0]) {
			case "PROMPT":     env.write("Symbol for PROMPT is \'");
							   env.write(env.getPromptSymbol()+"");
							   env.writeln("\'");
							   break;
			case "MULTILINE":  env.write("Symbol for MULTILINE is \'");
							   env.write(env.getMultilineSymbol()+"");
							   env.writeln("\'");
							   break;
			case "MORELINES":  env.write("Symbol for MORELINES is \'");
							   env.write(env.getMorelinesSymbol()+"");
							   env.writeln("\'");
							   break;	
			default:           env.writeln("Named symbol is not used in shell");
			}
		}else if(arrayOfArgs.length==2) {
			switch(arrayOfArgs[0]) {
			case "PROMPT":     env.write("Symbol for PROMPT changed from \'");
							   env.write(env.getPromptSymbol()+"");
							   env.write("\' to \'");
							   env.writeln(arrayOfArgs[1]+"\'");
							   env.setPromptSymbol(arrayOfArgs[1].charAt(0));
							   break;
			case "MULTILINE":  env.write("Symbol for MULTILINE changed from \'");
							   env.write(env.getMultilineSymbol()+"");
							   env.write("\' to \'");
							   env.writeln(arrayOfArgs[1]+"\'");
							   env.setMultilineSymbol(arrayOfArgs[1].charAt(0));
							   break;
			case "MORELINES":  env.write("Symbol for MORELINES changed from \'");
							   env.write(env.getMorelinesSymbol()+"");
							   env.write("\' to \'");
							   env.writeln(arrayOfArgs[1]+"\'");
							   env.setMorelinesSymbol(arrayOfArgs[1].charAt(0));
							   break;	
			default:           env.writeln("Named symbol is not used in shell");
			}
		}else {
			throw new ShellIOException("Symbol command supports only 2 arguments. Check \'help symbol\' for more information");
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
