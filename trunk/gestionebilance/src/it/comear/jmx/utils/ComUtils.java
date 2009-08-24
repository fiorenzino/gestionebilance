package it.comear.jmx.utils;

public class ComUtils {
	public static String getPeso(String peso) {
		if (peso != null)
			return peso.substring(1, 10) + " " + peso.substring(21, 23);
		return "";
	}
}
