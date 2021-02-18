package hr.fer.oprpp1.hw08.jnotepadpp;
/**
 * represents a model of single document, 
 * having information about file path from which document was loaded (can be null for new document), 
 * document modification status and reference to Swing component which is used for editing
 * @author Iva
 *
 */

import java.nio.file.Path;
import javax.swing.JTextArea;

public interface SingleDocumentModel {
	
	/**
	 * Method gets reference to Swing component which is used for editing
	 * @return textComponent which is JTextArea type
	 */
	JTextArea getTextComponent();
	
	/**
	 * Method returns file path from which file was loaded
	 * @return file path
	 */
	Path getFilePath(); 
	
	/**
	 * Method sets file path from which file was loaded to variable path
	 * @param path variable which will be set to class variable, path can not be null
	 */
	void setFilePath(Path path);
	
	/**
	 * Method for getting modification status of file
	 * @return boolean flag modified
	 */
	boolean isModified();
	
	/**
	 * Method for setting boolean value to local variable modified
	 * @param modified boolean flag which will be set to class variable
	 */
	void setModified(boolean modified);
	
	
	/**
	 * Method adds SingleDocumentListener l to list of SingleDocumentListeners
	 * @param l SingleDocumentListener that needs to be added to list of SingleDocumentListeners
	 */
	void addSingleDocumentListener(SingleDocumentListener l); 
	
	
	/**
	 * Method removes SingleDocumentListener l from list of SingleDocumentListeners
	 * @param l SingleDocumentListener that needs to be removed from list of SingleDocumentListeners
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
	

}
