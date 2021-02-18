package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class LocalizableAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	private String key;
	private ILocalizationListener listener;
	
	public LocalizableAction(String key,ILocalizationProvider lp) {
		putValue(NAME, lp.getString(key));
		lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				LocalizableAction.this.putValue(NAME, lp.getString(key));
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

}
