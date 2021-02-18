package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {
	private static final long serialVersionUID = 1L;
	private List<SingleDocumentModel> documents;
	private List<MultipleDocumentListener> multipleDocumentListener;
	private SingleDocumentModel currentDocument;
	private ImageIcon notModifiedFileIcon=icon("fileBlue.png");
	private ImageIcon modifiedFileIcon=icon("fileRed.png");
	
	public DefaultMultipleDocumentModel() {
		this.documents=new ArrayList<>();
		this.multipleDocumentListener=new ArrayList<>();
		addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				currentDocument=documents.get(getSelectedIndex());
			}
		});
	}
	
	
	
	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel document=new DefaultSingleDocumentModel(null, "");
		SingleDocumentModel previousCurrent=currentDocument;
		currentDocument=document;
		documents.add(document);
		
		JTextArea content=document.getTextComponent() ;
		
		JScrollPane scrollPane=new JScrollPane(content);
		
		JPanel panel=new JPanel(new BorderLayout());
		panel.add(scrollPane,BorderLayout.CENTER);

		this.addTab("(unnamed)",notModifiedFileIcon, panel, "(unnamed)");
		
		document.addSingleDocumentListener(new DefaultSingleDocumentListener());
		
		setSelectedIndex(documents.size()-1);
		
		addedDocument(document);
		currentDocumentChanged(previousCurrent,document);
		
		return document;
	}
	
	@Override
	public SingleDocumentModel getCurrentDocument() {
		return documents.get((getSelectedIndex()));
	}
	
	
	@Override
	public SingleDocumentModel loadDocument(Path path) {
		boolean alreadyOpen=false;
		if( path== null) throw new NullPointerException("To load the document path can not be null.");
		for(SingleDocumentModel document : documents) {
			if(document.getFilePath()!=null && path!=null && document.getFilePath().equals(path)) {
				currentDocument=document;
				alreadyOpen=true;
				setSelectedIndex(documents.indexOf(document));
			}
		}
		
		if(!alreadyOpen) {
			
			String content = null;
			
			File f = new File(path.toString());
	        try {
	            byte[] bytes = Files.readAllBytes(f.toPath());
	           content=new String(bytes,"UTF-8");
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	       
			
			SingleDocumentModel loadDocument=new DefaultSingleDocumentModel(path, content);
			documents.add(loadDocument);
			currentDocument=loadDocument;
			
			JTextArea textComponent=loadDocument.getTextComponent();
			
			JScrollPane scrollPane=new JScrollPane(textComponent);
			
			JPanel panel=new JPanel(new BorderLayout());
			panel.add(scrollPane,BorderLayout.CENTER);
			
			this.addTab(loadDocument.getFilePath().getFileName().toString(),notModifiedFileIcon, panel,loadDocument.getFilePath().toString());
			setSelectedIndex(documents.size()-1);
			currentDocument.addSingleDocumentListener(new DefaultSingleDocumentListener());
			addedDocument(loadDocument);
		}
		
		return currentDocument;
	}
	
	
	
	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		for(SingleDocumentModel document : documents) {
			if(document.getFilePath()!=null && newPath!=null &&  !document.equals(currentDocument) && document.getFilePath().equals(newPath)) {
				throw new IllegalArgumentException("File with given path is opend in one of tabs.");
			}
		}
		
		if(newPath==null)
			newPath=model.getFilePath();
		
		byte[] bytes =model.getTextComponent().getText().getBytes();
		
		try {
			Files.write(newPath, bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(!newPath.equals(model.getFilePath()))
			model.setFilePath(newPath);
		model.setModified(false);
		
		
		
	}
	
	@Override
	public void closeDocument(SingleDocumentModel model) {
		if(model.equals(null)) throw new NullPointerException("Closing document can not be null");
		SingleDocumentModel previousModel;
		previousModel=currentDocument;
		if(getNumberOfDocuments()==1) {
			createNewDocument();
			currentDocument=documents.get(0);
			setSelectedIndex(0);
		}else {
			currentDocument=documents.get(documents.size()-2);
			setSelectedIndex(documents.size()-2);
		}
		currentDocumentChanged(previousModel, currentDocument);
		
		int index=documents.indexOf(model);
		documentRemoved(model);
		documents.remove(index);	
		removeTabAt(index);
	}
	
	
	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		multipleDocumentListener=new ArrayList<>(multipleDocumentListener);
		multipleDocumentListener.add(l);
	}
	
	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		multipleDocumentListener=new ArrayList<>(multipleDocumentListener);
		multipleDocumentListener.remove(l);
	}
	
	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}
	
	
	@Override
	public SingleDocumentModel getDocument(int index) {
		if(index > documents.size()-1 || index < 0) throw new IllegalArgumentException("Given index is out of range.");
		return documents.get(index);
	}
	
	
	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return documents.iterator();
	}
	
	private ImageIcon icon(String name) {
		InputStream is = this.getClass().getResourceAsStream("icons/"+name);
		if(is==null) {
			throw new IllegalArgumentException("Icon with given name does not exists in resorces");
		}
		byte[] bytes = null;
		try {
			bytes = is.readAllBytes();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new ImageIcon(new ImageIcon(bytes).getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
	}
	
	private void addedDocument(SingleDocumentModel model) {
		for(MultipleDocumentListener ml : multipleDocumentListener) {
			ml.documentAdded(model);
		}
	}
	
	private void currentDocumentChanged(SingleDocumentModel previousModel,SingleDocumentModel currentModel) {
		for(MultipleDocumentListener ml : multipleDocumentListener) {
			ml.currentDocumentChanged(previousModel, currentModel);
		}
	}
	
	private void documentRemoved(SingleDocumentModel model) {
		for(MultipleDocumentListener ml : multipleDocumentListener) {
			ml.documentRemoved(model);
		}
	}
	
	
	
	private class DefaultSingleDocumentListener implements SingleDocumentListener{

		
		@Override
		public void documentModifyStatusUpdated(SingleDocumentModel model) {
			int index=getSelectedIndex();
			if(model.isModified())
				setIconAt(index, modifiedFileIcon);
			else
				setIconAt(index, notModifiedFileIcon);
		}
		
		@Override
		public void documentFilePathUpdated(SingleDocumentModel model) {
			int index=getSelectedIndex();
			setToolTipTextAt(index, model.getFilePath().toString());
			setTitleAt(index, model.getFilePath().getFileName().toString());
		}
	
	}
}
