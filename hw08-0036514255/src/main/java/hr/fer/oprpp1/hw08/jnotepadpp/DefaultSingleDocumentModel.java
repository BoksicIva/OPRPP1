package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
/**
 * Class is implementation of SingleDocumentModel interface 
 * @author Iva
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {
	private Path filePath;
	private boolean modified;
	private JTextArea textComponent;
	private List<SingleDocumentListener> singleDocumentListeners;

	public DefaultSingleDocumentModel(Path filePath, String content) {
		this.singleDocumentListeners=new ArrayList<>();
		this.textComponent=new JTextArea(content);
		this.filePath=filePath;
		textComponent.getDocument().addDocumentListener(new MyDocumentListener());
		
	}
	
	class MyDocumentListener implements DocumentListener {

		@Override
		public void insertUpdate(DocumentEvent e) {
			setModified(true);
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			setModified(true);
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			setModified(true);
		}
		
	}

	@Override
	public JTextArea getTextComponent() {
		return textComponent;
	}

	@Override
	public Path getFilePath() {
		return filePath;
	}

	@Override
	public void setFilePath(Path path) {
		if(path.equals(null)) throw new IllegalArgumentException("Path of file cannot be null");
		this.filePath=path;
		pathUpdated();
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified=modified;
		statusUpdated();
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		singleDocumentListeners=new ArrayList<>(singleDocumentListeners);
		singleDocumentListeners.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		singleDocumentListeners=new ArrayList<>(singleDocumentListeners);
		singleDocumentListeners.remove(l);
	}
	
	private void statusUpdated() {
		for(SingleDocumentListener listener : singleDocumentListeners) {
			listener.documentModifyStatusUpdated(this);
		}
	}
	
	private void pathUpdated() {
		for(SingleDocumentListener listener : singleDocumentListeners) {
			listener.documentFilePathUpdated(this);
		}
	}
		

}
