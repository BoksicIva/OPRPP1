package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

public class HexDumpShellCommand implements ShellCommand {
	private String commandName="hexdump";
	private List<String> commandDescription=Arrays.asList(
			"The hexdump command expects a single argument: file name, and produces hex-output.");

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments==null) throw new ShellIOException("hexdump command must have one argument. ");
		String[] arrayOfArgs=SplitLineIntoArguments.split(arguments);
		if(arrayOfArgs.length!=1)  throw new ShellIOException("hexdump command must have one argument. ");
		
		Path filePath= Paths.get(arrayOfArgs[0]);
		if(Files.isDirectory(filePath)) throw new ShellIOException("Argument of hexdump command must be file not directory. ");
		if(!Files.exists(filePath)) throw new ShellIOException("Argument of HEXDUMP command must be existing file. ");
		
		try(InputStream is=Files.newInputStream(filePath)){
			byte[] buff=new byte[16];
			int lineNumber=0;
			while(true) {
				int r = is.read(buff);
				if(r<1) break;
				//String bytesFromFile=new String()
				String hex=Integer.toHexString(lineNumber);
				env.write("0".repeat(16-hex.length())+hex.toUpperCase()+": ");
				
				for(int i=0;i<16;i++) {
					if(i==8)
						env.write("|");
					if(i>=r) {
						env.write("  ");
					}else {
						env.write(String.format("%2X", buff[i]));	
					}
					if(i!=7)
						env.write(" ");
				}
				env.write("| ");
				for(int i=0;i<16;i++) {
					if(i>=r) {
						env.write("  ");
					}else {
						if(buff[i]<32 || buff[i]>127)
							env.write(".");
						else
							env.write((char)buff[i]+"");
					}
				}
				env.writeln("");
				
				lineNumber+=16;
				}
		} catch (IOException e) {
			e.printStackTrace();
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
