package hr.fer.zemris.java.hw06.shell.commands;



import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.SplitLineIntoArguments;

public class CatShellCommand implements ShellCommand {
	private String commandName="cat";
	private List<String> commandDescription=Arrays.asList(
			"Command cat takes one or two arguments.",
			"The first argument is path to some file and is mandatory.",
			"The second argument is charset name that should be used to interpret chars from bytes.",
			"If not provided, a default platform charset should be used (see java.nio.charset.Charset class for details).",
			"This command opens given file and writes its content to console.");

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments==null) throw new ShellIOException("Cat command must have one or two arguments. ");
		String[] arrayOfArgs=SplitLineIntoArguments.split(arguments);
		
		Charset charset=Charset.defaultCharset();;
		if(arrayOfArgs.length==2) {
			if(Charset.isSupported(arrayOfArgs[1]))
				charset=Charset.forName(arrayOfArgs[1]);
		}else if(arrayOfArgs.length!=1) {
			 throw new ShellIOException("Cat command must have one or two arguments. ");
		}
		
		Path filePath= Paths.get(arrayOfArgs[0]);
		if(Files.isDirectory(filePath))
			throw new ShellIOException("Given path is direcoty. Enter file path to execute cat command. ");
		
		try(InputStream is = Files.newInputStream(filePath)) {
			
			byte[] buff = new byte[1024];
			while(true) {
				int r = is.read(buff);
				if(r<1) break;
				String fromFile=new String(buff,0,r, charset);
				env.write(fromFile);
				}
		}catch(IOException ex) {
			throw new ShellIOException("Error while reading. ");
		}
		
		env.writeln("");
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
