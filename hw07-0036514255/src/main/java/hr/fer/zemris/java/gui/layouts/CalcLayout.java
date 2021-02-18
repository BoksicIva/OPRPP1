package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;


public class CalcLayout implements LayoutManager2{
	private int gap;
	private final int NUMBER_OF_ROWS=5;
	private final int NUMBER_OF_COLUMNS=7;
	
	private Map<RCPosition,Component> mapOfComponents=new HashMap<>();	
	private int[] componentsHeight= new int[5];
	private int[] componentsWidth= new int[7];
	
	public CalcLayout() {
		this(0);
	}

	public CalcLayout(int gap) {
		if(gap < 0) throw new IllegalArgumentException("gap between components cannot be less than zero.");
		this.gap = gap;
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();	
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		mapOfComponents.values().removeIf(val -> comp.equals(val));
		
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return size(parent,"pref");
	}
	
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return size(parent,"min");
	}
	
	@Override
	public Dimension maximumLayoutSize(Container target) {
		return size(target,"max");
	}
	
	/**
	 * Method calculates size of components
	 * @param parent 
	 * @param keyWord tell us which function must be used
	 * @return new Dimension
	 */
	private Dimension size(Container parent,String keyWord) {
		double width=0;
		double widthOfComponent=0;
		int height=0;
		
		for(RCPosition key : mapOfComponents.keySet()) {
			if(key.equals(new RCPosition(1, 1)))
				widthOfComponent=(widthOfComponent(mapOfComponents.get(new RCPosition(1, 1)),keyWord)- 4 * gap)/5;
			else
				widthOfComponent= widthOfComponent(mapOfComponents.get(key), keyWord);
			if( widthOfComponent>width)
				width=widthOfComponent;
		}
		if(!mapOfComponents.isEmpty())
			height=heightOfComponent(keyWord);
		
		if(width==0 && height==0) 
			return null;
		
		return new Dimension((int) Math.ceil(width*7+6*gap+parent.getInsets().left+parent.getInsets().right),
				              height*5+4*gap+parent.getInsets().top+parent.getInsets().bottom);
	}
	
	/**
	 * Method calculates width of component 
	 * @param comp Componet that is checked
	 * @param keyWord tell us which function must be used
	 * @return
	 */
	private int widthOfComponent(Component comp,String keyWord) {
		switch(keyWord) {
		case "min"  :  return  (int) (comp.getMinimumSize().getWidth());
		case "max"  :  return  (int) (comp.getMaximumSize().getWidth());
		case "pref" :  return  (int) (comp.getPreferredSize().getWidth());
		}
		return 0;
	}
	
	/**
	 *  Method calculates height of component 
	 * @param keyWord
	 * @return
	 */
	private int heightOfComponent(String keyWord) {
		switch(keyWord) {
		case "min" :  return mapOfComponents.values().stream().mapToInt(v -> (int)v.getMinimumSize().getHeight()).max().getAsInt(); 
		case "max" :  return mapOfComponents.values().stream().mapToInt(v -> (int)v.getMaximumSize().getHeight()).max().getAsInt();
		case "pref" : return mapOfComponents.values().stream().mapToInt(v -> (int)v.getPreferredSize().getHeight()).max().getAsInt();
		}
		return 0;
	}
	

	@Override
	public void layoutContainer(Container parent) {
		Insets ins=parent.getInsets();
		int w=parent.getWidth()-ins.left-ins.right;
		int h=parent.getHeight()-ins.top-ins.bottom;
		int y=ins.top;
		int x=ins.left;
		
		double widthOfComponent=(int)Math.floor((w-(NUMBER_OF_COLUMNS-1)*gap)/NUMBER_OF_COLUMNS);
		double heightOfComponent=(h-(NUMBER_OF_ROWS-1)*gap)/NUMBER_OF_ROWS;
		
		calculateEachWidth(widthOfComponent,(w-(NUMBER_OF_COLUMNS-1)*gap) % NUMBER_OF_COLUMNS);
		calculateEachHeight(heightOfComponent,(h-(NUMBER_OF_ROWS-1)*gap)/NUMBER_OF_ROWS);
		
		for(RCPosition rc:mapOfComponents.keySet()) {
			//Dimension dim=c.getPreferredSize();
			
			if(rc.equals(new RCPosition(1, 1))) {
				mapOfComponents.get(rc).setBounds((int) (x),
												  (int) (y),
												  previousWidth(5)+4*gap,
												  componentsHeight[0]);
			}else {
				mapOfComponents.get(rc).setBounds((int) (x+(rc.getColumn()-1)*gap+previousWidth(rc.getColumn()-1)),
												  (int) (y+(rc.getRow()-1)*gap+previousHeight(rc.getRow()-1)),
												  componentsWidth[rc.getColumn()-1],
												  componentsHeight[rc.getRow()-1]);
				//System.out.println(componentsWidth[rc.getColumn()-1]);
				//System.out.println(componentsHeight[rc.getRow()-1]);
			}
			
			
		}	
		
	}
	/**
	 * Calculates height of all components before index
	 * @param index
	 * @return
	 */
	private int previousHeight(int index) {
		int sum=0;
		for(int i=0;i<index;i++)
			sum+=componentsHeight[i];
		return sum;
	}
	/**
	 * Calculates width of all components before index
	 * @param index
	 * @return
	 */
	private int previousWidth(int index) {
		int sum=0;
		for(int i=0;i<index;i++)
			sum+=componentsWidth[i];
		return sum;
	}
	
	/**
	 * Calculates height for each component
	 * @param hightOfComponent
	 * @param mode
	 */
	private void calculateEachHeight(double hightOfComponent,int mode) {
		for(int i=0;i<5;i++) {
			componentsHeight[i]=(int)Math.floor(hightOfComponent);
			}
		switch(mode) {
		case 0: break;			
		case 1: 
			componentsHeight[2]++;	
				break;	
		case 2:
			componentsHeight[3]++;	
			componentsHeight[1]++;
			break;	
		case 3:
			componentsHeight[4]++;	
			componentsHeight[2]++;	
			componentsHeight[0]++;
			break;	
		case 4:
			componentsHeight[4]++;
			componentsHeight[3]++;	
			componentsHeight[1]++;
			componentsHeight[0]++;
			break;	
		}
	}
	/**
	 * Calculates width of each component
	 * @param widthOfComponent
	 * @param mode
	 */
	private void calculateEachWidth(double widthOfComponent,int mode) {
		for(int i=0;i<7;i++) {
			componentsWidth[i]=(int)Math.floor(widthOfComponent);
			}
		
		switch(mode) {
		case 0: break;			
		case 1: 
			componentsWidth[3]++;	
			break;	
		case 2:
			componentsWidth[2]++;	
			componentsWidth[5]++;
			break;	
		case 3:
			componentsWidth[5]++;	
			componentsWidth[3]++;	
			componentsWidth[1]++;
			break;	
		case 4:
			componentsWidth[6]++;	
			componentsWidth[4]++;
			componentsWidth[2]++;
			componentsWidth[0]++;
			break;	
		case 5:
			componentsWidth[6]++;	
			componentsWidth[4]++;
			componentsWidth[3]++;
			componentsWidth[1]++;
			componentsWidth[0]++;
			break;	
		case 6:
			componentsWidth[6]++;
			componentsWidth[5]++;	
			componentsWidth[4]++;
			componentsWidth[2]++;
			componentsWidth[1]++;
			componentsWidth[0]++;
			break;		
		}
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		RCPosition rc;
		if(comp==null || constraints==null) throw new NullPointerException("Component and constraints cannot have null value");
		if(constraints.getClass()!=String.class &&  
		   constraints.getClass()!=RCPosition.class) throw new IllegalArgumentException("Constraints are not String or RCPosyition class type.");
		if(constraints.getClass()==String.class) {
			try {
				rc=RCPosition.parse((String) constraints);
			}catch(IllegalArgumentException e) {
				throw new IllegalArgumentException("Constraints are not parseable to RCPosition");
			}
		}else {
			rc=(RCPosition) constraints;
		}
		if(!mapOfComponents.containsKey(rc) && validConstraints(rc) && !mapOfComponents.values().contains(comp))
		   mapOfComponents.put(rc, comp);
		else
			throw new CalcLayoutException("Given constraints are already used or out of range.");
	}
	
	private boolean validConstraints(RCPosition rc) {
		if(rc.getRow()==1 && rc.getColumn()>1 && rc.getColumn()<6)
			return false;
		if(rc.getRow() > 0 && rc.getRow()<= NUMBER_OF_ROWS && 
		   rc.getColumn() > 0 && rc.getColumn() <= NUMBER_OF_COLUMNS)
			return true;
		return false;
	}

	

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {	
	}
	
	

}
