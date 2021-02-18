package hr.fer.oprpp1.custom.collections;
 /**
  * Interface Processor with abstract method process
  * @author Iva
  *
  */
public interface Processor<P> {

	/**
	 * Definition of method process
	 * @param object which method uses depending on implementation
	 */
	public void process(P object);
}
