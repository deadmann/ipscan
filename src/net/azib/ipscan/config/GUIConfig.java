/**
 * This file is a part of Angry IP Scanner source code,
 * see http://www.azib.net/ for more information.
 * Licensed under GPLv2.
 */
package net.azib.ipscan.config;

import java.util.prefs.Preferences;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

/**
 * DimensionsConfig
 *
 * @author Anton Keks
 */
public class GUIConfig {

	private Preferences preferences;
	
	public boolean isFirstRun;
	public int activeFeeder;
	public DisplayMethod displayMethod;
	public boolean showScanStats;
	public boolean askScanConfirmation;
	
	public Rectangle mainWindowBounds;
	public boolean isMainWindowMaximized;
	
	public Point detailsWindowSize;
	
	/** this one is not saved, just a globally accessed parameter */
	public int standardButtonHeight = 22;
	
	public static enum DisplayMethod {ALL, ALIVE, PORTS}

	// package local constructor
	GUIConfig(Preferences preferences) {
		this.preferences = preferences;
		load();
	}
	
	private void load() {
		isFirstRun = preferences.getBoolean("firstRun", true);
		activeFeeder = preferences.getInt("activeFeeder", 0);
		displayMethod = DisplayMethod.valueOf(preferences.get("displayMethod", DisplayMethod.ALL.toString()));
		showScanStats = preferences.getBoolean("showScanStats", true);
		askScanConfirmation = preferences.getBoolean("askScanConfirmation", true);

		isMainWindowMaximized = preferences.getBoolean("windowMaximized", false);
		mainWindowBounds = new Rectangle(
			preferences.getInt("windowLeft", 100),
			preferences.getInt("windowTop", 100),
			preferences.getInt("windowWidth", 560),
			preferences.getInt("windowHeight", 350));
		
		detailsWindowSize = new Point(
			preferences.getInt("detailsWidth", 300),
			preferences.getInt("detailsHeight", 200));
	}

	public void store() {
		preferences.putBoolean("firstRun", isFirstRun);
		preferences.putInt("activeFeeder", activeFeeder);
		preferences.put("displayMethod", displayMethod.toString());
		preferences.putBoolean("showScanStats", showScanStats);
		preferences.putBoolean("askScanConfirmation", askScanConfirmation);

		preferences.putBoolean("windowMaximized", isMainWindowMaximized);
		if (!isMainWindowMaximized) {
			preferences.putInt("windowLeft", mainWindowBounds.x);
			preferences.putInt("windowTop", mainWindowBounds.y);
			preferences.putInt("windowWidth", mainWindowBounds.width);
			preferences.putInt("windowHeight", mainWindowBounds.height);
		}
		
		preferences.putInt("detailsWidth", detailsWindowSize.x);
		preferences.putInt("detailsHeight", detailsWindowSize.y);
	}

	public Rectangle getMainWindowBounds() {
		return mainWindowBounds;
	}

	/**
	 * @param bounds
	 * @param isMaximized 
	 */
	public void setMainWindowBounds(Rectangle bounds, boolean isMaximized) {
		if (!isMaximized) {
			mainWindowBounds = bounds;
		}
		isMainWindowMaximized = isMaximized;
	}

	/**
	 * @param fetcherName
	 * @return column width corresponding to a fetcher
	 */
	public int getColumnWidth(String fetcherName) {
		return preferences.getInt("columnWidth." + fetcherName, 90);
	}
	
	/**
	 * Persist the width of a column corresponding to a fetcher
	 * @param fetcherName
	 * @param width
	 */
	public void setColumnWidth(String fetcherName, int width) {
		preferences.putInt("columnWidth." + fetcherName, width);
	}

}