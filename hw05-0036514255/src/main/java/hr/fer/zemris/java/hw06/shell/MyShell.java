package hr.fer.zemris.java.hw06.shell;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexDumpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeShellCommand;
/**
 * Implementation of user console
 * @author Iva
 *
 */
public class MyShell {
	
	/**
	 * Start of shell inside main method
	 * @param args arguments given by user while starting program
	 */
	public static void main(String[] args) {
		
		System.out.println("Welcome to MyShell v 1.0");
		ShellStatus status=ShellStatus.CONTINUE;
		Environment environment=new EnviromentOfShell();
		String commandName;
		String arguments=null;
		String l;
		
		do {
			try {
				
			 l = environment.readLine();
			}catch(ShellIOException ex) {
				System.err.println("Exeption :" +ex );
				break;
			}
			try {  
			  if(l.indexOf(' ')==-1){
				  commandName=l;
			  } else {
				  commandName=l.substring(0,l.indexOf(' '));
				  arguments=l.substring(l.indexOf(' ')+1);
			  }   
			  ShellCommand command = environment.commands().get(commandName);
			  if(command==null) {
				  environment.writeln("Command not found. Try again!");
				  continue;
			  }
			  status = command.executeCommand(environment, arguments);
			}catch(ShellIOException ex) {
				System.out.println("Exeption :" +ex +"Try again.");
			}
		}while(status!=ShellStatus.TERMINATE);
		

		
	}
	/**
	 * Class represents environment for shell
	 * @author Iva
	 *
	 */
	private static class EnviromentOfShell implements Environment{
		private Character multilineSymbol='|';
		private Character promptSymbol='>';
		private Character morelinesSymbol='\\';
		private Scanner scanner=new Scanner(System.in);
		private SortedMap<String,ShellCommand> commands;
		
		/**
		 * Constructor for class
		 */
		public EnviromentOfShell() {
			commands=new TreeMap<>();
			mapOfCommands();
		}
		
		/**
		 * Method for putting all commands in map which user cannot modified
		 */
		private void mapOfCommands() {
			commands.put("charsets", new CharsetsShellCommand());
			commands.put("cat", new CatShellCommand());
			commands.put("ls",new LsShellCommand());
			commands.put("tree", new TreeShellCommand());
			commands.put("copy", new CopyShellCommand());
			commands.put("mkdir", new MkdirShellCommand());
			commands.put("hexdump", new HexDumpShellCommand());
			commands.put("help", new HelpShellCommand());
			commands.put("symbol", new SymbolShellCommand());
			commands.put("exit",new ExitShellCommand());
		}
		
		@Override
		public String readLine() throws ShellIOException {
			write(promptSymbol+" ");
			StringBuilder sb=new StringBuilder();
			String l;
			
			try {
				l=scanner.nextLine();
			}catch(NoSuchElementException | IllegalStateException e) {
				throw new ShellIOException("There is no next line.");
			}
			
			while(l.endsWith(""+morelinesSymbol) ) {
				write(multilineSymbol+" ");
				l=l.substring(0, l.indexOf(morelinesSymbol));
				sb.append(l);
				try {
					l=scanner.nextLine();
				}catch(NoSuchElementException | IllegalStateException e) {
					throw new ShellIOException("Line ends with morelines symbol \"\\\" but there is no more lines to read.");
				}
			}
			sb.append(l);
			return  sb.toString();
				
		}

		@Override
		public void write(String text) throws ShellIOException {
			try {
				System.out.print(text);
			}catch(Exception ex) {
				throw new ShellIOException();
			}
			
		}

		@Override
		public void writeln(String text) throws ShellIOException {
			try {
				System.out.println(text);
			}catch(Exception ex) {
				throw new ShellIOException();
			}
			
		}

		@Override
		public SortedMap<String, ShellCommand> commands() {
			return Collections.unmodifiableSortedMap(commands);
		}

		@Override
		public Character getMultilineSymbol() {
			return multilineSymbol;
		}

		@Override
		public void setMultilineSymbol(Character symbol) {
			this.multilineSymbol=symbol;
		}

		@Override
		public Character getPromptSymbol() {
			return promptSymbol;
		}

		@Override
		public void setPromptSymbol(Character symbol) {
			this.promptSymbol=symbol;
		}

		@Override
		public Character getMorelinesSymbol() {
			return morelinesSymbol;
		}

		@Override
		public void setMorelinesSymbol(Character symbol) {
			this.morelinesSymbol=symbol;
			
		}
		
	}

}
