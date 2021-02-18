package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.SplitLineIntoArguments;

public class LsShellCommand implements ShellCommand {
	private String commandName="ls";
	private List<String> commandDescription=Arrays.asList(
			"Command ls takes a single argument – directory – and writes a directory listing (not recursive)",
			"The output consists of 4 columns.", 
			"First column indicates if current object is directory (d), readable (r), writable (w) and executable (x).",
			"Second column contains object size in bytes that is right aligned and occupies 10 characters.",
			"Follows file creation date/time and finally file name.");


	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments==null) throw new ShellIOException("Cat command must have one or two arguments. ");
		String[] arrayOfArgs=SplitLineIntoArguments.split(arguments);
		
		Path directoryPath= Paths.get(arrayOfArgs[0]);
		File directory = new File(arrayOfArgs[0]);
		if(!Files.isDirectory(directoryPath))
			throw new ShellIOException("Given path is not direcoty. Enter directory path to execute ls command. ");
		
		if(arrayOfArgs.length==1) {
			File[] childeren = directory.listFiles();
			for(File file : childeren) {
				env.write(file.isDirectory()? "d":"-");
				env.write(Files.isReadable(file.toPath())? "r":"-");
				env.write(Files.isWritable(file.toPath())? "w":"-");
				env.write(Files.isExecutable(file.toPath())? "x ":"- ");
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				BasicFileAttributeView faView = Files.getFileAttributeView(file.toPath(), BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
				);
				BasicFileAttributes attributes = null;
				try {
					attributes = faView.readAttributes();
				} catch (IOException e) {
					 throw new ShellIOException();
				}
				FileTime fileTime = attributes.creationTime();
				String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
				
				try {
					env.write(String.format("%10s ", Files.size(file.toPath())));
				} catch (ShellIOException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				env.write(formattedDateTime);
				env.writeln(" "+file.getName());
				}
			
		}else {
			 throw new ShellIOException("ls command must have one argument. ");
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
