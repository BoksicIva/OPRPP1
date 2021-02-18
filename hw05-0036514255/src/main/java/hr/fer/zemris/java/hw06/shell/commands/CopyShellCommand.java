package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

public class CopyShellCommand implements ShellCommand {
	private String commandName="copy";
	private List<String> commandDescription=Arrays.asList(
			"The copy command expects two arguments: source file name and destination file name (i.e. paths and names).",
			"If destination file exists, you should ask user if it allowed to overwrite it.",
			"Copy method works only with files (no directories).",
			"If the second argument is directory, original file is copied into that directory using the original file name.");

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments==null) throw new ShellIOException("Cat command must have one or two arguments. ");
		String[] arrayOfArgs=SplitLineIntoArguments.split(arguments);
		if(arrayOfArgs.length!=2) throw new ShellIOException("copy command must have two arguments. ");
		
		Path sorcePath= Paths.get(arrayOfArgs[0]);
		Path destPath=Paths.get(arrayOfArgs[1]);
		
		if(Files.isDirectory(sorcePath)) throw new ShellIOException("First argument of copy command must be file not directory. ");
		if(!Files.exists(sorcePath)) throw new ShellIOException("First argument of copy command must be existing file. ");
		if(!Files.exists(destPath) && Files.isDirectory(destPath)) throw new ShellIOException("Second argument of copy command must be existing directory. ");
		
		if(Files.isDirectory(destPath))
			destPath=destPath.resolve(sorcePath.getFileName());
		
		if(Files.exists(destPath)) {
			env.writeln("Given destination path is existing file. Write \"NO\" if you don't want to overwrite, everything else will overwrite it.");
			String answer=env.readLine();
			if(answer=="NO") 
				return ShellStatus.CONTINUE;
		}
		
		
		try(InputStream is = Files.newInputStream(sorcePath);OutputStream os=Files.newOutputStream(destPath)){
			byte[] buff = new byte[1024];
			while(true) {
				int r = is.read(buff);
				if(r<1) break;
				os.write(buff,0,r);
				}
		} catch (IOException e) {
			e.printStackTrace();
		}
		env.writeln("File "+sorcePath.getFileName()+" is copied to " + destPath);
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
