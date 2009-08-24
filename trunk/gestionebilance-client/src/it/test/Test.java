package it.test;

import java.net.MalformedURLException;

import org.jboss.remoting.InvokerLocator;

public class Test {
	public static void main(String[] args) {
		InvokerLocator myLocator;
		try {
			myLocator = new InvokerLocator("socket://192.168.0.201:7777");
			org.jboss.remoting.Client myClient = new org.jboss.remoting.Client(
					myLocator, "leggiDispositivo");
			myClient.connect();
			System.out.println("Risposta:" + myClient.invoke("COM1"));
			myClient.disconnect();
			myClient = new org.jboss.remoting.Client(myLocator,
					"restartBilance");
			myClient.connect();
			System.out.println("Risposta: " + myClient.invoke(""));

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
