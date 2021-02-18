package hr.fer.zemris.java.gu.charts;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;



public class BarChartDemo extends JFrame{
	private static final long serialVersionUID = 1L;
	private static BarChart barChart;
	private static Path path;
	
	public BarChartDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
		setSize(700,700);
		//pack();
	}
	
	private void initGUI() {
		Container cp=getContentPane();
		cp.setLayout(new BorderLayout());
		
		JLabel label=new JLabel(path.toString(),SwingConstants.CENTER );
		cp.add(label,BorderLayout.PAGE_START);
		
		JComponent barChartComponent=new BarChartComponent(barChart);
		cp.add(barChartComponent);
	}
	
	/**
	 * Method reads line of document and parses it into list of XYValues
	 * @param s line of document
	 * @return list of XYValues
	 */
	private static List<XYValue> parseInXYList(String s){
		String[] elements=s.split(" ");
		List<XYValue> list=new ArrayList<>();
		int x,y;
		for(String element: elements) {
			String[] coordinates=element.split(",");
			x=isInt(coordinates[0]);
			y=isInt(coordinates[1]);
			list.add(new XYValue(x,y));
			
		}
		return list;
	}
	
	/**
	 * method checks if number is parseable to int 
	 * @param val Objcet that needs to be checked
	 * @return object parsed into int
	 */
	private static int isInt(Object val) {
	    if (val == null) {
	    	throw new NumberFormatException();
	    }
	    try {
	        int number = Integer.parseInt((String) val);
	        return number;
	    } catch (NumberFormatException nfe) {
	        throw new NumberFormatException();
	    }   
	}


	public static void main(String... args) {
		List<String> lines = null;
		if(args.length!=1) throw new IllegalArgumentException("Only file path should be entered.");
		
		path=Paths.get(args[0]);
		
		try{ 
	      lines =  Files.readAllLines(path, StandardCharsets.UTF_8); 
	    } catch (IOException e){ 
	    	e.printStackTrace(); 
	    }
		
		
		try {
			List<XYValue> list=parseInXYList(lines.get(2));
			barChart=new BarChart(list, lines.get(0), lines.get(1), isInt(lines.get(3)),isInt( lines.get(4)),isInt( lines.get(5)));
		}catch(NumberFormatException num){
			throw new IllegalArgumentException("Lines that sholud represent numeric values inside file are not parsable into int.");
		}
		
		SwingUtilities.invokeLater(()->{
			new BarChartDemo().setVisible(true);
		});
		
		
	}
	

}
