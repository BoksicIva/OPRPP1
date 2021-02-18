package hr.fer.oprpp1.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantInteger;
import hr.fer.oprpp1.custom.scripting.elems.ElementFunction;
import hr.fer.oprpp1.custom.scripting.elems.ElementOperator;
import hr.fer.oprpp1.custom.scripting.elems.ElementString;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;
import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.nodes.EchoNode;
import hr.fer.oprpp1.custom.scripting.nodes.ForLoopNode;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;

public class SmartScriptParserTest {
	
	private static DocumentNode runParserOnFile(String filepath) throws IOException {
		String docBody = new String(
				Files.readAllBytes(Paths.get(filepath)),
				StandardCharsets.UTF_8);
		SmartScriptParser parser = null;
		parser = new SmartScriptParser(docBody);
		return parser.getDocumentNode();
	}
	@Disabled
	@Test
	void testParsingDocument1() throws IOException {
		DocumentNode parsed = runParserOnFile("src/test/resources/extra/primjer1.txt");

		DocumentNode expected = new DocumentNode();

		expected.addChildNode(new TextNode("Ovo je \r\n"
				+ "sve jedan text node\r\n".toString()));

		assertEquals(expected, parsed);
	}


}
