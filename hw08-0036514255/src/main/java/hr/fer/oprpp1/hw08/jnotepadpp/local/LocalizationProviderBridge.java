package hr.fer.oprpp1.hw08.jnotepadpp.local;

public class LocalizationProviderBridge extends AbstractLocalizationProvider{
	private ILocalizationListener listener;
	private ILocalizationProvider parent;
	private boolean connected;
	
	public LocalizationProviderBridge(ILocalizationProvider parent) {
		this.parent=parent;
		this.listener=new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				LocalizationProviderBridge.this.fire();
			}
		}; 
		parent.addLocalizationListener(listener);
	}
	
	/**
	 * Method checks if someone is connected if it is  connected removes  listener to parent
	 */
	public void disconnect() {
		if(connected) {
			connected=false;
			parent.removeLocalizationListener(listener);
		}
	}
	
	/**
	 * Method checks if someone is already connected if not connects listener to parent
	 */
	public void connect() {
		if(!connected) {
			connected=true;
			parent.addLocalizationListener(listener);
		}
	}
	
	
	@Override
	public String getString(String key) {
		return parent.getString(key);
	}

	@Override
	public String getCurrentLanguage() {
		return parent.getCurrentLanguage();
	}
	
	
	
	

}
