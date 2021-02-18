package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.lsystems.LSystem;

public class LSystemBuilderImplTest {
	
	@Test
	public void SimpleGenerateFromHomeworkTest() {
		LSystemBuilderImpl builder = new LSystemBuilderImpl();
		builder.setAxiom("F");
		builder.registerProduction('F', "F+F--F+F");
		LSystem system0 = builder.build();
		String result0 = system0.generate(0);
		assertEquals("F", result0);

		String result1 = system0.generate(1);
		assertEquals("F+F--F+F", result1);

		String result2 = system0.generate(2);
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", result2);

	}

	@Test
	public void PlantProductionsTest() {
		LSystemBuilderImpl builder = new LSystemBuilderImpl();
		builder.setAxiom("GF");
		builder.registerProduction('F', "F[+F]F[-F]F");
		LSystem system0 = builder.build();
		String result0 = system0.generate(0);
		assertEquals("GF", result0);
	}

}
