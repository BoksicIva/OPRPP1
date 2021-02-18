package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.oprpp1.custom.collections.Dictionary;
import hr.fer.oprpp1.math.Vector2D;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
/**
 * Class LSystemBuilderImpl is implementation of LSystemBuilder interface
 * @author Iva
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder{
	private Dictionary<Character,String> productions;
	private Dictionary<Character,Command> actions;
	private double unitLength=0.1;
	private double unitLengthDegreeScaler=1;
	private Vector2D origin=new Vector2D(0,0);
	private double angle=0;
	private String axiom="";
	
	/**
	 * Class LSystemImpl is implementation of LSystem interface and overrides 
	 * @author Iva
	 *
	 */
	private class LSystemImpl implements LSystem{
		/**
		 * Method creates new turtle state and sets values for TurtleState class variables and generates string for given level 
		 */
		@Override
		public void draw(int level, Painter painter) {
			Context ctx=new Context();
			TurtleState state=new TurtleState();
			state.setColor(Color.BLACK);
			state.setCurrentPosition(origin.copy());
			state.setDirection((new Vector2D(1.0,0.0)).rotated(angle*Math.PI/180.0));
			state.setEffectiveLength(unitLength*Math.pow(unitLengthDegreeScaler,level));
			ctx.pushState(state);
			String generatedString=generate(level);
			for(int i=0;i<generatedString.length();i++) {
				Command command=actions.get(generatedString.charAt(i));
				if(command==null) continue;
				command.execute(ctx, painter);
			}
			
		}
		/**
		 * Method generate new commands for turtle replacing axion with production on specific level
		 */
		@Override
		public String generate(int level) {
			String axiomImpl=axiom;
			for(int i=0;i<=level-1;i++) {
				String axiomLevel="";
				for(int j=0;j<axiomImpl.length();j++) {
					if(productions.get(axiomImpl.charAt(j))!=null) {
						axiomLevel+=productions.get(axiomImpl.charAt(j));
					}else {
						axiomLevel+=axiomImpl.charAt(j);
					}
				}
				axiomImpl=axiomLevel;
			}
			return axiomImpl;
		}
		
	}
	
	
	/**
	 * Constructor for LSystemBuilderImpl class
	 * Makes new instance for two Dictionaries productions and actions
	 */
	public LSystemBuilderImpl() {
		this.productions = new Dictionary<Character,String>();
		this.actions = new Dictionary<Character,Command>();
	}
	/**
	 * Method created new instance of LSystemImpl class
	 */
	@Override
	public LSystem build() {
		return new LSystemImpl();
	}
	/**
	 * Method configure commands and actions from given array of String representing each row in document
	 */
	@Override
	public LSystemBuilder configureFromText(String[] expressions) {
		for(int i=0;i<expressions.length;i++) {
			String[] expression=expressions[i].split("\\s+");
			try {
				switch(expression[0]) {
				case "origin" :  				setOrigin(Double.parseDouble(expression[1]),Double.parseDouble(expression[2]));
								 				break;
								 				
				case "angle" :   				setAngle(Double.parseDouble(expression[1]));
								 				break;
								 				
				case "unitLength" : 			setUnitLength(Double.parseDouble(expression[1]));
												break;
												
				case "unitLengthDegreeScaler" : setUnitLengthDegreeScaler(createUnitLengthDegreeScaler(expression));
												break;
												
				case "command" : 				createCommand(expression);
												break;
					
				case "axiom" :                  setAxiom(expression[1]);
												break;
												
				case "production":              registerProduction(expression[1].toCharArray()[0], expression[2]);	
												break;
												
				case ""  :                      break;
				
				default:                        throw new IllegalArgumentException();
				
				}
			}catch(Exception e) {
				throw new IllegalArgumentException();
		    }
	    }
		return this;
	}
	/**
	 * Method creates unitLengthDegreeScaler from String expression
	 * @param expression array of String representing row where unitLengthDegreeScaler is set to double value
	 * @return double value for unitLengthDegreeScaler given from row by parsing math expression into double
	 */
	private double createUnitLengthDegreeScaler(String[] expression) {
		if(expression.length==2 && isDouble(expression[1])) {
			return Double.parseDouble(expression[1]);
		}else if(expression.length==4 && expression[2].equals("/") && isDouble(expression[1]) && isDouble(expression[3])) {
			double scaler=Double.parseDouble(expression[1]) / Double.parseDouble(expression[3]);
			return scaler;
		}else if(expression.length==2 && expression[1].contains("/")) {
			String[] numbers=expression[1].split("/");
			if(isDouble(numbers[0]) && isDouble(numbers[1])) {
				double scaler=Double.parseDouble(numbers[0]) / Double.parseDouble(numbers[1]);
				return scaler;
			}		
		}else if(expression.length==3 && expression[2].contains("/")) {
			String[] number=expression[2].split("/");
			double scaler=Double.parseDouble(expression[1]) / Double.parseDouble(number[1]);
			return scaler;
		}
		throw new IllegalArgumentException();
			
	}
	/**
	 * Method checks if given Object value is double 
	 * @param val parameter that is checked
	 * @return true if val can be parsed into double, otherwise returns false
	 */
	private static boolean isDouble(Object val) {
	    if (val == null) {
	        return false;
	    }
	    try {
	        Double number = Double.parseDouble((String) val);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	/**
	 * Method creates command from array of Strings by calling registerCommand method
	 * @param expression
	 * @return
	 */
	private LSystemBuilder createCommand(String[] expression) {
		char key=expression[1].toCharArray()[0];
		String value="";
		for(int i=2;i<expression.length;i++) {
			value+=expression[i]+" ";
		}
		return registerCommand(key, value);
	}
	/**
	 * Method adds new command in Dictionary actions depending on keyword in given string named value which is split by space 
	 */
	@Override
	public LSystemBuilder registerCommand(char key, String value) {
		Command command = null;
		String[] expression=value.split("\\s+");
		try {
			switch(expression[0]) {
			case "draw":    		command=new DrawCommand(Double.parseDouble(expression[1]));
									break;
									
			case "skip":			command=new SkipCommand(Double.parseDouble(expression[1]));
									break;
				
			case "scale":			command=new ScaleCommand(Double.parseDouble(expression[1]));
									break;
				
			case "rotate":			command=new RotateCommand(Double.parseDouble(expression[1]));
									break;
				
			case "push":			command=new PushCommand();
									break;
									
			case "pop":             command=new PopCommand();
									break;
									
			case "color":           command=new ColorCommand(createColor(expression[1]));
									break;
									
			default:                throw new IllegalArgumentException();						
			}
		}catch(Exception e) {
			throw new IllegalArgumentException();
		}
		actions.put(key, command);
		return this;
	}
	/**
	 * Method creates new color from given hexadecimal value
	 * @param hexa
	 * @return
	 */
	private Color createColor(String hexa) {
		return new Color(
	            Integer.valueOf(hexa.substring(0, 2), 16),
	            Integer.valueOf(hexa.substring(2, 4), 16),
	            Integer.valueOf(hexa.substring(4, 6), 16));
	}
	/**
	 * Method gets key and value and adds it in Dictionary productions variable 
	 */
	@Override
	public LSystemBuilder registerProduction(char key, String value) {
		productions.put(key, value);
		return this;
	}
	/**
	 * Setter for angle variable
	 */
	@Override
	public LSystemBuilder setAngle(double angle) {
		this.angle=angle;
		return this;
	}
	/**
	 * Setter for axiom variable
	 */
	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom=axiom;
		return this;
	}
	/**
	 * Setter for origin variable
	 */
	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		this.origin=new Vector2D(x,y);
		return this;
	}
	/**
	 * Setter for unitLength variable
	 */
	@Override
	public LSystemBuilder setUnitLength(double length) {
		this.unitLength=length;
		return this;
	}
	/**
	 * Setter for unitLengthDegreeScaler variable
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double scaler) {
		this.unitLengthDegreeScaler=scaler;
		return this;
	}

	

}



