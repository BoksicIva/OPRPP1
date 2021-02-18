package hr.fer.oprpp1.hw08.jnotepadpp;

public interface MultipleDocumentListener {
	
	/**
	 * Method is called when current document is changed
	 * previousModel or currentModel can be null but not both
	 * @param previousModel
	 * @param currentModel
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, 	SingleDocumentModel currentModel); 
	
	
	/**
	 *  Method is called when document is added
	 * @param model
	 */
	void documentAdded(SingleDocumentModel model); 
	
	/**
	 *  Method is called when document is removed
	 * @param model
	 */
	void documentRemoved(SingleDocumentModel model);


}
