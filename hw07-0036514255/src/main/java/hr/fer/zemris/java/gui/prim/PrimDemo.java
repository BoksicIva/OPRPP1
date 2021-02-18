package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;


public class PrimDemo extends JFrame{

	private static final long serialVersionUID = 1L;
	
	public PrimDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("PrimDemo");
		setLocation(20, 20);
		setSize(500, 200);
		initGUI();
		//pack();
	}
	
	private void initGUI() {
		Container cp=getContentPane();
		cp.setLayout(new BorderLayout());
		
		PrimListModel modelList=new PrimListModel();
		
		JList<Integer> list1=new JList<>(modelList);
		JList<Integer> list2=new JList<>(modelList);
		
		list1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		list2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		JPanel p=new JPanel(new GridLayout(1,0));
		p.add(new JScrollPane(list1));
		p.add(new JScrollPane(list2));
		
		cp.add(p,BorderLayout.CENTER);
		
		JButton nextPrim = new JButton("Generate next prim number!");
		cp.add(nextPrim,BorderLayout.PAGE_END);
		
		nextPrim.addActionListener(e ->{
			modelList.next();
		});
	}
	
	private static class PrimListModel implements ListModel<Integer>{
		private List<Integer> primNumbers=new ArrayList<>();
		private List<ListDataListener> listeners=new ArrayList<>();
		
		
		
		public PrimListModel() {
			super();
			primNumbers.add(1);
		}


		public void next() {
			add();
		}
		
		
		private void add() {
			int lastPrim;
			boolean isPrim=false;
			lastPrim=primNumbers.get(primNumbers.size()-1);
			
			while(!isPrim) {
				lastPrim++;
				isPrim=true;
				for(int i=2;i<=lastPrim/2;i++)
					if(lastPrim % i==0) {
						isPrim=false;
						break;
					}
			}
		
			primNumbers.add(lastPrim);
			
			ListDataEvent e=new ListDataEvent(this,ListDataEvent.INTERVAL_ADDED, primNumbers.size()-1, primNumbers.size()-1);
			for(ListDataListener l: listeners) {
				l.intervalAdded(e);
			}
		}


		@Override
		public int getSize() {
			return primNumbers.size();
		}

		@Override
		public Integer getElementAt(int index) {
			return primNumbers.get(index);
		}

		@Override
		public void addListDataListener(ListDataListener l) {
			listeners.add(l);
			
		}

		@Override
		public void removeListDataListener(ListDataListener l) {
			listeners.remove(l);
			
		}
		
	}
	
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new PrimDemo().setVisible(true);
		});
	}
}
