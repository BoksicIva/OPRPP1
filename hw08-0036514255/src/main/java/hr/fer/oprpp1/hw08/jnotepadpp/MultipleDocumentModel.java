package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;

/**
 *  Interface represents a model capable of holding zero, one or more documents,
 *  where each document and having a concept of current document 
 *  – the one which is shown to the user and on which user works
 *  MultipleDocumentModel can hold a single instance for each different path, 
 *  but can have multiple “unnamed” documents
 *  (documents with no path associated)
 * @author Iva
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel>  {
	
	/**
	 * Method creates new document and adds it to collection of opened documents
	 * @return new created document
	 */
	SingleDocumentModel createNewDocument();
	
	/**
	 * Method returns document on which is user currently working at the moment
	 * @return currentDocument variable
	 */
	SingleDocumentModel getCurrentDocument(); 
	
	/**
	 * Method loads document from given path, creates SingleDocumentModel for it,
	 *  adds tab and switches to it. If there already is SingleDocumentModel for specified document, does not create new one, 
	 *  but switches view to this document.
	 * @param path -> path must not be null
	 * @return loaded document
	 * @throws IllegalArgumentException if path is null
	 */
	SingleDocumentModel loadDocument(Path path);
	
	
	/**
	 * If saveDocument is called with newPath of some existing SingleDocumentModel,
	 * method must fail and tell the user that the specified file is already opened.
	 * newPath can be null; if null, document should be saved using path associated from document,
	 * otherwise, new path should be used and after saving is completed, 
	 * document’s path should be updated to newPath
	 * 
	 * @param model
	 * @param newPath
	 */
	void saveDocument(SingleDocumentModel model, Path newPath); 
	
	/**
	 * Method removes specified document (does not check modification status or ask any questions)
	 * @param model which needs to be closed and removed from collection of opened documents
	 */
	void closeDocument(SingleDocumentModel model);
	
	
	/**
	 * Method adds MultipleDocumentListener l from list of MultipleDocumentListeners
	 * @param l MultipleDocumentListener that needs to be added to list of MultipleDocumentListeners
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * Method removes MultipleDocumentListener l from list of MultipleDocumentListeners
	 * @param l MultipleDocumentListener that needs to be removed from list of MultipleDocumentListeners
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l); 
	
	/**
	 * Method returns number of documents on which user works and which are shown to the user
	 * @return int number of documents
	 */
	int getNumberOfDocuments();
	
	/**
	 * Method gets document on which is user working at given index
	 * Method checks if index is valid.
	 * @param index of wanted document
	 * @return document as SingleDocumentModel on given index
	 * @throws IllegalArgumentException if index is not valid
	 */
	SingleDocumentModel getDocument(int index);

	

}
