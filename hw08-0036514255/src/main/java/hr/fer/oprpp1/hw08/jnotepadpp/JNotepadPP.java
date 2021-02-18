package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;




public class JNotepadPP extends JFrame  {
	private static final long serialVersionUID = 1L;
	private DefaultMultipleDocumentModel tabbedPane;
	private JTextArea editor;
	private Path openedFilePath;
	private String title=" - Notepad++";
	private JToolBar statusBar;
	private FormLocalizationProvider flp=new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	JLabel dateTimeLabel;
	
	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(0, 0);
		initGUI();
		setMinimumSize(new Dimension(600,600));
		pack();
	}
	
	private void initGUI() {
		this.getContentPane().setLayout(new BorderLayout());
		
		tabbedPane=new DefaultMultipleDocumentModel();
		tabbedPane.createNewDocument();
		editor=tabbedPane.getCurrentDocument().getTextComponent();
		titleOfFrame();
		
		this.getContentPane().add(new JPanel().add(tabbedPane), BorderLayout.CENTER);
		
		createActions();
		createMenus();
		createToolbars();
		createStatusBar();
		dateAndTime();
		WindowListener wl=windowAdapter();
		this.addWindowListener(wl);
		editor.addCaretListener(new CaretListener() {
			
			@Override
			public void caretUpdate(CaretEvent e) {
			editStatusBar();
			disableButtons(e);
			
			}
			});
		
		createListeners();
		
	}
	private void dateAndTime() {
		new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String formattedDate = LocalDateTime.now().format(dateTimeFormatter);
				dateTimeLabel.setText(formattedDate);
			}
		}).start();
	}
	
	private void disableButtons(CaretEvent e) {
		int duljinaSelekcije=editor.getSelectionEnd()-editor.getSelectionStart();
		deleteSelectedPartAction.setEnabled(duljinaSelekcije!=0);
		toggleCaseAction.setEnabled(duljinaSelekcije!=0);
		uppercaseAction.setEnabled(duljinaSelekcije!=0);
		lowercaseAction.setEnabled(duljinaSelekcije!=0);
		ascendingAction.setEnabled(duljinaSelekcije!=0);
		descendingAction.setEnabled(duljinaSelekcije!=0);
		uniqueAction.setEnabled(duljinaSelekcije!=0);
	}
	
	private void createListeners() {
		tabbedPane.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				editStatusBar();
				titleOfFrame();
				
			}
		});
		
		
		
		tabbedPane.addMultipleDocumentListener(new MultipleDocumentListener() {
			
			@Override
			public void documentRemoved(SingleDocumentModel model) {
				
			}
			
			@Override
			public void documentAdded(SingleDocumentModel model) {
				titleOfFrame();
				
				model.getTextComponent().addCaretListener(new CaretListener() {
									
					@Override
					public void caretUpdate(CaretEvent e) {
						editStatusBar();
						disableButtons(e);
						
					}
				});
		
			}
			
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				titleOfFrame();
				editStatusBar();
				currentModel.getTextComponent().addCaretListener(new CaretListener() {
					
					@Override
					public void caretUpdate(CaretEvent e) {
						editStatusBar();
						
					}
				});
				
				
				
			}
		});
		
	}
	
	private void titleOfFrame() {
		String title;
		if(tabbedPane.getCurrentDocument().getFilePath()!=null)
			title=tabbedPane.getCurrentDocument().getFilePath().toString();
		else
			title="(unnamed)";
		setTitle(title+this.title);
		
	}
	
	private WindowListener windowAdapter() {
		WindowListener wl=new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeAll();
			}
			
		};
		return wl;
	}
	
    private void closeAll() {
    	System.out.println("Netko me zatvara!!"); 
		

		if(!hasModified()) {
			dispose();
			return;
		}
	
		int i=0;
		for(SingleDocumentModel document : tabbedPane) {
			if(document.isModified()) {
				closeDocument(document);
			}
			tabbedPane.setSelectedIndex(i);
			i++;
		}
		dispose();
		return;
    	
    }
    
    private void closeDocument(SingleDocumentModel model) {
    	String[] opcije=new String[]{"Yes","No","Cancel"};
    	int rezultat=JOptionPane.showOptionDialog(JNotepadPP.this, "Modified files exist. Do you whant to save" ," Upozorenje!",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null, opcije,opcije[0]);
		switch(rezultat) {
		case JOptionPane.CLOSED_OPTION:
			return;
		case 0:
			System.out.println("Snimam.");
			saveDocument(model);
			break;
		case 1:
			// preskoci spremanje ovog dokumenta
			break;
		case 2:
			return;
		}
    }
	
	private boolean hasModified() {
		for(SingleDocumentModel document : tabbedPane) {
			if(document.isModified())
				return true;
		}
		return false;
	}
	
	private Action createNewDocumentAction = new LocalizableAction("createNewDocument", flp) {
			
			private static final long serialVersionUID = 1L;
	
			@Override
			public void actionPerformed(ActionEvent e) {
				tabbedPane.createNewDocument();
			}
		};
		
		
	private Action openDocumentAction = new LocalizableAction("openDocument", flp){
			
			private static final long serialVersionUID = 1L;
	
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Open file");
				if(fc.showOpenDialog(JNotepadPP.this)!=JFileChooser.APPROVE_OPTION) {
					return;
				}
				File fileName = fc.getSelectedFile();
				Path filePath = fileName.toPath();
				if(!Files.isReadable(filePath)) {
					JOptionPane.showMessageDialog(
							JNotepadPP.this, 
							"Datoteka "+fileName.getAbsolutePath()+" ne postoji!", 
							"Pogreška", 
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				tabbedPane.loadDocument(filePath);
			}
		};
	
	private Action saveDocumentAction = new LocalizableAction("saveDocument", flp) {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			saveDocument(tabbedPane.getCurrentDocument());
		}
	};
	
	private void saveDocument(SingleDocumentModel model) {
		if(model.getFilePath()==null) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Save document");
			if(jfc.showSaveDialog(JNotepadPP.this)!=JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(
						JNotepadPP.this, 
						"Ništa nije snimljeno.", 
						"Upozorenje", 
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			openedFilePath = jfc.getSelectedFile().toPath();
			tabbedPane.saveDocument(model,openedFilePath);
		}else {
			try {
			tabbedPane.saveDocument(model,model.getFilePath());
			JOptionPane.showMessageDialog(
					JNotepadPP.this, 
					"Datoteka je snimljena.", 
					"Informacija", 
					JOptionPane.INFORMATION_MESSAGE);
			}catch(Exception ex) {
				JOptionPane.showMessageDialog(
						JNotepadPP.this, 
						"Ništa nije snimljeno.", 
						"Upozorenje", 
						JOptionPane.WARNING_MESSAGE);
			}
			JOptionPane.showMessageDialog(
					JNotepadPP.this, 
					"Datoteka je snimljena.", 
					"Informacija", 
					JOptionPane.INFORMATION_MESSAGE);
			
		}
	}
	
	private Action saveAsDocumentAction = new LocalizableAction("saveAsDocument", flp) {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Save document as ");
			if(jfc.showSaveDialog(JNotepadPP.this)!=JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(
						JNotepadPP.this, 
						"Ništa nije snimljeno.", 
						"Upozorenje", 
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			openedFilePath = jfc.getSelectedFile().toPath();
			
			try {
				tabbedPane.saveDocument(tabbedPane.getCurrentDocument(), openedFilePath);
			} catch (IllegalArgumentException e1) {
				JOptionPane.showMessageDialog(
						JNotepadPP.this, 
						"Pogreška prilikom zapisivanja datoteke. Datoteka s zadanim putom je već otvorena u uređivaču. "+openedFilePath.toFile().getAbsolutePath()+".\nPažnja: nije jasno u kojem je stanju datoteka na disku!", 
						"Pogreška", 
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			JOptionPane.showMessageDialog(
					JNotepadPP.this, 
					"Datoteka je snimljena.", 
					"Informacija", 
					JOptionPane.INFORMATION_MESSAGE);
		}
	};
	
private Action closeDocumentAction = new LocalizableAction("closeDocument", flp){
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(tabbedPane.getCurrentDocument().getFilePath()==null && tabbedPane.getCurrentDocument().isModified()) {
				closeDocument(tabbedPane.getCurrentDocument());
			try {
				tabbedPane.closeDocument(tabbedPane.getCurrentDocument());
				
			} catch (IllegalArgumentException e1) {
				JOptionPane.showMessageDialog(
						JNotepadPP.this, 
						"Pogreška prilikom zapisivanja datoteke. Datoteka s zadanim putom je već otvorena u uređivaču. "+openedFilePath.toFile().getAbsolutePath()+".\nPažnja: nije jasno u kojem je stanju datoteka na disku!", 
						"Pogreška", 
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
		}else {
			try {
				if(tabbedPane.getCurrentDocument().isModified())
					tabbedPane.saveDocument(tabbedPane.getCurrentDocument(), tabbedPane.getCurrentDocument().getFilePath());
				tabbedPane.closeDocument(tabbedPane.getCurrentDocument());
				
			}catch(NullPointerException npe) {
				JOptionPane.showMessageDialog(
						JNotepadPP.this, 
						"Pogreška prilikom zapisivanja datoteke. Datoteka nema zadan put. "+tabbedPane.getCurrentDocument().getFilePath().toFile().getAbsolutePath()+".\nPažnja: nije jasno u kojem je stanju datoteka na disku!", 
						"Pogreška", 
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		
		}
	};
	
	private Action cutSelectedPartAction = new LocalizableAction("cutSelected", flp) {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				editor.cut();
			}
		};
		

	private Action copySelectedPartAction = new LocalizableAction("copySelected", flp) {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				editor.copy();
			
			}
		};
		
		
	private Action pasteSelectedPartAction = new LocalizableAction("pasteSelected", flp) {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				editor.paste();
			}
		};
	
	private Action deleteSelectedPartAction = new LocalizableAction("deleteSelected", flp) {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = editor.getDocument();
			int len = Math.abs(editor.getCaret().getDot()-editor.getCaret().getMark());
			if(len==0) return;
			int offset = Math.min(editor.getCaret().getDot(),editor.getCaret().getMark());
			try {
				doc.remove(offset, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
	};
	
	private Action uppercaseAction = new LocalizableAction("uppercase", flp) {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			caseAction("upper");
		}

	};
	
    private Action lowercaseAction = new LocalizableAction("lowercase", flp) {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			caseAction("lower");
		}

	};
	
	private Action toggleCaseAction = new LocalizableAction("toggleCase", flp) {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = editor.getDocument();
			int len = Math.abs(editor.getCaret().getDot()-editor.getCaret().getMark());
			int	offset = Math.min(editor.getCaret().getDot(),editor.getCaret().getMark());
			try {
				String text = doc.getText(offset, len);
				text = changeCase(text);
				doc.remove(offset, len);
				doc.insertString(offset, text, null);
			} catch(BadLocationException ex) {
				ex.printStackTrace();
			}
		}

		private String changeCase(String text) {
			char[] znakovi = text.toCharArray();
			for(int i = 0; i < znakovi.length; i++) {
				char c = znakovi[i];
				if(Character.isLowerCase(c)) {
					znakovi[i] = Character.toUpperCase(c);
				} else if(Character.isUpperCase(c)) {
					znakovi[i] = Character.toLowerCase(c);
				}
			}
			return new String(znakovi);
		}
	};
	
	private void caseAction(String action) {
		Document doc = editor.getDocument();
		int len = Math.abs(editor.getCaret().getDot()-editor.getCaret().getMark());
		int offset = Math.min(editor.getCaret().getDot(),editor.getCaret().getMark());
		try {
			String text = doc.getText(offset, len);
			switch(action) {
			case "upper":  text = text.toUpperCase();
						   break;
			case "lower":  text = text.toLowerCase();
						   break;
			}
			doc.remove(offset, len);
			doc.insertString(offset, text, null);
		} catch(BadLocationException ex) {
			ex.printStackTrace();
		}
	}
	
	private Action ascendingAction =  new LocalizableAction("ascending", flp) {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = editor.getDocument();
			javax.swing.text.Element root=doc.getDefaultRootElement();
			List<String> lines=new ArrayList<>();
			Locale locale = new Locale(LocalizationProvider.getInstance().getCurrentLanguage());
			Collator collator = Collator.getInstance(locale);
			
            int dot  =editor.getCaret().getDot();
			int mark =editor.getCaret().getMark();
			
			
			int start=(dot>=mark)? mark :dot;
			int end=(dot>=mark)? dot :mark;
			
			int firstRow = 0,lastRow = 0;
			firstRow =  root.getElementIndex(start);
			lastRow=root.getElementIndex(end);
			
			List<String> allLines=editor.getText().lines().collect(Collectors.toList());
			for(int i=firstRow;i<=lastRow;i++) {
				lines.add(allLines.get(i));
				System.out.println(allLines.get(i));
				System.out.println(firstRow+" "+lastRow);
				}
			Collections.sort(lines, collator);
			StringBuilder sb=new StringBuilder();
			for(int i=0;i<lines.size();i++) {
				sb.append(lines.get(i));
				if(i!=lines.size()-1)
					sb.append("\n");
				
			}
			editor.replaceSelection(sb.toString());
			
			

		}
	};
	
	private Action descendingAction =  new LocalizableAction("descending", flp) {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = editor.getDocument();
			javax.swing.text.Element root=doc.getDefaultRootElement();
			List<String> lines=new ArrayList<>();
			Locale locale = new Locale(LocalizationProvider.getInstance().getCurrentLanguage());
			Collator collator = Collator.getInstance(locale);
			
            int dot  =editor.getCaret().getDot();
			int mark =editor.getCaret().getMark();
			
			
			int start=(dot>=mark)? mark :dot;
			int end=(dot>=mark)? dot :mark;
			
			int firstRow = 0,lastRow = 0;
			firstRow =  root.getElementIndex(start);
			lastRow=root.getElementIndex(end);
			
			List<String> allLines=editor.getText().lines().collect(Collectors.toList());
			for(int i=firstRow;i<=lastRow;i++) {
				lines.add(allLines.get(i));
				System.out.println(allLines.get(i));
				System.out.println(firstRow+" "+lastRow);
				}
			Collections.sort(lines, collator.reversed());
			StringBuilder sb=new StringBuilder();
			for(int i=0;i<lines.size();i++) {
				sb.append(lines.get(i));
				if(i!=lines.size()-1)
					sb.append("\n");
				
			}
			editor.replaceSelection(sb.toString());
			
			
		}
	};
		
	private Action uniqueAction =  new LocalizableAction("unique", flp) {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Document doc = editor.getDocument();
				javax.swing.text.Element root=doc.getDefaultRootElement();
				List<String> lines=new ArrayList<>();
				
	            int dot  =editor.getCaret().getDot();
				int mark =editor.getCaret().getMark();
				
				
				int start=(dot>=mark)? mark :dot;
				int end=(dot>=mark)? dot :mark;
				
				int firstRow = 0,lastRow = 0;
				firstRow =  root.getElementIndex(start);
				lastRow=root.getElementIndex(end);
				
				List<String> allLines=editor.getText().lines().collect(Collectors.toList());
				for(int i=firstRow;i<=lastRow;i++) {
					lines.add(allLines.get(i));
					System.out.println(allLines.get(i));
					System.out.println(firstRow+" "+lastRow);
					}
				List<String> listWithoutDuplicates = new ArrayList<>(
					      new HashSet<>(lines));
				StringBuilder sb=new StringBuilder();
				for(int i=0;i<listWithoutDuplicates.size();i++) {
					sb.append(listWithoutDuplicates.get(i));
					if(i!=listWithoutDuplicates.size()-1)
						sb.append("\n");
					
				}
				editor.replaceSelection(sb.toString());
				
				
			}
		};
	
	
	
	private Action exitAction =  new LocalizableAction("exit", flp) {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			closeAll();
		}
	};
	
	
	private Action statisticalInfoAction =  new LocalizableAction("statisticalInfo", flp) {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			long x = 0,y = 0,z = 0;
			
			x=editor.getText().chars().count();
			y= editor.getText().chars().filter(letter-> !Character.isWhitespace(letter)).count();
			z= editor.getLineCount();
			
			JOptionPane.showMessageDialog(
					JNotepadPP.this, 
					"Your document has "+x+" characters, "+y+" non-blank characters and "+z+" lines.", 
					"Statistical info", 
					JOptionPane.INFORMATION_MESSAGE);
		}
	};
	
	private Action english = new AbstractAction() {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("en");
				
				
			}
		};
		
		private Action hrvatski = new AbstractAction() {
				
				private static final long serialVersionUID = 1L;
				
				@Override
				public void actionPerformed(ActionEvent e) {
					LocalizationProvider.getInstance().setLanguage("hr");
					
					
				}
			};
			
		private Action deutsch = new AbstractAction() {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("de");
				
				
			}
		};
	
	private void createActions() {
		createNewDocumentAction.putValue(
				Action.NAME, 
				"New");
		createNewDocumentAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control N")); 
		createNewDocumentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_N); 
		createNewDocumentAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Used to create new blank file."); 
		
		
		openDocumentAction.putValue(
				Action.NAME, 
				"Open");
		openDocumentAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control O")); 
		openDocumentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_O); 
		openDocumentAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Used to open existing file from disk."); 
		
		saveDocumentAction.putValue(
				Action.NAME, 
				"Save");
		saveDocumentAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control S")); 
		saveDocumentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_S); 
		saveDocumentAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Used to save current file to disk."); 
		
		saveAsDocumentAction.putValue(
				Action.NAME, 
				"Save as");
		saveAsDocumentAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control alt S")); 
		saveAsDocumentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_S); 
		saveAsDocumentAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Used to save current file to disk."); 
		
		closeDocumentAction.putValue(
				Action.NAME, 
				"Close");
		closeDocumentAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control W")); 
		closeDocumentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_W); 
		closeDocumentAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Used to close current file."); 
		
		
		cutSelectedPartAction.putValue(
				Action.NAME, 
				"Cut");
		cutSelectedPartAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control X")); 
		cutSelectedPartAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_X); 
		cutSelectedPartAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Used to cut the selected part of text."); 
		
		copySelectedPartAction.putValue(
				Action.NAME, 
				"Copy");
		copySelectedPartAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control C")); 
		copySelectedPartAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_C); 
		copySelectedPartAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Used to copy the selected part of text.");
		
		pasteSelectedPartAction.putValue(
				Action.NAME, 
				"Paste");
		pasteSelectedPartAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control P")); 
		pasteSelectedPartAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_P); 
		pasteSelectedPartAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Used to paste copied or cut text.");
		
		
		deleteSelectedPartAction.putValue(
				Action.NAME, 
				"Delete selected text");
		deleteSelectedPartAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("F2")); 
		deleteSelectedPartAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_D); 
		deleteSelectedPartAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Used to delete the selected part of text."); 
		deleteSelectedPartAction.setEnabled(false);
		
		uppercaseAction.putValue(
				Action.NAME, 
				"To uppercase");
		uppercaseAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control F3")); 
		uppercaseAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_U); 
		uppercaseAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Used to upper character case in selected part."); 
		uppercaseAction.setEnabled(false);
		
		lowercaseAction.putValue(
				Action.NAME, 
				"To lowercase");
		lowercaseAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control F4")); 
		lowercaseAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_L); 
		lowercaseAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Used to lower character case in selected part.");
		lowercaseAction.setEnabled(false);
		
		toggleCaseAction.putValue(
				Action.NAME, 
				"Invert case");
		toggleCaseAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control F5")); 
		toggleCaseAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_V); 
		toggleCaseAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Used to toggle character case in selected part."); 
		toggleCaseAction.setEnabled(false);
		
		ascendingAction.putValue(
				Action.NAME, 
				"Ascending");
		ascendingAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control A")); 
		ascendingAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_A); 
		ascendingAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Used to sort ascending order selected lines.");
		ascendingAction.setEnabled(false);
		
		descendingAction.putValue(
				Action.NAME, 
				"Descending");
		descendingAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control D")); 
		descendingAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_D); 
		descendingAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Used to sort descending order selected lines.");
		descendingAction.setEnabled(false);
		
		uniqueAction.putValue(
				Action.NAME, 
				"Unique");
		uniqueAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control U")); 
		uniqueAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_U); 
		uniqueAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Used to delete duplicate lines that are selected.");
		uniqueAction.setEnabled(false);
		
		
		exitAction.putValue(
				Action.NAME, 
				"Exit");
		exitAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control X"));
		exitAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_X); 
		exitAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Exit application."); 
		
		statisticalInfoAction.putValue(
				Action.NAME, 
				"Statistical info");
		statisticalInfoAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control I"));
		statisticalInfoAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_I); 
		statisticalInfoAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Statistical info.");
		
		english.putValue(
				Action.NAME, 
				"en");
		english.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control shift E")); 
		english.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_E); 
		english.putValue(
				Action.SHORT_DESCRIPTION, 
				"Sets language on english.");
		
		hrvatski.putValue(
				Action.NAME, 
				"hr");
		hrvatski.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control shift H")); 
		hrvatski.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_H); 
		hrvatski.putValue(
				Action.SHORT_DESCRIPTION, 
				"Sets language on english.");
		
		
		
		deutsch.putValue(
				Action.NAME, 
				"de");
		deutsch.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control shift D")); 
		deutsch.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_D); 
		deutsch.putValue(
				Action.SHORT_DESCRIPTION, 
				"Sets language on deutsch.");
	}
	
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu(new LocalizableAction("file", flp));
		menuBar.add(fileMenu);

		fileMenu.add(new JMenuItem(createNewDocumentAction));
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsDocumentAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(closeDocumentAction));
		fileMenu.add(new JMenuItem(exitAction));
		
		JMenu editMenu = new JMenu(new LocalizableAction("edit", flp));
		menuBar.add(editMenu);
		
		editMenu.add(new JMenuItem(cutSelectedPartAction));
		editMenu.add(new JMenuItem(copySelectedPartAction));
		editMenu.add(new JMenuItem(pasteSelectedPartAction));
		editMenu.addSeparator();
		
		editMenu.add(new JMenuItem(statisticalInfoAction));
		
		JMenu toolsMenu = new JMenu(new LocalizableAction("tools", flp));
		toolsMenu.add(new JMenuItem(uppercaseAction));
		toolsMenu.add(new JMenuItem(lowercaseAction));
		toolsMenu.add(new JMenuItem(toggleCaseAction));
		toolsMenu.addSeparator();
		toolsMenu.add(new JMenuItem(deleteSelectedPartAction));
		
		JMenu lang = new JMenu(new LocalizableAction("languages", flp));
		lang.add(new JMenuItem(english));
		lang.add(new JMenuItem(hrvatski));
		lang.add(new JMenuItem(deutsch));
		
		menuBar.add(lang);
		
		toolsMenu.addSeparator();
		JMenu sort=new JMenu(new LocalizableAction("sort", flp));
		sort.add(new JMenuItem(ascendingAction));
		sort.add(new JMenuItem(descendingAction));
		toolsMenu.add(sort);
		
		toolsMenu.add(new JMenuItem(uniqueAction));
		
		menuBar.add(toolsMenu);
		
		
		
		this.setJMenuBar(menuBar);
	}

	private void createToolbars() {
		JToolBar toolBar = new JToolBar("Alati");
		toolBar.setFloatable(true);
		
		toolBar.add(new JButton(createNewDocumentAction));
		toolBar.add(new JButton(openDocumentAction));
		toolBar.add(new JButton(saveDocumentAction));
		toolBar.add(new JButton(saveAsDocumentAction));
		toolBar.add(new JButton(closeDocumentAction));
		toolBar.add(new JButton(exitAction));
		
		toolBar.addSeparator();
		toolBar.add(new JButton(cutSelectedPartAction));
		toolBar.add(new JButton(copySelectedPartAction));
		toolBar.add(new JButton(pasteSelectedPartAction));
		
		toolBar.addSeparator();
		toolBar.add(new JButton(deleteSelectedPartAction));
		
		toolBar.addSeparator();
		toolBar.add(new JButton(uppercaseAction));
		toolBar.add(new JButton(lowercaseAction));
		toolBar.add(new JButton(toggleCaseAction));
		
		toolBar.addSeparator();
		toolBar.add(new JButton(ascendingAction));
		toolBar.add(new JButton(descendingAction));
		
		toolBar.addSeparator();
		toolBar.add(new JButton(uniqueAction));
		
		toolBar.addSeparator();
		toolBar.add(new JButton(statisticalInfoAction));
		
		
		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}
	
	private void createStatusBar() {
		statusBar = new JToolBar("Info");
		statusBar.setFloatable(false);
		statusBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
		
		JPanel panelLeft=new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelLeft.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));
		JPanel panelRight=new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelRight.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));
		JPanel panelRightEnd=new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		panelLeft.add(new JLabel(" length : "+editor.getText().chars().count()));
		statusBar.add(panelLeft);
		
		try {
			panelRight.add(new JLabel("Ln : "+(editor.getLineOfOffset(editor.getCaretPosition())+1)));
			panelRight.add(new JLabel(" Col : "+(editor.getCaretPosition()-editor.getLineStartOffset(editor.getCaretPosition())+1)));
		} catch (BadLocationException e) {
			
			e.printStackTrace();
		}
		panelRight.add(new JLabel(" Sel : "+(editor.getSelectionEnd()-editor.getSelectionStart())));
		statusBar.add(panelRight);
		
		dateTimeLabel= new JLabel();
		String formattedDate = LocalDateTime.now().format(dateTimeFormatter);
		dateTimeLabel.setText(formattedDate);
		panelRightEnd.add(dateTimeLabel);
		
		statusBar.add(panelRightEnd);
		
		this.getContentPane().add(statusBar, BorderLayout.PAGE_END);
	}
	
	private void editStatusBar() {
		editor=tabbedPane.getCurrentDocument().getTextComponent();
		JPanel panelLeft=(JPanel) statusBar.getComponent(0);
		JPanel panelRigth=(JPanel) statusBar.getComponent(1);
		
		JLabel len=(JLabel) panelLeft.getComponent(0);
		len.setText(" length : "+editor.getText().chars().count());
		
		JLabel ln=(JLabel) panelRigth.getComponent(0);
		JLabel col=(JLabel) panelRigth.getComponent(1);
		JLabel sel=(JLabel) panelRigth.getComponent(2);
		
		
		try {
			ln.setText("Ln : "+(editor.getLineOfOffset(editor.getCaretPosition())+1));
			col.setText(" Col : "+(editor.getCaretPosition()-editor.getLineStartOffset(editor.getLineOfOffset(editor.getCaretPosition()))+1));
		} catch (BadLocationException e) {
			
			e.printStackTrace();
		}
		
		sel.setText(" Sel : "+(editor.getSelectionEnd()-editor.getSelectionStart()));
	}
	
	
	public static void main(String... args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new JNotepadPP().setVisible(true);
			}
		});
	}

}
