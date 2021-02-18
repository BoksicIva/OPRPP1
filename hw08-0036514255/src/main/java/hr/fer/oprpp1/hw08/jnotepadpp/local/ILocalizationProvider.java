package hr.fer.oprpp1.hw08.jnotepadpp.local;

public interface ILocalizationProvider {
	
	/**
	 * Method gets localization word for given key
	 * @param key for which is searched in localization translation dictionary
	 * @return
	 */
	String getString(String key);
	
	/**
	 * Listener is added to list of listeners
	 * @param l listener
	 */
	void addLocalizationListener(ILocalizationListener l);
	
	/**
	 * Listener is removed from list of listeners
	 * @param l
	 */
	void removeLocalizationListener(ILocalizationListener l);

	/**
	 * Method gets current language
	 * @return current language
	 */
	String getCurrentLanguage();
}
