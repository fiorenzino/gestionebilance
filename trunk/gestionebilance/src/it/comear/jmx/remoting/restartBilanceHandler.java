package it.comear.jmx.remoting;

import it.comear.jmx.utils.JNDIUtils;

import javax.management.MBeanServer;

import org.jboss.remoting.InvocationRequest;
import org.jboss.remoting.ServerInvocationHandler;
import org.jboss.remoting.ServerInvoker;
import org.jboss.remoting.callback.InvokerCallbackHandler;

/**
 * 
 * Restituisce una versione UPPER CASE della stringa passata in ingresso
 * 
 * In callback, invia il tempo di elaborazione richiesto dal metodo di invoke
 * 
 * @author ste
 * 
 */
public class restartBilanceHandler implements ServerInvocationHandler {

	InvokerCallbackHandler callBackHandler = null;

	public void addListener(InvokerCallbackHandler arg0) {
		callBackHandler = arg0;
	}

	public Object invoke(InvocationRequest arg0) throws Throwable {
		long startTime = System.nanoTime();
		try {
			JNDIUtils.getComManager().stopSystem();
			JNDIUtils.getComManager().startSystem();
			return "OK";
		} catch (Exception e) {
			return "ERROR";
		} finally {
			if (callBackHandler != null) {
				long elapsedTime = System.nanoTime() - startTime;
				System.out.println("Elaborazione lato server = " + elapsedTime
						+ " nanosec");
			}
		}
	}

	public void removeListener(InvokerCallbackHandler arg0) {
		// TODO Auto-generated method stub

	}

	public void setInvoker(ServerInvoker arg0) {
		// TODO Auto-generated method stub

	}

	public void setMBeanServer(MBeanServer arg0) {
		// TODO Auto-generated method stub

	}

}
