package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.junit.jupiter.api.Test;

public class CalcLayoutTest {
	@Test
	public void ValidConstraintsTest() {
		CalcLayout cl = new CalcLayout();
		JTextArea tArea = new JTextArea();
		JTextField tField = new JTextField();
		cl.addLayoutComponent(tArea, new RCPosition(1, 1));
		cl.addLayoutComponent(tField, RCPosition.parse("5,7"));
	}

	@Test
	public void ConstraintsOutOfBoundsTest() {
		CalcLayout cl = new CalcLayout();
		JTextArea tArea = new JTextArea();

		assertThrows(CalcLayoutException.class, () -> {
			cl.addLayoutComponent(tArea, new RCPosition(0, 1));
		});

		assertThrows(CalcLayoutException.class, () -> {
			cl.addLayoutComponent(tArea, RCPosition.parse("5,-7"));
		});

		assertThrows(CalcLayoutException.class, () -> {
			cl.addLayoutComponent(tArea, RCPosition.parse("6,8"));
		});
	}

	@Test
	public void PredefinedReservedConstraintsTest() {
		CalcLayout cl = new CalcLayout();
		JTextArea tArea = new JTextArea();

		assertThrows(CalcLayoutException.class, () -> {
			cl.addLayoutComponent(tArea, new RCPosition(1, 2));
		});

		assertThrows(CalcLayoutException.class, () -> {
			cl.addLayoutComponent(tArea, RCPosition.parse("1,3"));
		});

		assertThrows(CalcLayoutException.class, () -> {
			cl.addLayoutComponent(tArea, RCPosition.parse("1,5"));
		});
	}

	@Test
	public void MultipleComponentOnSameConstraintTest() {
		CalcLayout cl = new CalcLayout();
		JTextArea tArea = new JTextArea();
		JTextArea tField = new JTextArea();

		cl.addLayoutComponent(tArea, new RCPosition(1, 1));
		assertThrows(CalcLayoutException.class, () -> {
			cl.addLayoutComponent(tField, new RCPosition(1, 1));
		});
	}

	@Test
	public void MultipleConstraintsOnSameComponentTest() {
		CalcLayout cl = new CalcLayout();
		JTextArea tArea = new JTextArea();

		cl.addLayoutComponent(tArea, new RCPosition(1, 1));
		assertThrows(CalcLayoutException.class, () -> {
			cl.addLayoutComponent(tArea, new RCPosition(2, 2));
		});
	}
	
	@Test
	public void testAddThrowsOnInvalidConstraints() {
		JPanel p = new JPanel(new CalcLayout(3));

		assertThrows(
				IllegalArgumentException.class, () -> p.add(new JLabel("x"), Integer.valueOf(5))
		);
		assertThrows(IllegalArgumentException.class, () -> p.add(new JLabel("x"), "x,y"));
		assertThrows(IllegalArgumentException.class, () -> p.add(new JLabel("x"), "5.0,1"));
		assertThrows(IllegalArgumentException.class, () -> p.add(new JLabel("x"), "1,5.0"));
		assertThrows(IllegalArgumentException.class, () -> p.add(new JLabel("x"), "1,,5.0"));

		String[] array
				= new String[] {
						"1,2", "1,3", "1,4", "1,5", "-1,1", "1,-1", "-1,-1", "-1,-1", "1, 8",
						"6, 1", "0,0", "6,8" };

		for (int i = 0; i < array.length; i++) {
			String s = array[i];
			assertThrows(CalcLayoutException.class, () -> {
				p.add(new JLabel("x"), s);
			});
		}

		assertThrows(CalcLayoutException.class, () -> {
			p.add(new JLabel("x"), "1,1");
			p.add(new JLabel("x"), new RCPosition(1, 1));
		});

		assertThrows(CalcLayoutException.class, () -> {
			JLabel label = new JLabel("x");
			p.add(label, "1,1");
			p.add(label, new RCPosition(2, 1));
		});

		assertThrows(NullPointerException.class, () -> p.add(null, "1,1"));
		assertThrows(NullPointerException.class, () -> p.add(new JLabel("x"), null));
	}

	@Test
	public void testWidthAndHeight1() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(10, 30));
		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(20, 15));
		p.add(l1, new RCPosition(2, 2));
		p.add(l2, new RCPosition(3, 3));
		Dimension dim = p.getPreferredSize();

		assertEquals(dim.width, 152);
		assertEquals(dim.height, 158);
	}

	@Test
	public void testWidthAndHeight2() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(108, 15));
		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(16, 30));
		p.add(l1, new RCPosition(1, 1));
		p.add(l2, new RCPosition(3, 3));
		Dimension dim = p.getPreferredSize();

		assertEquals(dim.width, 152);
		assertEquals(dim.height, 158);
	}

}
