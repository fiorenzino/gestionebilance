package it.comear.jmx.utils;

import it.comear.jmx.ComManagerLocal;

import javax.naming.InitialContext;

public class JNDIUtils {

	private static ComManagerLocal ComManager = null;

	public static ComManagerLocal getComManager() {
		if (ComManager != null) {
			return ComManager;
		} else {
			try {
				InitialContext ctx = new InitialContext();
				return (ComManagerLocal) ctx
						.lookup("gestione-bilance/ComManagerBean/local");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}
}
