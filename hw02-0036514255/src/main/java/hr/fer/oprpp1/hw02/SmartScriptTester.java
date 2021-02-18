package hr.fer.oprpp1.hw02;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths; 

public class SmartScriptTester {
	
	public static void  main(String[] args) throws IOException{
		String docBody = new String(
                Files.readAllBytes(Paths.get("src/test/resources/extra/primjer1.txt")), 
                StandardCharsets.UTF_8);
		SmartScriptParser parser = null; 
		
		try {
		  parser = new SmartScriptParser(docBody);
		} catch(SmartScriptParserException e) {
		  System.out.println("Unable to parse document!");
		  System.exit(-1);
		} catch(Exception e) {
		  System.out.println("If this line ever executes, you have failed this class!");
		  System.exit(-1);
		}
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = document.toString();
		System.out.println(originalDocumentBody); 
		System.out.println(document);
		
		SmartScriptParser parser1 = new SmartScriptParser(docBody);
		DocumentNode document1 = parser1.getDocumentNode();
		String originalDocumentBody1 = document.toString();
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody1);
		DocumentNode document2 = parser2.getDocumentNode();
		// now document and document2 should be structurally identical trees boolean same = document.equals(document2);  // ==> "same" must be true
		boolean same = document.equals(document2);  
		System.out.println(document2);
		System.out.println(same);
	}

}
