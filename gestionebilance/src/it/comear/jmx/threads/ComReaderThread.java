package it.comear.jmx.threads;

import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;
import java.util.TooManyListenersException;

import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;

import org.jboss.logging.Logger;

public class ComReaderThread implements Runnable, SerialPortEventListener {

	Logger log = Logger.getLogger("it.comear.jmx.threads");
	StringBuffer tutto = new StringBuffer();
	Stack<String> pila;
	InputStream inputStream;
	Thread readThread;
	boolean filter;
	SerialPort serialPort = null;

	public ComReaderThread() {
		pila = new Stack<String>();
	}

	public ComReaderThread(SerialPort serialPort, boolean filter) {
		pila = new Stack<String>();
		try {
			this.serialPort = serialPort;
			setInputStream(this.serialPort.getInputStream());
			setFilter(false);
			this.serialPort.addEventListener(this);
			this.serialPort.notifyOnDataAvailable(true);
			this.serialPort.setSerialPortParams(4800, SerialPort.DATABITS_7,
					SerialPort.STOPBITS_2, SerialPort.PARITY_EVEN);
			this.serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
			log.info("START");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TooManyListenersException e) {
			e.printStackTrace();
		} catch (UnsupportedCommOperationException e) {

			e.printStackTrace();
		}
	}

	public void stop() {

		try {
			if (this.inputStream != null)
				this.inputStream.close();
			if (this.serialPort != null)
				this.serialPort.close();
			this.serialPort = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getPorta() {
		if (serialPort != null) {
			return serialPort.getName();
		}
		return "";
	}

	public void run() {
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
		}
	}

	public void serialEvent(SerialPortEvent event) {
		switch (event.getEventType()) {
		case SerialPortEvent.BI:
		case SerialPortEvent.OE:
		case SerialPortEvent.FE:
		case SerialPortEvent.PE:
		case SerialPortEvent.CD:
		case SerialPortEvent.CTS:
		case SerialPortEvent.DSR:
		case SerialPortEvent.RI:
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
			break;
		case SerialPortEvent.DATA_AVAILABLE:
			// log.info("DATA_AVAILABLE");
			byte[] readBuffer = new byte[30];

			try {
				while (inputStream.available() > 0) {
					int numBytes = inputStream.read(readBuffer);
				}
				String cara = new String(readBuffer).trim();

				if ((getTutto().toString().length() == 23)
						&& (cara.contains("301") || cara.contains("101"))) {
					// System.out.println("size: "
					// + getTutto().toString().length());
					getTutto().append(cara);
					// System.out.println("size tot: "
					// + getTutto().toString().length());
					if (!isFilter()) {
						getPila().add(getTutto().toString());
					} else {
						if (cara.contains("301"))
							getPila().add(getTutto().toString());
					}
					// System.out.println(getTutto().toString());

					resetTutto();
				} else {
					// System.out.println("size parz: "
					// + getTutto().toString().length());
					getTutto().append(cara);
					// SSystem.out.print(cara);
				}

			} catch (IOException e) {
			}
			break;
		}
	}

	public Stack<String> getPila() {
		return pila;
	}

	public void setPila(Stack<String> pila) {
		this.pila = pila;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public StringBuffer getTutto() {
		return tutto;
	}

	public void setTutto(StringBuffer tutto) {
		this.tutto = tutto;
	}

	public void resetTutto() {
		this.tutto = new StringBuffer();
	}

	public boolean isFilter() {
		return filter;
	}

	public void setFilter(boolean filter) {
		this.filter = filter;
	}

	public SerialPort getSerialPort() {
		return serialPort;
	}

	public void setSerialPort(SerialPort serialPort) {
		this.serialPort = serialPort;
	}
}
