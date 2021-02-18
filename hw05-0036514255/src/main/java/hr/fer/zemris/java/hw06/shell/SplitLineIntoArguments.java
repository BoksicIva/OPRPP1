package hr.fer.zemris.java.hw06.shell;

import java.util.ArrayList;
import java.util.List;

public class SplitLineIntoArguments {
	
	
	public static String[] split(String args) {
		boolean inside=false;
		String[] arguments;
		List<String> argumentsList = new ArrayList<>();
		String sb ="";
		
		for(int i=0;i<args.length();i++) {
			if(!inside && args.charAt(i)=='\"') {
				inside=true;
			}else if(inside && args.charAt(i)=='\"') {
				argumentsList.add(sb);
				sb="";
				inside=false;
				if(i+1<args.length() &&  args.charAt(i+1)!=' ')
					throw new ShellIOException("After \" only space can be present. ");
			}else  if(!inside &&  args.charAt(i)==' ' && sb!= "") {
				argumentsList.add(sb);
				sb="";
				
			}else	if(!inside &&  args.charAt(i)!=' ' ) {
				sb+=args.charAt(i);
			}else if(inside){
				if(args.charAt(i)=='\\')
					sb+="\\";
				else
					sb+=args.charAt(i);
			}
		}
		
		if(sb!= "") {
			argumentsList.add(sb);
			sb="";
		}
			
		
		arguments=new String[argumentsList.size()];
		int index=0;
		for(String argument: argumentsList) 
			arguments[index++]=argument;
			
		return arguments;
	}

}
