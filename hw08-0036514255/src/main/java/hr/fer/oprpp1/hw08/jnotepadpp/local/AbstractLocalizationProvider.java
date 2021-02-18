package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLocalizationProvider implements ILocalizationProvider{
	private List<ILocalizationListener> listeners;
	
	public AbstractLocalizationProvider() {
		listeners=new ArrayList<>();
	}


	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		listeners=new ArrayList<>(listeners);
		listeners.add(l);
		
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners=new ArrayList<>(listeners);
		listeners.remove(l);
	}

	/**
	 * Method notify each listener that same change happened
	 */
	void fire() {
		for(ILocalizationListener l : listeners) {
			l.localizationChanged();
		}
	}
}
