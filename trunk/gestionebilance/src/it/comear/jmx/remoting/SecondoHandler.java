package it.comear.jmx.remoting;

import javax.management.MBeanServer;

import org.jboss.remoting.InvocationRequest;
import org.jboss.remoting.ServerInvocationHandler;
import org.jboss.remoting.ServerInvoker;
import org.jboss.remoting.callback.Callback;
import org.jboss.remoting.callback.InvokerCallbackHandler;

/**
 * 
 * Restituisce un array di stringhe di lunghezza pari al num di caratteri 
 * della stringa passata in ingresso; la entry i-esima dell'array contiene 
 * 10 volte il carattere i-esimo della stringa in ingresso
 * 
 * In callback, invia il tempo di elaborazione richiesto dal metodo di invoke
 * 
 * @author ste
 *
 */
public class SecondoHandler 
implements ServerInvocationHandler {

	InvokerCallbackHandler callBackHandler = null;
	
	public void addListener(InvokerCallbackHandler arg0) {
		callBackHandler = arg0;
	}

	public Object invoke(InvocationRequest arg0) throws Throwable {
		long startTime = System.nanoTime();
		try	{
			if (arg0.getParameter() == null)
				return new String[]{ "Nessun parametro"};
			
			String [] out = new String[arg0.getParameter().toString().length()];
			StringBuffer buf = new StringBuffer();
			for (int i = 0; i < arg0.getParameter().toString().length(); i++){
				for (int k = 0; k<10 ;k++){
					buf.append(arg0.getParameter().toString().charAt(i));
				}
				out[i]= buf.toString();
				buf = new StringBuffer();
			}
	 
			return out;
		}
		catch (Exception e) {
			return e.toString();
		}
		finally{
			if (callBackHandler != null){
				long elapsedTime = System.nanoTime() - startTime;
				callBackHandler.handleCallback(
						new Callback("Elaborazione lato server = "+ elapsedTime +" nanosec")
					);
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
