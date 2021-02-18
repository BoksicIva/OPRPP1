package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationProvider extends AbstractLocalizationProvider {
	private String language;
	private ResourceBundle bundle;
	private static LocalizationProvider provider=new LocalizationProvider();
	
	private LocalizationProvider() {
		language="en";
		Locale locale = Locale.forLanguageTag(language); 
		bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.prijevodi", locale);
		fire();
		
	}
	
	
	public static LocalizationProvider getInstance() {
		return provider;
	}
	
	public void setLanguage(String l) {
		this.language=l;
		Locale locale = Locale.forLanguageTag(language); 
		bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.prijevodi", locale);
		fire();
	}
	
	
	
	
	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}


	@Override
	public String getCurrentLanguage() {
		return language;
	}
	
	
	

}
