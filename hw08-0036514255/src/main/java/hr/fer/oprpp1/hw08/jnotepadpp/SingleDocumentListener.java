package hr.fer.oprpp1.hw08.jnotepadpp;

public interface SingleDocumentListener {
	
	/**
	 * Method is alert when status of SingleDocumentModel is modified
	 * @param model
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model); 
	
	/**
	 * Method is alert when path of SingleDocumentModel is modified
	 * @param model
	 */
	void documentFilePathUpdated(SingleDocumentModel model);

}
