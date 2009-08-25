package it.test;

import java.net.MalformedURLException;

import org.jboss.remoting.InvokerLocator;

public class RestartBilance {
	public static void main(String[] args) {
		InvokerLocator myLocator;
		try {
			myLocator = new InvokerLocator("socket://localhost:7777");
			org.jboss.remoting.Client myClient = new org.jboss.remoting.Client(
					myLocator, "leggiDispositivo");
			myClient.connect();
			myClient = new org.jboss.remoting.Client(myLocator,
					"restartBilance");
			myClient.connect();
			System.out.println("Risposta: " + myClient.invoke(""));
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
