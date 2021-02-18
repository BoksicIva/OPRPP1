package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.SplitLineIntoArguments;

public class TreeShellCommand implements ShellCommand {
	private String commandName="tree";
	private List<String> commandDescription=Arrays.asList(
			"The tree command expects a single argument: directory name and prints a tree (each directory level shifts output two charatcers to the right).");

	private static class PrintFiles
    extends SimpleFileVisitor<Path> {
		int depth;
		Environment env;
		
		public PrintFiles(Environment env) {
			depth=0;
			this.env=env;
		}
		
		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			depth-=2;
			return super.postVisitDirectory(dir, exc);
		}
		
		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			depth+=2;
			return super.preVisitDirectory(dir, attrs);
		}
		
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			env.writeln(" ".repeat(depth)+file.getFileName());
			return FileVisitResult.CONTINUE;
		}
		
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments==null) throw new ShellIOException("Cat command must have one or two arguments. ");
		String[] arrayOfArgs=SplitLineIntoArguments.split(arguments);
		PrintFiles pf=new PrintFiles(env);
		
		if(arrayOfArgs.length==1) {
			try {
				Files.walkFileTree(Paths.get(arrayOfArgs[0]),pf);
			}catch(Exception e) {
				throw new ShellIOException();
			}
		}else {
			throw new ShellIOException();
		}
		
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return commandName;
	}

	@Override
	public List<String> getCommandDescription() {
		return commandDescription;
	}

}
