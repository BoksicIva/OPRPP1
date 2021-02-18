package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Class draws Mandelbrot fractals for given roots of complex number 
 * @author Iva
 *
 */
public class Newton {

	/**
	 * The program must ask user to enter roots, 
	 * and then it must start fractal viewer and display the fractal
	 * @param args are not used in this program
	 * 
	 */
	public static void main(String[] args) {
		String input;
		ComplexRootedPolynomial polynomial = null;
		List<Complex> lines=new ArrayList<>();
		Complex complex;
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer. Please enter at least two roots, one root per line. Enter 'done' when done.");
		Scanner scanner = new Scanner(System.in);
		int index=1;
		
		
	    while(true) {
			System.out.print("Root "+(index++) +"> ");
			input=scanner.nextLine();
			if(input.startsWith("done") && lines.size()>=2) {
				break;
			}else if(input=="done" && lines.size()<2){
				System.out.println("Please enter at least two roots , one root per line.");
				continue;
			}
			try {
				complex=Complex.parse(input);
				lines.add(complex);
			 } catch(NumberFormatException nfe) {
				 System.out.println("Entered complex number dont have valid form. Please try again.");
				 continue;
			 }
			
		}
		 
		 System.out.print("Image of fractal will appear shortly. Thank you.");
		 
		 polynomial=ComplexRootedPolynomial.toComplexRootedPolynomial(lines);
		 
		 
		 FractalViewer.show(new MyProducer(polynomial));
	}
	/**
	 * Class that communicate whit GUI which draw Mandelbrod's fractals
	 * @author Iva
	 *
	 */
	public static class MyProducer implements IFractalProducer{
		private static final double convergenceTreshold = 1E-3;
		private ComplexRootedPolynomial polynomial;
		
		/**
		 * Constructor of MyProducers
		 * @param polynomial parameter is given from main program which 
		 * represents roots given from user
		 */
		public MyProducer(ComplexRootedPolynomial polynomial) {
			this.polynomial=polynomial;
		}
		
		
		
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo,
				IFractalResultObserver observer, AtomicBoolean cancel) {
			long maxIter= 16*16;
			double rootTreshold=0.002;
			Complex znold;
			int index;
			int offset = 0;
			int xMin=0,yMin=0,xMax=width,yMax=height;
			short[] data = new short[width * height];
			for(int y = 0; y < height; y++) {  
				if(cancel.get()) break;
				for(int x = 0; x < width; x++) {
			    Complex c = map_to_complex_plain(x, y, xMin, xMax, yMin, yMax, reMin, reMax, imMin,imMax);  
			    Complex zn = c;     
				int iter = 0;     
				do {     
					Complex numerator = polynomial.apply(zn);
					Complex denominator = polynomial.toComplexPolynom().derive().apply(zn);
					znold = zn;
					Complex fraction = numerator.divide(denominator); 
					zn = zn.sub(fraction);          
					iter++;
				} while(zn.sub(znold).module()>convergenceTreshold && iter<maxIter);     
				index = polynomial.indexOfClosestRootFor(zn, rootTreshold);
				data[offset++]=(short) (index+1);
				}
		   }
		observer.acceptResult(data, (short)(polynomial.toComplexPolynom().order()+1), requestNo);
			
		}
		
		/**
		 * Method use given values and reconstructs new Complex number
		 * @param x representation of x coordinate which is converted into real part of complex number
		 * @param y representation of x coordinate which is converted into real imaginary of complex number
		 * @param xMin first coordinate of x axis for calculation complex number
		 * @param xMax last coordinate of x axis for calculation complex number
		 * @param yMin first coordinate of y axis for calculation complex number
		 * @param yMax last coordinate of y axis for calculation complex number
		 * @param reMin minimal number that can be used for real part of complex number
		 * @param reMax maximal number that can be used for real part of complex number
		 * @param imMin minimal number that can be used for imaginary part of complex number
		 * @param imMax maximal number that can be used for imaginary part of complex number
		 * @return
		 */
		private Complex  map_to_complex_plain(int x,int y,int xMin,int xMax,int yMin,int yMax,double reMin,double reMax,double imMin, double imMax) {
			double cre = x / (xMax-1.0) * (reMax - reMin) + reMin;
			double cim = (yMax-1.0-y) / (yMax-1.0) * (imMax - imMin) + imMin;
			return new Complex(cre,cim);
		}
		
	}
	
}
