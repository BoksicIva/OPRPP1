package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexRootedPolynomial;
/**
 * Class draws Mandelbrot fractals using threads for given roots of complex number 
 * @author Iva
 *
 */
public class NewtonParallel {
	/**
	 * The program must ask user to enter roots, 
	 * and then it must start fractal viewer and display the fractal
	 * @param args are used for setting number of workers and number of tracks
	 * 
	 */
	public static void main(String[] args) {
		
		String input;
		String errorMess="Entered values are not suported. Use \\\"--tracks=N\\\" \\\\ \\\"--numOfnumOfWorkers=N\\\" or shorter  \\\"-t K\\\" \\\\ \\\"-w N\\\" form for setting parameters.";
		boolean enterednumOfnumOfWorkers=false;
		boolean enteredTracks=false;
		int numOfnumOfWorkers=Runtime.getRuntime().availableProcessors(); 
		int tracks=4*numOfnumOfWorkers;
		ComplexRootedPolynomial polynomial = null;
		List<Complex> lines=new ArrayList<>();
		Complex complex;
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer. Please enter at least two roots, one root per line. Enter 'done' when done.");
		Scanner scanner = new Scanner(System.in);
		int index=1;
		
		for(int i=0;i<args.length;i++) {
			if(args[i].length()>2) {
				if(args[i].startsWith("--workers=")&& enterednumOfnumOfWorkers==false) {
					enterednumOfnumOfWorkers=true;
					numOfnumOfWorkers=Integer.parseInt(args[i].substring("--workers=".length(),args[i].length()));
				}else if(args[i].startsWith("--tracks=") && enteredTracks==false) {
					enteredTracks=true;
					tracks=Integer.parseInt(args[i].substring("--tracks=".length(),args[i].length()));
				}else {
					throw new IllegalArgumentException(errorMess);
				}
			}else if(args[i].length()==2) {
				if(args[i].startsWith("-w")&& enterednumOfnumOfWorkers==false) {
					enterednumOfnumOfWorkers=true;
					i++;
					numOfnumOfWorkers=Integer.parseInt(args[i]);
				}else if(args[i].startsWith("-k")&& enterednumOfnumOfWorkers==false) {
					enteredTracks=true;
					i++;
					numOfnumOfWorkers=Integer.parseInt(args[i]);
				}
				else {
					throw new IllegalArgumentException(errorMess+" Parametars should only be set once." );
				}
			}else {
				throw new IllegalArgumentException(errorMess );
			}
		}
		
		if(tracks<1 || numOfnumOfWorkers <1) {
			throw new IllegalArgumentException("Minimal acceptable value for numOfnumOfWorkers or tracks is 1");
		}
		
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
		 
		 
		 FractalViewer.show(new MyProducer(polynomial,numOfnumOfWorkers,tracks));
	}
	
	
	/**
	 * Class is implementation of work each thread do after it is started
	 * @author Iva
	 *
	 */
	public static class WorkCalculate implements Runnable {
		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		int m;
		short[] data;
		AtomicBoolean cancel;
		int numOfnumOfWorkers;
		int tracks;
		private ComplexRootedPolynomial polynomial;
		public static WorkCalculate NO_JOB = new WorkCalculate();
		private static final double convergenceTreshold = 1E-3;
		
		private WorkCalculate() {
		}
		
		public WorkCalculate(double reMin, double reMax, double imMin,
				double imMax, int width, int height, int yMin, int yMax, 
				int m, short[] data, AtomicBoolean cancel,int numOfnumOfWorkers,int tracks, ComplexRootedPolynomial polynomial) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.cancel = cancel;
			this.numOfnumOfWorkers=numOfnumOfWorkers;
			this.tracks=tracks;
			this.polynomial=polynomial;
		}
		
		@Override
		public void run() {
			
			calculate(reMin, reMax, imMin, imMax, width, height, m, yMin, yMax, data, cancel);
			
		}
		/**
		 * Method fills data array with indexes of closest roots which is later used for coloring
		 * @param reMin minimal number that can be used for real part of complex number
		 * @param reMax maximal number that can be used for real part of complex number
		 * @param imMin minimal number that can be used for imaginary part of complex number
		 * @param imMax maximal number that can be used for imaginary part of complex number
		 * @param width of coordinate system
		 * @param height of coordinate system
		 * @param m number of iterations for calculating if number divergence
		 * @param yMin first coordinate of y axis for calculation complex number
		 * @param yMax last coordinate of y axis for calculation complex number
		 * @param data array of short filled with indexes of closes roots
		 * @param cancel used for stop work of thread
		 */
		private void calculate(double reMin,double reMax,double imMin,double imMax,int width,int height,int m,int yMin,int yMax,short[] data,AtomicBoolean cancel) {
			long maxIter= 16*16*16;
			double rootTreshold=0.002;
			Complex znold;
			int index;
			int xMin=0,xMax=width;
			int offset = yMin * xMax;
			for(int y = yMin; y <= yMax; y++) {  
				if(cancel.get()) break;
				for(int x = 0; x < width; x++) {
			    Complex c = map_to_complex_plain(x, y, xMin, xMax, height, reMin, reMax, imMin,imMax);  
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
		private Complex  map_to_complex_plain(int x,int y,int xMin,int xMax, int height, double reMin,double reMax,double imMin, double imMax) {
			double cre = x / (xMax-1.0) * (reMax - reMin) + reMin;
			double cim = (height-1.0-y) / (height-1.0) * (imMax - imMin) + imMin;
			return new Complex(cre,cim);
		}
	}
	
	
	public static class MyProducer implements IFractalProducer {
		private ComplexRootedPolynomial polynomial;
		private int numOfnumOfWorkers;
		private int tracks;
		
		public MyProducer(ComplexRootedPolynomial polynomial,int numOfnumOfWorkers,int tracks) {
			this.polynomial=polynomial;
			this.numOfnumOfWorkers=numOfnumOfWorkers;
			this.tracks=tracks;
		}
		
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Calculation started...");
			System.out.println("Number of workers : " +numOfnumOfWorkers+". Number of tracks : "+tracks);
			int m = 16*16;
			short[] data = new short[width * height];
			int numberPerTrack = height / tracks;
			
			final BlockingQueue<WorkCalculate> queue = new LinkedBlockingQueue<>();

			Thread[] workers = new Thread[numOfnumOfWorkers];
			for(int i = 0; i < workers.length; i++) {
				workers[i] = new Thread(new Runnable() {
					@Override
					public void run() {
						while(true) {
							WorkCalculate p = null;
							try {
								p = queue.take();
								if(p==WorkCalculate.NO_JOB) break;
							} catch (InterruptedException e) {
								continue;
							}
							p.run();
						}
					}
				});
			}
			for(int i = 0; i < workers.length; i++) {
				workers[i].start();
			}
			
			for(int i = 0; i < tracks; i++) {
				int yMin = i*numberPerTrack;
				int yMax = (i+1)*numberPerTrack-1;
				if(i==tracks-1) {
					yMax = height-1;
				}
				WorkCalculate work = new WorkCalculate(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data, cancel,numOfnumOfWorkers,tracks,polynomial);
				while(true) {
					try {
						queue.put(work);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			for(int i = 0; i < workers.length; i++) {
				while(true) {
					try {
						queue.put(WorkCalculate.NO_JOB);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			for(int i = 0; i < workers.length; i++) {
				while(true) {
					try {
						workers[i].join();
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			
			observer.acceptResult(data, (short)(polynomial.toComplexPolynom().order()+1), requestNo);
		}
	}

}
