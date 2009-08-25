package it.test;

import java.net.MalformedURLException;

import org.jboss.remoting.InvokerLocator;

public class LeggiPeso {
	public static void main(String[] args) {
		InvokerLocator myLocator;
		try {
			myLocator = new InvokerLocator("socket://localhost:7777");
			org.jboss.remoting.Client myClient = new org.jboss.remoting.Client(
					myLocator, "leggiDispositivo");
			myClient.connect();
			for (int i = 0; i < 100; i++) {
				System.out.println("Risposta " + i + ":"
						+ myClient.invoke("COM1"));
				Thread.sleep(500);
			}

			myClient.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
